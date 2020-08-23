package mrs.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;

@Component
public class MeetingRoomDao {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public MeetingRoom getMeetingRoomByRoomId(Integer roomId) {

		MapSqlParameterSource map = new MapSqlParameterSource()
				.addValue("roomId", roomId);

		String sql = ""
				+ "SELECT me.room_id meRoomID, me.room_name meRoomName, res.room_id resRoomId, res.reserved_date resResrevedDate "
				+ "FROM meeting_room me LEFT JOIN reservable_room res ON me.room_id = res.room_id WHERE me.room_id = :roomId";
		List<MeetingRoom> list = jdbcTemplate.query(sql, map,
				new MeetingRoomResultSetExtractor());
		return list.get(0);
	}

	public List<MeetingRoom> getMeetingRoom() {

		String sql = ""
				+ "SELECT me.room_id meRoomID, me.room_name meRoomName, res.room_id resRoomId, res.reserved_date resResrevedDate "
				+ "FROM meeting_room me LEFT JOIN reservable_room res ON me.room_id = res.room_id";
		return jdbcTemplate.query(sql, new MeetingRoomResultSetExtractor());
	}

	public static class MeetingRoomResultSetExtractor
			implements ResultSetExtractor<List<MeetingRoom>> {

		@Override
		public List<MeetingRoom> extractData(ResultSet rs)
				throws SQLException, DataAccessException {

			Map<Integer, MeetingRoom> map = new HashMap<>();
			MeetingRoom meetingRoom = null;

			while (rs.next()) {
				Integer roomId = rs.getInt("meRoomID");
				meetingRoom = map.get(roomId);

				if (meetingRoom == null) {
					meetingRoom = new MeetingRoom();
					meetingRoom.setRoomId(roomId);
					meetingRoom.setRoomName(rs.getString("meRoomName"));
					map.put(roomId, meetingRoom);
				}

				Integer resRoomId = rs.getInt("resRoomId");
				if (resRoomId != null) {
					ReservableRoom reservableRoom = new ReservableRoom();
					ReservableRoomId id = new ReservableRoomId();
					id.setRoomId(resRoomId);
					id.setReservedDate(
							rs.getDate("resResrevedDate").toLocalDate());
					reservableRoom.setReservableRoomId(id);
					if (meetingRoom.getReservableRoom() == null) {
						meetingRoom.setReservableRoom(new ArrayList<>());
					}
					meetingRoom.getReservableRoom().add(reservableRoom);
				}
			}

			if (map.size() == 0) {
				throw new EmptyResultDataAccessException(1);
			}

			return new ArrayList<MeetingRoom>(map.values());
		}

	}

}

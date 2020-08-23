package mrs.domain.service.room;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrs.domain.dao.MeetingRoomDao;
import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.domain.repository.room.MeetingRoomRepository;
import mrs.domain.repository.room.ReservableRoomRepository;

@Service
@Transactional
public class RoomService {

	@Autowired
	ReservableRoomRepository reservableRoomRepository;

	@Autowired
	MeetingRoomRepository meetingRoomRepository;

	@Autowired
	MeetingRoomDao meetingRoomDao;

	private final Logger log = LoggerFactory.getLogger(RoomService.class);

	public MeetingRoom findMeetingRoom(Integer roomId) {

		log.info("■■DAO getMeetingRoomByRoomId(roomId: " + roomId + "): "
				+ meetingRoomDao.getMeetingRoomByRoomId(roomId));

		log.info("■■DAO getMeetingRoom: " + meetingRoomDao.getMeetingRoom());

		return meetingRoomRepository.findById(roomId).get();
	}

	public List<ReservableRoom> findReservableRooms(LocalDate date) {
		return reservableRoomRepository
				.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(
						date);
	}
}

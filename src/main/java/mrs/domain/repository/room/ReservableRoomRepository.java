package mrs.domain.repository.room;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;

public interface ReservableRoomRepository
		extends JpaRepository<ReservableRoom, ReservableRoomId> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	ReservableRoom findOneForUpdateByReservableRoomId(
			ReservableRoomId reservableRoomId);

	@Query(value = "SELECT * FROM reservable_room res LEFT JOIN meeting_room me ON res.room_id = me.room_id "
			+ "WHERE res.reserved_date = :reservedDate ORDER BY reserved_date, room_id ASC", nativeQuery = true)
//	@Query("SELECT DISTINCT x FROM ReservableRoom x LEFT JOIN FETCH x.meetingRoom "
//			+ "WHERE x.reservableRoomId.reservedDate = :reservedDate ORDER BY x.reservableRoomId ASC")
	List<ReservableRoom> findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(
			LocalDate reservedDate);

}

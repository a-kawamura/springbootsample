package mrs.app.room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mrs.domain.model.ReservableRoom;
import mrs.domain.service.room.RoomService;

@RestController
@RequestMapping("restRooms")
public class RestRoomsController {

	@Autowired
	RoomService roomService;

	@RequestMapping(method = RequestMethod.GET)
	List<ReservableRoom> listRooms(Model model) {
		throw new RuntimeException("XXX0002");
//		LocalDate today = LocalDate.now();
//		return roomService.findReservableRooms(today);
	}

	@RequestMapping(path = "{date}", method = RequestMethod.GET)
	List<ReservableRoom> listRooms(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
			Model model) {
		return roomService.findReservableRooms(date);
	}
}
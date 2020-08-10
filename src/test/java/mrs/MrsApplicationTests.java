package mrs;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import mrs.app.room.RestRoomsController;
import mrs.app.room.RoomsController;
import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import mrs.domain.service.room.RoomService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MrsApplication.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = MrsApplication.class)
@AutoConfigureMockMvc
public class MrsApplicationTests {

	private final Logger log = LoggerFactory
			.getLogger(MrsApplicationTests.class);

//	@LocalServerPort
//	private int port;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	RoomsController roomsController;

	@InjectMocks
	RestRoomsController restRoomsController;

	@MockBean
	RoomService roomService;

	@Autowired
	ObjectMapper objectMapper;

//	private MockMvc mockMvc;
//
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//				.apply(springSecurity()).build();
	}

	@Test
	public void mockMvc() throws Exception {
		mockMvc.perform(get("/loginForm")).andExpect(status().isOk());
	}

	@Test
	public void login() throws Exception {
		mockMvc.perform(formLogin().user("aaaa").password("demo"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/rooms"))
				.andExpect(authenticated().withRoles("USER"));
	}

	@Test
	public void rooms() throws Exception {

		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setRoomId(1);
		meetingRoom.setRoomName("ROOM 1");
		ReservableRoom reservableRoom = new ReservableRoom();
		reservableRoom.setMeetingRoom(meetingRoom);
		ReservableRoomId reservableRoomId = new ReservableRoomId();
		reservableRoomId.setReservedDate(LocalDate.now());
		reservableRoomId.setRoomId(1);
		reservableRoom.setReservableRoomId(reservableRoomId);

		List<ReservableRoom> list = new ArrayList<>();
		list.add(reservableRoom);

		Mockito.when(roomService.findReservableRooms(any())).thenReturn(list);

		// ↓ Stand alone MockMvc to avoid spring security
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(roomsController)
				.build();
		mockMvc.perform(get("/rooms")).andExpect(status().is(200))
				.andExpect(forwardedUrl("room/listRooms")).andDo(print());
	}

	@Test
	public void rest() throws Exception {

		MeetingRoom meetingRoom = new MeetingRoom();
		meetingRoom.setRoomId(1);
		meetingRoom.setRoomName("ROOM 1");
		ReservableRoom reservableRoom = new ReservableRoom();
		reservableRoom.setMeetingRoom(meetingRoom);
		ReservableRoomId reservableRoomId = new ReservableRoomId();
		reservableRoomId.setReservedDate(LocalDate.now());
		reservableRoomId.setRoomId(1);
		reservableRoom.setReservableRoomId(reservableRoomId);

		List<ReservableRoom> list = new ArrayList<>();
		list.add(reservableRoom);

		Mockito.when(roomService.findReservableRooms(any())).thenReturn(list);
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(restRoomsController)
				.build();
		mockMvc.perform(get("/restRooms")).andExpect(status().is(200))
				.andExpect(
						content().string(objectMapper.writeValueAsString(list)))
				.andDo(print());

		// ↓This must be error because of ssl self cert.
//		TestRestTemplate testRestTemplate = new TestRestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//		ResponseEntity<String> response = testRestTemplate.exchange(
//				"http://localhost:8080/springbootsample/loginForm",
//				HttpMethod.GET, entity, String.class);
//
//		log.info("response: " + response);

	}

	@Test
	public void contextLoads() {
	}
}

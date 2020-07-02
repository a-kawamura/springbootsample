package mrs.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ReservableRoomId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private LocalDate reservedDate;

	public ReservableRoomId(Integer roomId, LocalDate reservedDate) {
		this.roomId = roomId;
		this.reservedDate = reservedDate;
	}

	public ReservableRoomId() {
	}
}

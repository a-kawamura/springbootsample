package mrs.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Embeddable
public class ReservableRoomId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservedDate;

	public ReservableRoomId(Integer roomId, LocalDate reservedDate) {
		this.roomId = roomId;
		this.reservedDate = reservedDate;
	}

	public ReservableRoomId() {
	}
}

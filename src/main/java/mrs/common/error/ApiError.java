package mrs.common.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class ApiError implements Serializable {

	private static final long serialVersionUID = 1L;

	private String messaege;

	public ApiError(String message) {
		this.messaege = message;
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<Detail> details = new ArrayList<>();

	public void addDetail(String target, String message) {
		details.add(new Detail(target, message));
	}

	public List<Detail> getDetails() {
		return details;
	}

	@Data
	private static final class Detail implements Serializable {
		private static final long serialVersionUID = 1L;

		private final String target;
		private final String message;

		private Detail(String target, String message) {
			this.target = target;
			this.message = message;
		}

		public String getTarget() {
			return this.target;
		}

		public String getMessage() {
			return this.message;
		}
	}

}

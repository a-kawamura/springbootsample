package mrs.common.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String handleException(HttpServletRequest request,
			HttpServletResponse response, Exception e) {
		request.setAttribute("exception", e);

		return "forward:/error";
	}
}

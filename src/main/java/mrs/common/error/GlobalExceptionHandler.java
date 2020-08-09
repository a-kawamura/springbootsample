package mrs.common.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	public static final String APPLICATION_EXCEPTION_KEY = "Exception";

	@ExceptionHandler(Exception.class)
	public String handleException(HttpServletRequest request,
			HttpServletResponse response, Exception e) {
		request.setAttribute(APPLICATION_EXCEPTION_KEY, e);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return "forward:/error";
	}
}

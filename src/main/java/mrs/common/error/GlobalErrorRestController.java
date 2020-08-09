package mrs.common.error;

import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalErrorRestController {

	@Autowired
	ExceptionMessageMap exceptionMessageMap;

	@RequestMapping(path = "/error")
	public ApiError handleErrorForRest(HttpServletRequest request,
			Model model) {

		String message;
		Exception ex = (Exception) request
				.getAttribute(GlobalExceptionHandler.APPLICATION_EXCEPTION_KEY);

		if (ex != null) {
			return new ApiError(exceptionMessageMap.getMessage(ex));
		}
		ex = (Exception) request
				.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		if (ex != null) {
			return new ApiError(ex.getMessage());
		}

		Integer statusCode = (Integer) request
				.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (Arrays.asList(HttpStatus.values()).stream()
				.anyMatch(status -> status.value() == statusCode)) {
			message = HttpStatus.valueOf(statusCode).getReasonPhrase();
		} else {
			message = "Custom error (" + statusCode + ") is occured.";
		}
		return new ApiError(message);
	}

}

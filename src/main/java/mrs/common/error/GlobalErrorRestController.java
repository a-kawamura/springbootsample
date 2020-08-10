package mrs.common.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalErrorRestController {

	@Autowired
	ExceptionMessageMap exceptionMessageMap;

	@Autowired
	HttpStatusCodeMessageMap httpStatusCodeMessgeMap;

	private final Logger log = LoggerFactory
			.getLogger(GlobalErrorRestController.class);

	@RequestMapping(path = "/error")
	public ApiError handleErrorForRest(HttpServletRequest request,
			Model model) {

		String message;
		Exception ex = (Exception) request
				.getAttribute(GlobalExceptionHandler.APPLICATION_EXCEPTION_KEY);
		if (ex == null) {
			ex = (Exception) request
					.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		}
		if (ex != null) {
			message = exceptionMessageMap.getMessage(ex);
			log.error(message, ex);
			return new ApiError(message);
		}

		Integer statusCode = (Integer) request
				.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		message = httpStatusCodeMessgeMap.getMessage(statusCode);
		return new ApiError(message);
	}

}

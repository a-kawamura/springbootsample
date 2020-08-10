package mrs.common.error;

import static mrs.common.Constants.ERROR_MESSAGE_KEY;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController extends AbstractErrorController {

	@Autowired
	ExceptionMessageMap exceptionMessageMap;

	@Autowired
	HttpStatusCodeMessageMap httpStatusMessageMap;

	private static final String ERROR_PAGE_PATH = "/error/errorPage";

	private final Logger log = LoggerFactory
			.getLogger(GlobalErrorController.class);

	public GlobalErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping(path = "/error", produces = MediaType.TEXT_HTML_VALUE)
	public String handleError(HttpServletRequest request, Model model) {

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
			model.addAttribute(ERROR_MESSAGE_KEY, message);
			return ERROR_PAGE_PATH;
		}

		Integer statusCode = (Integer) request
				.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		log.error("Http Status Code: " + statusCode);
		model.addAttribute(ERROR_MESSAGE_KEY,
				httpStatusMessageMap.getMessage(statusCode));
		return ERROR_PAGE_PATH;
	}
}

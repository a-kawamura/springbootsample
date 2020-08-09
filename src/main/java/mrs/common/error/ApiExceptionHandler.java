package mrs.common.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	MessageSource messageSource;

	@Autowired
	ExceptionMessageMap exceptionMessageMap;

	private ApiError createApiError(Exception ex) {
		return new ApiError(exceptionMessageMap.getMessage(ex));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = createApiError(ex);
		ex.getBindingResult().getGlobalErrors().stream().forEach(e -> apiError
				.addDetail(e.getObjectName(), getMessage(e, request)));
		ex.getBindingResult().getFieldErrors().stream().forEach(
				e -> apiError.addDetail(e.getField(), getMessage(e, request)));
		return super.handleExceptionInternal(ex, apiError, headers, status,
				request);
	}

	private String getMessage(MessageSourceResolvable resolvable,
			WebRequest request) {
		return messageSource.getMessage(resolvable, request.getLocale());
	}

}

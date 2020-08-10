package mrs.app.login;

import static mrs.common.Constants.AJAX_REQUEST_HEADER_NAME;
import static mrs.common.Constants.AJAX_REQUEST_HEADER_VALUE;
import static mrs.common.Constants.ERROR_MESSAGE_KEY;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import mrs.common.error.ApiError;
import mrs.common.error.ExceptionMessageMap;
import mrs.common.error.HttpStatusCodeMessageMap;

public class CustomAuthenticationFailureHandler
		implements AuthenticationFailureHandler {

	private final String failureUrl;

	@Autowired
	private ExceptionMessageMap exceptionMessageMap;
	@Autowired
	private HttpStatusCodeMessageMap httpStatusCodeMessageMap;

	@Autowired
	private ObjectMapper objectMapper;

//	private ObjectMapper objectMapper = new ObjectMapper();

	public CustomAuthenticationFailureHandler() {
		this.failureUrl = "/loginForm?error=true";
	}

	public CustomAuthenticationFailureHandler(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

//		Map<String, Object> data = new HashMap<>();
//		data.put("exception", exception.getMessage());
//
//		response.getOutputStream()
//				.println(objectMapper.writeValueAsString(data));

		if (AJAX_REQUEST_HEADER_VALUE
				.equals(request.getHeader(AJAX_REQUEST_HEADER_NAME))) {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), objectMapper
					.writeValueAsString(new ApiError(httpStatusCodeMessageMap
							.getMessage(HttpStatus.UNAUTHORIZED.value()))));
			return;
		}

		request.setAttribute(ERROR_MESSAGE_KEY,
				exceptionMessageMap.getMessage(exception));
		request.getRequestDispatcher(failureUrl).forward(request, response);
	}

}

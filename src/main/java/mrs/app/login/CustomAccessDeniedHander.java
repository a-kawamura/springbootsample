package mrs.app.login;

import static mrs.common.Constants.AJAX_REQUEST_HEADER_NAME;
import static mrs.common.Constants.AJAX_REQUEST_HEADER_VALUE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import com.fasterxml.jackson.databind.ObjectMapper;

import mrs.common.error.ApiError;

public class CustomAccessDeniedHander extends AccessDeniedHandlerImpl {

	@Autowired
	AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException)
			throws IOException, ServletException {

		if (accessDeniedException instanceof MissingCsrfTokenException) {
			authenticationEntryPoint.commence(request, response, null);
		} else {
			if (AJAX_REQUEST_HEADER_VALUE
					.equals(request.getHeader(AJAX_REQUEST_HEADER_NAME))) {
				response.sendError(HttpStatus.FORBIDDEN.value(),
						objectMapper.writeValueAsString(new ApiError(
								HttpStatus.FORBIDDEN.getReasonPhrase())));
			} else {
				super.handle(request, response, accessDeniedException);
			}
		}

	}
}

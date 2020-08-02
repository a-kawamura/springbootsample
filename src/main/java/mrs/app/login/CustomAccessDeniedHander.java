package mrs.app.login;

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

public class CustomAccessDeniedHander extends AccessDeniedHandlerImpl {

	@Autowired
	AuthenticationEntryPoint authenticationEntryPoint;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException)
			throws IOException, ServletException {

		if (accessDeniedException instanceof MissingCsrfTokenException) {
			authenticationEntryPoint.commence(request, response, null);
		} else {
			if ("XMLHttpRequest"
					.equals(request.getHeader("X-Requested-With"))) {
				response.sendError(HttpStatus.FORBIDDEN.value(),
						HttpStatus.FORBIDDEN.getReasonPhrase());
			} else {
				super.handle(request, response, accessDeniedException);
			}
		}

	}
}

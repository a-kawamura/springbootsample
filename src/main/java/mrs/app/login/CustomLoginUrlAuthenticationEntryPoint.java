package mrs.app.login;

import static mrs.common.Constants.AJAX_REQUEST_HEADER_NAME;
import static mrs.common.Constants.AJAX_REQUEST_HEADER_VALUE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import mrs.common.error.ApiError;

public class CustomLoginUrlAuthenticationEntryPoint
		extends LoginUrlAuthenticationEntryPoint {

	@Autowired
	private ObjectMapper objectMapper;

	public CustomLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	protected String buildRedirectUrlToLoginPage(HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authException) {

		String redirectUrl = super.buildRedirectUrlToLoginPage(request,
				response, authException);

		redirectUrl += redirectUrl.contains("?") ? "&" : "?";
		if (isSessionInvalid(request)) {
			redirectUrl += "timeout";
		} else {
			redirectUrl += "accessDenied";
		}
		return redirectUrl;

	}

	private boolean isSessionInvalid(HttpServletRequest request) {
		return request.getRequestedSessionId() != null
				&& !request.isRequestedSessionIdValid();
	}

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		if (AJAX_REQUEST_HEADER_VALUE
				.equals(request.getHeader(AJAX_REQUEST_HEADER_NAME))) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().print(objectMapper.writeValueAsString(
					new ApiError(HttpStatus.FORBIDDEN.getReasonPhrase())));
			return;
		}
		super.commence(request, response, authException);
	}
}

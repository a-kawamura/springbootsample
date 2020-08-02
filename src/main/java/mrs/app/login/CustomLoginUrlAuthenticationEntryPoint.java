package mrs.app.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class CustomLoginUrlAuthenticationEntryPoint
		extends LoginUrlAuthenticationEntryPoint {

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
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		super.commence(request, response, authException);
	}
}

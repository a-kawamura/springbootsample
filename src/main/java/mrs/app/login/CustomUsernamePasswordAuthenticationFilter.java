package mrs.app.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomUsernamePasswordAuthenticationFilter
		extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

		HttpSession session = ((HttpServletRequest) request).getSession(false);
		SecurityContext context = (SecurityContext) (session
				.getAttribute("SPRING_SECURITY_CONTEXT"));
		if (context != null) {
			// Already logined
			throw new AlreadyLoginedException("Already logined!");
		}
		return super.attemptAuthentication(request, response);
	}
}

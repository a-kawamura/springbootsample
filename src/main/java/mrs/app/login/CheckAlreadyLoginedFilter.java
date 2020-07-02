package mrs.app.login;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 同一ブラウザでの重複ログイン防止(同一ブラウザで重複ログインが可能であると、<br/>
 * 先着のものがログアウトした場合に、ログアウトに失敗し、その後sessionが残るようで、 再度ログインができなくなる)。
 * 
 * UsernamePasswordAuthenticationFilterより確実に先に実施したいため、WebSecurityConfigで
 * addBeforeFilterで登録。そのため、urlの指定ができず、doFilter内で/loginのみを対象としている。
 */
public class CheckAlreadyLoginedFilter extends GenericFilterBean {

	private final String loginProcessingUrl;

	public CheckAlreadyLoginedFilter(String loginProcessingUrl) {
		this.loginProcessingUrl = loginProcessingUrl;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		String requestedUrl = ((HttpServletRequest) request).getRequestURI();
		if (!requestedUrl.equals(loginProcessingUrl)) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = ((HttpServletRequest) request).getSession(false);
		SecurityContext context = (SecurityContext) (session
				.getAttribute("SPRING_SECURITY_CONTEXT"));

		if (context != null) {
			request.setAttribute("exception", "Already logined.");
			request.getRequestDispatcher("/loginForm?error=true")
					.forward(request, response);
//			throw new AuthenticationException("Already logined.");
			return;
		}
		chain.doFilter(request, response);

	}
}

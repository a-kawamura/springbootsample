package mrs.app.login;

import static mrs.config.WebSecurityConfig.FAILURE_URL;
import static mrs.config.WebSecurityConfig.LOGIN_PROCESS_URL;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

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

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		String requestedUrl = ((HttpServletRequest) request).getServletPath();
		if (!requestedUrl.equals(LOGIN_PROCESS_URL)) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = ((HttpServletRequest) request).getSession(false);
		if (session == null) {
			chain.doFilter(request, response);
			return;
		}

		try {
			SecurityContext context = (SecurityContext) (session
					.getAttribute(SPRING_SECURITY_CONTEXT_KEY));

			if (context != null) {
				request.setAttribute("exception", "Already logined.");
				request.getRequestDispatcher(FAILURE_URL).forward(request,
						response);
				return;

			}
		} catch (Exception e) {
			// ここせ発生したexceptionはAuthenticationFailureHandlerでも補足されず、/errorにも遷移せず、
			// login画面を再表示してしまうために、ここで/errorへforwardする。
			request.setAttribute("exception", e);
			request.getRequestDispatcher("/error").forward(request, response);
			return;
		}
		chain.doFilter(request, response);

	}
}

package mrs.app.login;

import static mrs.common.Constants.AJAX_REQUEST_HEADER_NAME;
import static mrs.common.Constants.AJAX_REQUEST_HEADER_VALUE;
import static mrs.config.WebSecurityConfig.FAILURE_URL;
import static mrs.config.WebSecurityConfig.LOGIN_PROCESS_URL;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import mrs.common.error.ApiError;
import mrs.common.error.GlobalExceptionHandler;

/**
 * 同一ブラウザでの重複ログイン防止(同一ブラウザで重複ログインが可能であると、<br/>
 * 先着のものがログアウトした場合に、ログアウトに失敗し、その後sessionが残るようで、 再度ログインができなくなる)。
 * 
 * UsernamePasswordAuthenticationFilterより確実に先に実施したいため、WebSecurityConfigで
 * addBeforeFilterで登録。そのため、urlの指定ができず、doFilter内で/loginのみを対象としている。
 */
@Component
public class CheckAlreadyLoginedFilter extends GenericFilterBean {

	@Autowired
	private ObjectMapper objectMapper;

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
				if (AJAX_REQUEST_HEADER_VALUE
						.equals(((HttpServletRequest) request)
								.getHeader(AJAX_REQUEST_HEADER_NAME))) {
					((HttpServletResponse) response).sendError(
							HttpStatus.FORBIDDEN.value(),
							objectMapper.writeValueAsString(new ApiError(
									HttpStatus.FORBIDDEN.getReasonPhrase())));
					return;
				}
				request.setAttribute("errorMessage", "XXX0001");
				request.getRequestDispatcher(FAILURE_URL).forward(request,
						response);
				return;
			}
		} catch (Exception e) {
			// ここせ発生したexceptionはAuthenticationFailureHandlerでも補足されず、/errorにも遷移せず、
			// login画面を再表示してしまうために、ここで/rrorへforwardする。
			request.setAttribute(
					GlobalExceptionHandler.APPLICATION_EXCEPTION_KEY, e);
			request.getRequestDispatcher("/error").forward(request, response);
			return;
		}

		session.setAttribute("hostName", request.getServerName());
		chain.doFilter(request, response);

	}
}

package mrs.app.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler
		implements AuthenticationFailureHandler {

	private final String failureUrl;

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

		request.setAttribute("exception", exception);
		request.getRequestDispatcher(failureUrl).forward(request, response);
	}

}

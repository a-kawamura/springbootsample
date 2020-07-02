package mrs.app.login;

import org.springframework.security.core.AuthenticationException;

public class AlreadyLoginedException extends AuthenticationException {
	public AlreadyLoginedException(String message) {
		super(message);
	}
}

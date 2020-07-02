package mrs.app.login;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(1)
public class SecurityWebApplicationInitializer
		extends AbstractSecurityWebApplicationInitializer {

//	public SecurityWebApplicationInitializer() {
//		super(WebSecurityConfig.class);
//	}

//	@Override
	/*
	 * 2重ログインチェックはこれなしでも実行可。ただし、logout時のsessionRegisterからのsessionの削除はこれがないとできない。
	 */
//	protected boolean enableHttpSessionEventPublisher() {
//		return true;
//	}

}

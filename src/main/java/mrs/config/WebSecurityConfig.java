package mrs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import mrs.app.login.CheckAlreadyLoginedFilter;
import mrs.app.login.CustomAuthenticationFailureHandler;
import mrs.domain.service.usesr.ReservationUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String LOGIN_PROCESS_URL = "/login";
	public static final String FAILURE_URL = "/loginForm?error=true";

	@Autowired
	ReservationUserDetailsService userDetailesService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/js/**", "/css/**", "/login", "/loginForm**")
				.permitAll().antMatchers("/**").authenticated().and()
				.formLogin().loginPage("/loginForm")
				.loginProcessingUrl(LOGIN_PROCESS_URL)
				.usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/rooms", true)
//				.failureUrl("/loginForm?error=true")
				.failureHandler(customAuthenticationFailureHandler(FAILURE_URL))
//						"/error"))
				.permitAll()
//				.and().logout().invalidateHttpSession(true)
//				.logoutUrl("/logout").logoutSuccessUrl("/loginForm").permitAll()
				.and().sessionManagement().maximumSessions(1)
				.maxSessionsPreventsLogin(true);

//		http.addFilterBefore(authenticationFilter(),
//				UsernamePasswordAuthenticationFilter.class);

		http.csrf().ignoringAntMatchers("/login", "/loginForm**");

//      failed↓
		http.addFilterBefore(new CheckAlreadyLoginedFilter(),
				UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailesService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler(
			String failureUrl) {
		return new CustomAuthenticationFailureHandler(failureUrl);
	}

	@Bean
	/*
	 * AbstractSecurityWebApplicationInitializer.enableHttpSessionEventPublisher
	 * ()でtrueを返しても、Listenerとして登録されないため、ここで登録
	 */
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

//	public CustomUsernamePasswordAuthenticationFilter authenticationFilter()
//			throws Exception {
//		CustomUsernamePasswordAuthenticationFilter authenticationFilter = new CustomUsernamePasswordAuthenticationFilter();
//		authenticationFilter.setRequiresAuthenticationRequestMatcher(
//				new AntPathRequestMatcher("/login", "POST"));
//		authenticationFilter
//				.setAuthenticationManager(authenticationManagerBean());
//		return authenticationFilter;
//	}
}

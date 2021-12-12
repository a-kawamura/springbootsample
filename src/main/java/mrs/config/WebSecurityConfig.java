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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.GenericFilterBean;

import mrs.app.login.CheckAlreadyLoginedFilter;
import mrs.app.login.CustomAccessDeniedHander;
import mrs.app.login.CustomAuthenticationFailureHandler;
import mrs.app.login.CustomLoginUrlAuthenticationEntryPoint;
import mrs.domain.service.usesr.ReservationUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_PROCESS_URL = "/login";
    public static final String FAILURE_URL = "/loginForm?error=true";
    public static final String ERROR_URL = "/error";

    @Autowired
    ReservationUserDetailsService userDetailesService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/js/**", "/css/**", "/loginForm**", "/logout",
                        "/error**", "/swagger-ui.html")
                .permitAll().antMatchers("/**").authenticated().and()
                .formLogin().loginPage("/loginForm")
                .loginProcessingUrl(LOGIN_PROCESS_URL)
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/rooms", true)
                .failureHandler(customAuthenticationFailureHandler(FAILURE_URL))
                .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
//				.and().logout().invalidateHttpSession(true)
//				.logoutUrl("/logout").logoutSuccessUrl("/loginForm").permitAll()
                .and().sessionManagement().maximumSessions(1)
                .maxSessionsPreventsLogin(true);

//		http.addFilterBefore(authenticationFilter(),
//				UsernamePasswordAuthenticationFilter.class);

//      failed↓
        http.addFilterBefore(checkAlreadyLoginedFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailesService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public GenericFilterBean checkAlreadyLoginedFilter() {
        return new CheckAlreadyLoginedFilter();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler(
            String failureUrl) {
        return new CustomAuthenticationFailureHandler(failureUrl);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHander handler = new CustomAccessDeniedHander();
        handler.setErrorPage("/loginForm?accessDenied");
        return handler;
    }

    @Bean
    /*
     * AbstractSecurityWebApplicationInitializer.enableHttpSessionEventPublisher
     * ()でtrueを返しても、Listenerとして登録されないため、ここで登録
     */
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomLoginUrlAuthenticationEntryPoint("/loginForm");
    }
}

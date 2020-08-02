package mrs.domain.service.usesr;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mrs.domain.model.User;
import mrs.domain.repository.user.UserRepository;

@Service
public class ReservationUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession httpSession;

	private final Logger log = LoggerFactory
			.getLogger(ReservationUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		log.info("session in service: " + httpSession.getAttribute("hostName"));

		User user = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(
						username + " is not found."));
		return new ReservationUserDetails(user);
	}
}

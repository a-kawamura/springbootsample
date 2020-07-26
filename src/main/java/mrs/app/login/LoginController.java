package mrs.app.login;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mrs.domain.service.usesr.ReservationUserDetails;

@Controller
public class LoginController {

	@RequestMapping("loginForm")
	String loginForm(
			@AuthenticationPrincipal ReservationUserDetails userDetails,
			Model model) {
		return "login/loginForm";
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	String defaultPath() {
		return "login/loginForm";
	}

}

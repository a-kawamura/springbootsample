package mrs.app.login;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import mrs.domain.service.usesr.ReservationUserDetails;

@Controller
public class LoginController {

	@RequestMapping("loginForm")
	String loginForm(
			@AuthenticationPrincipal ReservationUserDetails userDetails,
			Model model) {
//		if (userDetails != null) {
//			return "login/doubleLogin";
//		}
		return "login/loginForm";
	}
}

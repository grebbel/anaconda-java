package nl.runnable.lims.webapp.api;

import javax.inject.Inject;

import nl.runnable.lims.domain.User;
import nl.runnable.lims.security.UserHelper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {

	@Inject
	private UserHelper userHelper;

	@RequestMapping("/current")
	@ResponseBody
	public User showCurrent() {
		return userHelper.getCurrentUser();
	}

}

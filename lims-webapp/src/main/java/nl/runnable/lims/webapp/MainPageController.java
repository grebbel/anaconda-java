package nl.runnable.lims.webapp;

import javax.inject.Inject;

import nl.runnable.lims.domain.User;
import nl.runnable.lims.security.UserHelper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that handles the main page. This implementation populates the model with the current {@link User}.
 * 
 * @author Laurens Fridael
 * 
 */
@Controller
@RequestMapping("/")
public class MainPageController {

	@Inject
	private UserHelper userHelper;

	@RequestMapping
	public String show() {
		return "main";
	}

	@ModelAttribute("user")
	protected User getUserModelAttribute() {
		return userHelper.getCurrentUser();
	}

}

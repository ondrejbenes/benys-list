package cz.beny.list.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import cz.beny.list.CommonConstants;
import cz.beny.list.db.service.RegisteredUserService;

@Controller
public class HomeController {
	@Autowired
	private RegisteredUserService registeredUserService;

	/**
	 * If the user is logged in, he is redirected to the list of entries.
	 * Otherwise, he will be asked to log in using a Google account. If the
	 * account is not registered, the user is redirected back to login.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/")
	public RedirectView home() {
		String redirectUrl;

		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();

		registeredUserService.saveRegisteredUser("beny",
				"st39703@student.upce.cz");

		if (currentUser != null) {
			if (registeredUserService.isRegistered(currentUser.getEmail())) {
				redirectUrl = CommonConstants.URL_LIST;
			} else {
				redirectUrl = userService
					.createLogoutURL(CommonConstants.URL_HOME);
			}
		} else {
			redirectUrl = userService.createLoginURL(CommonConstants.URL_HOME);
		}

		return new RedirectView(redirectUrl);
	}
}

package cz.beny.list.logic;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import cz.beny.list.db.service.RegisteredUserService;
import cz.beny.list.db.service.bean.RegisteredUserServiceBean;

/**
 * Class used for managment of Logged-in User.
 * 
 */
public class LoginManager {
	private static RegisteredUserService registeredUserService = new RegisteredUserServiceBean();

	private static UserService userService = UserServiceFactory
			.getUserService();

	/**
	 * Return true, if a valid user is logged in. A valid user is registered and
	 * signed in.
	 * 
	 * @return
	 */
	public static Boolean isValidUserLoggedIn() {
		User currentUser = userService.getCurrentUser();
		if (currentUser == null)
			return Boolean.FALSE;
		return registeredUserService.isRegistered(currentUser.getEmail());
	}
}
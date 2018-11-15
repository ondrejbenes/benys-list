package cz.beny.list.db.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;
import cz.beny.list.model.RegisteredUser;

/**
 * Database Access Object for {@link RegisteredUser} class.
 * 
 * @author Admin
 * 
 */
public class RegisteredUserDAO {

	/**
	 * Returns a {@link RegisteredUser} with matching email.
	 * 
	 * @param email
	 * @return
	 */
	public RegisteredUser getRegisteredUserByEmail(String email) {
		return ofy().load().type(RegisteredUser.class).id(email).now();
	}

	/**
	 * Persist a {@link RegisteredUser}.
	 * @param nickname
	 * @param email
	 */
	public void saveRegisteredUser(String nickname, String email) {
		RegisteredUser newUser = new RegisteredUser(nickname, email);
		ofy().save().entity(newUser).now();
	}

}

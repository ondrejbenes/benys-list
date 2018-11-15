package cz.beny.list.db.service;

import cz.beny.list.db.dao.RegisteredUserDAO;
import cz.beny.list.model.RegisteredUser;

/**
 * Service layer component. Exposes {@link RegisteredUserDAO} methods.
 * 
 */
public interface RegisteredUserService {
	
	/**
	 * Return RegisteredUser with matching email.
	 * @param email
	 * @return
	 */
	RegisteredUser getRegisteredUserByEmail(final String email);
	
	/**
	 * Return true, if a User with passed email is registered.
	 * @param email
	 * @return
	 */
	Boolean isRegistered(final String email);
	
	/**
	 * Persist {@link RegisteredUser}
	 * @param nickname
	 * @param email
	 */
	void saveRegisteredUser(String nickname, String email);

}

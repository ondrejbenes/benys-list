package cz.beny.list.db.service.bean;

import cz.beny.list.db.dao.RegisteredUserDAO;
import cz.beny.list.db.service.RegisteredUserService;
import cz.beny.list.model.RegisteredUser;

/**
 * Implementation of {@link RegisteredUserService}.
 * 
 */
public class RegisteredUserServiceBean implements RegisteredUserService {
	
	private RegisteredUserDAO registeredUserDAO = new RegisteredUserDAO();

	@Override
	public RegisteredUser getRegisteredUserByEmail(final String email) {
		return registeredUserDAO.getRegisteredUserByEmail(email);
	}

	@Override
	public Boolean isRegistered(String email) {
		return registeredUserDAO.getRegisteredUserByEmail(email) != null;
	}

	@Override
	public void saveRegisteredUser(String nickname, String email) {
		registeredUserDAO.saveRegisteredUser(nickname, email);
	}
}
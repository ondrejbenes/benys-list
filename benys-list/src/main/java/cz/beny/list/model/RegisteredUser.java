package cz.beny.list.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Entity representing a User that is registered.
 *
 */
@Entity
public class RegisteredUser {
	
	@Id
	private String email;
	
	private String nickname;

	public RegisteredUser() {

	}

	public RegisteredUser(String nickname, String email) {
		this.nickname = nickname;
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

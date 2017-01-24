package coop.biantik.traductor.network.beans;

import java.io.Serializable;

public class ChangePassword implements Serializable {

	private static final long serialVersionUID = 4802305059790584920L;

	private String userId;
	private String password;
	private String newPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

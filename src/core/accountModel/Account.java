package core.accountModel;

import core.Entity;

public class Account extends Entity {
	private String email;
	private String password;
	
	public Account(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Account [email=" + email + ", password=" + password + "]";
	}
}
package core.account;

import core.Entity;
import core.employee.Employee;

public class Account extends Entity {
	private String email;
	private String password;
	private Employee employee;

	public Account(String email, String password, Employee employee) {
		super();
		this.email = email;
		this.password = password;
		this.employee = employee;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Account [email=" + email + ", password=" + password + ", employee=" + employee + "]";
	}
}
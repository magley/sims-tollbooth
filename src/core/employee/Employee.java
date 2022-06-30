package core.employee;

import core.Entity;

public class Employee extends Entity {
	public enum Role {
		UNKNOWN, ADMIN, MANAGER, TAG_SELLER, COLLECTOR, STATION_CHEIF
	}

	private String name;
	private String surname;
	private Role role;

	public Employee(String name, String surname, Role role) {
		super();
		this.name = name;
		this.surname = surname;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", surname=" + surname + ", role=" + role + "]";
	}
}

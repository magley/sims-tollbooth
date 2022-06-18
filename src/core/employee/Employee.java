package core.employee;

import core.Entity;
import core.account.Account;

public class Employee extends Entity {
	public enum Type {
		UNKNOWN, ADMIN, MANAGER, TAG_SELLER, COLLECTOR, STATION_CHEIF
	}
	
	private String name;
	private String surname;
	private Account account;
	private Type type;
	public Employee(String name, String surname, Account account, Type type) {
		super();
		this.name = name;
		this.surname = surname;
		this.account = account;
		this.type = type;
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
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", surname=" + surname + ", account=" + account + ", type=" + type + "]";
	}
}

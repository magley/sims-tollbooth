package core.employee;

import java.util.List;

import core.account.Account;

public interface IEmployeeService {
	public Employee add(Employee obj);
	public List<Employee> getAll();
	public void save();
}

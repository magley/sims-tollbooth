package core.employeeModel;

import java.util.List;

import core.accountModel.Account;

public interface IEmployeeService {
	public Employee add(Employee obj);
	public List<Employee> getAll();
	public Employee getByAccount(Account account);
	public void save();
}

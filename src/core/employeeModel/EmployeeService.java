package core.employeeModel;

import java.util.List;

import core.accountModel.Account;

public class EmployeeService implements IEmployeeService {
	private IEmployeeRepo repo;
	
	public EmployeeService(IEmployeeRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public Employee add(Employee obj) {
		return repo.add(obj);
	}
	
	@Override
	public List<Employee> getAll() {
		return repo.getAll();
	}
	
	@Override
	public void save() {
		repo.save();
	}

	@Override
	public Employee getByAccount(Account account) {
		return repo.getByAccount(account);
	}
}

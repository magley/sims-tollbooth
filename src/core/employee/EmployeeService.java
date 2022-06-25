package core.employee;

import core.common.ServiceAdapter;

public class EmployeeService extends ServiceAdapter<Employee> implements IEmployeeService {

	public EmployeeService(IEmployeeRepo repo) {
		super(repo);
	}
}

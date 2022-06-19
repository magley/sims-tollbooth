package core.employee;

import core.account.Account;
import core.employee.exception.NoEmployeeWithAccountException;

public class EmployeeController {
	private IEmployeeService service;
	
	public EmployeeController(IEmployeeService service) {
		this.service = service;
	}
	
	public Employee getByAccount(Account acc) throws NoEmployeeWithAccountException {
		Employee e = service.getByAccount(acc);
		
		if (e == null) {
			throw new NoEmployeeWithAccountException();
		}
		
		return e;
	}
}

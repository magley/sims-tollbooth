package core.employee;

import core.account.Account;
import core.common.IRepo;

public interface IEmployeeRepo extends IRepo<Employee> {
	Employee getByAccount(Account account);
}

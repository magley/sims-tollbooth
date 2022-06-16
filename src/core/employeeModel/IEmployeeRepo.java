package core.employeeModel;

import core.accountModel.Account;
import core.common.IRepo;

public interface IEmployeeRepo extends IRepo<Employee> {
	Employee getByAccount(Account account);
}

package core.employeeModel;

import java.util.List;

import core.accountModel.Account;
import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class EmployeeXMLRepo extends DefaultXMLRepo<Employee> implements IEmployeeRepo {
	public EmployeeXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<Employee> getAll() {
		return master.getEmployees();
	}

	@Override
	public Employee getByAccount(Account account) {
		if (account == null)
			return null;
		for (Employee e : getAll()) {
			if (e.getAccount().getId() == account.getId()) {
				return e;
			}
		}
		return null;
	}
}

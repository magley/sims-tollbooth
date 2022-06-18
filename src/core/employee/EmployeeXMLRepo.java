package core.employee;

import java.util.List;

import core.account.Account;
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
		return getAll().stream().filter(e -> e.getAccount().getId() == account.getId()).findFirst().orElse(null);
	}
}

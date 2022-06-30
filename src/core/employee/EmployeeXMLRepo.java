package core.employee;

import java.util.List;

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
}

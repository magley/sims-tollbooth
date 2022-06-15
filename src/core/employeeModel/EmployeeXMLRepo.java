package core.employeeModel;

import java.util.List;

import core.common.MasterXMLRepo;

public class EmployeeXMLRepo implements IEmployeeRepo {
	private MasterXMLRepo master;
	
	public EmployeeXMLRepo(MasterXMLRepo master) { 
		this.master = master;
	}
	
	@Override
	public List<Employee> getAll() {
		return master.getEmployees();
	}

	@Override
	public Employee get(int id) {
		for (Employee e : getAll()) {
			if (e.getId() == id)
				return e;
		}
		return null;
	}

	@Override
	public Employee add(Employee obj) {
		obj.setId(getAll().size());
		getAll().add(obj);
		return obj;
	}

	@Override
	public void remove(Employee obj) {
		getAll().remove(obj);
	}

	@Override
	public void save() {
		master.save();
	}
}

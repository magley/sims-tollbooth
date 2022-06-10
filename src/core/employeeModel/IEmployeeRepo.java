package core.employeeModel;

import java.util.List;

public interface IEmployeeRepo {
	public List<Employee> getAll();
	public Employee get(int id);
	public Employee add(Employee obj);
	public void remove(Employee obj);
	public void save();
}

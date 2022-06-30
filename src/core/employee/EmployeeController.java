package core.employee;

public class EmployeeController {
	private IEmployeeService service;

	public EmployeeController(IEmployeeService service) {
		this.service = service;
	}
}

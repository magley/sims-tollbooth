package core.employeeModel;

public class EmployeeController {
	private IEmployeeService service;
	
	public EmployeeController(IEmployeeService service) {
		this.service = service;
	}
	
	public void add(String name, String surname, String email, String password) throws Exception {
		if (name.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank()) {
			throw new Exception("Field cannot be blank!");
		}
		
		//Employee e = new Employee(name, surname, email, password);
		//service.add(e);
	}
}

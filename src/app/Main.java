package app;

import core.common.MasterXMLRepo;
import core.employeeModel.EmployeeController;
import core.employeeModel.EmployeeService;
import core.employeeModel.EmployeeXMLRepo;
import core.employeeModel.IEmployeeRepo;
import core.employeeModel.IEmployeeService;
import desktop.employeeModel.EmployeeCRUDView;

public class Main {
	public static void main(String[] args) {
		MasterXMLRepo masterRepo = new MasterXMLRepo("data", "database.xml");
		
		IEmployeeRepo employeeRepo = new EmployeeXMLRepo(masterRepo);
		IEmployeeService employeeService = new EmployeeService(employeeRepo);
		EmployeeController employeeController = new EmployeeController(employeeService);
		
		EmployeeCRUDView frm = new EmployeeCRUDView(employeeService, employeeController);
		frm.setSize(640, 480);
		frm.setVisible(true);

		employeeRepo.save();
	}
}

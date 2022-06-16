package app;

import core.accountModel.Account;
import core.accountModel.AccountXMLRepo;
import core.accountModel.IAccountRepo;
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
		IAccountRepo accountRepo = new AccountXMLRepo(masterRepo);

		if (!accountRepo.emailTaken("man01@sims.com")) {
			accountRepo.add(new Account("man01@sims.com", "man01"));
		}
		
		//IEmployeeService employeeService = new EmployeeService(employeeRepo);
		//EmployeeController employeeController = new EmployeeController(employeeService);
		
		//EmployeeCRUDView frm = new EmployeeCRUDView(employeeService, employeeController);
		//frm.setSize(640, 480);
		//frm.setVisible(true);

		masterRepo.save();
	}
}

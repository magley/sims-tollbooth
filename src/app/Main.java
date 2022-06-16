package app;

import core.accountModel.Account;
import core.accountModel.AccountService;
import core.accountModel.AccountXMLRepo;
import core.accountModel.IAccountRepo;
import core.accountModel.IAccountService;
import core.common.MasterXMLRepo;
import core.employeeModel.EmployeeXMLRepo;
import core.employeeModel.IEmployeeRepo;

public class Main {
	public static void main(String[] args) {
		MasterXMLRepo masterRepo = new MasterXMLRepo("data", "database.xml");
		
		IEmployeeRepo employeeRepo = new EmployeeXMLRepo(masterRepo);
		IAccountRepo accountRepo = new AccountXMLRepo(masterRepo);
		IAccountService accountService = new AccountService(accountRepo);
		
		System.out.println(accountService.getByCredentials("man01@sims.com", "man01"));
		System.out.println(accountService.getByCredentials("man02@sims.com", "man01"));
		System.out.println(accountService.getByCredentials("man01@sims.com", "man02"));
		System.out.println(accountService.getByCredentials("man02@sims.com", "man02"));

		if (!accountRepo.emailTaken("man01@sims.com")) {
			accountRepo.add(new Account("man01@sims.com", "man01"));
		}
		
		//IEmployeeService employeeService = new EmployeeService(employeeRepo);
		//EmployeeController employeeController = new EmployeeController(employeeService);
		
		//EmployeeCRUDView frm = new EmployeeCRUDView(employeeService, employeeController);
		//frm.setSize(640, 480);
		//frm.setVisible(true);

		masterRepo.save();
		
		System.out.println("Finished successfully");
	}
}

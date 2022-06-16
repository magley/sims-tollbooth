package app;

import core.accountModel.Account;
import core.accountModel.AccountService;
import core.accountModel.AccountXMLRepo;
import core.accountModel.IAccountRepo;
import core.accountModel.IAccountService;
import core.common.MasterXMLRepo;
import core.employeeModel.Employee;
import core.employeeModel.EmployeeService;
import core.employeeModel.EmployeeXMLRepo;
import core.employeeModel.IEmployeeRepo;
import core.employeeModel.IEmployeeService;

public class Main {
	public static void main(String[] args) {
		MasterXMLRepo masterRepo = new MasterXMLRepo("data", "database.xml");
		
		IAccountRepo accountRepo = new AccountXMLRepo(masterRepo);
		IAccountService accountService = new AccountService(accountRepo);
		
		IEmployeeRepo employeeRepo = new EmployeeXMLRepo(masterRepo);
		IEmployeeService employeeService = new EmployeeService(employeeRepo);

		System.out.println(employeeService.getByAccount(accountService.getByCredentials("user0@sims.com", "user0")));
		System.out.println(employeeService.getByAccount(accountService.getByCredentials("user0@TYPO.com", "user0")));
		System.out.println(employeeService.getByAccount(accountService.getByCredentials("user4@sims.com", "user4")));
		
		masterRepo.save();
		System.out.println("Finished successfully");
	}
	
	private static void generateAccountsAndEmployees(IAccountService accountService, IEmployeeService employeeService) {
		for (int i = 0; i < 5; i++) {
			String email = "user" + i + "@sims.com";
			String password = "user" + i;
			if (!accountService.emailTaken(email)) {
				accountService.add(new Account(email, password));
			}
		}
		
		for (int i = 0; i < 4; i++) {
			Employee e = new Employee("person" + i, "lastname" + i, accountService.get(i), Employee.Type.values()[1 + i]);
			employeeService.add(e);
		}
	}
}

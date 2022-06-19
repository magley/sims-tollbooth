package app;

import javax.swing.JFrame;

import core.account.Account;
import core.account.AccountService;
import core.account.AccountXMLRepo;
import core.account.IAccountRepo;
import core.account.IAccountService;
import core.common.MasterXMLRepo;
import core.employee.Employee;
import core.employee.EmployeeService;
import core.employee.EmployeeXMLRepo;
import core.employee.IEmployeeRepo;
import core.employee.IEmployeeService;
import desktop.employee.EmployeeLoginView;

public class Main {
	public static void main(String[] args) {
		MasterXMLRepo masterRepo = new MasterXMLRepo("data", "database.xml");
		
		IAccountRepo accountRepo = new AccountXMLRepo(masterRepo);
		IAccountService accountService = new AccountService(accountRepo);
		
		IEmployeeRepo employeeRepo = new EmployeeXMLRepo(masterRepo);
		IEmployeeService employeeService = new EmployeeService(employeeRepo);
		
		JFrame frame = new JFrame();
		frame.add(new EmployeeLoginView(null));
		frame.setSize(800, 600);  
        frame.setLocationRelativeTo(null);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setVisible(true);
        
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        		masterRepo.save();
        		System.out.println("Finished successfully");
            }
        });
   
		//System.out.println(employeeService.getByAccount(accountService.getByCredentials("user0@sims.com", "user0")));
		//System.out.println(employeeService.getByAccount(accountService.getByCredentials("user0@TYPO.com", "user0")));
		//System.out.println(employeeService.getByAccount(accountService.getByCredentials("user4@sims.com", "user4")));
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
			Employee e = new Employee("person" + i, "lastname" + i, accountService.get(i), Employee.Role.values()[1 + i]);
			employeeService.add(e);
		}
	}
}

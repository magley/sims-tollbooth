package app;

import javax.swing.JFrame;

import core.account.Account;
import core.account.AccountController;
import core.account.AccountService;
import core.account.AccountXMLRepo;
import core.account.IAccountRepo;
import core.account.IAccountService;
import core.common.MasterXMLRepo;
import core.employee.Employee;
import core.employee.Employee.Role;
import core.employee.EmployeeController;
import core.employee.EmployeeService;
import core.employee.EmployeeXMLRepo;
import core.employee.IEmployeeRepo;
import core.employee.IEmployeeService;
import desktop.employee.EmployeeLoginView;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
	public static void main(String[] args) {
		MasterXMLRepo masterRepo = new MasterXMLRepo("data", "database.xml");

		IAccountRepo accountRepo = new AccountXMLRepo(masterRepo);
		IAccountService accountService = new AccountService(accountRepo);

		IEmployeeRepo employeeRepo = new EmployeeXMLRepo(masterRepo);
		IEmployeeService employeeService = new EmployeeService(employeeRepo);
	/*	
		employeeRepo.add(new Employee("Jay", "Cobson", Role.ADMIN));
		employeeRepo.add(new Employee("John", "Yesgem", Role.MANAGER));
		employeeRepo.add(new Employee("Martin", "Chud", Role.COLLECTOR));
		employeeRepo.add(new Employee("Soot", "Cado", Role.STATION_CHEIF));
		employeeRepo.add(new Employee("Bob", "Jones", Role.TAG_SELLER));
		
		accountRepo.add(new Account("admin@sims.com", "123", employeeRepo.get(0)));
		accountRepo.add(new Account("manager@sims.com", "123", employeeRepo.get(1)));
		accountRepo.add(new Account("collector@sims.com", "123", employeeRepo.get(2)));
		accountRepo.add(new Account("station@sims.com", "123", employeeRepo.get(3)));
		accountRepo.add(new Account("tagseller@sims.com", "123", employeeRepo.get(4)));
*/
		AccountController accountController = new AccountController(accountService);
		EmployeeController employeeController = new EmployeeController(employeeService);

		JFrame frame = new EmployeeLoginView(accountController, employeeController);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				masterRepo.save();
				System.out.println("Finished successfully");
			}
		});
	}
}

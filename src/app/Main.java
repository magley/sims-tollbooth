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

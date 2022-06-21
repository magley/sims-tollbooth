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
import core.station.place.IPlaceRepo;
import core.station.place.IPlaceService;
import core.station.place.Place;
import core.station.place.PlaceService;
import core.station.place.PlaceXMLRepo;
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

		IPlaceRepo placeRepo = new PlaceXMLRepo(masterRepo);
		IPlaceService placeService = new PlaceService(placeRepo);
		
		AccountController accountController = new AccountController(accountService);
		EmployeeController employeeController = new EmployeeController(employeeService);

		boolean runApp = false;
		
		if (runApp) {
			startApp(masterRepo, accountController, employeeController);
		} else {
			for (Place place : placeService.getAll()) {
				System.out.println(place);
			}
			
			exitApp(masterRepo);
		}

	}
	
	private static void startApp(MasterXMLRepo masterRepo, AccountController accountController, EmployeeController employeeController) {
		JFrame frame = new EmployeeLoginView(accountController, employeeController);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				exitApp(masterRepo);
			}
		});
	}
	
	private static void exitApp(MasterXMLRepo masterRepo) {
		masterRepo.save();
		System.out.println("Finished successfully");
	}
}

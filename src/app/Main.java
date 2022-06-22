package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import core.account.AccountController;
import core.account.AccountService;
import core.account.AccountXMLRepo;
import core.account.IAccountRepo;
import core.account.IAccountService;
import core.common.MasterXMLRepo;
import core.employee.EmployeeController;
import core.employee.EmployeeService;
import core.employee.EmployeeXMLRepo;
import core.employee.IEmployeeRepo;
import core.employee.IEmployeeService;
import core.station.IStationRepo;
import core.station.IStationService;
import core.station.StationController;
import core.station.StationService;
import core.station.StationXMLRepo;
import core.station.location.ILocationRepo;
import core.station.location.ILocationService;
import core.station.location.LocationService;
import core.station.location.LocationXMLRepo;
import desktop.employee.EmployeeLoginView;
import desktop.station.StationDashboardView;

public class Main {
	public static void main(String[] args) {
		MasterXMLRepo masterRepo = new MasterXMLRepo("data", "database.xml");

		IAccountRepo accountRepo = new AccountXMLRepo(masterRepo);
		IAccountService accountService = new AccountService(accountRepo);

		IEmployeeRepo employeeRepo = new EmployeeXMLRepo(masterRepo);
		IEmployeeService employeeService = new EmployeeService(employeeRepo);

		ILocationRepo locationRepo = new LocationXMLRepo(masterRepo);
		ILocationService locationService = new LocationService(locationRepo);
		
		IStationRepo stationRepo = new StationXMLRepo(masterRepo);
		IStationService stationService = new StationService(stationRepo);
		
		AccountController accountController = new AccountController(accountService);
		EmployeeController employeeController = new EmployeeController(employeeService);
		StationController stationController = new StationController(stationService);

		boolean runApp = false;
		
		if (runApp) {
			startApp(masterRepo, accountController, employeeController);
		} else {	
			JFrame frame = new JFrame();
			frame.getContentPane().add(new StationDashboardView(stationService, locationService, stationController));
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

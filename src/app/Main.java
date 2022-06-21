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
import core.station.IStationRepo;
import core.station.IStationService;
import core.station.Station;
import core.station.Station.Type;
import core.station.location.ILocationRepo;
import core.station.location.ILocationService;
import core.station.location.Location;
import core.station.location.LocationService;
import core.station.location.LocationXMLRepo;
import core.station.StationService;
import core.station.StationXMLRepo;
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

		ILocationRepo locationRepo = new LocationXMLRepo(masterRepo);
		ILocationService locationService = new LocationService(locationRepo);
		
		IStationRepo stationRepo = new StationXMLRepo(masterRepo);
		IStationService stationService = new StationService(stationRepo);
		
		AccountController accountController = new AccountController(accountService);
		EmployeeController employeeController = new EmployeeController(employeeService);

		boolean runApp = false;
		
		if (runApp) {
			startApp(masterRepo, accountController, employeeController);
		} else {		
			for (Location place : locationService.getAll()) {
				System.out.println(place);
			}
			
			for (Station station : stationService.getAll()) {
				System.out.println(station);
			}
			
			// Add into system.
			Station s = new Station("TEST", Type.ENTER, locationService.get(0));
			stationService.add(s);

			// Update in memory.
			s.setType(Type.EXIT);
			s.setCode("TEST UPDATED");
			
			// Update in datastore.
			stationService.update(s);
			System.out.println(stationService.get(8));
			
			// Remove.
			stationService.remove(s);
			
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

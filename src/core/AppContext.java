package core;

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

public class AppContext {
	IAccountRepo accountRepo;
	IAccountService accountService;
	AccountController accountController;
	
	IEmployeeRepo employeeRepo;
	IEmployeeService employeeService;
	EmployeeController employeeController;
	
	ILocationRepo locationRepo;
	ILocationService locationService;
	
	IStationRepo stationRepo;
	IStationService stationService;
	StationController stationController;
	
	public AppContext(MasterXMLRepo masterRepo) {
		accountRepo = new AccountXMLRepo(masterRepo);
		accountService = new AccountService(accountRepo);

		employeeRepo = new EmployeeXMLRepo(masterRepo);
		employeeService = new EmployeeService(employeeRepo);

		locationRepo = new LocationXMLRepo(masterRepo);
		locationService = new LocationService(locationRepo);
		
		stationRepo = new StationXMLRepo(masterRepo);
		stationService = new StationService(stationRepo);
		
		accountController = new AccountController(accountService);
		employeeController = new EmployeeController(employeeService);
		stationController = new StationController(stationService);
	}

	public IAccountRepo getAccountRepo() {
		return accountRepo;
	}

	public void setAccountRepo(IAccountRepo accountRepo) {
		this.accountRepo = accountRepo;
	}

	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

	public AccountController getAccountController() {
		return accountController;
	}

	public void setAccountController(AccountController accountController) {
		this.accountController = accountController;
	}

	public IEmployeeRepo getEmployeeRepo() {
		return employeeRepo;
	}

	public void setEmployeeRepo(IEmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public EmployeeController getEmployeeController() {
		return employeeController;
	}

	public void setEmployeeController(EmployeeController employeeController) {
		this.employeeController = employeeController;
	}

	public ILocationRepo getLocationRepo() {
		return locationRepo;
	}

	public void setLocationRepo(ILocationRepo locationRepo) {
		this.locationRepo = locationRepo;
	}

	public ILocationService getLocationService() {
		return locationService;
	}

	public void setLocationService(ILocationService locationService) {
		this.locationService = locationService;
	}

	public IStationRepo getStationRepo() {
		return stationRepo;
	}

	public void setStationRepo(IStationRepo stationRepo) {
		this.stationRepo = stationRepo;
	}

	public IStationService getStationService() {
		return stationService;
	}

	public void setStationService(IStationService stationService) {
		this.stationService = stationService;
	}

	public StationController getStationController() {
		return stationController;
	}

	public void setStationController(StationController stationController) {
		this.stationController = stationController;
	}
}

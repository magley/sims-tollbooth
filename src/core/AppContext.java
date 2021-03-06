package core;

import core.account.AccountController;
import core.account.AccountService;
import core.account.AccountXMLRepo;
import core.account.IAccountRepo;
import core.account.IAccountService;
import core.booth.BoothController;
import core.booth.BoothService;
import core.booth.BoothXMLRepo;
import core.booth.IBoothRepo;
import core.booth.IBoothService;
import core.common.MasterXMLRepo;
import core.employee.EmployeeController;
import core.employee.EmployeeService;
import core.employee.EmployeeXMLRepo;
import core.employee.IEmployeeRepo;
import core.employee.IEmployeeService;
import core.malfunction.IMalfunctionService;
import core.malfunction.MalfunctionService;
import core.malfunction.MalfunctionXMLRepo;
import core.payment.IPaymentRepo;
import core.payment.IPaymentService;
import core.payment.PaymentController;
import core.payment.PaymentService;
import core.payment.PaymentXMLRepo;
import core.pricelist.IPricelistRepo;
import core.pricelist.IPricelistService;
import core.pricelist.PricelistController;
import core.pricelist.PricelistService;
import core.pricelist.PricelistXMLRepo;
import core.pricelist.entry.IPricelistEntryRepo;
import core.pricelist.entry.IPricelistEntryService;
import core.pricelist.entry.PricelistEntryController;
import core.pricelist.entry.PricelistEntryService;
import core.pricelist.entry.PricelistEntryXMLRepo;
import core.station.IStationRepo;
import core.station.IStationService;
import core.station.StationController;
import core.station.StationService;
import core.station.StationXMLRepo;
import core.station.location.ILocationRepo;
import core.station.location.ILocationService;
import core.station.location.LocationService;
import core.station.location.LocationXMLRepo;
import core.ticket.ITicketRepo;
import core.ticket.ITicketService;
import core.ticket.TicketController;
import core.ticket.TicketService;
import core.ticket.TicketXMLRepo;
import core.tollsegment.ITollSegmentService;
import core.tollsegment.TollSegmentService;
import core.tollsegment.TollSegmentXMLRepo;

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
	
	IBoothRepo boothRepo;
	IBoothService boothService;
	BoothController boothController;

	IPricelistEntryRepo pricelistEntryRepo;
	IPricelistEntryService pricelistEntryService;
	PricelistEntryController pricelistEntryController;

	IPricelistRepo pricelistRepo;
	IPricelistService pricelistService;
	PricelistController pricelistController;
	
	IMalfunctionService malfunctionService;
	
	ITicketRepo ticketRepo;
	ITicketService ticketService;
	TicketController ticketController;

	IPaymentRepo paymentRepo;
	IPaymentService paymentService;
	PaymentController paymentController;
	
	ITollSegmentService tollSegmentService;
	
	public AppContext(MasterXMLRepo masterRepo) {
		accountRepo = new AccountXMLRepo(masterRepo);
		accountService = new AccountService(accountRepo);
		accountController = new AccountController(accountService);

		employeeRepo = new EmployeeXMLRepo(masterRepo);
		employeeService = new EmployeeService(employeeRepo);
		employeeController = new EmployeeController(employeeService);

		locationRepo = new LocationXMLRepo(masterRepo);
		locationService = new LocationService(locationRepo);
		
		stationRepo = new StationXMLRepo(masterRepo);
		stationService = new StationService(stationRepo);
		stationController = new StationController(stationService);
		
		boothRepo = new BoothXMLRepo(masterRepo);
		boothService = new BoothService(boothRepo);
		boothController = new BoothController(boothService);

		pricelistEntryRepo = new PricelistEntryXMLRepo(masterRepo);
		pricelistEntryService = new PricelistEntryService(pricelistEntryRepo);
		pricelistEntryController = new PricelistEntryController(pricelistEntryService);

		pricelistRepo = new PricelistXMLRepo(masterRepo);
		pricelistService = new PricelistService(pricelistRepo);
		pricelistController = new PricelistController(pricelistService);
		
		malfunctionService = new MalfunctionService(new MalfunctionXMLRepo(masterRepo));
		
		ticketRepo = new TicketXMLRepo(masterRepo);
		ticketService = new TicketService(ticketRepo);
		ticketController = new TicketController(ticketService);
		
		paymentRepo = new PaymentXMLRepo(masterRepo);
		paymentService = new PaymentService(paymentRepo);
		paymentController = new PaymentController(paymentService);
		
		tollSegmentService = new TollSegmentService(new TollSegmentXMLRepo(masterRepo));
	}
	
	public IBoothRepo getBoothRepo() {
		return boothRepo;
	}

	public IBoothService getBoothService() {
		return boothService;
	}

	public IAccountRepo getAccountRepo() {
		return accountRepo;
	}

	public IAccountService getAccountService() {
		return accountService;
	}

	public AccountController getAccountController() {
		return accountController;
	}

	public IEmployeeRepo getEmployeeRepo() {
		return employeeRepo;
	}

	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	public EmployeeController getEmployeeController() {
		return employeeController;
	}

	public ILocationRepo getLocationRepo() {
		return locationRepo;
	}

	public ILocationService getLocationService() {
		return locationService;
	}

	public IStationRepo getStationRepo() {
		return stationRepo;
	}

	public IStationService getStationService() {
		return stationService;
	}
 
	public StationController getStationController() {
		return stationController;
	}
	
	public BoothController getBoothController() {
		return boothController;
	}

	public IPricelistEntryRepo getPricelistEntryRepo() {
		return pricelistEntryRepo;
	}

	public IPricelistEntryService getPricelistEntryService() {
		return pricelistEntryService;
	}

	public PricelistEntryController getPricelistEntryController() {
		return pricelistEntryController;
	}

	public IPricelistRepo getPricelistRepo() {
		return pricelistRepo;
	}

	public IPricelistService getPricelistService() {
		return pricelistService;
	}

	public PricelistController getPricelistController() {
		return pricelistController;
	}

	public IMalfunctionService getMalfunctionService() {
		return malfunctionService;
	}

	public ITicketRepo getTicketRepo() {
		return ticketRepo;
	}

	public ITicketService getTicketService() {
		return ticketService;
	}

	public TicketController getTicketController() {
		return ticketController;
	}

	public IPaymentRepo getPaymentRepo() {
		return paymentRepo;
	}

	public IPaymentService getPaymentService() {
		return paymentService;
	}

	public PaymentController getPaymentController() {
		return paymentController;
	}

	public ITollSegmentService getTollSegmentService() {
		return tollSegmentService;
	}
}

package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;

import core.AppContext;
import core.booth.Booth;
import core.booth.DeviceStatus.Status;
import core.booth.DeviceStatus.Type;
import core.common.MasterXMLRepo;
import core.pricelistEntry.PricelistEntry;
import core.station.Station;
import core.station.location.Location;
import desktop.employee.EmployeeLoginView;

public class Main {
	public static void main(String[] args) {
		MasterXMLRepo masterRepo = new MasterXMLRepo("data", "database.xml");
		AppContext ctx = new AppContext(masterRepo);
		boolean runApp = true;

		if (runApp) {
			startApp(masterRepo, ctx);
		} else {	
			
			Booth b = new Booth("123", null);
			ctx.getBoothService().add(b);
			ctx.getStationService().get(0).addTollBooth(b);

			// Print initial status.
			
			System.out.println(b.getDeviceStatus());
			
			// Let's pretend the semaphore sent an 'OK' signal
			
			b.setDeviceStatus(Type.SEMAPHORE, Status.WORKING);
			
			// Print new status
			
			System.out.println(b.getDeviceStatus());

			System.out.println("Finished");
			masterRepo.save();
		}
	}
	
	private static void startApp(MasterXMLRepo masterRepo, AppContext ctx) {
		JFrame frame = new EmployeeLoginView(ctx);
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
	
	private static void generateStationBoothData(AppContext ctx) {
		String locationName[] = {"City Aaa", "City Bbb", "City Ccc"};
		for (int i = 0; i < locationName.length; i++) {
			ctx.getLocationService().add(new Location(locationName[i], "Zip " + i));
		}
		
		for (int i = 0; i < ctx.getLocationService().getAll().size(); i++) {
			Location l = ctx.getLocationService().get(i);
			ctx.getStationService().add(new Station("Station En S0" + i, Station.Type.ENTER, l));
			ctx.getStationService().add(new Station("Station Ex S0" + i, Station.Type.EXIT, l));
		}
		
		int k = 0;
		for (Station s : ctx.getStationService().getAll()) {
			for (int i = 0; i < 3; i++) {
				Booth b = new Booth(s.getCode() + " Booth " + (k++), s);
				ctx.getBoothService().add(b);
			}
		}
	}

	private static void generatePricelistEntryData(AppContext ctx) {
		Random rnd = new Random();
		for (int i = 0; i < 10; ++i) {
			int price;
			PricelistEntry.Currency currency;
			PricelistEntry.VehicleCategory category;

			if (i % 2 == 0) {
				price = Math.abs(rnd.nextInt()) % 1000;
				currency = PricelistEntry.Currency.RSD;
			} else {
				price = Math.abs(rnd.nextInt()) % 10;
				currency = PricelistEntry.Currency.EUR;
			}
			category = PricelistEntry.VehicleCategory.values()[i % PricelistEntry.VehicleCategory.values().length];

			ctx.getPricelistEntryService().add(new PricelistEntry(price, currency, category));
		}
	}
}

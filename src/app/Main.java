package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import core.AppContext;
import core.booth.Booth;
import core.booth.DeviceStatus.Status;
import core.booth.DeviceStatus.Type;
import core.common.MasterXMLRepo;
import core.pricelist.Pricelist;
import core.pricelist.entry.PricelistEntry;
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
//		for (LookAndFeelInfo el : UIManager.getInstalledLookAndFeels()) {
//			if ("Nimbus".equals(el.getName()))
//				try {
//					UIManager.setLookAndFeel(el.getClassName());
//				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//						| UnsupportedLookAndFeelException e) {
//					e.printStackTrace();
//				}
//		}
		UIManager.put("Label.font", new FontUIResource(UIManager.getFont("Label.font").deriveFont((float) 22)));
		UIManager.put("Button.font", new FontUIResource(UIManager.getFont("Button.font").deriveFont((float) 22)));
		UIManager.put("TextField.font", new FontUIResource(UIManager.getFont("TextField.font").deriveFont((float) 22)));
		UIManager.put("PasswordField.font", new FontUIResource(UIManager.getFont("PasswordField.font").deriveFont((float) 22)));
		SwingUtilities.updateComponentTreeUI(frame);
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

		List<Station> entryStations = ctx.getStationService().getByType(Station.Type.ENTER);
		List<Station> exitStations = ctx.getStationService().getByType(Station.Type.EXIT);

		for (Station entry : entryStations) {
			for (Station exit : exitStations) {
				for (PricelistEntry.VehicleCategory category : PricelistEntry.VehicleCategory.values()) {
					for (PricelistEntry.Currency currency : PricelistEntry.Currency.values()) {
						int price;
						if (currency == PricelistEntry.Currency.EUR) {
							price = Math.abs(rnd.nextInt()) % 10;
						} else {
							price = Math.abs(rnd.nextInt()) % 1000;
						}
						ctx.getPricelistEntryService().add(new PricelistEntry(entry, exit, category, currency, price));
					}
				}
			}
		}
	}

	private static void generateNewPricelist(AppContext ctx) {
		int halfSize = ctx.getPricelistEntryService().getAll().size() / 2;
		List<PricelistEntry> firstHalf = ctx.getPricelistEntryRepo().getAll(p -> p.getId() < halfSize);

		// Entries were generated in such a way that the second half of all pricelists
		// mirror the first half, but with a different price field. However, this is not
		// a guarantee.
		List<PricelistEntry> secondHalf = ctx.getPricelistEntryRepo().getAll(p -> p.getId() >= halfSize);

		List<PricelistEntry> entries = new ArrayList<PricelistEntry>();

		for (int i = 0; i < firstHalf.size(); ++i) {
			entries.add((Math.random() < 0.7) ? firstHalf.get(i) : secondHalf.get(i));
		}

		ctx.getPricelistService().add(new Pricelist(LocalDateTime.now(), entries));
	}
}

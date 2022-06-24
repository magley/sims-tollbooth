package app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import core.AppContext;
import core.booth.Booth;
import core.common.MasterXMLRepo;
import desktop.employee.EmployeeLoginView;

public class Main {
	public static void main(String[] args) {
		MasterXMLRepo masterRepo = new MasterXMLRepo("data", "database.xml");
		AppContext ctx = new AppContext(masterRepo);
		boolean runApp = true;

		ctx.getBoothRepo().add(new Booth("ABC1", null));
		ctx.getStationRepo().get(0).addTollBooth(ctx.getBoothRepo().get(0));
		
		if (runApp) {
			startApp(masterRepo, ctx);
		} else {	
			System.out.println("Nothing to test...");
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
}

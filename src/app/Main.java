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
		boolean runApp = false;


		if (runApp) {
			startApp(masterRepo, ctx);
		} else {	
			// Add 
			
			Booth b = new Booth("Remove me", null);
			ctx.getBoothService().add(b);
			
			ctx.getStationService().get(0).addTollBooth(b);
			
			System.out.println(ctx.getStationService().get(0));
			System.out.println(b);
			
			ctx.getStationService().get(0).removeTollBooth(b);
			
			System.out.println(ctx.getStationService().get(0));
			System.out.println(b);

			System.out.println("Finished");
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

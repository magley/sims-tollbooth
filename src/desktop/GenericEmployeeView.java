package desktop;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.AppContext;
import core.employee.Employee;
import desktop.booth.BoothDashboardView;
import desktop.station.StationDashboardView;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GenericEmployeeView extends JFrame {
	private Employee employee;
	private JFrame parent;
	private static final long serialVersionUID = -6746602820794452880L;
	private JTabbedPane tabbedPane;
	private AppContext ctx;

	public void logOut() {
		setVisible(false);
		dispose();
		parent.setVisible(true);
	}
	
	public GenericEmployeeView(AppContext ctx, JFrame parent, Employee employee) {
		this.ctx = ctx;
		this.parent = parent;
		this.employee = employee;

		getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JButton btnLogout = new JButton("Log out");
		getContentPane().add(btnLogout, "cell 0 0");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logOut();
			}
		});
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (!(tabbedPane.getSelectedComponent() instanceof ITabbedPanel)) {
					System.err.println("All panels from GenericEmployeeView *MUST* implement ITabbedPanel interface");
					return;
				}
				
				((ITabbedPanel)(tabbedPane.getSelectedComponent())).onShow();
			}
		});
		getContentPane().add(tabbedPane, "cell 0 1,grow");
		
		switch (employee.getRole()) {
		case ADMIN:
			initAdminGUI();
			break;
		case COLLECTOR: case MANAGER: case STATION_CHEIF: case TAG_SELLER:
			JOptionPane.showMessageDialog(this, "No implementation.");
			break;
		case UNKNOWN: default:
			JOptionPane.showMessageDialog(this, "Bad or unknown role!", "Unexpected error", JOptionPane.ERROR_MESSAGE);
			logOut();
			break;		
		}
	}

	private void initAdminGUI() {
		StationDashboardView stationDashboardView = new StationDashboardView(ctx.getStationService(), ctx.getLocationService(), ctx.getStationController(), ctx.getBoothController());
		BoothDashboardView boothDashboardView = new BoothDashboardView(ctx);
		tabbedPane.add("Stations", stationDashboardView);
		tabbedPane.add("Booths", boothDashboardView);
	}
	
}

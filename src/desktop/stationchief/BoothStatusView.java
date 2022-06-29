package desktop.stationchief;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import core.booth.Booth;
import core.employee.IEmployeeService;
import core.malfunction.IMalfunctionService;
import core.malfunction.Malfunction;
import core.station.Station;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class BoothStatusView extends JPanel implements ITabbedPanel {
	private static final long serialVersionUID = -5836717774227837702L;

	public BoothStatusView(Station station, IMalfunctionService malfunctionService, 
			IEmployeeService employeeService) {
//		if (station.getType().equals(Station.Type.EXIT)) {
//			throw new Exception("Shouldn't be here");  // TODO: change exception
//		}
//		service.get(0);
		setLayout(new MigLayout("debug", "[grow]", "[grow, fill][]"));
		JTable boothStatusTable = new BoothStatusTable(station);
		JScrollPane scrollTable = new JScrollPane(boothStatusTable);
		add(scrollTable, "grow, flowy, wrap");
		JButton btnSimulateMalfunctionReport = new JButton("Simulate malfunction reporting");
		btnSimulateMalfunctionReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				Booth booth = station.getTollBooths().get(rand.nextInt(station.getTollBooths().size()));
				Malfunction malf = new Malfunction(booth.getDeviceStatus().get(0), booth);
				malfunctionService.add(malf);
			}
		});
		add(btnSimulateMalfunctionReport, "");
	}

	@Override
	public void onShow() {
		// TODO Auto-generated method stub
		
	}

}

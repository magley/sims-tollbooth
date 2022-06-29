package desktop.stationchief;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		BoothStatusTable boothStatusTable = new BoothStatusTable(station);
		JScrollPane scrollTable = new JScrollPane(boothStatusTable);
		add(scrollTable, "grow, flowy, wrap");
		JButton btnSimulateMalfunctionReport = new JButton("Simulate malfunction reporting");
		btnSimulateMalfunctionReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				Booth booth = station.getTollBooths().get(rand.nextInt(station.getTollBooths().size()));
				Malfunction malf = new Malfunction(booth.getDeviceStatus().get(1), booth);
				malfunctionService.add(malf);
			}
		});
		JButton btnActivate = new JButton("Activate");
		boothStatusTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				int selected = boothStatusTable.getSelectedRow();
				if (selected >= 0 && boothStatusTable.getSelectedRowCount() == 1) {
					if (boothStatusTable.getValueAt(selected, 2).equals("NOT ACTIVE"))
						btnActivate.setEnabled(true);
					else
						btnActivate.setEnabled(false);
				}
			}
		});
		btnActivate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Booth booth = boothStatusTable.getBoothAt(boothStatusTable.getSelectedRow());
				booth.activate();
			}
		});
		btnActivate.setEnabled(false);
		add(btnActivate, "cell 0 1");
		add(btnSimulateMalfunctionReport, "cell 0 1");
	}

	@Override
	public void onShow() {
		// TODO Auto-generated method stub
		
	}

}

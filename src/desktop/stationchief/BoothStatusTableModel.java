package desktop.stationchief;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.booth.Booth;
import core.booth.DeviceStatus;
import core.booth.observer.IObserver;
import core.malfunction.Malfunction;
import core.station.Station;

public class BoothStatusTableModel extends AbstractTableModel implements IObserver {
	private static final long serialVersionUID = 1330292339212982246L;
	private List<Booth> booths;
	private static final String[] cols = {"Code", "Working", "Active"};
	
	public BoothStatusTableModel(Station station) {
		this.booths = station.getTollBooths();
		for (Booth booth : booths) {
			booth.initDeviceStatus();
			booth.addObserver(this);
			booth.activate();  // TODO: check this, might rarely cause err if broken when coming in
		}
	}

	@Override
	public int getRowCount() {
		return booths.size();
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return cols[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return booths.get(rowIndex).getCode();
			// TODO: rest
		case 1:
			if (booths.get(rowIndex).getDeviceStatus().stream()
					.anyMatch(d -> d.getStatus().equals(DeviceStatus.Status.NOT_WORKING))) {
				return "NO";
			}
			return "YES";
		case 2:
			if (booths.get(rowIndex).isActive()) {
				return "ACTIVE";
			}
			return "NOT ACTIVE";
		default:
			return "INVALID";
		}
	}

	@Override
	public void notify(Malfunction malf) {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}
	
	public Booth getBoothAt(int row) {
		return booths.get(row);
	}

	@Override
	public void notifyState() {
		fireTableDataChanged();
	}

}

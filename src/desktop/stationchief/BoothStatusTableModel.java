package desktop.stationchief;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.booth.Booth;
import core.booth.observer.IObserver;
import core.malfunction.Malfunction;
import core.station.Station;

public class BoothStatusTableModel extends AbstractTableModel implements IObserver {
	private static final long serialVersionUID = 1330292339212982246L;
	private List<Booth> booths;
	private static final String[] cols = {"Code", "Flags", "Working", "Active"};
	
	public BoothStatusTableModel(Station station) {
		this.booths = station.getTollBooths();
		for (Booth booth : booths) {
			booth.initDeviceStatus();
			booth.addObserver(this);
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
//		case 1:
//			if (booths.get(rowIndex).get)
		default:
			return "INVALID";
		}
	}

	@Override
	public void notify(Malfunction malf) {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}

}

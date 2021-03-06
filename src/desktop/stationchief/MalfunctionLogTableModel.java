package desktop.stationchief;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.booth.Booth;
import core.booth.observer.IBoothObserver;
import core.employee.Employee;
import core.malfunction.IMalfunctionService;
import core.malfunction.Malfunction;
import core.station.Station;

public class MalfunctionLogTableModel extends AbstractTableModel implements IBoothObserver {

	private static final long serialVersionUID = 2700365819274952491L;
	private static final String[] cols = {"Time", "Code", "Device", "Who Reported"};
	
	private List<Malfunction> malfunctions;
	
	public MalfunctionLogTableModel(Station station, IMalfunctionService malfunctionService) {
		malfunctions = malfunctionService.getAllPastDay();
		for (Booth booth : station.getTollBooths()) {
			booth.initDeviceStatus();
			booth.addObserver(this);
			booth.activate();
		}
	}

	@Override
	public int getRowCount() {
		return malfunctions.size();
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
		int index = malfunctions.size() - rowIndex - 1;
		switch (columnIndex) {
		case 0:
			return malfunctions.get(index).getWhenCreated()
					.format(DateTimeFormatter.ofPattern("MM-dd    HH:mm:ss"));
		case 1:
			return malfunctions.get(index).getBooth().getCode();
		case 2:
			return malfunctions.get(index).getStatus().getType().toString();
		case 3:
			Employee whoReported = malfunctions.get(index).getWhoReported();
			return (whoReported != null) ? whoReported.getName() + " " + whoReported.getSurname() : "/";
		default:
			return null;
		}
	}

	@Override
	public void notify(Malfunction malf) {
		malfunctions.add(malf);
		fireTableDataChanged();
	}

	@Override
	public void notifyState() {
	}

	@Override
	public void notifyDevice() {
	}
}

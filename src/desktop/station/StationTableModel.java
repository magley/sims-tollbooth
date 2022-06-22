package desktop.station;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.views.AbstractView;

import core.station.IStationService;
import core.station.Station;
import core.station.location.Location;

public class StationTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 8918229221773283926L;
	private static final String[] cols = {"ID", "Code", "Type", "Location"};

	private IStationService service;
	public StationTableModel() {
	}
	public StationTableModel(IStationService service) {
		this.service = service;
	}
	
	@Override
	public int getRowCount() {
		return service.getAll().size();
	}

	@Override
	public int getColumnCount() {
		return cols.length; 
	}
	
	@Override
	public String getColumnName(int col) {
		return cols[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Station s = service.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return s.getId();
		case 1:
			return s.getCode();
		case 2:
			return s.getType();
		case 3:
			Location l = s.getLocation();
			return l.getName() + ", " + l.getZipCode();
		default:
			throw new ArrayIndexOutOfBoundsException("Bad column!");
		}
	}
	
	public Location getLocation(int rowIndex) {
		Station s = service.get(rowIndex);
		return s.getLocation();
	}
	
	public Station getStation(int rowIndex) {
		return service.get((int)getValueAt(rowIndex, 0));
	}

}

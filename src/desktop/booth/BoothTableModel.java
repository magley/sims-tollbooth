package desktop.booth;

import javax.swing.table.AbstractTableModel;

import core.booth.Booth;
import core.booth.IBoothService;

public class BoothTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 8918229221773283926L;
	private static final String[] cols = {"ID", "Code", "Station"};

	private IBoothService service;
	public BoothTableModel() {
	}
	public BoothTableModel(IBoothService service) {
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
		Booth b = getBooth(rowIndex);
		switch (columnIndex) {
		case 0:
			return b.getId();
		case 1:
			return b.getCode();
		case 2:
			return b.getStation() == null ? "null" : (b.getStation().getCode() + " " + b.getStation().getType());
		default:
			throw new ArrayIndexOutOfBoundsException("Bad column!");
		}
	}

	public Booth getBooth(int rowIndex) {
		return service.getAll().get(rowIndex);
	}
}

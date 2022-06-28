package desktop.stationchief;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.booth.Booth;

public class BoothStatusTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1330292339212982246L;
	private List<Booth> booths;
	private static final String[] cols = {"Code", "Status", "Flags"};

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

}

package desktop.stationchief;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import core.station.Station;

public class BoothStatusTable extends JTable {

	private static final long serialVersionUID = 5712828049119524094L;

	public BoothStatusTable(Station station) {
		this.setRowSelectionAllowed(false);
		this.setColumnSelectionAllowed(false);
		this.setModel(new BoothStatusTableModel(station));
		getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		return c;
	}
}

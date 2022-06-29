package desktop.stationchief;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import core.booth.Booth;
import core.station.Station;

public class BoothStatusTable extends JTable {

	private static final long serialVersionUID = 5712828049119524094L;

	public BoothStatusTable(Station station) {
		this.setColumnSelectionAllowed(false);
		this.setCellSelectionEnabled(false);
		this.setRowSelectionAllowed(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setModel(new BoothStatusTableModel(station));
		getTableHeader().setReorderingAllowed(false);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component c = super.prepareRenderer(renderer, row, column);
		if (getBoothAt(row).anyDeviceNotWorking()) {
			c.setForeground(Color.RED);
		}
		else if (!getBoothAt(row).isNotDeactivated()) {
			c.setForeground(Color.YELLOW);
		} else {
			if (isRowSelected(row)) {
				c.setForeground(Color.WHITE);
			} else {
				c.setForeground(Color.BLACK);
			}
		}
		return c;
	}
	
	public Booth getBoothAt(int row) {
		return ((BoothStatusTableModel) this.getModel()).getBoothAt(row);
	}
}

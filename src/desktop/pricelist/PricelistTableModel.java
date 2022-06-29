package desktop.pricelist;

import java.time.format.DateTimeFormatter;

import javax.swing.table.AbstractTableModel;

import core.pricelist.IPricelistService;
import core.pricelist.Pricelist;

public class PricelistTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 4462780930360261660L;
	private static final String[] cols = { "ID", "Start", "Active" };

	private IPricelistService service;

	public PricelistTableModel(IPricelistService service) {
		super();
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
		Pricelist p = service.getAll().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getId();
		case 1:
			return p.getStart().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
		case 2:
			return (service.isActive(p)) ? "YES": "NO";
		default:
			throw new ArrayIndexOutOfBoundsException("Bad column!");
		}
	}

	public Pricelist getPricelist(int rowIndex) {
		return service.getAll().get(rowIndex);
	}

}

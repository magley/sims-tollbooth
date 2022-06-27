package desktop.pricelistEntry;

import javax.swing.table.AbstractTableModel;

import core.pricelistEntry.IPricelistEntryService;
import core.pricelistEntry.PricelistEntry;

public class PricelistEntryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 4462780930360261660L;
	private static final String[] cols = { "ID", "Entry", "Exit", "Category", "Currency", "Price" };

	private IPricelistEntryService service;

	public PricelistEntryTableModel(IPricelistEntryService service) {
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
		PricelistEntry p = service.getAll().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getId();
		case 1:
			return p.getEntry().getCode();
		case 2:
			return p.getExit().getCode();
		case 3:
			return p.getCategory();
		case 4:
			return p.getCurrency();
		case 5:
			return p.getPrice();
		default:
			throw new ArrayIndexOutOfBoundsException("Bad column!");
		}
	}

	public PricelistEntry getPricelistEntry(int rowIndex) {
		return service.getAll().get(rowIndex);
	}

}

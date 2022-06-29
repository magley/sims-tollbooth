package desktop.pricelist.entry;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.pricelist.entry.PricelistEntry;

public class PricelistEntryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 4462780930360261660L;
	private static final String[] cols = { "ID", "Entry", "Exit", "Category", "Currency", "Price" };

	private List<PricelistEntry> entries;

	public PricelistEntryTableModel(List<PricelistEntry> entries) {
		super();
		this.entries = Collections.unmodifiableList(entries);
	}

	@Override
	public int getRowCount() {
		return entries.size();
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
		PricelistEntry p = entries.get(rowIndex);
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
		return entries.get(rowIndex);
	}

}

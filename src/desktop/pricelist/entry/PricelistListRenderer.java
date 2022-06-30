package desktop.pricelist.entry;

import java.awt.Component;
import java.time.format.DateTimeFormatter;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import core.pricelist.IPricelistService;
import core.pricelist.Pricelist;

public class PricelistListRenderer extends JCheckBox implements ListCellRenderer<Pricelist> {

	private static final long serialVersionUID = 8125819147830173769L;

	private IPricelistService service;

	public PricelistListRenderer(IPricelistService service) {
		this.service = service;
		this.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Pricelist> list, Pricelist value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if (isSelected || cellHasFocus) {
			this.setBackground(list.getSelectionBackground());
			this.setForeground(list.getSelectionForeground());
			this.setSelected(true);
		} else {
			this.setBackground(list.getBackground());
			this.setForeground(list.getForeground());
			this.setSelected(false);
		}

		String datetime = value.getStart().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " "
				+ value.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		this.setText(datetime + " Active: " + (service.isActive(value) ? "YES" : "NO"));
		return this;
	}

}

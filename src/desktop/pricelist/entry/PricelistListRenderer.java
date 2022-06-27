package desktop.pricelist.entry;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import core.pricelist.Pricelist;

public class PricelistListRenderer extends JCheckBox implements ListCellRenderer<Pricelist> {

	private static final long serialVersionUID = 8125819147830173769L;

	public PricelistListRenderer() {
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

		this.setText(value.getStart().toString() + " Active: " + value.getActive());
		return this;
	}

}

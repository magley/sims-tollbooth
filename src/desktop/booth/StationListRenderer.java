package desktop.booth;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import core.station.Station;

public class StationListRenderer extends JLabel implements ListCellRenderer<Station> {
	private static final long serialVersionUID = 351294770097303592L;

	public StationListRenderer() {
		setOpaque(true);
	}
	
	@Override
    public Component getListCellRendererComponent(JList<? extends Station> list, Station value, int index, boolean isSelected, boolean cellHasFocus) {  
		if (isSelected || cellHasFocus) {
		    setBackground(list.getSelectionBackground());
		    setForeground(list.getSelectionForeground());
		}
		else {
		    setBackground(list.getBackground());
		    setForeground(list.getForeground());
		}
		
        setText(value.getCode());       
        return this;
    }
}

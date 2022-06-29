package desktop.stationchief;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import core.station.Station;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class MalfunctionLogView extends JPanel implements ITabbedPanel {

	private static final long serialVersionUID = -4685644874677618546L;
	
	public MalfunctionLogView(Station station) {
		setLayout(new MigLayout("debug", "[grow]", ""));
		MalfunctionLogTableModel mfLogTableModel = new MalfunctionLogTableModel(station);
		JTable mfLogTable = new JTable(mfLogTableModel);
		mfLogTable.setRowSelectionAllowed(false);
		mfLogTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollTable = new JScrollPane(mfLogTable);
		add(scrollTable, "grow, flowy, wrap");
	}

	@Override
	public void onShow() {

	}

}

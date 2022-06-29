package desktop.stationchief;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import core.station.Station;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class MalfunctionLogView extends JPanel implements ITabbedPanel {

	private static final long serialVersionUID = -4685644874677618546L;
	
	public MalfunctionLogView(Station station) {
		setLayout(new MigLayout("debug", "", ""));
		MalfunctionLogTableModel mfLogTableModel = new MalfunctionLogTableModel(station);
		JTable mfLogTable = new JTable(mfLogTableModel);
		mfLogTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollTable = new JScrollPane(mfLogTable);
		add(scrollTable, "growx");
	}

	@Override
	public void onShow() {

	}

}

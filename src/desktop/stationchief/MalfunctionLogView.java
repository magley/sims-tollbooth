package desktop.stationchief;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import core.malfunction.IMalfunctionService;
import core.station.Station;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class MalfunctionLogView extends JPanel implements ITabbedPanel {

	private static final long serialVersionUID = -4685644874677618546L;
	
	public MalfunctionLogView(Station station, IMalfunctionService malfunctionService) {
		setLayout(new MigLayout("debug", "[grow]", "[grow, fill]"));
		MalfunctionLogTableModel mfLogTableModel = new MalfunctionLogTableModel(station, malfunctionService);
		JTable mfLogTable = new JTable(mfLogTableModel);
		mfLogTable.setRowHeight(32);
		mfLogTable.setRowSelectionAllowed(false);
		mfLogTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollTable = new JScrollPane(mfLogTable);
		add(scrollTable, "grow, flowy");
	}

	@Override
	public void onShow() {

	}

}

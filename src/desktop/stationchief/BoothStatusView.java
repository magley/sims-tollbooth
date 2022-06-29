package desktop.stationchief;

import javax.swing.JPanel;

import core.station.Station;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class BoothStatusView extends JPanel implements ITabbedPanel {
	private static final long serialVersionUID = -5836717774227837702L;

	public BoothStatusView(Station station) {
//		if (station.getType().equals(Station.Type.EXIT)) {
//			throw new Exception("Shouldn't be here");  // TODO: change exception
//		}
//		service.get(0);
		setLayout(new MigLayout());
	}

	@Override
	public void onShow() {
		// TODO Auto-generated method stub
		
	}

}

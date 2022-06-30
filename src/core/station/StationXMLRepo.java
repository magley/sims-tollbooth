package core.station;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class StationXMLRepo extends DefaultXMLRepo<Station> implements IStationRepo {
	public StationXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<Station> getAll() {
		return master.getStations();
	}
}

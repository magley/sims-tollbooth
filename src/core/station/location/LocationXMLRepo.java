package core.station.location;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class LocationXMLRepo extends DefaultXMLRepo<Location> implements ILocationRepo {
	public LocationXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<Location> getAll() {
		return master.getLocations();
	}
}

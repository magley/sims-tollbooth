package core.station.location;

import core.common.ServiceAdapter;

public class LocationService extends ServiceAdapter<Location> implements ILocationService {

	public LocationService(ILocationRepo repo) {
		super(repo);
	}
}

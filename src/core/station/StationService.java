package core.station;

import core.common.ServiceAdapter;

public class StationService extends ServiceAdapter<Station> implements IStationService {

	public StationService(IStationRepo repo) {
		super(repo);
	}
}

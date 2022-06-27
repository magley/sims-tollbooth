package core.station;

import java.util.List;

import core.common.ServiceAdapter;
import core.station.Station.Type;

public class StationService extends ServiceAdapter<Station> implements IStationService {

	public StationService(IStationRepo repo) {
		super(repo);
	}

	@Override
	public List<Station> getByType(Type type) {
		return repo.getAll(s -> s.getType() == type);
	}
}

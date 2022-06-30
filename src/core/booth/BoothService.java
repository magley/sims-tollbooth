package core.booth;

import java.util.List;

import core.common.ServiceAdapter;
import core.station.Station;

public class BoothService extends ServiceAdapter<Booth> implements IBoothService {

	public BoothService(IBoothRepo repo) {
		super(repo);
	}

	@Override
	public List<Booth> getFrom(Station station) {
		return repo.getAll(b -> b.getStation() == station);
	}

}

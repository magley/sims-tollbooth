package core.station.location;

import java.util.Collections;
import java.util.List;

public class LocationService implements ILocationService {
	private ILocationRepo repo;
	
	public LocationService(ILocationRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public Location add(Location obj) {
		return repo.add(obj);
	}

	@Override
	public List<Location> getAll() {
		return Collections.unmodifiableList(repo.getAll());
	}

	@Override
	public void save() {
		repo.save();
	}

	@Override
	public Location get(int id) {
		return repo.get(id);
	}

}

package core.station;

import java.util.List;

public class StationService implements IStationService {
	private IStationRepo repo;
	
	public StationService(IStationRepo repo) {
		this.repo = repo;
	}

	@Override
	public Station add(Station obj) {
		return repo.add(obj);
	}

	@Override
	public List<Station> getAll() {
		return repo.getAll();
	}

	@Override
	public void save() {
		repo.save();
	}

	@Override
	public Station get(int id) {
		return repo.get(id);
	}

	@Override
	public void remove(Station obj) {
		repo.remove(obj);
	}

	@Override
	public Station update(Station obj) {
		return repo.update(obj);
	}
}

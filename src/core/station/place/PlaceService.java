package core.station.place;

import java.util.Collections;
import java.util.List;

public class PlaceService implements IPlaceService {
	private IPlaceRepo repo;
	
	public PlaceService(IPlaceRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public Place add(Place obj) {
		return repo.add(obj);
	}

	@Override
	public List<Place> getAll() {
		return Collections.unmodifiableList(repo.getAll());
	}

	@Override
	public void save() {
		repo.save();
	}

	@Override
	public Place get(int id) {
		return repo.get(id);
	}

}

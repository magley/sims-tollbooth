package core.station.place;

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
		return repo.getAll();
	}

	@Override
	public void save() {
		repo.save();
	}

}

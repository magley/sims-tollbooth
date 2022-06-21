package core.station.place;

import java.util.List;

public interface IPlaceService {
	public Place add(Place obj);
	public List<Place> getAll();
	public void save();
	public Place get(int id);
}

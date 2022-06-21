package core.station.location;

import java.util.List;

public interface ILocationService {
	public Location add(Location obj);
	public List<Location> getAll();
	public void save();
	public Location get(int id);
}

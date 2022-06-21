package core.station;

import java.util.List;

public interface IStationService {
	public Station add(Station obj);
	public List<Station> getAll();
	public void save();
	public Station get(int id);
	public void remove(Station obj);
	public Station update(Station obj);
}

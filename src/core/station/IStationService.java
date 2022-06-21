package core.station;

import java.util.List;

public interface IStationService {
	public Station add(Station obj);
	public List<Station> getAll();
	public void save();
	public Station get(int id);
}

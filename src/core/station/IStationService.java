package core.station;

import java.util.List;

import core.common.IService;

public interface IStationService extends IService<Station> {
	
	public List<Station> getByType(Station.Type type);

	public Station getByString(String item);
}

package core.booth;

import java.util.List;

import core.common.IService;
import core.station.Station;

public interface IBoothService extends IService<Booth> {
	
	public List<Booth> getFrom(Station station);

}

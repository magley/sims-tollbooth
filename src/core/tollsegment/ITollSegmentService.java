package core.tollsegment;

import java.util.List;

import core.common.IService;
import core.station.Station;

public interface ITollSegmentService extends IService<TollSegment> {

	public TollSegment getFor(Station entry, Station exit);
	public List<Station> getEntriesFor(Station exit);
}

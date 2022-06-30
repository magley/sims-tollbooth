package core.tollsegment;

import java.util.List;

import core.common.IRepo;
import core.station.Station;

public interface ITollSegmentRepo extends IRepo<TollSegment> {
	public TollSegment getFor(Station entry, Station exit);

	public List<Station> getEntriesFor(Station exit);
}

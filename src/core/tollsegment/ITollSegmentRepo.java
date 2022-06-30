package core.tollsegment;

import core.common.IRepo;
import core.station.Station;

public interface ITollSegmentRepo extends IRepo<TollSegment> {
	public TollSegment getFor(Station entry, Station exit);
}

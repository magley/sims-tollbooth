package core.tollsegment;

import core.common.IService;
import core.station.Station;

public interface ITollSegmentService extends IService<TollSegment> {

	public TollSegment getFor(Station entry, Station exit);
}

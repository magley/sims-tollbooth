package core.tollsegment;

import core.common.ServiceAdapter;
import core.station.Station;

public class TollSegmentService extends ServiceAdapter<TollSegment> implements ITollSegmentService {

	public TollSegmentService(ITollSegmentRepo repo) {
		super(repo);
	}

	public TollSegment getFor(Station entry, Station exit) {
		return ((ITollSegmentRepo) repo).getFor(entry, exit);
	}
}

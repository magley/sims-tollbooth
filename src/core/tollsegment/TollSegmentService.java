package core.tollsegment;

import java.util.List;

import core.common.ServiceAdapter;
import core.station.Station;

public class TollSegmentService extends ServiceAdapter<TollSegment> implements ITollSegmentService {

	public TollSegmentService(ITollSegmentRepo repo) {
		super(repo);
	}

	public TollSegment getFor(Station entry, Station exit) {
		return ((ITollSegmentRepo) repo).getFor(entry, exit);
	}

	@Override
	public List<Station> getEntriesFor(Station exit) {
		return ((ITollSegmentRepo) repo).getEntriesFor(exit);
	}
}

package core.tollsegment;

import core.common.ServiceAdapter;

public class TollSegmentService extends ServiceAdapter<TollSegment> implements ITollSegmentService {

	public TollSegmentService(ITollSegmentRepo repo) {
		super(repo);
	}

}

package core.tollsegment;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class TollSegmentXMLRepo extends DefaultXMLRepo<TollSegment> implements ITollSegmentRepo {

	public TollSegmentXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<TollSegment> getAll() {
		return master.getTollSegments();
	}

}

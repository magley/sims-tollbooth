package core.tollsegment;

import java.util.List;
import java.util.stream.Collectors;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;
import core.station.Station;

public class TollSegmentXMLRepo extends DefaultXMLRepo<TollSegment> implements ITollSegmentRepo {

	public TollSegmentXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<TollSegment> getAll() {
		return master.getTollSegments();
	}

	@Override
	public TollSegment getFor(Station entry, Station exit) {
		return getAll().stream()
				.filter(ts -> ts.getEntry().equals(entry) && ts.getExit().equals(exit))
				.findFirst().orElse(null);
	}

	@Override
	public List<Station> getEntriesFor(Station exit) {
		return getAll().stream()
				.filter(ts -> ts.getExit().equals(exit))
			    .map(ts -> ts.getEntry())
			    .collect(Collectors.toList());
	}

}

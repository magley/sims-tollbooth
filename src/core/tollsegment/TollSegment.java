package core.tollsegment;

import core.Entity;
import core.station.Station;

public class TollSegment extends Entity {
	private Station entry;
	private Station exit;

	public TollSegment(Station entry, Station exit) {
		super();
		this.entry = entry;
		this.exit = exit;
	}

	public Station getEntry() {
		return entry;
	}

	public void setEntry(Station entry) {
		this.entry = entry;
	}

	public Station getExit() {
		return exit;
	}

	public void setExit(Station exit) {
		this.exit = exit;
	}
	
}

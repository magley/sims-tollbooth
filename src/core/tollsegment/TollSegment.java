package core.tollsegment;

import core.Entity;
import core.station.Station;

public class TollSegment extends Entity {
	private Station entry;
	private Station exit;
	private int distance;  // measured in km

	public TollSegment(Station entry, Station exit, int distance) {
		super();
		// TODO: maybe check if they are really entry and exit stations
		this.entry = entry;
		this.exit = exit;
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
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

	@Override
	public boolean equals(Object obj) {
		// TODO: might have to make a custom
		return super.equals(obj);
	}
	
}

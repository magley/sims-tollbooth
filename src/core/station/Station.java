package core.station;

import java.util.ArrayList;
import java.util.List;

import core.Entity;
import core.booth.Booth;
import core.station.location.Location;

public class Station extends Entity {
	public enum Type {
		ENTER,
		EXIT
	}

	private String code;
	private Type type;
	private Location location;
	private List<Booth> tollbooths;
	
	public Station(String code, Type type, Location location) {
		super();
		this.code = code;
		this.type = type;
		this.location = location;
		this.tollbooths = new ArrayList<Booth>();
	}
	
	public void addTollBooth(Booth b) {
		tollbooths.add(b);
		b.setStation(this);
	}
	
	public void removeTollBooth(Booth b) {
		tollbooths.remove(b);
		if (b.getStation() == this) {
			b.setStation(null);
		}
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Station [code=" + code + ", type=" + type + ", location=" + location + "]";
	}
}

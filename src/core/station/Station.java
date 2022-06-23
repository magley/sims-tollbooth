package core.station;

import core.Entity;
import core.station.location.Location;

public class Station extends Entity {
	public enum Type {
		ENTER,
		EXIT
	}

	private String code;
	private Type type;
	private Location location;
	
	public Station(String code, Type type, Location location) {
		super();
		this.code = code;
		this.type = type;
		this.location = location;
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

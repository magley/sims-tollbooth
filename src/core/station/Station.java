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
	private Location place;
	
	public Station(String code, Type type, Location place) {
		super();
		this.code = code;
		this.type = type;
		this.place = place;
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
	
	public Location getPlace() {
		return place;
	}
	
	public void setPlace(Location place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "Station [code=" + code + ", type=" + type + ", place=" + place + "]";
	}
}

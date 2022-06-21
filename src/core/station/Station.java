package core.station;

import core.Entity;
import core.station.place.Place;

public class Station extends Entity {
	public enum Type {
		ENTER,
		EXIT
	}

	private String code;
	private Type type;
	private Place place;
	
	public Station(String code, Type type, Place place) {
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
	
	public Place getPlace() {
		return place;
	}
	
	public void setPlace(Place place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return "Station [code=" + code + ", type=" + type + ", place=" + place + "]";
	}
}

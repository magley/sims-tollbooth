package core.station.place;

import core.Entity;

public class Place extends Entity {
	private String name;
	private String zipCode;
	
	public Place(String name, String zipCode) {
		super();
		this.name = name;
		this.zipCode = zipCode;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Override
	public String toString() {
		return "Place [name=" + name + ", zipCode=" + zipCode + "]";
	}
}

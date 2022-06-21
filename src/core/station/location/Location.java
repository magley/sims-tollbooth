package core.station.location;

import core.Entity;

public class Location extends Entity {
	private String name;
	private String zipCode;
	
	public Location(String name, String zipCode) {
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
		return "Location [name=" + name + ", zipCode=" + zipCode + "]";
	}
}

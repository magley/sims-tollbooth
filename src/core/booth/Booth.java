package core.booth;

import java.util.ArrayList;
import java.util.List;

import core.Entity;
import core.station.Station;

public class Booth extends Entity {
	private String code;
	private Station station;
	private List<DeviceStatus> deviceStatus;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public List<DeviceStatus> getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(List<DeviceStatus> deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public Booth(String code, Station station) {
		super();
		this.code = code;
		
		if (station != null)
			station.addTollBooth(this);
		
		this.deviceStatus = new ArrayList<DeviceStatus>();
	}
	@Override
	public String toString() {
		return "Booth [code=" + code + ", station.id=" + (station != null ? station.getId() : "null") + ", deviceStatus=" + deviceStatus + "]";
	}
}

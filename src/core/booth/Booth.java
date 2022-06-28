package core.booth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import core.Entity;
import core.booth.DeviceStatus.Status;
import core.station.Station;

public class Booth extends Entity {
	private String code;
	private Station station;

	@XStreamOmitField
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
	public Booth(String code, Station station) {
		super();
		this.code = code;
		
		if (station != null)
			station.addTollBooth(this);
		
		initDeviceStatus();
	}
	@Override
	public String toString() {
		if (deviceStatus == null) {
			initDeviceStatus();
		}
		return "Booth [code=" + code + ", station.id=" + (station != null ? station.getId() : "null") + ", deviceStatus=" + deviceStatus + "]";
	}
	
	public void setDeviceStatus(DeviceStatus.Type deviceType, DeviceStatus.Status status) {
		if (deviceStatus == null) {
			initDeviceStatus();
		}
		
		Optional<DeviceStatus> o = deviceStatus.stream().filter(ds -> ds.getType() == deviceType).findAny();
		
		if (o.isEmpty()) {
			System.err.println("Device not found! Ignoring...");
		} else {
			DeviceStatus ds = o.get();
			ds.setStatus(status);
		}
	}
	
	// TODO: extract common method
	public void setDeviceFlags(DeviceStatus.Type deviceType, int flags) {
		if (deviceStatus == null) {
			initDeviceStatus();
		}
		
		Optional<DeviceStatus> o = deviceStatus.stream().filter(ds -> ds.getType() == deviceType).findAny();
		
		if (o.isEmpty()) {
			System.err.println("Device not found! Ignoring...");
		} else {
			DeviceStatus ds = o.get();
			ds.setFlags(flags);
		}
	}
	
	// TODO: extract common method
	public void flipDeviceFlags(DeviceStatus.Type deviceType) {
		if (deviceStatus == null) {
			initDeviceStatus();
		}
		
		Optional<DeviceStatus> o = deviceStatus.stream().filter(ds -> ds.getType() == deviceType).findAny();
		
		if (o.isEmpty()) {
			System.err.println("Device not found! Ignoring...");
		} else {
			DeviceStatus ds = o.get();
			ds.flipFlags();
		}
	}
	
	private void initDeviceStatus() {
		deviceStatus = new ArrayList<DeviceStatus>();
		for (DeviceStatus.Type t : DeviceStatus.Type.values()) {
			deviceStatus.add(new DeviceStatus(t, Status.NOT_WORKING));
		}
	}
}

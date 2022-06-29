package core.booth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import core.Entity;
import core.booth.DeviceStatus.Status;
import core.booth.DeviceStatus.Type;
import core.booth.observer.IBoothObserver;
import core.booth.observer.IPublisher;
import core.booth.state.BoothDeactivated;
import core.booth.state.BoothState;
import core.booth.state.BoothVehiclePassing;
import core.malfunction.Malfunction;
import core.station.Station;

public class Booth extends Entity implements IPublisher {
	private String code;
	private Station station;

	@XStreamOmitField
	private BoothState state;
	@XStreamOmitField
	private List<DeviceStatus> deviceStatus;
	@XStreamOmitField
	private List<IBoothObserver> observers;

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

	public int getDeviceFlags(DeviceStatus.Type type) {
		DeviceStatus ds = getDeviceStatus(type);
		return ds != null ? ds.getFlags() : -1;
	}
	
	public Boolean anyDeviceNotWorking() {
		return getDeviceStatus().stream()
				.anyMatch(d -> d.getStatus().equals(Status.NOT_WORKING));
	}

	public DeviceStatus getDeviceStatus(DeviceStatus.Type type) {
		Optional<DeviceStatus> dStatus = this.deviceStatus.stream()
				.filter(d -> d.getType().equals(type)).findFirst();
		if (dStatus.isEmpty()) {
			return null;
		}
		return dStatus.get();
		// TODO: use this in next refactor
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
		initDeviceStatus();
		return "Booth [code=" + code + ", station.id=" + (station != null ? station.getId() : "null") + ", deviceStatus=" + deviceStatus + "]";
	}
	
	public void setDeviceStatus(DeviceStatus.Type deviceType, DeviceStatus.Status status) {
		initDeviceStatus();
		
		Optional<DeviceStatus> o = deviceStatus.stream().filter(ds -> ds.getType() == deviceType).findAny();
		
		if (o.isEmpty()) {
			System.err.println("Device not found! Ignoring...");
		} else {
			DeviceStatus ds = o.get();
			ds.setStatus(status);
		}
		notifyObserversDevice();
	}
	
	// TODO: extract common method
	public void setDeviceFlags(DeviceStatus.Type deviceType, int flags) {
		initDeviceStatus();
		
		Optional<DeviceStatus> o = deviceStatus.stream().filter(ds -> ds.getType() == deviceType).findAny();
		
		if (o.isEmpty()) {
			System.err.println("Device not found! Ignoring...");
		} else {
			DeviceStatus ds = o.get();
			ds.setFlags(flags);
		}
		notifyObserversDevice();
	}
	
	// TODO: extract common method
	public void flipDeviceFlags(DeviceStatus.Type deviceType) {
		initDeviceStatus();
		
		Optional<DeviceStatus> o = deviceStatus.stream().filter(ds -> ds.getType() == deviceType).findAny();
		
		if (o.isEmpty()) {
			System.err.println("Device not found! Ignoring...");
		} else {
			DeviceStatus ds = o.get();
			ds.flipFlags();
		}
		notifyObserversDevice();
	}
	
	public void initDeviceStatus() {
		if (deviceStatus == null) {
			deviceStatus = new ArrayList<DeviceStatus>();
			for (DeviceStatus.Type t : DeviceStatus.Type.values()) {
				deviceStatus.add(new DeviceStatus(t, Status.WORKING));
			}
			this.observers = new ArrayList<IBoothObserver>();
			this.state = new BoothDeactivated(this);
			this.state.entry();
		}
	}

	@Override
	public void addObserver(IBoothObserver o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(IBoothObserver o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Malfunction malf) {
		for (IBoothObserver o : observers) {
			o.notify(malf);
		}
	}
	
	public void lowerRamp() {
		setDeviceFlags(Type.RAMP, 0);
	}
	
	public void raiseRamp() {
		setDeviceFlags(Type.RAMP, 1);
	}

	public void setSemaphoreRed() {
		setDeviceFlags(Type.SEMAPHORE, 0);
	}

	public void setSemaphoreGreen() {
		setDeviceFlags(Type.SEMAPHORE, 1);
	}
	
	public void setDisplayX() {
		setDeviceFlags(Type.SCREEN, 0);
	}

	public void setDisplayActive() {
		setDeviceFlags(Type.SCREEN, 1);
	}
	
	public void activate() {
		this.state.activate();
	}
	
	public void pause() {
		this.state.pause();
	}
	
	public void vehiclePassed() {
		this.state.vehiclePassed();
	}
	
	public void vehicleStartedPassing() {
		this.state.vehicleStartedPassing();
	}
	
	public void malfunctionOccurred(Malfunction malf) {
		initDeviceStatus();
		this.state.malfunctionOccurred(malf);
		this.notifyObservers(malf);
	}
	
	public void changeState(BoothState newState) {
		state.exit();
		this.state = newState;
		state.entry();
		notifyObserversState();
	}
	
	public Boolean isNotDeactivated() {
		return this.state.getClass() != BoothDeactivated.class;
	}

	public Boolean isVehiclePassing() {
		return this.state.getClass() == BoothVehiclePassing.class;
	}

	@Override
	public void notifyObserversState() {
		for (IBoothObserver o : observers) {
			o.notifyState();
		}
	}
	@Override
	public void notifyObserversDevice() {
		for (IBoothObserver o : observers) {
			o.notifyDevice();
		}
	}
}

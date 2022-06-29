package core.booth;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class DeviceStatus {
	public enum Type {
		TAG_READER, TABLE_READER, RAMP, SEMAPHORE, SCREEN
	}
	
	public enum Status {
		WORKING, NOT_WORKING
	}
	
	private Type type;
	private Status status;
	
	@XStreamOmitField
	private int flags = 0;  // 0 is down, 1 is up
	
	public void flipFlags() {
		flags = 1 - flags;
	}
	
	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public DeviceStatus(Type type, Status status) {
		super();
		this.type = type;
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "DeviceStatus [type=" + type + ", status=" + status + "]";
	}
}

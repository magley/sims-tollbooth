package core.malfunction;

import java.time.LocalDateTime;

import core.Entity;
import core.booth.Booth;
import core.booth.DeviceStatus;
import core.employee.Employee;

public class Malfunction extends Entity {
	private LocalDateTime whenCreated;
	private DeviceStatus status;
	private Booth booth;
	private Employee whoReported;

	public Malfunction(DeviceStatus status, Booth booth, Employee reportedBy) {
		super();
		this.status = status;
		this.booth = booth;
		this.whoReported = reportedBy;
		whenCreated = LocalDateTime.now();
	}

	public Malfunction(DeviceStatus status, Booth booth) {
		super();
		this.status = status;
		this.booth = booth;
		this.whoReported = null;
		whenCreated = LocalDateTime.now();
	}
	
	public LocalDateTime getWhenCreated() {
		return whenCreated;
	}

	public DeviceStatus getStatus() {
		return status;
	}

	public Booth getBooth() {
		return booth;
	}

	public Employee getWhoReported() {
		return whoReported;
	}
}

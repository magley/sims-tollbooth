package core.ticket;

import java.time.LocalDateTime;

import core.Entity;
import core.booth.Booth;

public class Ticket extends Entity {

	// Note: We're not keeping track of Tags.
	private Booth entryBooth;
	private LocalDateTime enteredAt;
	private Booth exitBooth;
	private LocalDateTime leftAt;
	private String licensePlate;

	public Ticket(Booth entryBooth, LocalDateTime enteredAt, Booth exitBooth, LocalDateTime leftAt,
			String licensePlate) {
		super();
		this.entryBooth = entryBooth;
		this.enteredAt = enteredAt;
		this.exitBooth = exitBooth;
		this.leftAt = leftAt;
		this.licensePlate = licensePlate;
	}

	public Booth getEntryBooth() {
		return entryBooth;
	}

	public void setEntryBooth(Booth entryBooth) {
		this.entryBooth = entryBooth;
	}

	public LocalDateTime getEnteredAt() {
		return enteredAt;
	}

	public void setEnteredAt(LocalDateTime enteredAt) {
		this.enteredAt = enteredAt;
	}

	public Booth getExitBooth() {
		return exitBooth;
	}

	public void setExitBooth(Booth exitBooth) {
		this.exitBooth = exitBooth;
	}

	public LocalDateTime getLeftAt() {
		return leftAt;
	}

	public void setLeftAt(LocalDateTime leftAt) {
		this.leftAt = leftAt;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	@Override
	public String toString() {
		return "Ticket [entryBooth=" + entryBooth + ", enteredAt=" + enteredAt + ", exitBooth=" + exitBooth
				+ ", leftAt=" + leftAt + ", licensePlate=" + licensePlate + "]";
	}

}

package core.pricelist;

import java.time.LocalDateTime;
import java.util.List;

import core.Entity;
import core.pricelist.entry.PricelistEntry;

public class Pricelist extends Entity {

	public enum Active {
		YES, NO
	}

	private LocalDateTime start;
	private List<PricelistEntry> entries;
	private Active active;

	public Pricelist(LocalDateTime start, List<PricelistEntry> entries, Active active) {
		super();
		this.start = start;
		this.entries = entries;
		this.active = active;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public List<PricelistEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<PricelistEntry> entries) {
		this.entries = entries;
	}

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Pricelist [start=" + start + ", active=" + active + "]";
	}

}

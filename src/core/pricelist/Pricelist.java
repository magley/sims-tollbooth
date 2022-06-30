package core.pricelist;

import java.time.LocalDateTime;
import java.util.List;

import core.Entity;
import core.pricelist.entry.PricelistEntry;

public class Pricelist extends Entity {

	private LocalDateTime start;
	private List<PricelistEntry> entries;

	public Pricelist(LocalDateTime start, List<PricelistEntry> entries) {
		super();
		this.start = start;
		this.entries = entries;
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

	@Override
	public String toString() {
		return "Pricelist [start=" + start + "]";
	}

}

package core.payment;

import java.time.LocalDateTime;

import core.Entity;
import core.employee.Employee;
import core.pricelist.entry.PricelistEntry;
import core.ticket.Ticket;

public class Payment extends Entity {

	private LocalDateTime time;
	private PricelistEntry pricelistEntry;
	private Ticket ticket;
	private Employee collector;
	private int amount;

	public Payment(LocalDateTime time, PricelistEntry pricelistEntry, Ticket ticket, Employee collector, int amount) {
		super();

		if (collector.getRole() != Employee.Role.COLLECTOR) {
			throw new IllegalArgumentException("Employee must be of type COLLECTOR");
		}

		this.time = time;
		this.pricelistEntry = pricelistEntry;
		this.ticket = ticket;
		this.collector = collector;
		this.amount = amount;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public PricelistEntry getPricelistEntry() {
		return pricelistEntry;
	}

	public void setPricelistEntry(PricelistEntry pricelistEntry) {
		this.pricelistEntry = pricelistEntry;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Employee getCollector() {
		return collector;
	}

	public void setCollector(Employee collector) {
		this.collector = collector;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Payment [time=" + time + ", pricelistEntry=" + pricelistEntry + ", ticket=" + ticket + ", collector="
				+ collector + ", amount=" + amount + "]";
	}

}

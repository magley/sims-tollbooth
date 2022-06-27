package core.pricelist.entry;

import core.Entity;
import core.station.Station;

public class PricelistEntry extends Entity {

	public enum Currency {
		RSD, EUR
	}

	public enum VehicleCategory {
		A, B, C, D, F, M
	}

	private Station entry;
	private Station exit;
	private VehicleCategory category;
	private Currency currency;
	private int price;

	public PricelistEntry(Station entry, Station exit, VehicleCategory category, Currency currency, int price) {
		super();
		this.entry = entry;
		this.exit = exit;
		this.category = category;
		this.currency = currency;
		this.price = price;
	}

	public Station getEntry() {
		return entry;
	}

	public void setEntry(Station entry) {
		this.entry = entry;
	}

	public Station getExit() {
		return exit;
	}

	public void setExit(Station exit) {
		this.exit = exit;
	}

	public VehicleCategory getCategory() {
		return category;
	}

	public void setCategory(VehicleCategory category) {
		this.category = category;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "PricelistEntry [entry=" + entry + ", exit=" + exit + ", category=" + category + ", currency=" + currency
				+ ", price=" + price + "]";
	}

}

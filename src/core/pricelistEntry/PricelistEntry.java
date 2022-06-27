package core.pricelistEntry;

import core.Entity;
import core.station.Station;

public class PricelistEntry extends Entity {
	public enum Currency {
		RSD, EUR
	}

	public enum VehicleCategory {
		A, B, C, D, F, M
	}

	private int price;
	private Currency currency;
	private VehicleCategory category;
	private Station entry;
	private Station exit;

	public PricelistEntry(int price, Currency currency, VehicleCategory category, Station entry, Station exit) {
		super();
		this.price = price;
		this.currency = currency;
		this.category = category;
		this.entry = entry;
		this.exit = exit;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public VehicleCategory getCategory() {
		return category;
	}

	public void setCategory(VehicleCategory category) {
		this.category = category;
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

	@Override
	public String toString() {
		return "PricelistEntry [price=" + price + ", currency=" + currency + ", category=" + category + ", entry="
				+ entry + ", exit=" + exit + "]";
	}

}

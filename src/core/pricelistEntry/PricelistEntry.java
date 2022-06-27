package core.pricelistEntry;

import core.Entity;

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

	public PricelistEntry(int price, Currency currency, VehicleCategory category) {
		super();
		this.price = price;
		this.currency = currency;
		this.category = category;
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

	@Override
	public String toString() {
		return "PricelistEntry [price=" + price + ", currency=" + currency + ", category=" + category + "]";
	}
}

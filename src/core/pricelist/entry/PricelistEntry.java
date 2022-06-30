package core.pricelist.entry;

import core.Entity;
import core.tollsegment.TollSegment;

public class PricelistEntry extends Entity {

	public enum Currency {
		RSD, EUR
	}

	public enum VehicleCategory {
		A, B, C, D, F, M
	}

	private VehicleCategory category;
	private Currency currency;
	private int price;
	private TollSegment segment;

	public PricelistEntry(TollSegment segment, VehicleCategory category, Currency currency, int price) {
		super();
		this.segment = segment;
		this.category = category;
		this.currency = currency;
		this.price = price;
	}

	public TollSegment getSegment() {
		return segment;
	}

	public void setSegment(TollSegment segment) {
		this.segment = segment;
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
		return "PricelistEntry [entry=" + segment.getEntry() + ", exit=" + segment.getExit() + 
				", category=" + category + ", currency=" + currency + ", price=" + price + "]";
	}

}

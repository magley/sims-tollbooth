package core.pricelist;

import java.util.List;

import core.common.IService;
import core.pricelist.entry.PricelistEntry;
import core.pricelist.entry.PricelistEntry.Currency;
import core.pricelist.entry.PricelistEntry.VehicleCategory;
import core.station.Station;

public interface IPricelistService extends IService<Pricelist> {

	public List<Pricelist> getContaining(PricelistEntry entry);

	public Pricelist getActive();
	
	public boolean isActive(Pricelist p);

	public PricelistEntry getFor(Station entry, Station exit, VehicleCategory category, Currency currency);
}

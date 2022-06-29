package core.pricelist;

import java.util.List;

import core.common.ServiceAdapter;
import core.pricelist.entry.PricelistEntry;
import core.pricelist.entry.PricelistEntry.Currency;
import core.pricelist.entry.PricelistEntry.VehicleCategory;
import core.station.Station;

public class PricelistService extends ServiceAdapter<Pricelist> implements IPricelistService {

	public PricelistService(IPricelistRepo repo) {
		super(repo);
	}

	@Override
	public List<Pricelist> getContaining(PricelistEntry entry) {
		return repo.getAll(p -> p.getEntries().contains(entry));
	}

	@Override
	public Pricelist getActive() {
		return repo.get(p -> p.getActive() == Pricelist.Active.YES);
	}

	@Override
	public PricelistEntry getFor(Station entry, Station exit, VehicleCategory category, Currency currency) {
		Pricelist active = getActive();
		return active.getEntries().stream().filter(e -> e.getEntry() == entry && e.getExit() == exit
				&& e.getCategory() == category && e.getCurrency() == currency).findFirst().orElse(null);
	}

}

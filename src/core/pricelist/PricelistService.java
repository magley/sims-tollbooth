package core.pricelist;

import java.util.List;

import core.common.ServiceAdapter;
import core.pricelist.entry.PricelistEntry;

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

}

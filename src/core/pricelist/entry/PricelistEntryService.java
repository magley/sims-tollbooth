package core.pricelist.entry;

import core.common.ServiceAdapter;

public class PricelistEntryService extends ServiceAdapter<PricelistEntry> implements IPricelistEntryService {

	public PricelistEntryService(IPricelistEntryRepo repo) {
		super(repo);
	}

}

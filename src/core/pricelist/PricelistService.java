package core.pricelist;

import core.common.ServiceAdapter;

public class PricelistService extends ServiceAdapter<Pricelist> implements IPricelistService {

	public PricelistService(IPricelistRepo repo) {
		super(repo);
	}

}

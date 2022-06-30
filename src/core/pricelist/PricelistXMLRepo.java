package core.pricelist;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class PricelistXMLRepo extends DefaultXMLRepo<Pricelist> implements IPricelistRepo {

	public PricelistXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<Pricelist> getAll() {
		return master.getPricelists();
	}

}

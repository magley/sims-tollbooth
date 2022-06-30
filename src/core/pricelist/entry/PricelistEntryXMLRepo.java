package core.pricelist.entry;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class PricelistEntryXMLRepo extends DefaultXMLRepo<PricelistEntry> implements IPricelistEntryRepo {

	public PricelistEntryXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<PricelistEntry> getAll() {
		return master.getPricelistEntries();
	}
}

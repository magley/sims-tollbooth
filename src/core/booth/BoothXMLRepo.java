package core.booth;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class BoothXMLRepo extends DefaultXMLRepo<Booth> implements IBoothRepo {
	public BoothXMLRepo(MasterXMLRepo master) {
		super(master);
	}
	
	@Override
	public List<Booth> getAll() {
		return master.getBooths();
	}

}

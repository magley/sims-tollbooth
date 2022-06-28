package core.malfunction;

import java.util.List;
import java.util.function.Predicate;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class MalfunctionXMLRepo extends DefaultXMLRepo<Malfunction> implements IMalfunctionRepo {

	public MalfunctionXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<Malfunction> getAll() {
		return master.getMalfunctions();
	}
}

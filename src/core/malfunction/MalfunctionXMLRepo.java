package core.malfunction;

import java.time.LocalDateTime;
import java.util.List;

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

	@Override
	public List<Malfunction> getAllPastDay() {
		return getAll(m -> m.getWhenCreated().isAfter(LocalDateTime.now().minusDays(1)));
	}
}

package core.malfunction;

import core.common.IRepo;
import core.common.ServiceAdapter;

public class MalfunctionService extends ServiceAdapter<Malfunction> implements IMalfunctionService {

	public MalfunctionService(IRepo<Malfunction> repo) {
		super(repo);
	}

	@Override
	public Malfunction add(Malfunction obj) {
		obj.getBooth().malfunctionOccurred(obj);
		return super.add(obj);
	}
	
}

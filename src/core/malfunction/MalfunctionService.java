package core.malfunction;

import core.common.IRepo;
import core.common.ServiceAdapter;

public class MalfunctionService extends ServiceAdapter<Malfunction> implements IMalfunctionService {

	public MalfunctionService(IRepo<Malfunction> repo) {
		super(repo);
	}
}

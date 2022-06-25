package core.booth;

import core.common.ServiceAdapter;

public class BoothService extends ServiceAdapter<Booth> implements IBoothService {

	public BoothService(IBoothRepo repo) {
		super(repo);
	}
}

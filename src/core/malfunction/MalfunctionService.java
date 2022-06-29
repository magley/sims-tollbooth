package core.malfunction;

import java.util.List;

import core.common.ServiceAdapter;

public class MalfunctionService extends ServiceAdapter<Malfunction> implements IMalfunctionService {

	public MalfunctionService(IMalfunctionRepo repo) {
		super(repo);
	}

	@Override
	public Malfunction add(Malfunction obj) {
		obj.getBooth().malfunctionOccurred(obj);
		return super.add(obj);
	}

	@Override
	public List<Malfunction> getAllPastDay() {
		return ((IMalfunctionRepo) repo).getAllPastDay();
	}
	
}

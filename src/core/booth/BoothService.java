package core.booth;

import java.util.List;

public class BoothService implements IBoothService {
	private IBoothRepo repo;
	
	public BoothService(IBoothRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public Booth add(Booth obj) {
		return repo.add(obj);
	}

	@Override
	public List<Booth> getAll() {
		return repo.getAll();
	}

	@Override
	public void save() {
		repo.save();
	}

	@Override
	public Booth get(int id) {
		return repo.get(id);
	}

	@Override
	public void remove(Booth obj) {
		repo.remove(obj);
	}

	@Override
	public Booth update(Booth obj) {
		return repo.update(obj);
	}
}

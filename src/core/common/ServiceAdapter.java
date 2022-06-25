package core.common;

import java.util.Collections;
import java.util.List;

import core.Entity;

public class ServiceAdapter<T extends Entity> extends DefaultService<T> {

	public ServiceAdapter(IRepo<T> repo) {
		super(repo);
	}

	@Override
	public T add(T obj) {
		return repo.add(obj);
	}

	@Override
	public List<T> getAll() {
		return Collections.unmodifiableList(repo.getAll());
	}

	@Override
	public void save() {
		repo.save();
	}

	@Override
	public T get(int id) {
		return repo.get(id);
	}

	@Override
	public void remove(T obj) {
		repo.remove(obj);
	}

	@Override
	public T update(T obj) {
		return repo.update(obj);
	}
}

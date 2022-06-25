package core.common;

import java.util.List;

import core.Entity;

public interface IService<T extends Entity> {
	public T add(T obj);

	public List<T> getAll();

	public void save();

	public T get(int id);

	public void remove(T obj);

	public T update(T obj);
}

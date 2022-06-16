package core.common;

import java.util.List;

import core.Entity;

/**
 * IRepo declares a minimal set of methods that all repositories should implement.
 * @param <T> Has to extend Entity.
 */
public interface IRepo<T extends Entity> {
	public List<T> getAll();
	public T get(int id);
	public T add(T obj);
	public void remove(T obj);
	public void save();
}

package core.common;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import core.Entity;

/**
 * Default implementation of IRepo<T> for an XStream data store. Implements all
 * IRepo<T> methods except for getAll(), which has to be done manually.
 * 
 * @param <T> Has to extend Entity.
 */
public abstract class DefaultXMLRepo<T extends Entity> implements IRepo<T> {
	protected MasterXMLRepo master;

	public DefaultXMLRepo(MasterXMLRepo master) {
		this.master = master;
	}
	
	@Override
	public List<T> getAll(Predicate<T> pred) {
		return getAll().stream().filter(t -> pred.test(t)).collect(Collectors.toList());
	}

	@Override
	public T get(Predicate<T> pred) {
		return getAll().stream().filter(t -> pred.test(t)).findFirst().orElse(null);
	}

	@Override
	public T add(T obj) {
		obj.setId(getAll().size());
		getAll().add(obj);
		return obj;
	}
	
	@Override
	public T update(T obj) {
		for (T e : getAll()) {
			if (e.getId() == obj.getId()) {
				e = obj;
			}
		}
		return obj;
	}

	@Override
	public void remove(T obj) {
		getAll().remove(obj);
	}

	@Override
	public void save() {
		master.save();
	}
}

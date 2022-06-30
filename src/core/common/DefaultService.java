package core.common;

import core.Entity;

public abstract class DefaultService<T extends Entity> implements IService<T> {
	protected IRepo<T> repo;
	
	public DefaultService(IRepo<T> repo) {
		this.repo = repo;
	}
}

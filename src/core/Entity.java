package core;

public class Entity {
	int id;
	boolean removed;
	
	public Entity() {
		id = -1;
		removed = false;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
}
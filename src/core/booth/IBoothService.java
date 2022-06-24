package core.booth;

import java.util.List;

public interface IBoothService {
	public Booth add(Booth obj);
	public List<Booth> getAll();
	public void save();
	public Booth get(int id);
	public void remove(Booth obj);
	public Booth update(Booth obj);
}

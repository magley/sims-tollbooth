package core.malfunction;

import java.util.List;

import core.common.IRepo;

public interface IMalfunctionRepo extends IRepo<Malfunction> {
	public List<Malfunction> getAllPastDay();
}

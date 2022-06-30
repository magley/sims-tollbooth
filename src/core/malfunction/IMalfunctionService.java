package core.malfunction;

import java.util.List;

import core.common.IService;

public interface IMalfunctionService extends IService<Malfunction> {
	public List<Malfunction> getAllPastDay();
}

package core.pricelist;

import java.util.List;

import core.common.IService;
import core.pricelist.entry.PricelistEntry;

public interface IPricelistService extends IService<Pricelist> {

	public List<Pricelist> getContaining(PricelistEntry entry);
}

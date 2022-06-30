package core.pricelist;

public class PricelistController {

	private IPricelistService service;

	public PricelistController(IPricelistService service) {
		super();
		this.service = service;
	}

}

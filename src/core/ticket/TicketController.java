package core.ticket;

public class TicketController {

	private ITicketService service;

	public TicketController(ITicketService service) {
		super();
		this.service = service;
	}

}

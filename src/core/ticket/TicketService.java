package core.ticket;

import core.common.ServiceAdapter;

public class TicketService extends ServiceAdapter<Ticket> implements ITicketService {

	public TicketService(ITicketRepo repo) {
		super(repo);
	}

}

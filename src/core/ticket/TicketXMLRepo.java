package core.ticket;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class TicketXMLRepo extends DefaultXMLRepo<Ticket> implements ITicketRepo {

	public TicketXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<Ticket> getAll() {
		return master.getTickets();
	}

}

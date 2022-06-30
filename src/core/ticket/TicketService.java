package core.ticket;

import java.util.ArrayList;
import java.util.List;

import core.common.ServiceAdapter;
import core.util.IObserver;

public class TicketService extends ServiceAdapter<Ticket> implements ITicketService {

	private List<IObserver> observers;

	public TicketService(ITicketRepo repo) {
		super(repo);
		observers = new ArrayList<IObserver>();
	}

	@Override
	public Ticket add(Ticket t) {
		repo.add(t);
		notifyObservers(t);
		return t;
	}

	@Override
	public void registerObserver(IObserver o) {
		observers.add(o);
	}

	@Override
	public void unregisterObserver(IObserver o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Object e) {
		for (IObserver o : observers) {
			o.update(e);
		}
	}

}

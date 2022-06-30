package core.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import core.AppContext;
import core.booth.Booth;
import core.station.Station;
import core.ticket.Ticket;

public class TicketStream implements Runnable {

	private AppContext ctx;
	private Booth exitBooth;

	public TicketStream(AppContext ctx, Booth exitBooth) {
		this.ctx = ctx;
		this.exitBooth = exitBooth;
	}

	@Override
	public void run() {
		simulateTicketStream();
	}

	public void simulateTicketStream() {
		Random rnd = new Random();
		List<Station> entryStations = ctx.getStationService().getByType(Station.Type.ENTER);
		for (int i = 0; i < 3; ++i) {
			int idx = Math.abs(rnd.nextInt()) % entryStations.size();
			Station entryStation = entryStations.get(idx);

			List<Booth> entryBooths = ctx.getBoothService().getFrom(entryStation);
			idx = Math.abs(rnd.nextInt()) % entryBooths.size();
			Booth entryBooth = entryBooths.get(idx);

			ctx.getTicketService()
					.add(new Ticket(entryBooth, LocalDateTime.now().minusHours(2), this.exitBooth, null, String.valueOf(i)));

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

}

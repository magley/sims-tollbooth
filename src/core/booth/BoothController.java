package core.booth;

import core.common.FieldEmptyException;
import core.station.Station;
import core.station.exception.CodeAlreadyTakenException;

public class BoothController {
	private IBoothService service;
	
	public BoothController(IBoothService service) {
		this.service = service;
	}
	
	public void add(String code, Station station) throws FieldEmptyException, CodeAlreadyTakenException {
		if (code.isBlank() || station == null) {
			throw new FieldEmptyException();
		}
		if (service.getAll().stream().anyMatch(s -> s.getCode().equalsIgnoreCase(code))) {
			throw new CodeAlreadyTakenException();
		}

		service.add(new Booth(code, station));
	}
}

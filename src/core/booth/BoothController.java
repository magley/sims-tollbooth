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
		if (service.getAll().stream().anyMatch(b -> b.getCode().equalsIgnoreCase(code))) {
			throw new CodeAlreadyTakenException();
		}

		service.add(new Booth(code, station));
	}
	
	public void update(Booth booth, String code, Station station) throws CodeAlreadyTakenException, FieldEmptyException {
		if (code.isBlank() || station == null) {
			throw new FieldEmptyException();
		}
		if (service.getAll().stream().anyMatch(b -> b.getCode().equalsIgnoreCase(code) && b.getId() != booth.getId())) {
			throw new CodeAlreadyTakenException();
		}
		
		booth.setCode(code);	
		
		if (booth.getStation() != null)
			booth.getStation().removeTollBooth(booth);
		station.addTollBooth(booth);
	}
}

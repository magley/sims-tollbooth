package core.station;

import core.common.FieldEmptyException;
import core.station.exception.CodeAlreadyTakenException;
import core.station.location.Location;

public class StationController {
	private IStationService service;

	public StationController(IStationService service) {
		this.service = service;
	}
	
	public void update(Station station, String code, Station.Type type, Location location) throws CodeAlreadyTakenException, FieldEmptyException {
		if (code.isBlank() || type == null || location == null) {
			throw new FieldEmptyException();
		}
		if (service.getAll().stream().anyMatch(s -> s.getCode().equalsIgnoreCase(code) && s.getId() != station.getId())) {
			throw new CodeAlreadyTakenException();
		}
		
		station.setCode(code);
		station.setType(type);
		station.setLocation(location);
	}
}

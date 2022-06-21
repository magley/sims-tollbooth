package core.station.place;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class PlaceXMLRepo extends DefaultXMLRepo<Place> implements IPlaceRepo {
	public PlaceXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<Place> getAll() {
		return master.getPlaces();
	}
}

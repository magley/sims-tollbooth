package core.booth.state;

import core.booth.Booth;
import core.malfunction.Malfunction;

public class BoothActive extends BoothState {

	public BoothActive(Booth ctxt) {
		super(ctxt);
	}

	@Override
	public void pause() {
		this.ctxt.changeState(new BoothDeactivated(ctxt));
	}

	@Override
	public void malfunctionOccurred(Malfunction malf) {
		this.ctxt.changeState(new BoothDeactivated(ctxt));
	}

	@Override
	public void entry() {
		this.ctxt.setDisplayActive();
	}

	@Override
	public void vehicleStartedPassing() {
		this.ctxt.changeState(new BoothVehiclePassing(ctxt));
	}

}

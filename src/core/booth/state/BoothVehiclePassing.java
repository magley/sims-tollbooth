package core.booth.state;

import core.booth.Booth;
import core.malfunction.Malfunction;

public class BoothVehiclePassing extends BoothState {

	public BoothVehiclePassing(Booth ctxt) {
		super(ctxt);
	}

	@Override
	public void malfunctionOccurred(Malfunction malf) {
		this.ctxt.changeState(new BoothDeactivated(ctxt));
	}

	@Override
	public void entry() {
		this.ctxt.raiseRamp();
		this.ctxt.setSemaphoreGreen();
	}

	@Override
	public void exit() {
		this.ctxt.lowerRamp();
		this.ctxt.setSemaphoreRed();
	}

	@Override
	public void vehiclePassed() {
		this.ctxt.changeState(new BoothActive(ctxt));
	}

}

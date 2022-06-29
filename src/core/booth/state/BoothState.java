package core.booth.state;

import core.booth.Booth;
import core.malfunction.Malfunction;

public abstract class BoothState {
	protected Booth ctxt;

	public BoothState(Booth ctxt) {
		this.ctxt = ctxt;
	}
	
	public void pause() {

	}
	
	public void malfunctionOccurred(Malfunction malf) {
		
	}
	
	public void activate() {
		
	}
	
	public void entry() {
		
	}

	public void exit() {
		
	}

	public void vehiclePassed() {
		
	}

	public void vehicleStartedPassing() {
		
	}
}

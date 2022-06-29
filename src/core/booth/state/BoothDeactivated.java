package core.booth.state;

import core.booth.Booth;

public class BoothDeactivated extends BoothState {

	public BoothDeactivated(Booth ctxt) {
		super(ctxt);
	}

	@Override
	public void activate() {
		ctxt.changeState(new BoothActive(ctxt));
	}

	@Override
	public void entry() {
		ctxt.setDisplayX();
	}
	
}

package core.booth.observer;

import core.malfunction.Malfunction;

public interface IBoothObserver {
	public void notify(Malfunction malf);
	public void notifyState();
	public void notifyDevice();
}

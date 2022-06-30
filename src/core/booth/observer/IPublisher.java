package core.booth.observer;

import core.malfunction.Malfunction;

public interface IPublisher {
	public void addObserver(IBoothObserver o);
	public void removeObserver(IBoothObserver o);
	public void notifyObservers(Malfunction malf);
	public void notifyObserversState();
	public void notifyObserversDevice();
}

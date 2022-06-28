package core.booth.observer;

import core.malfunction.Malfunction;

public interface IPublisher {
	public void addObserver(IObserver o);
	public void removeObserver(IObserver o);
	public void notifyObservers(Malfunction malf);
}

package core.booth.observer;

import core.malfunction.Malfunction;

public interface IObserver {
	public void notify(Malfunction malf);
}

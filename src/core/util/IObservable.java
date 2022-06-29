package core.util;

public interface IObservable {

	public void registerObserver(IObserver o);

	public void unregisterObserver(IObserver o);

	public void notifyObservers(Object e);

}

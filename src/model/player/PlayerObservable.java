package model.player;

import controllers.Observer;

public interface PlayerObservable {
	/**
	 * Method that registers the observer passed as a parameter.
	 * @param obs
	 */
	void register(Observer obs);
	
	
	/**
	 * Method that alerts all registered observer.
	 */
	void notifyObserver();
	
}

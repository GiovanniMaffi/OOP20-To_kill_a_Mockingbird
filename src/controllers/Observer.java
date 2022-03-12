package controllers;

import model.player.Skin;


/**
 * Interface that describe an object that wishes to be notified when the state of another object changes.
 */
public interface Observer {

	/**
	 * Method that updates the state of the object based on the passed skin.
	 * @param skin
	 */
	void update(Skin skin);

}

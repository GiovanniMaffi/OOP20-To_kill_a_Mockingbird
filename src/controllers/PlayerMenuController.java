package controllers;

import model.player.Skin;

public interface PlayerMenuController extends Controller {
    
	void startGame(Skin skin);
	
	void closeMenu();

}

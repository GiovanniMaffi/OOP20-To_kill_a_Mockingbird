package controllers;

import model.player.Skin;
import view.GameView;
import view.PlayerMenuView;
import view.View;

public class PlayerMenuControllerImpl implements PlayerMenuController {

	private final View playerView;

    public PlayerMenuControllerImpl() {
        this.playerView = new PlayerMenuView(this);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        this.playerView.setup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeMenu() {
        final MainMenuController mainController = new MainMenuControllerImpl();
        this.playerView.exit();
        mainController.setup();
    }
    
	@Override
	public void startGame(final Skin skin) {
		final GameView gameV = new GameView(skin);
        this.playerView.exit();
        gameV.setup();
	}
}

package controllers;

import view.View;
import view.EndGameViewImpl;
import view.GameView;
import view.PlayerMenuView;

public class EndGameControllerImpl implements EndGameController {

    private final View view;

    public EndGameControllerImpl() {
        this.view = new EndGameViewImpl(this);
        this.view.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        System.exit(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restart() {
        final PlayerMenuController playerV = new PlayerMenuControllerImpl();
        this.view.exit();
        playerV.setup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        this.view.setup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void backToMenu() {
        final MainMenuController mainMenuController = new MainMenuControllerImpl();
        this.view.exit();
        mainMenuController.setup();
    }

}

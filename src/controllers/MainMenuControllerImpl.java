package controllers;

import view.GameView;
import view.View;
import view.MainMenuViewImpl;

public class MainMenuControllerImpl implements MainMenuController {

    private final View view;

    public MainMenuControllerImpl() {
        this.view = new MainMenuViewImpl(this);
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
    public void newGame() {
        final GameView gameV;
        this.view.exit();
        gameV = new GameView();
        gameV.setup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openShop() {
        final ShopMenuController shopController = new ShopMenuControllerImpl();
        this.view.exit();
        shopController.setup();
    }
}



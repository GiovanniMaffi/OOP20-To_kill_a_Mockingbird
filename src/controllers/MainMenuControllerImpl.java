package controllers;

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
        final PlayerMenuController playerMenuView = new PlayerMenuControllerImpl();
        this.view.exit();
        playerMenuView.setup();
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



package controllers;

import view.ShopView;

public class ShopMenuControllerImpl implements ShopMenuController {

    private final ShopView shopView;

    public ShopMenuControllerImpl() {
        this.shopView = new ShopView(this);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setup() {
        this.shopView.setup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeShop() {
        final MainMenuController mainController = new MainMenuControllerImpl();
        this.shopView.exit();
        mainController.setup();
    }

}

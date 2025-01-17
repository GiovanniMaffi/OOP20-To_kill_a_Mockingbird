package model.upgrades;

/**
 * Singleton class that check if the "Double Coins" upgrade has been purchased.
 */
public final class DoubleCoins {
    private static DoubleCoins instance;
    private boolean status;

    private DoubleCoins() {

    }

    public static synchronized DoubleCoins getInstance() {

        if (instance == null) {
            instance = new DoubleCoins();
        }

        return instance;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public boolean isPurchased() {
        return this.status;
    }
}

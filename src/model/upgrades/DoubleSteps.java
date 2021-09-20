package model.upgrades;

/**
 * Singleton class that check if the "Double Steps" upgrade has been purchased.
 */
public final class DoubleSteps {
    private static DoubleSteps instance;
    private boolean status;

    private DoubleSteps() {

    }

    public static synchronized DoubleSteps getInstance() {

        if (instance == null) {
            instance = new DoubleSteps();
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

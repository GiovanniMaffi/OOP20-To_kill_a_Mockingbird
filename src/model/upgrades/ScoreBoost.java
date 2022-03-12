package model.upgrades;

/**
 * Singleton class that check if the "Score Boost" upgrade has been purchased.
 */
public final class ScoreBoost {
    private static ScoreBoost instance;
    private boolean status;

    private ScoreBoost() {

    }

    public static synchronized ScoreBoost getInstance() {
        if (instance == null) {
            instance = new ScoreBoost();
        }

        return instance	;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public boolean isPurchased() {
        return this.status;
    }
}

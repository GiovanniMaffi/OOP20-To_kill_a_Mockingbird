package model.score;

/**
 * Singleton class that stores the number of coins that the player collects.
 */
public final class CoinCounter {

    private static CoinCounter instance;
    private int numberOfCoin = 100;

    private CoinCounter() {
    }

    public static synchronized CoinCounter getInstance() {

        if (instance == null) {
            instance = new CoinCounter();
        }

        return instance;
    }
    
    public int getCoins() {
        return this.numberOfCoin;
    }
    
    public void incrementCoins() {
        this.numberOfCoin++;
    }

    public void decreaseCoins(final int substracting) {
        this.numberOfCoin -= substracting; 
    }
}

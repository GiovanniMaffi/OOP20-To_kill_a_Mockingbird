package model.player;

import java.util.HashSet;
import java.util.Set;

import controllers.Observer;

public class PlayerMovementImpl extends PlayerImpl implements PlayerMovement, PlayerObservable {

    private static final double X_MOVE = 100;
    private static final double Y_MOVE = 100.2;
    
	private final Set<Observer> observerSet = new HashSet<>();
	private final Skin skin;

    public PlayerMovementImpl(final double xPos, final double yPos, final Skin skin) {
    	super(skin, xPos, yPos);
        this.skin = skin;
    }

    /**
     * move character one box up.
     */
    private void goUp() {

        this.setYLoc(this.getYLoc() - Y_MOVE);
    }

    /**
     * move character one box down.
     */
    private void goDown() {

        this.setYLoc(this.getYLoc() + Y_MOVE);
    }

    /**
     * move character one Box left.
     */
    private void goLeft() {

        this.setXLoc(this.getXLoc() - X_MOVE);
    }

    /**
     * move character one Box right.
     */
    private void goRight() {

        this.setXLoc(this.getXLoc() + X_MOVE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveDirection(final Directions direction) {

        switch (direction) {
            case UP:
                this.goUp();
                break;

            case DOWN:
                this.goDown();
                break;

            case RIGHT:
                this.goRight();
                break;

            case LEFT:
                this.goLeft();
                break;

            default:
                break;
        }
    }

	@Override
	public void register(final Observer obs) {
		this.observerSet.add(obs);
	}

	@Override
	public void notifyObserver() {
		for (final Observer observer : observerSet) {
			observer.update(this.skin);
		}
	}

	@Override
	public void triggerAbility() {
		// TODO Auto-generated method stub
		this.notifyObserver();
	}
}

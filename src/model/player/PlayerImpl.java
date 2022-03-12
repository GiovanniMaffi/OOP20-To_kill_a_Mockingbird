package model.player;

import java.util.HashMap;
import java.util.Map;

import model.map.BoxImpl;

public class PlayerImpl extends BoxImpl implements Player {

	private static final double MAP_SCROLL = 1;
	private static final Map<Skin, String> SKIN_TO_IMAGE = Map.of(Skin.CHICK, "bird.png", Skin.MOCKINGBIRD, "bird2.png",
			Skin.JAY, "bird3.png");

	private int collectedCoins;

	public PlayerImpl(final Skin skin, final double xPos, final double yPos) {
		setImage(SKIN_TO_IMAGE.get(skin));
		this.setXLoc(xPos);
		this.setYLoc(yPos);
		this.setYDir(MAP_SCROLL);
		this.collectedCoins = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCoins() {

		return this.collectedCoins;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoins(final int numberOfCoins) {

		this.collectedCoins = numberOfCoins;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void increaseCoins() {
		this.collectedCoins++;
	}

}

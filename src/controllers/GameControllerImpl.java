package controllers;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import input.player.KeyInput;
import input.player.KeyInputImpl;
import model.enemy.Vehicle;
import model.enemy.VehicleImpl;
import model.map.Box;
import model.map.Strip;
import model.map.StripImpl;
import model.player.PlayerMovement;
import model.player.PlayerMovementImpl;
import model.player.Skin;
import model.score.Coin;
import model.score.CoinCounter;
import model.score.CoinImpl;
import model.upgrades.ScoreBoost;
import model.upgrades.DoubleCoins;
import model.upgrades.DoubleSteps;
import view.GameView;

public class GameControllerImpl implements GameController {

	/**
	 * constants for generating the map.
	 */
	private static final int NSTRIP_TO_GENERATE = 11;
	private static final int BOXFORSTRIP = 8;
	private static final int MAP_SCROLL = 1;
	private static final int MAP_HIGHER_LIMIT = 800;

	/**
	 * constants for vehicles.
	 */
	private static final int VEHICLE_HIGHER_LIMIT = 900;
	private static final int VEHICLE_INFERIOR_LIMIT = -100;
	private static final int SPEED_MOLTIPLICATOR = 30;
	private static final int ADJUST_ON_ROAD = 10;
	private static final int SPAWN_CHARACTER_LINE = 2;
	private static final int COIN_SPAWN_PROB = 1;
	private static final int CAR_DELAY = 1500;
	private static final int TRAIN_DELAY = 5000;
	private static final int X_SPAWN_PLAYER = 400;
	private static final int Y_SPAWN_PLAYER = 600;
	private static final Long TIMER_DELAY_MOCK = 10_000L;
	private static final Long TIMER_DELAY_JAY = 5_000L;

	/**
	 * constants for upgrades.
	 */
	private static final int SCORE_BOOSTED = 50;

	/**
	 * local variables.
	 */
	private final Strip striscia = new StripImpl();
	private int score;
	private int realScore;
	private Boolean pause = false;
	private PlayerMovement player;
	private CollisionController collisionController;
	private KeyInput input;
	private final GameView gameView;
	private final Skin skin;
	private Set<Vehicle> slowedDownVehicles;
	private boolean slowVehicles;
	private boolean clearVehicles;

	public GameControllerImpl(final GameView gv, final Skin skin) {
		this.gameView = gv;
		this.skin = skin;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setup() {
		if (ScoreBoost.getInstance().isPurchased() && this.score == 0) {
			this.score = SCORE_BOOSTED;
			this.realScore = SCORE_BOOSTED;
		}
		this.player = new PlayerMovementImpl(X_SPAWN_PLAYER, Y_SPAWN_PLAYER, this.skin);
		this.collisionController = new CollisionControllerImpl(this.player);
		this.input = new KeyInputImpl(this, collisionController);
		this.player.register(this);
		this.slowedDownVehicles = new HashSet<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean getPause() {
		return pause;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPause() {
		this.pause = !this.pause;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveVehicle(final List<Vehicle> vehicles) {
		vehicles.forEach(v -> v.move());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void moveMoney(final List<Coin> coins) {
		coins.forEach(v -> v.move());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restartVehicle(final List<Vehicle> vehicles, final int delay) {

		vehicles.forEach(s -> {
			if (s.getXLoc() > (VEHICLE_HIGHER_LIMIT + s.getImage().getImgWidth()) && s.getXDir() > 0) {
				s.setXLoc(-(s.getXDir()) * SPEED_MOLTIPLICATOR - delay / 2);
			} else if (s.getXLoc() < (VEHICLE_INFERIOR_LIMIT - s.getImage().getImgWidth()) && s.getXDir() < 0) {
				s.setXLoc((s.getXDir()) * SPEED_MOLTIPLICATOR + delay);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startVehicle(final Vehicle vehicleManager, final List<Vehicle> vehicles, final int delay) {
		this.moveVehicle(vehicles);
		this.restartVehicle(vehicles, delay);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void scrollScreen(final List<ArrayList<Box>> allStrips) {
		allStrips.forEach(strip -> strip.forEach(box -> {
			box.setYDir(MAP_SCROLL);
			if (!"coin.png".equals(box.getName())) {
				box.move();
			}
		}));
	}

	/**
	 * main method for the map movement.
	 * 
	 * @param allStrips      contains all the strips that make up the map.
	 * @param vehicleManager
	 * @param cars           contains all cars
	 * @param coins          contains all coins
	 * @param trains         contains all trains
	 */
	public void actionPerformed(final List<ArrayList<Box>> allStrips, final Vehicle vehicleManager,
			final List<Vehicle> cars, final List<Coin> coins, final List<Vehicle> trains) {
		if (!pause) {
			this.scrollScreen(allStrips);

			if (!this.clearVehicles) {
				this.startVehicle(vehicleManager, cars, CAR_DELAY);
				this.startVehicle(vehicleManager, trains, TRAIN_DELAY);
			} else {
				trains.clear();
				cars.clear();
			}

			if (this.slowVehicles) {
				final Set<Vehicle> vehiclesToSlowedDown = cars.stream()
						.filter(car -> !this.slowedDownVehicles.contains(car)).collect(Collectors.toSet());
				vehiclesToSlowedDown.addAll(trains.stream().filter(car -> !this.slowedDownVehicles.contains(car))
						.collect(Collectors.toSet()));
				if (!vehiclesToSlowedDown.isEmpty()) {
					vehiclesToSlowedDown.forEach(vehicle -> vehicle.setXDir(vehicle.getXDir() / 2));
					this.slowedDownVehicles.addAll(vehiclesToSlowedDown);
				}

			} else if (!this.slowedDownVehicles.isEmpty()) {
				this.slowedDownVehicles.forEach(vehicle -> vehicle.setXDir(vehicle.getXDir() * 2));
				this.slowedDownVehicles.clear();
			}

			this.moveMoney(coins);
			this.player.move();

			collisionController.unBlockAll();

			final Optional<Vehicle> car = cars.stream().filter(x -> collisionController.collideWithVehicles(x))
					.findAny();
			if (car.orElse(null) != null) {
				this.gameOver();
			}

			final Optional<Vehicle> train = trains.stream().filter(x -> collisionController.collideWithVehicles(x))
					.findAny();
			if (train.orElse(null) != null) {
				this.gameOver();
			}

			final Optional<Coin> coin = coins.stream().filter(x -> collisionController.collideWithCoins(x)).findAny();
			if (coin.orElse(null) != null) {
				coins.remove(coin.get());
				CoinCounter.getInstance().incrementCoins();
				if (DoubleCoins.getInstance().isPurchased()) {
					CoinCounter.getInstance().incrementCoins();
				}
				player.increaseCoins();
			}

			allStrips.forEach(strip -> strip.forEach(box -> {
				if ("Tree.png".equals(box.getName())) {
					collisionController.checkTrees(box);
				}
			}));

			if (collisionController.checkBorders()) {
				this.gameOver();
			}

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spawnVehicle(final List<ArrayList<Box>> allStrips, final List<Vehicle> cars, final List<Vehicle> trains,
			final int i) {
		this.carOnRoad(allStrips, cars, i);
		this.trainOnRail(allStrips, trains, i);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void carOnRoad(final List<ArrayList<Box>> allStrips, final List<Vehicle> cars, final int i) {
		final Box tile = allStrips.get(i).get(0);
		if ("Road.png".equals(tile.getName())) {
			cars.add(new VehicleImpl().initializeCar(tile.getYLoc() + ADJUST_ON_ROAD));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void trainOnRail(final List<ArrayList<Box>> allStrips, final List<Vehicle> trains, final int i) {
		final Box tile = allStrips.get(i).get(0);
		if ("Rail.png".equals(tile.getName())) {
			trains.add(new VehicleImpl().initializeTrain(tile.getYLoc() + ADJUST_ON_ROAD));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spawnCoin(final List<ArrayList<Box>> allStrips, final List<Coin> coins, final int i, final int j) {
		final Box tile = allStrips.get(i).get(j);
		if (!"Tree.png".equals(tile.getName())) {
			coins.add(new CoinImpl().initializeCoin(tile.getXLoc(), tile.getYLoc()));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setInitialPosition(final List<ArrayList<Box>> allStrip) {
		for (int i = 0; i < NSTRIP_TO_GENERATE; i++) {
			if (i == SPAWN_CHARACTER_LINE || i == SPAWN_CHARACTER_LINE + 1) {
				allStrip.add(this.striscia.getSpecificStrip("Grass.png", i));
			} else {
				allStrip.add(this.striscia.getSpecificStrip("Grass.png", "Tree.png", i));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateMap(final List<ArrayList<Box>> allStrip, final List<Vehicle> vehiclesonroad,
			final List<Vehicle> trains, final List<Coin> coins) {
		final Random rndYLoc = new Random();
		for (int i = 0; i < NSTRIP_TO_GENERATE; i++) {
			if (allStrip.get(i).get(0).getYLoc() > MAP_HIGHER_LIMIT) {
				allStrip.set(i, this.striscia.getRndStrip(GameControllerImpl.NSTRIP_TO_GENERATE));
				this.spawnVehicle(allStrip, vehiclesonroad, trains, i);

				if (rndYLoc.nextInt(COIN_SPAWN_PROB + 1) == COIN_SPAWN_PROB) {
					this.spawnCoin(allStrip, coins, i, rndYLoc.nextInt(BOXFORSTRIP));
				}
			}
		}
	}

	/**
	 * TODO explain what this do.
	 * 
	 * @param e
	 */
	public void keyCatch(final KeyEvent e) {
		this.input.command(e);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerMovement getPlayer() {
		return this.player;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getScore() {
		return Math.max(this.score, this.realScore);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setScore(final int score) {
		if (score >= this.score) {
			this.score = score;
		}
		this.realScore = score;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRealScore() {
		return this.realScore;
	}

	/**
	 * {@inheritDoc}
	 */
	public void gameOver() {
		ScoreBoost.getInstance().setStatus(false);
		DoubleCoins.getInstance().setStatus(false);
		DoubleSteps.getInstance().setStatus(false);
		final EndGameController endGame = new EndGameControllerImpl();
		this.setPause();
		endGame.setup();
		this.gameView.exit();
	}

	@Override
	public void update(final Skin s) {
		switch (s) {
		case MOCKINGBIRD:
			this.slowVehicles = true;
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					slowVehicles = false;
				}
			}, TIMER_DELAY_MOCK);

			break;

		case JAY:
			this.clearVehicles = true;
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					clearVehicles = false;
				}
			}, TIMER_DELAY_JAY);
			break;

		default:
			break;
		}

	}

}

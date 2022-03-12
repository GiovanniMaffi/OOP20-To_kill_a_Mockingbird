package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import controllers.PlayerMenuController;
import controllers.ShopMenuController;
import model.player.Skin;
import model.score.CoinCounter;
import model.upgrades.DoubleCoins;
import model.upgrades.DoubleSteps;
import model.upgrades.ScoreBoost;
import view.ShopView.PanelMenu;

public class PlayerMenuView implements View {
	/**
	 * constants.
	 */
	private static final int MENU_WIDTH = 600;
	private static final int MENU_HEIGHT = 700;
	private static final int HALF_MENU_WIDTH = MENU_WIDTH / 2 - 80;
	private static final int IMAGE_WIDTH = 150;
	private static final int IMAGE_HEIGHT = 70;
	private static final int EXIT_BUTTON_SIZE = 50;
	private static final int FRAMEXLOC = 350;
	private static final int FRAMEYLOC = 10;
	private static final Color BACKGROUND_COLOR = new Color(60, 179, 113);
	private static final int BUTTON_Y_LOCATION = 419;
	private static final int FIRST_IMAGE_X = (MENU_WIDTH / 2 - 80) - 177;
	private static final int THIRD_IMAGE_X = HALF_MENU_WIDTH + 180;
	private static final int EXIT_BUTTON_LOCATION_X = 10;
	private static final int EXIT_BUTTON_LOCATION_Y = 18;

	/**
	 * local variables.
	 */
	private final JFrame frame = new JFrame();
	private JLabel lblBackground;
	private JButton firstBirdButton, secondBirdButton, thirdBirdButton, exitButton;
	private Rectangle rFirstBird, rSecondBird, rThirdBird, rExit, rLblBackground;
	private final PlayerMenuController playerController;

	public PlayerMenuView(final PlayerMenuController controller) {
		final PanelMenu menuPanel = new PanelMenu();
		this.playerController = controller;
		this.frame.setTitle("Shop");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setLocation(FRAMEXLOC, FRAMEYLOC);
		this.frame.setSize(MENU_WIDTH, MENU_HEIGHT);
		this.frame.getContentPane().add(menuPanel);
		this.frame.setBackground(BACKGROUND_COLOR);
		this.frame.setVisible(true);
		this.frame.setLayout(null);
	}

	class PanelMenu extends JLayeredPane {

		private static final long serialVersionUID = 1L;

		PanelMenu() {

			/**
			 * create the background of the shop.
			 */
			rLblBackground = new Rectangle(0, 0, MENU_WIDTH, MENU_HEIGHT);
			final ImageIcon background = new ImageIcon(new ImageIcon(this.getClass().getResource("/PlayerMenu.png"))
					.getImage().getScaledInstance(MENU_WIDTH, MENU_HEIGHT, Image.SCALE_SMOOTH));

			lblBackground = new JLabel(background);
			lblBackground.setBounds(rLblBackground);
			add(lblBackground, DEFAULT_LAYER);

			/**
			 * set images for the shop's buttons.
			 */
			final ImageIcon chooseImage = new ImageIcon(new ImageIcon(this.getClass().getResource("/chooseButton.png"))
					.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH));
			final ImageIcon exitImage = new ImageIcon(new ImageIcon(this.getClass().getResource("/backButton.png"))
					.getImage().getScaledInstance(EXIT_BUTTON_SIZE, EXIT_BUTTON_SIZE, Image.SCALE_SMOOTH));

			rFirstBird = new Rectangle(FIRST_IMAGE_X, BUTTON_Y_LOCATION, IMAGE_WIDTH, IMAGE_HEIGHT);
			rSecondBird = new Rectangle(HALF_MENU_WIDTH, BUTTON_Y_LOCATION, IMAGE_WIDTH, IMAGE_HEIGHT);
			rThirdBird = new Rectangle(THIRD_IMAGE_X, BUTTON_Y_LOCATION, IMAGE_WIDTH, IMAGE_HEIGHT);
			rExit = new Rectangle(EXIT_BUTTON_LOCATION_X, EXIT_BUTTON_LOCATION_Y, EXIT_BUTTON_SIZE, EXIT_BUTTON_SIZE);

			firstBirdButton = new JButton("", chooseImage);
			firstBirdButton.setBorder(BorderFactory.createEmptyBorder());
			firstBirdButton.setBounds(rFirstBird);
			lblBackground.add(firstBirdButton);

			secondBirdButton = new JButton("", chooseImage);
			secondBirdButton.setBorder(BorderFactory.createEmptyBorder());
			secondBirdButton.setBounds(rSecondBird);
			lblBackground.add(secondBirdButton);

			thirdBirdButton = new JButton("", chooseImage);
			thirdBirdButton.setBorder(BorderFactory.createEmptyBorder());
			thirdBirdButton.setBounds(rThirdBird);
			lblBackground.add(thirdBirdButton);

			exitButton = new JButton("", exitImage);
			exitButton.setBorder(BorderFactory.createEmptyBorder());
			exitButton.setBounds(rExit);
			lblBackground.add(exitButton);

			exitButton.addActionListener(e -> {
				playerController.closeMenu();
			});

			final ActionListener firstButtonAL = e -> {
				playerController.startGame(Skin.CHICK);
			};

			final ActionListener sercondButtonAL = e -> {
				playerController.startGame(Skin.MOCKINGBIRD);
			};

			final ActionListener thirdButtonAL = e -> {
				playerController.startGame(Skin.JAY);
			};

			firstBirdButton.addActionListener(firstButtonAL);
			secondBirdButton.addActionListener(sercondButtonAL);
			thirdBirdButton.addActionListener(thirdButtonAL);

		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setup() {
		this.frame.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exit() {
		this.frame.dispose();
	}

}

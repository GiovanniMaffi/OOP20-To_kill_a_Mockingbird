package view;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import controllers.MainMenuController;

public class MainMenuViewImpl implements View {

	private static final int MENU_WIDTH = 600;
	private static final int MENU_HEIGHT = 700;
	private static final int HALF_MENU_WIDTH = MENU_WIDTH / 2 - 80;
	private static final int IMAGE_WIDTH = 150;
	private static final int IMAGE_HEIGHT = 70;
	private static final int FRAMEXLOC = 350;
	private static final int FRAMEYLOC = 10;
	private static final int FIRST_IMAGE_Y = 500;
	private static final Color BACKGROUND_COLOR = new Color(60, 179, 113);
	private static final int SECOND_IMAGE_Y = 500;
	private static final int SECOND_IMAGE_X = HALF_MENU_WIDTH - 200;
	private static final int THIRD_IMAGE_Y = 500;
	private static final int THIRD_IMAGE_X = HALF_MENU_WIDTH + 200;

	private final MainMenuController controller;

	private final JFrame frame = new JFrame();
	private JLabel lblBackground;
	private JButton startButton, controlsButton, shopButton;
	private Rectangle rStartButton, rControlsButton, rShopButton, rLblBackground;

	public MainMenuViewImpl(final MainMenuController controller) {
		final PanelMenu menuPanel = new PanelMenu();
		this.controller = controller;
		this.frame.setTitle("To Kill a Mockingbird");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setLocation(FRAMEXLOC, FRAMEYLOC);
		this.frame.setSize(MENU_WIDTH, MENU_HEIGHT);
		this.frame.getContentPane().add(menuPanel);
		this.frame.setBackground(BACKGROUND_COLOR);
		this.frame.setVisible(true);
		// Set layout to absolute for buttons.
		this.frame.setLayout(null);
	}

	class PanelMenu extends JLayeredPane {

		private static final long serialVersionUID = 1L;

		PanelMenu() {

			rLblBackground = new Rectangle(0, 0, MENU_WIDTH, MENU_HEIGHT);
			final ImageIcon background = new ImageIcon(new ImageIcon(this.getClass().getResource("/MainMenu.png"))
					.getImage().getScaledInstance(MENU_WIDTH, MENU_HEIGHT, Image.SCALE_SMOOTH));

			lblBackground = new JLabel(background);
			lblBackground.setBounds(rLblBackground);
			add(lblBackground, DEFAULT_LAYER);

			final ImageIcon startImage = new ImageIcon(new ImageIcon(this.getClass().getResource("/startButton.png"))
					.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH));
			final ImageIcon controlsImage = new ImageIcon(
					new ImageIcon(this.getClass().getResource("/controlsButton.png")).getImage()
							.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH));
			final ImageIcon shopImage = new ImageIcon(new ImageIcon(this.getClass().getResource("/shopButton.png"))
					.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH));
			rStartButton = new Rectangle(HALF_MENU_WIDTH, FIRST_IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT);
			rControlsButton = new Rectangle(SECOND_IMAGE_X, SECOND_IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT);
			rShopButton = new Rectangle(THIRD_IMAGE_X, THIRD_IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT);
			// Create button component, set image, remove borders.
			startButton = new JButton("", startImage);
			startButton.setBounds(rStartButton);
			startButton.setBorder(BorderFactory.createEmptyBorder());
			lblBackground.add(startButton);

			controlsButton = new JButton("", controlsImage);
			controlsButton.setBorder(BorderFactory.createEmptyBorder());
			controlsButton.setBounds(rControlsButton);
			lblBackground.add(controlsButton);

			shopButton = new JButton("", shopImage);
			shopButton.setBorder(BorderFactory.createEmptyBorder());
			shopButton.setBounds(rShopButton);
			lblBackground.add(shopButton);

			startButton.addActionListener(e -> {
				controller.newGame();
			});

			controlsButton.addActionListener(e -> {
				JOptionPane.showMessageDialog(null, "Arrow Keys:  Move the character."
						+ "\nEsc:  Pause / Resume the game." + "\nQ: Trigger ability.");
			});

			shopButton.addActionListener(equals -> {
				controller.openShop();
			});
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exit() {
		this.frame.dispose();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setup() {

		this.frame.setVisible(true);
	}
}

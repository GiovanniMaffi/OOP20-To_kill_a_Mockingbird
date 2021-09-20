package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import controllers.ShopMenuController;
import model.score.CoinCounter;
import model.upgrades.ScoreBoost;
import model.upgrades.DoubleCoins;
import model.upgrades.DoubleSteps;

public class ShopView implements View {

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
    private static final int BUTTON_Y_LOCATION = 400;
    private static final int FIRST_IMAGE_X = (MENU_WIDTH / 2 - 80) - 177;
    private static final int THIRD_IMAGE_X = HALF_MENU_WIDTH + 180;
    private static final int EXIT_BUTTON_LOCATION = 10;
    private static final int COIN_JLABEL_X = 541;
    private static final int COIN_JLABEL_Y = 45;
    private static final int COIN_JLABEL_WIDTH = 130;
    private static final int COIN_JLABEL_HEIGHT = 30;
    private static final int FONT_SIZE = 25;
    private static final int FIRST_POWERUP_COST = 10;
    private static final int SECOND_POWERUP_COST = 15;
    private static final int THIRD_POWERUP_COST = 20;

    /**
     * local variables.
     */
    private final JFrame frame = new JFrame();
    private JLabel lblBackground, lblCoins;
    private JButton scoreBoostButton, doubleCoinsButton, doubleStepsButton, exitButton;
    private Rectangle rScoreBoost, rDoubleCoin, rDoubleSteps, rExit, rLblBackground;
    private final ShopMenuController shopController;

    public ShopView(final ShopMenuController controller) {
        final PanelMenu menuPanel = new PanelMenu();
        this.shopController = controller;
        this.frame.setTitle("Shop");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setLocation(FRAMEXLOC, FRAMEYLOC);
        this.frame.setSize(MENU_WIDTH, MENU_HEIGHT);
        this.frame.getContentPane().add(menuPanel);
        this.frame.setBackground(BACKGROUND_COLOR);
        this.frame.setVisible(true);
        this.frame.setLayout(null);
        this.checkButtons();
    }

    class PanelMenu extends JLayeredPane {

        private static final long serialVersionUID = 1L;

        PanelMenu() {

            /**
             * create the background of the shop.
             */
            rLblBackground = new Rectangle(0, 0, MENU_WIDTH, MENU_HEIGHT);
            final ImageIcon background = new ImageIcon(new ImageIcon(this.getClass().getResource("/ShopMenu.png"))
                    .getImage().getScaledInstance(MENU_WIDTH, MENU_HEIGHT, Image.SCALE_SMOOTH));

            lblBackground = new JLabel(background);
            lblBackground.setBounds(rLblBackground);
            add(lblBackground, DEFAULT_LAYER);

            /**
             * set images for the shop's buttons.
             */
            final ImageIcon buyImage = new ImageIcon(new ImageIcon(this.getClass().getResource("/buyButton.png"))
                    .getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH));
            final ImageIcon exitImage = new ImageIcon(new ImageIcon(this.getClass().getResource("/backButton.png"))
                    .getImage().getScaledInstance(EXIT_BUTTON_SIZE, EXIT_BUTTON_SIZE, Image.SCALE_SMOOTH));

            rScoreBoost = new Rectangle(FIRST_IMAGE_X, BUTTON_Y_LOCATION, IMAGE_WIDTH, IMAGE_HEIGHT);
            rDoubleCoin = new Rectangle(HALF_MENU_WIDTH, BUTTON_Y_LOCATION, IMAGE_WIDTH, IMAGE_HEIGHT);
            rDoubleSteps = new Rectangle(THIRD_IMAGE_X, BUTTON_Y_LOCATION, IMAGE_WIDTH, IMAGE_HEIGHT);
            rExit = new Rectangle(EXIT_BUTTON_LOCATION, EXIT_BUTTON_LOCATION, EXIT_BUTTON_SIZE, EXIT_BUTTON_SIZE);

            scoreBoostButton = new JButton("", buyImage);
            scoreBoostButton.setBorder(BorderFactory.createEmptyBorder());
            scoreBoostButton.setBounds(rScoreBoost);
            lblBackground.add(scoreBoostButton);

            doubleCoinsButton = new JButton("", buyImage);
            doubleCoinsButton.setBorder(BorderFactory.createEmptyBorder());
            doubleCoinsButton.setBounds(rDoubleCoin);
            lblBackground.add(doubleCoinsButton);

            doubleStepsButton = new JButton("", buyImage);
            doubleStepsButton.setBorder(BorderFactory.createEmptyBorder());
            doubleStepsButton.setBounds(rDoubleSteps);
            lblBackground.add(doubleStepsButton);

            exitButton = new JButton("", exitImage);
            exitButton.setBorder(BorderFactory.createEmptyBorder());
            exitButton.setBounds(rExit);
            lblBackground.add(exitButton);

            lblCoins = new JLabel();
            lblCoins.setText(String.valueOf(CoinCounter.getInstance().getCoins()));
            lblCoins.setBounds(COIN_JLABEL_X, COIN_JLABEL_Y, COIN_JLABEL_WIDTH, COIN_JLABEL_HEIGHT);
            lblCoins.setFont(new Font("Bernard MT Condensed", Font.PLAIN, FONT_SIZE));
            lblBackground.add(lblCoins);

            exitButton.addActionListener(e -> {
                shopController.closeShop();
            });

            scoreBoostButton.addActionListener(e -> {
                if (CoinCounter.getInstance().getCoins() < FIRST_POWERUP_COST) {
                    JOptionPane.showMessageDialog(null, "NON HAI ABBASTANZA MONETE PER COMPRARE QUESTO POTENZIAMENTO");
                } else {
                    CoinCounter.getInstance().decreaseCoins(FIRST_POWERUP_COST);
                    ScoreBoost.getInstance().setStatus(true);
                    scoreBoostButton.setEnabled(false);
                    this.refreshCoinCounter();
                }

            });

            doubleCoinsButton.addActionListener(e -> {
                if (CoinCounter.getInstance().getCoins() < SECOND_POWERUP_COST) {
                    JOptionPane.showMessageDialog(null, "NON HAI ABBASTANZA MONETE PER COMPRARE QUESTO POTENZIAMENTO");
                } else {
                    CoinCounter.getInstance().decreaseCoins(SECOND_POWERUP_COST);
                    DoubleCoins.getInstance().setStatus(true);
                    doubleCoinsButton.setEnabled(false);
                    this.refreshCoinCounter();
                }
            });

            doubleStepsButton.addActionListener(e -> {
                if (CoinCounter.getInstance().getCoins() < THIRD_POWERUP_COST) {
                    JOptionPane.showMessageDialog(null, "NON HAI ABBASTANZA MONETE PER COMPRARE QUESTO POTENZIAMENTO");
                } else {
                    CoinCounter.getInstance().decreaseCoins(THIRD_POWERUP_COST);
                    DoubleSteps.getInstance().setStatus(true);
                    doubleStepsButton.setEnabled(false);
                    this.refreshCoinCounter();
                }
            });
        }

        private void refreshCoinCounter() {
            lblCoins.setText(String.valueOf(CoinCounter.getInstance().getCoins()));
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

    private void checkButtons() {
        if (ScoreBoost.getInstance().isPurchased()) {
            this.scoreBoostButton.setEnabled(false);
        }
        if (DoubleCoins.getInstance().isPurchased()) {
            this.doubleCoinsButton.setEnabled(false);
        }
        if (DoubleSteps.getInstance().isPurchased()) {
            this.doubleStepsButton.setEnabled(false);
        }
    }
}

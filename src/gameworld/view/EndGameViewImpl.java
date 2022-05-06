package gameworld.view;

import gameworld.controller.Features;
import gameworld.service.ReadOnlyGameWorld;
import gameworld.view.listeners.ButtonListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class Implements {@link EndGame} interface to provide the game over
 * information to the user and also offer restart and quit game functionality.
 */
public class EndGameViewImpl extends JFrame implements EndGame {

  private static final long serialVersionUID = -2179965453492637485L;
  private ReadOnlyGameWorld readOnlyGameWorld;
  private GridBagConstraints gc;
  private JLabel gameOverLabel;
  private JButton restartBtn;
  private JButton quitBtn;
  private List<JButton> listOfButtons;
  private Font buttonFont;
  private Font labelFont;
  private String gameOverInfo;
  private ValidateParameterHelper validateParameterHelper;

  /**
   * Constructor that creates an object of type {@link EndGameViewImpl}.
   * 
   * @param readOnlyGameWorld Object to type {@link ReadOnlyGameWorld}.
   * @throw {@link IllegalArgumentException} if {@link ReadOnlyGameWorld} object
   *        is null.
   */
  public EndGameViewImpl(ReadOnlyGameWorld readOnlyGameWorld) {
    super("END GAME");
    validateParameterHelper = new ValidateParameterHelper();
    validateParameterHelper.validateReadOnlyModel(readOnlyGameWorld);
    this.readOnlyGameWorld = readOnlyGameWorld;
  }

  private void initializeComponents() {

    buttonFont = new Font("Serif", Font.BOLD, 15);
    labelFont = new Font("Serif", Font.ITALIC, 40);
    gc = new GridBagConstraints();
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.insets = new Insets(5, 0, 5, 0);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setBackground(Color.black);
    this.setLayout(new GridBagLayout());
    listOfButtons = new ArrayList<JButton>();

    createRestartButton();
    createQuitGameButton();

    for (JButton button : listOfButtons) {
      button.setForeground(Color.black);
      button.setFont(buttonFont);
      button.setFocusable(false);
    }

    setView();

  }

  private void createRestartButton() {
    try {
      restartBtn = new JButton("RESTART GAME");
      restartBtn.setActionCommand("RESTART GAME");
      restartBtn.setBackground(Color.GREEN);
      URL restartBtnPath = this.getClass().getResource("/img/restart.jpg");
      restartBtn.setIcon(new ImageIcon(ImageIO.read(restartBtnPath)
          .getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
      listOfButtons.add(restartBtn);
    } catch (IOException e) {
      throw new IllegalArgumentException("Couldn't read Restart Icon!!!");
    }

  }

  private void createQuitGameButton() {
    try {
      quitBtn = new JButton("QUIT GAME");
      quitBtn.setActionCommand("QUIT FOR END GAME");
      quitBtn.setBackground(Color.red);
      URL quitBtnPath = this.getClass().getResource("/img/quit.jpg");
      quitBtn.setIcon(new ImageIcon(ImageIO.read(quitBtnPath).getScaledInstance(15,
          15, Image.SCALE_DEFAULT)));
      this.add(quitBtn, gc);
      listOfButtons.add(quitBtn);
    } catch (IOException e) {
      throw new IllegalArgumentException("Couldn't read Quit Icon!!!");
    }

  }

  @Override
  public void setupView() {
    initializeComponents();
  }

  @Override
  public void setFeatures(Features features) {
    validateParameterHelper.validateFeatures(features);
    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();

    buttonClickedMap.put("RESTART GAME", features::restartGame);

    buttonClickedMap.put("QUIT FOR END GAME", () -> {
      features.promptToQuitGame();
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);

    restartBtn.addActionListener(buttonListener);
    quitBtn.addActionListener(buttonListener);

  }

  private void setView() {
    gameOverInfo = "";
    gameOverLabel = new JLabel(gameOverInfo);
    gameOverLabel.setFont(labelFont);
    gameOverLabel.setForeground(Color.YELLOW);
    gameOverLabel.setFocusable(true);
    this.add(gameOverLabel, gc);
    this.add(restartBtn, gc);
    this.add(quitBtn, gc);

    setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
    pack();
  }

  @Override
  public void makeVisible() {
    setVisible(true);

  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void makeInvisible() {
    setVisible(false);

  }

  @Override
  public void updateWinner() {
    gameOverLabel = new JLabel(readOnlyGameWorld.getWinner());
    gameOverLabel.setFont(labelFont);
    gameOverLabel.setForeground(Color.YELLOW);
    gameOverLabel.setFocusable(true);
    this.add(gameOverLabel, gc);
    revalidate();
    repaint();

  }

}

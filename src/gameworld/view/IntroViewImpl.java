package gameworld.view;

import gameworld.controller.Features;
import gameworld.service.ReadOnlyGameWorld;
import gameworld.view.listeners.ButtonListener;
import gameworld.view.panels.PreScreenPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * This class implements {@link IntroView} interface and provides basic
 * information on the welcome screen.
 *
 */
public class IntroViewImpl extends JFrame implements IntroView {

  private static final long serialVersionUID = -2179965453492637485L;
  private ReadOnlyGameWorld readOnlyGameWorld;
  private GridBagConstraints gbc;
  private Font gameTitleFont;
  private Font creatorsFont;
  private JLabel gameTitle;
  private JLabel creators;
  private JLabel creator1;
  private JLabel creator2;
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem useDefaultMapOption;
  private JMenuItem useCustomMapOption;
  private JMenuItem quitOption;
  private Font buttonFont;
  private final ValidateParameterHelper validateParameterHelper;

  /**
   * Constructs an object of type {@link IntroViewImpl}.
   * 
   * @param readOnlyGameWorld Object to type {@link ReadOnlyGameWorld}.
   * @throw {@link IllegalArgumentException} if {@link ReadOnlyGameWorld} object
   *        is null.
   */
  public IntroViewImpl(ReadOnlyGameWorld readOnlyGameWorld) {
    super("Welcome Screen");
    validateParameterHelper = new ValidateParameterHelper();
    validateParameterHelper.validateReadOnlyModel(readOnlyGameWorld);
    this.readOnlyGameWorld = readOnlyGameWorld;
  }

  private void initializeComponents() {

    buttonFont = new Font("Serif", Font.BOLD, 15);
    gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gameTitleFont = new Font("Serif", Font.BOLD, 40);
    creatorsFont = new Font("Serif", Font.BOLD, 20);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    URL bgImgPath = this.getClass().getResource("/img/WelcomeImage.jpg");
    try {
      setContentPane(
          new PreScreenPanel(Toolkit.getDefaultToolkit().getImage(bgImgPath.toURI().toString())));
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("File is missing in resource folder!!!");
    }
    setLayout(new GridBagLayout());

    gameTitle = new JLabel(readOnlyGameWorld.getWorldDesc());
    creators = new JLabel("CREATORS");
    creator1 = new JLabel("GANESH PRASAD");
    creator2 = new JLabel("HARSHITHA YEDDULA");
    gameTitle.setForeground(Color.BLACK);
    creators.setForeground(Color.YELLOW);
    creators.setFont(creatorsFont);
    creator1.setForeground(Color.YELLOW);
    creator1.setFont(creatorsFont);
    creator2.setForeground(Color.YELLOW);
    creator2.setFont(creatorsFont);
    gameTitle.setFont(gameTitleFont);

    createMenuBar();

    add(gameTitle, gbc);
    add(creators, gbc);
    add(creator1, gbc);
    add(creator2, gbc);

    setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
    pack();

  }

  private void createMenuBar() {
    try {
      menuBar = new JMenuBar();
      menu = new JMenu("Menu Options");

      useDefaultMapOption = new JMenuItem("START GAME WITH DEFAULT MAP");
      useDefaultMapOption.setActionCommand("START GAME WITH DEFAULT MAP");
      useDefaultMapOption.setBackground(Color.YELLOW);
      useDefaultMapOption.setForeground(Color.black);
      useDefaultMapOption.setFont(buttonFont);
      URL useDefaultMapPath = this.getClass().getResource("/img/play.png");
      useDefaultMapOption.setIcon(new ImageIcon(
              ImageIO.read(useDefaultMapPath).getScaledInstance(15,
              15, Image.SCALE_DEFAULT)));

      useCustomMapOption = new JMenuItem("START GAME WITH CUSTOM MAP");
      useCustomMapOption.setActionCommand("START GAME WITH CUSTOM MAP");
      useCustomMapOption.setBackground(Color.YELLOW);
      useCustomMapOption.setForeground(Color.black);
      useCustomMapOption.setFont(buttonFont);
      useCustomMapOption.setIcon(new ImageIcon(ImageIO.read(new File("res/img/play.png"))
          .getScaledInstance(15, 15, Image.SCALE_DEFAULT)));

      quitOption = new JMenuItem("QUIT");
      quitOption.setActionCommand("QUIT FOR INTRO VIEW");
      quitOption.setBackground(Color.RED);
      quitOption.setForeground(Color.black);
      quitOption.setFont(buttonFont);
      URL quitPath = this.getClass().getResource("/img/quit.jpg");
      quitOption.setIcon(new ImageIcon(ImageIO.read(quitPath).getScaledInstance(15,
              15, Image.SCALE_DEFAULT)));

      menu.add(useDefaultMapOption);
      menu.add(useCustomMapOption);
      menu.add(quitOption);
      menuBar.add(menu);

      setJMenuBar(menuBar);
    } catch (IOException e) {
      throw new IllegalArgumentException("Couldn't read Icons!!!");
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
    buttonClickedMap.put("START GAME WITH DEFAULT MAP", features::startWithDefaultMap);

    buttonClickedMap.put("START GAME WITH CUSTOM MAP", features::startCustomMap);

    buttonClickedMap.put("QUIT FOR INTRO VIEW", () -> {
      features.promptToQuitGame();
    });
    
    buttonListener.setButtonClickedActionMap(buttonClickedMap);

    useCustomMapOption.addActionListener(buttonListener);
    useDefaultMapOption.addActionListener(buttonListener);
    quitOption.addActionListener(buttonListener);
  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void makeInvisible() {
    setVisible(false);
  }

}
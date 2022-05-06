package gameworld.view;

import gameworld.constants.PlayerType;
import gameworld.controller.Features;
import gameworld.service.ReadOnlyGameWorld;
import gameworld.view.listeners.ButtonListener;
import gameworld.view.panels.PreScreenPanel;
import gameworld.view.panels.TurnScreenPanel;
import java.awt.BorderLayout;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

/**
 * This class implements {@link PreGameView} interface the allows users to add
 * players to the game and start the game.
 *
 */
public class PreGameViewImpl extends JFrame implements PreGameView {

  private static final long serialVersionUID = -2179965453492637485L;
  private JRadioButton humanPlayerRadioButton;
  private JRadioButton computerPlayerRadioButton;
  private ButtonGroup group;
  private JButton startGameButton;
  private Font buttonsFont;
  private JMenuBar menuBar;
  private JMenu menu;
  private JButton addPlayerButton;
  private JButton getSpaceInfoButton;
  private JPanel topPanel;
  private JPanel buttonsPanel;
  private GridBagConstraints gc;
  private JPanel imagePanel;
  private final ReadOnlyGameWorld readOnlyGameWorld;
  private List<JButton> listOfButtons;
  private JMenuItem quitOption;
  private TurnScreenPanel preGameImage;
  private JScrollPane scrollPane;
  private final ValidateParameterHelper validateParameterHelper;

  /**
   * Constructs an object of type {@link PreGameViewImpl}.
   * 
   * @param readOnlyGameWorld Object to type {@link ReadOnlyGameWorld}.
   * @throw {@link IllegalArgumentException} if {@link ReadOnlyGameWorld} object
   *        is null.
   */
  public PreGameViewImpl(ReadOnlyGameWorld readOnlyGameWorld) {
    super("PreGame Screen");
    validateParameterHelper = new ValidateParameterHelper();
    validateParameterHelper.validateReadOnlyModel(readOnlyGameWorld);
    this.readOnlyGameWorld = readOnlyGameWorld;
  }

  private void initializeComponents() {

    buttonsFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    gc = new GridBagConstraints();
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.insets = new Insets(5, 0, 5, 0);
    listOfButtons = new ArrayList<>();

    createMenuBar();
    createAddPlayerButton();
    createGetSpaceInfoButton();
    createPlayerTypeRadioButtons();
    createStartGameButton();
    setTopPanel();
    setButtonsPanel();
    createImagePanel();

    for (JButton button : listOfButtons) {
      button.setBackground(Color.yellow);
      button.setForeground(Color.black);
      button.setFont(buttonsFont);
      button.setFocusable(false);
    }

    setView();

  }

  private void createImagePanel() {
    imagePanel = new JPanel();
  }

  private void setButtonsPanel() {
    buttonsPanel = new JPanel(new GridBagLayout());
    buttonsPanel.setPreferredSize(new Dimension(400, 300));
    buttonsPanel.add(addPlayerButton, gc);
    buttonsPanel.add(Box.createVerticalStrut(100));
    buttonsPanel.add(getSpaceInfoButton, gc);
    buttonsPanel.add(startGameButton, gc);

  }

  private void setTopPanel() {
    topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());
    getContentPane().add(topPanel);

  }

  private void createAddPlayerButton() {
    try {
      addPlayerButton = new JButton("ADD PLAYER");
      addPlayerButton.setActionCommand("ADD PLAYER BUTTON");
      URL userIcon = this.getClass().getResource("/img/userIcon.png");
      addPlayerButton.setIcon(new ImageIcon(ImageIO.read(userIcon)
          .getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
      listOfButtons.add(addPlayerButton);

    } catch (IOException e) {
      throw new IllegalArgumentException("Error while Reading Icons!!!");
    }

  }

  private void createGetSpaceInfoButton() {
    getSpaceInfoButton = new JButton("GET SPACE INFORMATION");
    getSpaceInfoButton.setActionCommand("GET SPACE INFO BUTTON");
    listOfButtons.add(getSpaceInfoButton);
  }

  private void createStartGameButton() {
    try {
      startGameButton = new JButton("START GAME");
      startGameButton.setActionCommand("START GAME BUTTON");
      startGameButton.setBackground(Color.RED);
      startGameButton.setForeground(Color.WHITE);
      startGameButton.setFont(buttonsFont);
      URL startGame = this.getClass().getResource("/img/playIcon.png");
      startGameButton.setIcon(new ImageIcon(ImageIO.read(startGame)
          .getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
      startGameButton.setEnabled(false);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error while Reading Icons!!!");
    }
  }

  private void createPlayerTypeRadioButtons() {
    humanPlayerRadioButton = new JRadioButton("HUMAN");
    computerPlayerRadioButton = new JRadioButton("COMPUTER");
    group = new ButtonGroup();
    group.add(humanPlayerRadioButton);
    group.add(computerPlayerRadioButton);

  }

  private void createMenuBar() {

    try {
      menuBar = new JMenuBar();
      menu = new JMenu("Menu Options");
      quitOption = new JMenuItem("QUIT");
      quitOption.setActionCommand("QUIT FOR PRE GAME PLAY");
      quitOption.setForeground(Color.black);
      quitOption.setBackground(Color.red);
      quitOption.setFont(new Font("Serif", Font.BOLD, 10));
      quitOption.setFocusable(false);
      URL quitPath = this.getClass().getResource("/img/quit.jpg");
      System.out.println(quitPath);
      quitOption.setIcon(new ImageIcon(ImageIO.read(quitPath)
          .getScaledInstance(15, 15, Image.SCALE_DEFAULT)));
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

    buttonClickedMap.put("ADD PLAYER BUTTON", features::addPlayer);

    buttonClickedMap.put("GET SPACE INFO BUTTON", features::getSpaceDescription);

    buttonClickedMap.put("START GAME BUTTON", () -> {
      startGameButton.setEnabled(false);
      startGameButton.setBackground(Color.red);
      features.playGame();
    });

    buttonClickedMap.put("QUIT FOR PRE GAME PLAY", () -> {
      features.promptToQuitGame();
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    quitOption.addActionListener(buttonListener);
    getSpaceInfoButton.addActionListener(buttonListener);
    addPlayerButton.addActionListener(buttonListener);
    startGameButton.addActionListener(buttonListener);
  }

  private void setView() {
    setImagePanel();
    createSplitPanel();

    setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
  }

  private void createSplitPanel() {
    JSplitPane splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    topPanel.add(splitPaneV, BorderLayout.CENTER);

    JSplitPane splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    buttonsPanel.setBackground(Color.black);
    splitPaneH.setLeftComponent(buttonsPanel);
    imagePanel.setBackground(Color.yellow);
    splitPaneH.setRightComponent(imagePanel);
    splitPaneV.setLeftComponent(splitPaneH);

  }

  private void setImagePanel() {
    preGameImage = new TurnScreenPanel();
    scrollPane = new JScrollPane(preGameImage);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    imagePanel.add(scrollPane);
    imagePanel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
  }

  @Override
  public void updateImagePanel() {
    if (preGameImage != null) {
      scrollPane.remove(preGameImage);
      imagePanel.remove(scrollPane);
    }
    preGameImage = new TurnScreenPanel();
    scrollPane = new JScrollPane(preGameImage);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    imagePanel.setBackground(Color.yellow);
    imagePanel.add(scrollPane);
    imagePanel.revalidate();
    imagePanel.repaint();
  }

  @Override
  public PlayerType getPlayerTypeToBeAdded() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JLabel playerTypeLabel = new JLabel("Select the PlayerType");
    panel.add(playerTypeLabel);
    panel.add(humanPlayerRadioButton);
    panel.add(computerPlayerRadioButton);

    panel.add(Box.createVerticalStrut(20));

    int result = JOptionPane.showConfirmDialog(null, panel, "PLAYER TYPE",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
      if (humanPlayerRadioButton.isSelected()) {
        return PlayerType.HUMAN;
      } else if (computerPlayerRadioButton.isSelected()) {
        return PlayerType.COMP;
      }
    }
    return null;
  }

  @Override
  public List<String> getInputForAddHumanPlayer() {
    List<String> params = new ArrayList<String>();
    JPanel humanPlayerPanel = new JPanel();
    humanPlayerPanel.setLayout(new BoxLayout(humanPlayerPanel, BoxLayout.Y_AXIS));

    JLabel playerNameLabel = new JLabel("Enter the Player Name");
    humanPlayerPanel.add(playerNameLabel);
    JTextField playerName = new JTextField(10);
    humanPlayerPanel.add(playerName);

    humanPlayerPanel.add(Box.createVerticalStrut(20));

    JLabel spaceNameLabel = new JLabel("Select the Space Index");
    humanPlayerPanel.add(spaceNameLabel);
    DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
    List<Integer> availableSpaceIndexList = readOnlyGameWorld.getSpaceIndexes();
    for (int spaceIndex : availableSpaceIndexList) {
      boxModel.addElement(Integer.toString(spaceIndex));
    }
    JComboBox<String> comboBox = new JComboBox<>(boxModel);
    humanPlayerPanel.add(comboBox);

    humanPlayerPanel.add(Box.createVerticalStrut(20));

    JLabel maxItemsLabel = new JLabel("Enter the Maxium Number of Items Player can carry");
    humanPlayerPanel.add(maxItemsLabel);
    JTextField maxItems = new JTextField(1);
    humanPlayerPanel.add(maxItems);

    humanPlayerPanel.add(Box.createVerticalStrut(20));

    int addPlayerResult = JOptionPane.showConfirmDialog(null, humanPlayerPanel, "ADD PLAYER",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

    if (addPlayerResult == JOptionPane.OK_OPTION) {
      params.add(playerName.getText());
      params.add((String) comboBox.getSelectedItem());
      params.add(maxItems.getText());
      return params;
    }
    return null;

  }

  @Override
  public void enablingStartGame() {
    if (!startGameButton.isEnabled() && readOnlyGameWorld.getPlayers().size() > 0) {
      startGameButton.setBackground(Color.GREEN);
      startGameButton.setEnabled(true);
    }

  }

  @Override
  public void playGame(Features listener) {
    makeInvisible();
    listener.playGame();
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
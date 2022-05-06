package gameworld.view;

import gameworld.constants.PlayerType;
import gameworld.controller.Features;
import gameworld.service.ReadOnlyGameWorld;
import gameworld.view.listeners.GamePlayMouseClickListener;
import gameworld.view.listeners.KeyboardListener;
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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * This class implements {@link TurnView} interface to provide all the
 * functionalities to the turn screen.
 *
 */
public class TurnViewImpl extends JFrame implements TurnView {

  private static final long serialVersionUID = -2179965453492637485L;

  private JPanel informationPanel;
  private Box targetBox;
  private GridBagConstraints gbc;
  private Box turnInformationBox;
  private Box turnsCountBox;
  private Box commandsBox;
  private JPanel imagePanel;
  private JPanel topPanel;
  private JSplitPane splitPaneV;
  private JSplitPane splitPaneH;
  private JLabel turnsCount;
  private JLabel turnInformation;
  private JLabel targetInfo;
  private JLabel commandsInformation;
  private ReadOnlyGameWorld readOnlyGameWorld;
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem quitOption;
  private TurnScreenPanel preGameImage;
  private JScrollPane scrollPane;
  private MouseAdapter mouseAdapter;
  private ValidateParameterHelper validateParameterHelper;


  /**
   * Constructs an object of type {@link TurnViewImpl}.
   * 
   * @param readOnlyGameWorld Object to type {@link ReadOnlyGameWorld}.
   * @throw {@link IllegalArgumentException} if {@link ReadOnlyGameWorld} object
   *        is null.
   */
  public TurnViewImpl(ReadOnlyGameWorld readOnlyGameWorld) {
    super("Game Play Screen");
    validateParameterHelper = new ValidateParameterHelper();
    validateParameterHelper.validateReadOnlyModel(readOnlyGameWorld);
    this.readOnlyGameWorld = readOnlyGameWorld;
  }

  private void initializeComponents() {
    createMenuBar();

    informationPanel = new JPanel();
    informationPanel.setPreferredSize(new Dimension(400, 300));
    informationPanel.setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.insets = new Insets(5, 0, 5, 0);

    imagePanel = new JPanel();
    preGameImage = null;
    scrollPane = new JScrollPane(preGameImage);
    imagePanel.add(scrollPane);

    topPanel = new JPanel();
    getContentPane().add(topPanel);

    splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    topPanel.add(splitPaneV, BorderLayout.CENTER);
    splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    turnsCountBox = Box.createHorizontalBox();
    turnsCountBox.setBorder(BorderFactory.createLineBorder(Color.white));
    turnsCount = new JLabel("");

    targetBox = Box.createVerticalBox();
    targetBox.setBorder(BorderFactory.createLineBorder(Color.white));
    targetInfo = new JLabel("");

    turnInformationBox = Box.createVerticalBox();
    turnInformationBox.setBorder(BorderFactory.createLineBorder(Color.white));
    turnInformation = new JLabel("");

    commandsBox = Box.createVerticalBox();
    commandsBox.setBorder(BorderFactory.createLineBorder(Color.white));
    commandsInformation = new JLabel("");

  }

  private void createMenuBar() {
    try {
      menuBar = new JMenuBar();
      menu = new JMenu("Menu Options");
      quitOption = new JMenuItem("QUIT");
      quitOption.setActionCommand("QUIT FOR GAME PLAY");
      quitOption.setForeground(Color.black);
      quitOption.setBackground(Color.red);
      quitOption.setFont(new Font("Serif", Font.BOLD, 10));
      quitOption.setFocusable(false);
      quitOption.setIcon(new ImageIcon(ImageIO.read(new File("res/img/quit.jpg"))
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

    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();
    keyPresses.put(KeyEvent.VK_P, () -> features.pickWeapon());
    keyPresses.put(KeyEvent.VK_L, () -> features.lookAround());
    keyPresses.put(KeyEvent.VK_K, () -> features.killTarget());
    keyPresses.put(KeyEvent.VK_M, () -> features.movePet());
    keyPresses.put(KeyEvent.VK_S, () -> features.getSpaceDescription());

    KeyboardListener kbd = new KeyboardListener();

    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    this.addKeyListener(kbd);

    mouseAdapter = new GamePlayMouseClickListener(features);

    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();
    buttonClickedMap.put("QUIT FOR GAME PLAY", () -> {
      features.promptToQuitGame();
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    quitOption.addActionListener(buttonListener);

  }

  private String transpileTohtml(String javaStr) {
    return javaStr.replaceAll("\n", "<br/>");
  }

  @Override
  public void performUpdatesOnView() {
    updateTurnsCount();
    updateTargetBox();
    updateTurnInformation();
    updateCommandsBox();
    updateImagePanel();
  }

  private void updateTurnsCount() {
    turnsCountBox.remove(turnsCount);
    PlayerType playerType = readOnlyGameWorld.getTurnsType();
    String type = "HUMAN";
    if (playerType.equals(PlayerType.COMP)) {
      type = "COMPUTER";
    }

    String turnsCountContent = String.format(
        "<html>TURNS COUNT ---------> %d/%d, TURN -------> %s</html>",
        readOnlyGameWorld.getCurrentTurnsCount() + 1, readOnlyGameWorld.getTotalTurnsCount(), type);
    turnsCount = new JLabel(turnsCountContent);
    turnsCount.setForeground(Color.RED);
    turnsCountBox.add(turnsCount);
    turnsCountBox.revalidate();
    turnsCountBox.repaint();
  }

  private void updateCommandsBox() {

    if (readOnlyGameWorld.getTurnsType().equals(PlayerType.HUMAN)) {
      commandsBox.remove(commandsInformation);
      StringBuilder builder = new StringBuilder();
      builder.append("SELECT THE COMMAND YOU WANT TO PERFORM\n");
      builder.append("To Move Player - Click on the space you want to\n");
      builder.append("To Pick Item - Press P\n");
      builder.append("To Perform Look Around - Press L\n");
      builder.append("To Kill Target - Press K\n");
      builder.append("To Move Pet - Press M\n");
      builder.append("To Get Space Information - Press S\n");
      builder.append("To Get Player Information - Click on the Player");

      commandsInformation = new JLabel(
          String.format("<html>%s</html>", transpileTohtml(builder.toString())));
      commandsInformation.setForeground(Color.white);
      commandsBox.add(commandsInformation);
      commandsBox.revalidate();
      commandsBox.repaint();
    } else if (readOnlyGameWorld.getTurnsType().equals(PlayerType.COMP)) {
      commandsBox.remove(commandsInformation);
      commandsInformation = new JLabel("NOT ELIGIBLE FOR KEY OPTIONS");
      commandsInformation.setForeground(Color.white);
      commandsBox.add(commandsInformation);
      commandsBox.revalidate();
      commandsBox.repaint();
    }
  }

  private void updateTurnInformation() {
    turnInformationBox.remove(turnInformation);
    String turnInfoContent = String.format("<html>TURN INFORMATION<br/>%s</html>",
        transpileTohtml(readOnlyGameWorld.getTurnInfo()));
    turnInformation = new JLabel(turnInfoContent);
    turnInformation.setForeground(Color.white);
    turnInformationBox.add(turnInformation);
    turnInformationBox.revalidate();
    turnInformationBox.repaint();

  }

  private void updateTargetBox() {

    targetBox.remove(targetInfo);
    String targetInfoContent = String.format("<html>TARGET INFORMATION<br/>%s</html>",
        transpileTohtml(readOnlyGameWorld.getTargetInfo()));
    targetInfo = new JLabel(targetInfoContent);
    targetInfo.setForeground(Color.white);
    targetBox.add(targetInfo);
    targetBox.revalidate();
    targetBox.repaint();
  }

  @Override
  public void updateImagePanel() {
    if (preGameImage != null) {
      scrollPane.remove(preGameImage);
      imagePanel.remove(scrollPane);
      for (MouseListener k : preGameImage.getMouseListeners()) {
        preGameImage.removeMouseListener(k);
      }
    }
    preGameImage = new TurnScreenPanel();
    preGameImage.addMouseListener(mouseAdapter);
    scrollPane = new JScrollPane(preGameImage);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    imagePanel.setBackground(Color.yellow);
    imagePanel.add(scrollPane);
    imagePanel.revalidate();
    imagePanel.repaint();

  }

  @Override
  public String getInputToMovePet() {
    JPanel movePetPanel = new JPanel();
    movePetPanel.setLayout(new BoxLayout(movePetPanel, BoxLayout.Y_AXIS));
    JLabel spaceNameLabel = new JLabel("Select the Space Index for the pet to move to");
    movePetPanel.add(spaceNameLabel);

    DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
    List<Integer> availableSpaceIndexList = readOnlyGameWorld.getSpaceIndexes();
    for (int spaceIndex : availableSpaceIndexList) {
      boxModel.addElement(Integer.toString(spaceIndex));
    }
    JComboBox<String> comboBox = new JComboBox<>(boxModel);
    movePetPanel.add(comboBox);

    movePetPanel.add(Box.createVerticalStrut(20));

    int spaceDescriptionResult = JOptionPane.showConfirmDialog(null, movePetPanel, "MOVE PET",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

    if (spaceDescriptionResult == JOptionPane.OK_OPTION) {
      return (String) comboBox.getSelectedItem();
    }

    return null;

  }

  @Override
  public String getInputToKillTarget() {
    JPanel killTargetPanel = new JPanel();
    killTargetPanel.setLayout(new BoxLayout(killTargetPanel, BoxLayout.Y_AXIS));
    JLabel weaponLabel = new JLabel("Select the Weapon to attack with");

    killTargetPanel.add(weaponLabel);
    DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();

    Map<Integer, String> getWeaponsOfPlayer = readOnlyGameWorld.getWeaponsOfPlayers();
    getWeaponsOfPlayer.values().forEach(boxModel::addElement);

    JComboBox<String> comboBox = new JComboBox<>(boxModel);
    killTargetPanel.add(comboBox);

    killTargetPanel.add(Box.createVerticalStrut(20));

    int pickItemResult = JOptionPane.showConfirmDialog(null, killTargetPanel, "KILL TARGET",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

    if (pickItemResult == JOptionPane.OK_OPTION) {
      return (String) comboBox.getSelectedItem();
    }

    return null;

  }

  @Override
  public String getInputWeaponToBePicked() {
    JPanel pickItemPanel = new JPanel();
    pickItemPanel.setLayout(new BoxLayout(pickItemPanel, BoxLayout.Y_AXIS));
    JLabel spaceNameLabel = new JLabel("Select the Weapon to be picked");
    JLabel noteLabel = new JLabel(
        "Note : All Weapons are in decreasing order of the damage they provide");
    pickItemPanel.add(spaceNameLabel);
    pickItemPanel.add(noteLabel);
    DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();

    Map<Integer, String> availableWeaponsInSpace = readOnlyGameWorld.getWeaponsOfPlayerSpace();
    if (availableWeaponsInSpace != null) {
      (availableWeaponsInSpace).forEach((key, value) -> {
        boxModel.addElement(value);
      });

      JComboBox<String> comboBox = new JComboBox<>(boxModel);
      pickItemPanel.add(comboBox);

      pickItemPanel.add(Box.createVerticalStrut(20));

      int pickItemResult = JOptionPane.showConfirmDialog(null, pickItemPanel, "PICK ITEM",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

      if (pickItemResult == JOptionPane.OK_OPTION) {
        return (String) comboBox.getSelectedItem();
      }
    }
    return null;
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
  public void updateView() {
    performUpdatesOnView();

    informationPanel.setBackground(Color.black);
    informationPanel.add(turnsCountBox, gbc);
    informationPanel.add(targetBox, gbc);
    informationPanel.add(turnInformationBox, gbc);
    informationPanel.add(commandsBox, gbc);

    updateImagePanel();

    splitPaneH.setLeftComponent(informationPanel);
    splitPaneH.setRightComponent(imagePanel);
    splitPaneV.setLeftComponent(splitPaneH);

    setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}

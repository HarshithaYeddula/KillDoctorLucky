package gameworld.view;

import gameworld.constants.PlayerType;
import gameworld.controller.Features;
import gameworld.service.ReadOnlyGameWorld;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class implements all the functionalities of {@link GamePlayView}
 * interface.
 *
 */
public class GamePlayViewImpl implements GamePlayView {
  private final ReadOnlyGameWorld readOnlyGameWorld;
  private final IntroView introView;
  private final PreGameView preGameView;
  private final TurnView gamePlayView;
  private final PreReqsInfoView defaultMapView;
  private final PreReqsInfoView customMapView;
  private final EndGame endGame;
  private final ValidateParameterHelper validateParameterHelper;

  /**
   * Constructs an object of type {@link GamePlayViewImpl}.
   * 
   * @param readOnlyGameWorld Object to type {@link ReadOnlyGameWorld}.
   * @throw {@link IllegalArgumentException} if {@link ReadOnlyGameWorld} object
   *        is null.
   */
  public GamePlayViewImpl(ReadOnlyGameWorld readOnlyGameWorld) {
    validateParameterHelper = new ValidateParameterHelper();
    validateParameterHelper.validateReadOnlyModel(readOnlyGameWorld);
    this.readOnlyGameWorld = readOnlyGameWorld;
    this.introView = new IntroViewImpl(readOnlyGameWorld);
    this.preGameView = new PreGameViewImpl(readOnlyGameWorld);
    this.gamePlayView = new TurnViewImpl(readOnlyGameWorld);
    this.defaultMapView = new DefaultMapViewImpl();
    this.customMapView = new CustomMapViewImpl();
    this.endGame = new EndGameViewImpl(readOnlyGameWorld);
  }

  @Override
  public void setupComponent() {
    this.introView.setupView();
    this.preGameView.setupView();
    this.gamePlayView.setupView();
    this.defaultMapView.setupView();
    this.customMapView.setupView();
    this.endGame.setupView();
  }

  @Override
  public void setFeatures(Features features) {
    validateParameterHelper.validateFeatures(features);
    this.introView.setFeatures(features);
    this.preGameView.setFeatures(features);
    this.gamePlayView.setFeatures(features);
    this.defaultMapView.setFeatures(features);
    this.customMapView.setFeatures(features);
    this.endGame.setFeatures(features);
  }

  @Override
  public void setEchoOutput(String message) {
    if (message == null || "".equals(message)) {
      throw new IllegalArgumentException("Output message cannot be null!!!");
    }
    JOptionPane.showMessageDialog(null, message);
  }

  @Override
  public void enableDefaultMap() {
    this.introView.makeInvisible();
    this.customMapView.makeInvisible();
    this.defaultMapView.makeVisible();
  }

  @Override
  public List<String> promptToGetPreReqs(boolean isDefaultRoute) {
    if (isDefaultRoute) {
      return defaultMapView.promptToGetInputInformation();
    }
    return customMapView.promptToGetInputInformation();
  }

  @Override
  public PlayerType getPlayerTypeToBeAdded() {
    return preGameView.getPlayerTypeToBeAdded();
  }

  @Override
  public List<String> getInputForAddHumanPlayer() {
    return preGameView.getInputForAddHumanPlayer();
  }

  @Override
  public void enablingStartGame() {
    preGameView.enablingStartGame();
  }

  @Override
  public int getInputSpaceIndexForSpaceDescription() {
    JPanel spaceDescriptionPanel = new JPanel();
    spaceDescriptionPanel.setLayout(new BoxLayout(spaceDescriptionPanel, BoxLayout.Y_AXIS));
    JLabel spaceNameLabel = new JLabel("Select the Space Index for which you want the description");
    spaceDescriptionPanel.add(spaceNameLabel);
    DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>();
    List<Integer> availableSpaceIndexList = readOnlyGameWorld.getSpaceIndexes();
    for (int spaceIndex : availableSpaceIndexList) {
      boxModel.addElement(Integer.toString(spaceIndex));
    }
    JComboBox<String> comboBox = new JComboBox<>(boxModel);
    spaceDescriptionPanel.add(comboBox);

    spaceDescriptionPanel.add(Box.createVerticalStrut(20));

    int spaceDescriptionResult = JOptionPane.showConfirmDialog(null, spaceDescriptionPanel,
        "GET SPACE DESCRIPTION", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

    if (spaceDescriptionResult == JOptionPane.OK_OPTION) {
      try {
        return Integer.parseInt((String) comboBox.getSelectedItem());
      } catch (NumberFormatException nfe) {
        return -1;
      }

    }
    return -1;
  }

  @Override
  public String getInputToMovePet() {
    return gamePlayView.getInputToMovePet();
  }

  @Override
  public void initTurnScreen() {
    this.gamePlayView.updateView();
  }

  @Override
  public String getInputToKillTarget() {
    return this.gamePlayView.getInputToKillTarget();
  }

  @Override
  public void turnUpdatesOnView() {
    this.gamePlayView.performUpdatesOnView();
  }

  @Override
  public void enableCustomMap() {
    this.introView.makeInvisible();
    this.defaultMapView.makeInvisible();
    this.customMapView.makeVisible();
  }

  @Override
  public void enableWelcomeScreen() {
    this.preGameView.makeInvisible();
    this.gamePlayView.makeInvisible();
    this.defaultMapView.makeInvisible();
    this.customMapView.makeInvisible();
    this.introView.makeVisible();
    this.endGame.makeInvisible();
  }

  @Override
  public void enablePreGameScreen() {
    this.introView.makeInvisible();
    this.gamePlayView.makeInvisible();
    this.defaultMapView.makeInvisible();
    this.customMapView.makeInvisible();
    this.preGameView.makeVisible();
    this.endGame.makeInvisible();
  }

  @Override
  public void enableGamePlayScreen() {
    this.introView.makeInvisible();
    this.preGameView.makeInvisible();
    this.defaultMapView.makeInvisible();
    this.customMapView.makeInvisible();
    this.gamePlayView.makeVisible();
    this.endGame.makeInvisible();
  }

  @Override
  public void enableEndGamePlayScreen() {
    this.introView.makeInvisible();
    this.preGameView.makeInvisible();
    this.defaultMapView.makeInvisible();
    this.customMapView.makeInvisible();
    this.gamePlayView.makeInvisible();
    this.endGame.makeVisible();
  }

  @Override
  public String getInputWeaponToBePicked() {
    return gamePlayView.getInputWeaponToBePicked();
  }

  @Override
  public void updateWinner() {
    endGame.updateWinner();

  }

  @Override
  public boolean promptToQuitGame() {
    JPanel quitPanelDialogBox = new JPanel();
    quitPanelDialogBox.setLayout(new BoxLayout(quitPanelDialogBox, BoxLayout.Y_AXIS));
    JLabel quitLabel = new JLabel("ARE YOU SURE YOU WANT TO QUIT?");
    quitPanelDialogBox.add(quitLabel);

    int quitGameResult = JOptionPane.showConfirmDialog(null, quitPanelDialogBox, "QUIT GAME",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

    return quitGameResult == JOptionPane.OK_OPTION;
  }

  @Override
  public void refresh() {
    preGameView.updateImagePanel();
  }

}

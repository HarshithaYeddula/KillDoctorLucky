package view;

import gameworld.constants.PlayerType;
import gameworld.controller.Features;
import gameworld.view.GamePlayView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mock model for validating view.
 */
public class MockWorldView implements GamePlayView {

  private StringBuilder log;
  private final String uniqueCode;

  /**
   * Constructor of mock view.
   *
   * @param log        StringBuilder to update the info
   * @param uniqueCode UniqueCode based on which we decide view reached mock
   *                   model.
   */
  public MockWorldView(StringBuilder log, String uniqueCode) {
    this.uniqueCode = uniqueCode;
    this.log = log;
  }

  @Override
  public void setupComponent() {

  }

  @Override
  public void setFeatures(Features features) {
    log.append("Features has been set successfully!");

  }

  @Override
  public void setEchoOutput(String message) {
    log.append(String.format("Message %s displayed on view", message));

  }

  @Override
  public void enableWelcomeScreen() {
    log.append("Reached view-enableWelcomeScreen");
  }

  @Override
  public void enablePreGameScreen() {
    log.append("Reached view-enablePreGameScreen");
  }

  @Override
  public void enableGamePlayScreen() {
    log.append("Reached view-enableGamePlayScreen");
  }

  @Override
  public void enableEndGamePlayScreen() {
    log.append("Reached view-enableEndGamePlayScreen");
  }

  @Override
  public void enableCustomMap() {
    log.append("Reached view-enableCustomMap");
  }

  @Override
  public void enableDefaultMap() {
    log.append("Reached view-enableDefaultMap");
  }

  @Override
  public List<String> promptToGetPreReqs(boolean isDefaultRoute) {
    log.append(String.format("Reached view-promptToGetPreReqs: %s", isDefaultRoute));
    List<String> params = new ArrayList<>();
    params.add("1");
    params.add("2");
    return params;
  }

  @Override
  public PlayerType getPlayerTypeToBeAdded() {
    log.append("Reached view-getPlayerTypeToBeAdded");
    return PlayerType.COMP;
  }

  @Override
  public List<String> getInputForAddHumanPlayer() {
    log.append("Reached view-getInputForAddHumanPlayer");
    return Collections.singletonList(uniqueCode);
  }

  @Override
  public void enablingStartGame() {
    log.append("Reached view-enablingStartGame");
  }

  @Override
  public int getInputSpaceIndexForSpaceDescription() {
    log.append("Reached getInputSpaceIndexForSpaceDescription method!!!");
    return 999;
  }

  @Override
  public String getInputToMovePet() {
    log.append("Reached getInputToMovePet method!!!");
    return "999";
  }

  @Override
  public void initTurnScreen() {
    log.append("Initialize turn screen!!!");
  }

  @Override
  public String getInputToKillTarget() {
    log.append("Reached view-getInputToKillTarget");
    return uniqueCode;
  }

  @Override
  public void turnUpdatesOnView() {
    log.append("Turn updates on view method is called!!!");
  }

  @Override
  public String getInputWeaponToBePicked() {
    log.append("Input Weapon Selected");
    return uniqueCode;
  }

  @Override
  public void updateWinner() {
    log.append("Winner updated");

  }

  @Override
  public boolean promptToQuitGame() {
    return false;
  }

  /**
   * Updates the image panel with the updated game board.
   */
  @Override
  public void refresh() {
    log.append("Refresh method reached!!!");
  }

}

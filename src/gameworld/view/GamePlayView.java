package gameworld.view;

import gameworld.constants.PlayerType;
import gameworld.controller.Features;
import java.util.List;

/**
 * This interface acts as a bridge between the Controller and the views. If the
 * controller wants to communicate with the any view, the respective method from
 * this interface is called which in turn calls the associated method in the
 * required view.
 *
 */
public interface GamePlayView {

  /**
   * Initializes all the components of all the available views.
   */
  void setupComponent();

  /**
   * Sets all the features of all the available views.
   * 
   * @param features Object of type Features.
   * @throws IllegalArgumentException if Features object is Null.
   */
  void setFeatures(Features features) throws IllegalArgumentException;

  /**
   * Echos the output on the view.
   * 
   * @param message message to be displayed on the view.
   * @throws IllegalArgumentException if message is null.
   */
  void setEchoOutput(String message) throws IllegalArgumentException;

  /**
   * Enables the introduction frame and disables all other frames.
   */
  void enableWelcomeScreen();

  /**
   * Enables the PreGame frame and disables all other frames.
   */
  void enablePreGameScreen();

  /**
   * Enables the Turn frame and disables all other frames.
   */
  void enableGamePlayScreen();

  /**
   * Enables the EndGame frame and disables all other frames.
   */
  void enableEndGamePlayScreen();

  /**
   * Enables the frame that takes filePath and max turns count from user and
   * disables all other frames.
   */
  void enableCustomMap();

  /**
   * Enables the frame that takes the max turns count from user and disables all
   * other frames.
   */
  void enableDefaultMap();

  /**
   * Gets the input filePath and max turns count from the user.
   * 
   * @param isDefaultRoute if false - default map is used and max turns count is
   *                       taken as input. if true - both map path and the max
   *                       turns count are taken as input
   * @return list of parameters of type String taken as input from user, null is
   *         returned if no input is provided.
   */
  List<String> promptToGetPreReqs(boolean isDefaultRoute);

  /**
   * Prompts View to display a dialog Box with Radio Buttons to help select the
   * PlayerType to be added.
   * 
   * @return Object of type PlayerType. If User selected human, PlayerType.Human
   *         is returned, otherwise PlayerType.Comp is returned. If nothing is
   *         selected, null is returned.
   */
  PlayerType getPlayerTypeToBeAdded();

  /**
   * If User wants to add a human player, this method prompts view to take player
   * name, space index and max items from user.
   * 
   * @return list of paramaters of type String containing the provided player
   *         Name, space index and max Items. If user doesn't enter any values,
   *         null is returned.
   */
  List<String> getInputForAddHumanPlayer();

  /**
   * Enables the PreGame frame to be displayed and close all the other visible
   * frames.
   */
  void enablingStartGame();

  /**
   * Prompts View to display a dialog box to take space index from the user for
   * which they want the description.
   * 
   * @return selected space index by the user as type Integer, null if no input is
   *         provided.
   */
  int getInputSpaceIndexForSpaceDescription();

  /**
   * Prompts View to display a dialog box to take the space index to move the pet
   * to.
   * 
   * @return selected space index by the user, null if no input is provided by the
   *         user
   */
  String getInputToMovePet();

  /**
   * Updates the game Play view with the turn, target and the image information.
   */
  void initTurnScreen();

  /**
   * Prompts the view to select the weapon with which the target has to be
   * attacked with.
   * 
   * @return the weapon selected as a String. If user din't provide any input,
   *         null is returned.
   */
  String getInputToKillTarget();

  /**
   * Performs update on the game play view.
   */
  void turnUpdatesOnView();

  /**
   * Prompts the view to display a dialog box to accept the user input of what
   * weapon to be picked.
   * 
   * @return the weapon to be picked as a String. If user didn't provide any
   *         input, null is returned.
   */
  String getInputWeaponToBePicked();

  /**
   * Updates the Game over information on the view.
   */
  void updateWinner();

  /**
   * This displays a dialog box on the frame and asks if the user is sure to quit
   * the game. If user clicks on OK option, this method returns true. Otherwise
   * false.
   * 
   * @return boolean true - if user clicks OK, false - if user clicks CANCEL
   */
  boolean promptToQuitGame();

  /**
   * Updates the image panel with the updated game board.
   */
  void refresh();
}

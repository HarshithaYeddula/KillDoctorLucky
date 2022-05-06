package gameworld.controller;

import gameworld.controller.commands.AddComputerPlayer;
import gameworld.controller.commands.AddPlayer;
import gameworld.controller.commands.AttackTarget;
import gameworld.controller.commands.CreateGraphicalWorld;
import gameworld.controller.commands.DisplaySpaceInfo;
import gameworld.controller.commands.LookAround;
import gameworld.controller.commands.MovePet;
import gameworld.controller.commands.MovePlayer;
import gameworld.controller.commands.PickWeapon;
import java.util.List;

/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the view, to pass control to the controller. How
 * the view uses them as callback is completely up to how the view is designed
 * (e.g. it could use them as a callback for a button, or a callback for a
 * dialog box, or a set of text inputs, etc.)
 *
 * <p>
 * Each function is designed to take in the necessary data to complete that
 * functionality.
 */
public interface Features {

  /**
   * Makes a call to the {@link CreateGraphicalWorld} to create a buffered image.
   */
  void createBufferedImage();

  /**
   * Enables game Play.
   */
  void playGame();

  /**
   * Moves player to the coordinates provided in the parameters using
   * {@link MovePlayer} Command. Once operation is performed, the output is
   * displayed in the respective view.
   * 
   * @param x x-coordinate of the space.
   * @param y y-coordinate of the space.
   * @throws IllegalArgumentException if x or y are less than zero.
   */
  void decodeMoveCoOrdinates(int x, int y) throws IllegalArgumentException;

  /**
   * Performs Turn on Computer Controlled Player. Once operation is performed, the
   * output is displayed in the respective view.
   */
  void performComputerPlayerTurn();

  /**
   * Checks the player in turn and performs operation accordingly.
   */
  void checkPlayerInTurn();

  /**
   * Prompts View To display dialog box to let the user select the player type,
   * prompts for more user input based on the player type and add player to the
   * game by making a call to {@link AddPlayer} or {@link AddComputerPlayer}
   * command. Once operation is performed, the output is displayed in the
   * respective view.
   */
  void addPlayer();

  /**
   * Prompts the View to take preRequisite input (input filePath and Max turns
   * count from user) and updates them in the model.
   * 
   * @param params Parameters that need to be updated with.
   * @return output of the updation as a String
   * @throws IllegalArgumentException is params is null
   */
  String updatePreReqs(List<String> params) throws IllegalArgumentException;

  /**
   * Enables the Frame that accepts the preRequisite (turns count) info from the
   * user.
   */
  void startWithDefaultMap();

  /**
   * Enables the Frame that accepts the preRequisite (file path and turns count)
   * info from the user.
   */
  void startCustomMap();

  /**
   * Prompts the view to get the pre requiste information entered by the user.
   * 
   * @param isDefaultRoute if true, prompts view to take max turns count, if
   *                       false, prompts user to take both input file and max
   *                       turns count from user.
   */
  void promptToGetPreReqInfo(boolean isDefaultRoute);

  /**
   * Prompts the view to get input space index for description and makes a call to
   * {@link DisplaySpaceInfo} to get the description. Once received, sets the
   * output to the respective view.
   */
  void getSpaceDescription();

  /**
   * Prompts the view to get the input weapon the user want to be picked and makes
   * a call to {@link PickWeapon} Command . Once operation is performed, the
   * output is displayed in the respective view.
   */
  void pickWeapon();

  /**
   * A call to {@link LookAround} Command . Once operation is performed, the
   * output is displayed in the respective view.
   */
  void lookAround();

  /**
   * Prompts the view to get the input weapon the user want to attack the target
   * with and makes a call to {@link AttackTarget} Command . Once operation is
   * performed, the output is displayed in the respective view.
   */
  void killTarget();

  /**
   * Prompts the view to get the input space index the user wants the pet to move
   * to makes a call to {@link MovePet} Command . Once operation is performed, the
   * output is displayed in the respective view.
   */
  void movePet();

  /**
   * Restarts the game by initializing the Introduction Screen.
   */
  void restartGame();

  /**
   * Prompts the view to take input regarding if the user wants to really quit the
   * game. If user wants to quit, the game is quit.
   */
  void promptToQuitGame();
}

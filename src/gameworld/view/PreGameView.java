package gameworld.view;

import gameworld.constants.PlayerType;
import gameworld.controller.Features;
import java.util.List;

/**
 * This interface represents all the functionalities offered by the preGame
 * screen.
 */
public interface PreGameView extends View {

  /**
   * To Initialize all the components of the view.
   */
  void setupView();

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
   * Enables the start game button the frame when atleast one player is added to
   * the game.
   */
  void enablingStartGame();

  /**
   * Starts the game.
   * 
   * @param listener Features object listener.
   */
  void playGame(Features listener);

  /**
   * Updates the image panel with the new created buffered image.
   */
  void updateImagePanel();

}

package gameworld.service;

import gameworld.model.Player;
import gameworld.model.Space;
import gameworld.model.Target;
import gameworld.model.Weapon;
import java.util.List;
import java.util.Map;

/**
 * This interface represents all the operations offered by a gameWorld. This is
 * facade interface which handle all the conversations between controller and
 * models. In this we will store all the game world data.
 */
public interface GameWorld extends ReadOnlyGameWorld {

  /**
   * Function for creating buffer image of world, which will be invoked by the
   * createGUI method.
   * 
   * @return Acknowledgement of the function, which specifies the file location.
   */
  public String createBufferedImage();

  /**
   * Execute a move of the target, which doesn't require any input as target will
   * be moving according the space index orders.
   */
  public void moveTarget();

  /**
   * Return the description of the space, is asked by the spaceIdx parameter. This
   * gives clear idea for the players which spaces to start with.
   * 
   * @param spaceIdx index of the space for which we need description.
   * @return space description, which contains weapons and neighbors information.
   */
  public String displaySpaceDescription(int spaceIdx);

  /**
   * Return the description of the space, is asked by the spaceIdx parameter. This
   * gives clear idea for the players which spaces to start with.
   * 
   * @param spaceIdx index of the space for which we need description.
   * @return space description, which contains weapons and neighbors information.
   */
  public String displaySpaceDescription(String spaceIdx);
  
  /**
   * Returns the space index where the Target is located, which is used to display
   * before every players turn.
   *
   * @return the {@link Target}s space index of the target's location.
   */
  public Space getLocationOfTarget();

  /**
   * Function for getting neigbours for a specified space with index.
   *
   * @param idx Index of the space for which we have get neighbors
   * @return Returns list of spaces.
   */
  public List<Space> getNeighboursOfSpace(int idx);

  /**
   * Facade function to handle move operation.
   * 
   * @param spaceIndex space index number where player is suppose to be placed.
   * @return Acknowledge of movedPlayer.
   */
  public String movePlayer(int spaceIndex);

  /**
   * Adds new human player into the gameWorld, which takes name, spaceIndex and
   * weapons threshold. And it make sure name of the player is unique in the
   * gameWorld.
   * 
   * @param playerName    name of the player, who is getting added to the world.
   * @param spaceIndex    index where player is getting started his play, this is
   *                      independent choice.
   * @param maxItemsLimit determines max items a player can carry.
   * @return message which confirms the addition of player.
   */
  public String addPlayer(String playerName, int spaceIndex, int maxItemsLimit);

  /**
   * Look around functionality is extensively used in this game. Through this
   * functionality player takes decisions and plans his attack. Returns the space
   * information and neighbor space information along weapons availability.
   * 
   * @return string which contained detailed information abt space, weapons and
   *         players.
   */
  public String lookAround();

  /**
   * Picking an item-{@link Weapon} which will be stored under player, this
   * weapons later used for attack on target. As a part of game this is kind of a
   * first step.
   * 
   * @return confirmation upon specified weapon.
   */
  public String pickAnItem(String weaponName);

  /**
   * Returns the information about the gameworld. This gives brief summary for the
   * gamer on how many spaces, weapons, players are available.
   * 
   * @return world description with other required info.
   */
  public String getWorldInfo();

  /**
   * Adds new computer player using random generator. Even name, space and
   * maxItems are been generated from the random generated.
   * 
   * @param randomGenerator randomGenerator for testCases and random number
   *                        generation.
   * @return returns acknowledgment after player creation.
   */
  public String addComputerPlayer(RandomGenerator randomGenerator);

  /**
   * Returns all the available spaces in the game world. This is needed in the
   * time of addition of new players. Map format is chosen because it'll be easy
   * to dispaly through controller.
   * 
   * @return space map with index and name of the available spaces.
   */
  public Map<Integer, String> getSpaces();

  /**
   * Get the current turn {@link Player} location, i.e., the player who will be
   * holding turn.
   * 
   * @return the spaceIndex of player whose turn it is.
   */
  public String getTurnsLocation();

  /**
   * Maximum items that a player can carry, returns the if player reached this
   * items maximum.
   * 
   * @return True if the items maximum is reached, false otherwise.
   */
  public boolean isPlayerItemThresholdReached();

  /**
   * Returns the neighbors of the turn {@link Player} location, i.e., the player
   * who is in turn.
   * 
   * @return space index to the name of the space.
   */
  public Map<Integer, String> getTurnNeighborSpaces();

  /**
   * Perform turn operations when computer player who is playing or who is holding
   * the turn.
   * 
   * @param randomGenerator randomGenerator for randomNumber generation.
   */
  public String computersTurn(RandomGenerator randomGenerator);

  /**
   * Initializes turn to the first player who got added before everyone.
   */
  public void initializeTurn();

  /**
   * Execute the moving in the position specified by the given spaceIndex.
   * 
   * @param spaceIndex the spaceIndex of the intended move.
   * @return confirmation the pet moved.
   */
  public String movePet(int spaceIndex);

  /**
   * Execute the attacking target with the weapon specified by the given.
   * 
   * @param weaponName the weaponName for attcking target.
   * @return confirmation the attacking target.
   */
  public String attackTarget(String weaponName);

  /**
   * Updates the pre-Requisite information with the provided filePath and maxTurns.
   * 
   * @param filePath required filePath.
   * @param maxTurns required maxTurns.
   * @return output of the updation process.
   */
  public String updatePreReqs(String filePath, int maxTurns);

  /**
   * Checks if the mouse click on the view is for the player description or move
   * player. The respective functionality is then performed.
   * 
   * @param x x-coordinate of the mouse click.
   * @param y y-coordinate of the mouse click.
   * @return output of the operation
   */
  public String checkMoveDisplayPlayerInfo(int x, int y);

}

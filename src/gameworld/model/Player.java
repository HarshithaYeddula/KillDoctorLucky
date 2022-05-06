package gameworld.model;

import gameworld.constants.PlayerType;
import java.util.List;

/**
 * Player interface which will have all the required functions of players.
 */
public interface Player {
  /**
   * Function for fetching player name info.
   * @return PlayerName
   */
  public String getPlayerName();

  /**
   * Function for adding weapons onto the player's weapons list.
   * @param weapon weapon object.
   */
  public void addWeaponsInfo(Weapon weapon) throws IllegalArgumentException;

  /**
   * Fetching Weapons info from the player object.
   * @return return copy of weapons after deep copy.
   */
  public List<Weapon> getWeapons();

  /**
   * Fetching space info where player is recided.
   * @return get Space Id of palyer.
   */
  public int getSpace();

  /**
   * MovePlayer to new space index.
   * @param spaceIdx spaceIndex.
   */
  public void movePlayer(int spaceIdx);

  /**
   * Get playerType either comp or human.
   * @return either comp or human.
   */
  public PlayerType getPlayerType();

  /**
   * Function to check maxItemlimit reached or not.
   * @return Returns boolean based threshold check and number of object carried by player.
   */
  public boolean itemLimitReached();

  /**
   * Returns the threshold of weapons a player can carry.
   * @return items threshold which is finite number.
   */
  public int getItemLimit();

  /**
   * Deletes specifed weapon from the player, when player as used it on the target for attacking.
   * @param weapon weapon that's been used.
   */
  public void removeWeapon(Weapon weapon);
}

package gameworld.model;

import java.util.List;

/**
 * This interface represents all the operations offered by a space.
 * This interface which handle all the logics that are involved wrt space, like storing the index,
 * name, weapons, players, neighbors of the space.
 */
public interface Space {
  /**
   * Function for updating neighbors information inside space object.
   * @param neighbours This function expect List of neighbors to be sent as an argument.
   */
  public void updateNeighbours(List<Space> neighbours);

  /**
   * Function for getting neighbors of the space object.
   * This is been used by GameWorld to show neighbors info.
   * @return List of spaces which are sharing the "wall" with the calling space object.
   */
  public List<Space> getNeighbours();

  /**
   * Adding weapon information upon parsing happens through this function.
   * @param weapon Object of weapon which is added to the space.
   */
  public void addWeapons(Weapon weapon);

  /**
   * Deletes the specified weapon from the space upon being picked by the player.
   * @param weapon Object of weapon which is removed from the space.
   */
  public void deleteWeapons(Weapon weapon);

  /**
   * Getter for fetching weapon details, this function is used in displaying space information.
   * @return Returns list of weapons.
   */
  public List<Weapon> getWeapons();

  /**
   * Getting Co-Ordinates which can be used for overlap and neighbors calculation.
   * @return List of numbers which are co-ordinates of the room, rxd during the world creation time.
   */
  public List<Integer> getCoOrdinates();

  /**
   * Getter which is used in the calculating neighbor function,
   * which returns the index of the space.
   * @return Returns the index of the space
   */
  public Integer getIndex();

  /**
   * Getter for fetching name of the space, needed while displaying Space information.
   * @return Returns name of space object.
   */
  public String getName();

  /**
   * Return whether the players are present or not, which can be helpful in deciding attack success.
   * @return true if the players are present, false otherwise
   */
  public boolean isPlayersPresent();

  /**
   * Returns all the players which are present in that particular space.
   * This is used to determine if the attack is successfull or not.
   * @return List of players of the particular space.
   */
  public List<Player> getPlayers();

  /**
   * Adds new player into the space, so that space keep track of which all
   * players are receded in it.
   * @param player object of the player which is needed
   */
  public void addPlayer(Player player);

  /**
   * Upon movement of player, removePlayer function is called.
   * @param player which will be move out of space.
   */
  public void removePlayer(Player player);

  /**
   * Returns the space information which contains name, index, players,
   * neighbours and pet details in it.
   * @return Spaceinfo with weapon, players, coordinates.
   */
  public String getSpaceInfo(boolean noNeighbors);

  /**
   * Adds the pet into the space, so that space keep track of where pet is.
   * @param petName name of the pet that's getting added into the space.
   */
  public void addPet(String petName);

  /**
   * Removes the pet from the space, once the pet is moved to the new location.
   */
  public void removePet();

  /**
   * Return whether the pet is present or not, which can be helpful in deciding attack success.
   * @return true if the pet is present, false otherwise
   */
  public boolean isPetPresent();
}

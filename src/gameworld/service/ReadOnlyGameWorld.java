package gameworld.service;

import gameworld.constants.PlayerType;
import gameworld.model.Player;
import gameworld.model.Space;
import java.util.List;
import java.util.Map;

/**
 * The interface needed for a read-only model for the game.
 *
 */
public interface ReadOnlyGameWorld {

  /**
   * Get the current turn info, i.e., the player who will be holding turn.
   * 
   * @return the description of the {@link Player} whose turn it is.
   */
  public String getTurnInfo();

  /**
   * Gets the max turns count provided.
   * 
   * @return turns count as a string
   */
  public int getTotalTurnsCount();

  /**
   * Gets the current turns count.
   * 
   * @return current turns count as a String.
   */
  public int getCurrentTurnsCount();

  /**
   * Gets all the Space indexes in the world in the form of a list.
   * 
   * @return list of space indexes.
   */
  public List<Integer> getSpaceIndexes();

  /**
   * Return (@Link Target) information which contains the targets location, health
   * and name. This helps palyers to determine when to attack target.
   * 
   * @return targets information, which is required on the every turn.
   */
  public String getTargetInfo();

  /**
   * Returns the name of of the players that are added to the gameWorld. This is
   * used when briefing about the game.
   * 
   * @return list of names of all the players.
   */
  public List<String> getPlayers();

  /**
   * Gets the world description as a String.
   * 
   * @return world description.
   */
  public String getWorldDesc();

  /**
   * Get the current turn {@link Player} name, i.e., the player who will be
   * holding turn.
   * 
   * @return the name of the player whose turn it is.
   */
  public String getTurnName();

  /**
   * Get the current turn {@link Player} player, i.e., the player who will be
   * holding turn. This enum is used for differentiating human and computer
   * player.
   * 
   * @return the name of the player whose turn it is.
   */
  public PlayerType getTurnsType();

  /**
   * Return whether the game is over. The game is over when either maxturns is
   * reached, or one player has won.
   * 
   * @return true if the game is over, false otherwise
   */
  public boolean isGameOver();

  /**
   * Return the winner player name of the game, or {@code null} if there is no
   * winner. If the game is not over, returns {@code null}.
   *
   * @return name of the player if there is a winner, or null if there is no
   *         winner
   */
  public String getWinner();

  /**
   * Returns the weapons that are available in the turn-{@link Player}s
   * {@link Space} location. Weapons are returned in a map format, so that it's
   * easy to display on the controller side. And weapons are arranged in the
   * decreasing order of the damage they can make.
   * 
   * @return map of weapon and index, in the decreasing order of damage.
   */
  public Map<Integer, String> getWeaponsOfPlayerSpace();

  /**
   * Returns the weapons that are carried by turn-{@link Player}s location.
   * Weapons are returned in a map format, so that it's easy to display on the
   * controller side. This is used to determine the which weapon to choose for
   * attacking target. Sorting of weapons helps the gamers.
   * 
   * @return map of weapon and index, in the decreasing order of damage.
   */
  public Map<Integer, String> getWeaponsOfPlayers();

}

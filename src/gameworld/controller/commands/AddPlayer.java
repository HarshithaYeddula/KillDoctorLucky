package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * This command implements the add human player. Add human plyaer takes, name of the player,
 * spaceIndex where the player is started and maximum items a player can carry.
 */
public class AddPlayer implements WorldCommand {
  private final String name;
  private final int spaceIdx;
  private final int maxItemsLimit;

  /**
   * Constructor for addPlayer.
   * @param name playerName
   * @param spaceIdx space index
   * @param maxItemsLimit max items threshold.
   */
  public AddPlayer(String name, int spaceIdx, int maxItemsLimit) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Name shouldn't be null or empty");
    }

    if (spaceIdx < 0) {
      throw new IllegalArgumentException("Invalid space index!!!");
    }

    if (maxItemsLimit < 0) {
      throw new IllegalArgumentException("Invalid maxItemsLimit");
    }

    this.name = name;
    this.spaceIdx = spaceIdx;
    this.maxItemsLimit = maxItemsLimit;
  }

  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    return gameWorld.addPlayer(name, spaceIdx, maxItemsLimit);
  }
}

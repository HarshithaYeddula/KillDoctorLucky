package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * This command implements the Move Pet. The Move Pet receives spaceIndex as input,
 * index where pet is been moved by the player.
 */
public class MovePet implements WorldCommand {
  private final int spaceIndex;

  /**
   * Constructor the movePet which receives the spaceIndex where pet is suppose to be moved.
   * @param spaceIndex space index where pet is intended to move.
   */
  public MovePet(int spaceIndex) {
    if (spaceIndex < 0) {
      throw new IllegalArgumentException("Invalid space index");
    }
    this.spaceIndex = spaceIndex;
  }

  /**
   * Execute command which will be common across commands.
   *
   * @param gameWorld model object.
   * @return returns acknowledgment or display info to view object.
   */
  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    try {
      return gameWorld.movePet(spaceIndex);
    } catch (IllegalArgumentException exception) {
      return exception.getMessage();
    }
  }
}

package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * This command implements the move player.
 * Move player takes spaceIndex as input, where player is suppose to be moved.
 */
public class MovePlayer implements WorldCommand {
  private final int xCoOrdinate;
  private final int yCoOrdinate;

  /**
   * Constructor for movePlayer command.
   * @param x X CoOrdinate to which player is moved.
   * @param y Y CoOrdinate to which player is moved.
   */
  public MovePlayer(int x, int y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid co-ordinates");
    }
    this.xCoOrdinate = x;
    this.yCoOrdinate = y;
  }

  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    try {
      return gameWorld.checkMoveDisplayPlayerInfo(this.xCoOrdinate, this.yCoOrdinate);
    } catch (IllegalArgumentException exception) {
      return exception.getMessage();
    }
  }
}

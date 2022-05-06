package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * This command implements the look around.
 * This command helps player to determine when to attack the
 * target based on the other players location.
 */
public class LookAround implements WorldCommand {
  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    return gameWorld.lookAround();
  }
}

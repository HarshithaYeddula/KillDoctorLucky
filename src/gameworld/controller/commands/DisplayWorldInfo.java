package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * Displaying world info command class.
 */
public class DisplayWorldInfo implements WorldCommand {

  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    try {
      return gameWorld.getWorldInfo();
    } catch (IllegalArgumentException exception) {
      return exception.getMessage();
    }

  }
}

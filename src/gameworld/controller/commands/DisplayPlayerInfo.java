package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * Command class for displaying player info.
 */
public class DisplayPlayerInfo implements WorldCommand {
  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    return gameWorld.getTurnInfo();
  }
}

package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * Command class for world's GUI creation.
 */
public class CreateGraphicalWorld implements WorldCommand {
  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    gameWorld.createBufferedImage();
    return "Graphical representation of the world is created successfully!!!";
  }
}

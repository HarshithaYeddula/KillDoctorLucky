package gameworld.controller;

import gameworld.service.GameWorld;

/**
 * Interface for all the world commands,
 * execute functionality is something common between all the commands.
 */
public interface WorldCommand {
  /**
   * Execute command which will be common across commands.
   * @param gameWorld model object.
   * @return returns acknowledgment or display info to view object.
   */
  String execute(GameWorld gameWorld);
}

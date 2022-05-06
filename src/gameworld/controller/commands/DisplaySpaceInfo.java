package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * Command class for the displaying space info.
 */
public class DisplaySpaceInfo implements WorldCommand {
  private final int spaceId;

  /**
   * Constructor for displaying space info.
   * @param spaceId spaceId for which we are fetching information.
   */
  public DisplaySpaceInfo(int spaceId) {
    if (spaceId < 0) {
      throw new IllegalArgumentException("Invalid space index");
    }
    this.spaceId = spaceId;
  }

  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    return gameWorld.displaySpaceDescription(spaceId);
  }
}

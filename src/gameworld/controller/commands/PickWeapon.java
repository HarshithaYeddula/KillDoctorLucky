package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * This command implements the pick weapon.
 * Pick weapon takes weapon name which is been chosen
 * by the player during the game from the current space he is standing.
 */
public class PickWeapon implements WorldCommand {
  private final String weaponName;

  /**
   * Constructor for pickWeapon.
   * @param weaponName weapon name
   */
  public PickWeapon(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Name shouldn't be null or empty");
    }

    this.weaponName = weaponName;
  }

  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    try {
      return gameWorld.pickAnItem(weaponName);
    } catch (IllegalArgumentException exception) {
      return exception.getMessage();
    }
  }
}

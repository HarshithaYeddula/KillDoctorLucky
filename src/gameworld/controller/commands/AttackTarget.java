package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;

/**
 * This command implements the Attack target. The Attack target receives weaponName as input,
 * name of the weapon which will be used on the target to hurt its health.
 */
public class AttackTarget implements WorldCommand {
  private final String weaponName;

  /**
   * Constructor for AttackTarget.
   * @param weaponName weapon name
   */
  public AttackTarget(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Name shouldn't be null or empty");
    }

    this.weaponName = weaponName;
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
      return gameWorld.attackTarget(weaponName);
    } catch (IllegalArgumentException exception) {
      return exception.getMessage();
    }
  }
}

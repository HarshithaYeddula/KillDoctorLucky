package commands;

import gameworld.controller.WorldCommand;
import gameworld.controller.commands.PickWeapon;
import org.junit.Test;

/**
 * Test for PickWeapon command.
 */
public class PickWeaponTest {
  /**
   * Function for AddPlayer Object creation.
   */
  protected WorldCommand pickWeapon(String weaponName) {
    return new PickWeapon(weaponName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWeaponName() {
    pickWeapon("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullWeaponName() {
    pickWeapon(null);
  }
}

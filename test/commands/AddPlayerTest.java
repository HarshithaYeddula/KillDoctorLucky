package commands;

import gameworld.controller.WorldCommand;
import gameworld.controller.commands.AddPlayer;
import org.junit.Test;

/**
 * Test cases to validate AddPlayer command implementation.
 * Just validating inputs to the command.
 */
public class AddPlayerTest {
  /**
   * Function for AddPlayer Object creation.
   */
  protected WorldCommand addPlayer(String name, int spaceIdx, int maxItemsLimit) {
    return new AddPlayer(name, spaceIdx, maxItemsLimit);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidName() {
    addPlayer("", 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    addPlayer(null, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSpaceIdx() {
    addPlayer("ganesh", -2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMaxLimit() {
    addPlayer("ganesh", 2, -2);
  }

  @Test
  public void testValidAddPlayer() {
    WorldCommand addPlayer = addPlayer("ganesh", 2, 2);
  }
}

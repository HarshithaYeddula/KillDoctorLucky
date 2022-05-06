package commands;

import gameworld.controller.WorldCommand;
import gameworld.controller.commands.MovePlayer;
import org.junit.Test;

/**
 * Test cases to validate MovePlayer command implementation.
 * Just validating inputs to the command.
 */
public class MovePlayerTest {
  /**
   * Function for MovePlayer Object creation.
   */
  protected WorldCommand movePlayer(int x, int y) {
    return new MovePlayer(x, y);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSpaceId() {
    movePlayer(-9, -10);
  }

}

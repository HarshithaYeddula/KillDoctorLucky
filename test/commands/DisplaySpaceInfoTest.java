package commands;

import gameworld.controller.WorldCommand;
import gameworld.controller.commands.DisplaySpaceInfo;
import org.junit.Test;

/**
 * Test for displaySpaceInfo command.
 */
public class DisplaySpaceInfoTest {
  /**
   * Function for DisplaySpaceInfo Object creation.
   */
  protected WorldCommand displaySpaceInfo(int spaceIdx) {
    return new DisplaySpaceInfo(spaceIdx);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSpaceIndex() {
    displaySpaceInfo(-9);
  }
}

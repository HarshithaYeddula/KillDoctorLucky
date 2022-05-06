import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gameworld.constants.PlayerType;
import gameworld.model.Player;
import gameworld.model.PlayerImpl;
import gameworld.model.Space;
import gameworld.model.SpaceImpl;
import gameworld.model.Weapon;
import gameworld.model.WeaponImpl;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

/**
 * Test for the player implementation.
 */
public class PlayerImplTest {

  /**
   * Method to created player object.
   *
   * @param playerName name of player
   * @param spaceIndex spaceIndex
   * @param maxItems   maxItems
   * @param playerType playerType
   * @return Player object
   */
  protected Player createPlayer(String playerName, int spaceIndex, int maxItems,
                                PlayerType playerType) {
    return new PlayerImpl(playerName, spaceIndex, maxItems, playerType);
  }

  /**
   * Test invalid player name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlayerInvalidName() {
    createPlayer("", 1, 1, PlayerType.HUMAN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerInvalidSpace() {
    createPlayer("gani", -1, 1, PlayerType.HUMAN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerInvalidMaxItem() {
    createPlayer("", 1, -1, PlayerType.HUMAN);
  }

  @Test
  public void testPlayerValidInputs() {
    Player gani = createPlayer("gani", 1, 1, PlayerType.HUMAN);
    String expected = "Player Name: gani\n" + "Player Type: HUMAN\n"
            + "Not carrying any weapons. \n" + "MaxWeapons: 1";
    assertEquals(expected, gani.toString());
  }

  @Test
  public void testComputerPlayrCreation() {
    Player comp100 = createPlayer("comp100", 1, 1, PlayerType.COMP);
    String expected = "Player Name: comp100\n" + "Player Type: COMP\n"
            + "Not carrying any weapons. \n" + "MaxWeapons: 1";
    assertEquals(expected, comp100.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMaxItems() {
    Player comp100 = createPlayer("comp100", 1, 1, PlayerType.COMP);
    Space space = new SpaceImpl(0, "Armory", new ArrayList<>(Arrays.asList(2, 3, 4, 2)));
    Weapon weapon = new WeaponImpl("revolver", space, 3);
    Weapon weapon1 = new WeaponImpl("revolver1", space, 3);
    comp100.addWeaponsInfo(weapon);
    comp100.addWeaponsInfo(weapon1);
  }

  @Test
  public void testMovePlayer() {
    Player comp100 = createPlayer("comp100", 1, 1, PlayerType.COMP);
    comp100.movePlayer(10);
    int expected = 10;
    assertEquals(expected, comp100.getSpace());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove() {
    Player comp100 = createPlayer("comp100", 1, 1, PlayerType.COMP);
    comp100.movePlayer(-1);
  }

  @Test
  public void testPlayerType() {
    Player comp100 = createPlayer("comp100", 1, 1, PlayerType.COMP);
    assertEquals(PlayerType.COMP, comp100.getPlayerType());
  }

  @Test
  public void testEquals() {
    Player comp100 = createPlayer("comp100", 1, 1, PlayerType.COMP);
    Player comp1001 = createPlayer("comp100", 1, 1, PlayerType.COMP);
    assertTrue(comp100.equals(comp1001));
  }
}

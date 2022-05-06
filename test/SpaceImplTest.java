import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gameworld.model.Space;
import gameworld.model.SpaceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.junit.Test;

/**
 * Class for testing SpaceImpl class.
 */
public class SpaceImplTest {

  private static final int MAX_X = 40;
  private static final int MAX_Y = 40;
  private static final int MAX_SPACES = 40;
  private static final int MAX_WEAPONS = 20;

  /**
   * Function for creating SpaceImplTest objects.
   *
   * @param id          ID argument needed for space creation.
   * @param name        Name argument needed for space creation.
   * @param coordinates CoOrdinates argument needed for space creation.
   * @return Return spaceImpl object for testing
   */
  protected Space kdlSpace(int id, String name, List<Integer> coordinates) {
    return new SpaceImpl(id, name, coordinates);
  }

  /**
   * Function to product valid co-ordinates, which is used for unit tests.
   *
   * @return Integer array of coordinates.
   */
  private List<Integer> getValidCoOrdinates() {
    Random r = new Random();
    int x11 = r.nextInt(MAX_X);
    int x21 = r.nextInt(MAX_X - x11) + x11;
    int y11 = r.nextInt(MAX_Y);
    int y21 = r.nextInt(MAX_Y - x11) + x11;

    return new ArrayList<>(Arrays.asList(y11, x11, x21, y21));
  }

  /**
   * Test to make sure y co-ordinates validation is proper.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testYequalcoOrdinates() {
    kdlSpace(0, "Armory", new ArrayList<>(Arrays.asList(2, 3, 2, 5)));
  }

  /**
   * Test to make sure y co-ordinates validation is proper.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testYcoOrdinates() {
    kdlSpace(0, "Armory", new ArrayList<>(Arrays.asList(2, 3, 1, 5)));
  }

  /**
   * Test to make sure x co-ordinates validation is proper.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testXcoordinates() {
    kdlSpace(0, "Armory", new ArrayList<>(Arrays.asList(2, 3, 4, 2)));
  }

  /**
   * Test to make sure x co-ordinates validation is proper.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testXequalCoordinates() {
    kdlSpace(0, "Armory", new ArrayList<>(Arrays.asList(2, 3, 4, 3)));
  }

  /**
   * Test to make sure Neighbors update is working properly.
   */
  @Test
  public void validateUpdateNeighbors() {
    Space space = kdlSpace(0, "Armory", getValidCoOrdinates());
    Space neighbor1 = kdlSpace(1, "Billiard Room", getValidCoOrdinates());
    Space neighbor2 = kdlSpace(2, "Piazza", getValidCoOrdinates());

    ArrayList<Space> neighbors = new ArrayList<>(Arrays.asList(neighbor1, neighbor2));

    space.updateNeighbours(neighbors);

    assertEquals(neighbors, space.getNeighbours());
  }

  /**
   * Test to make sure Equals is working.
   */
  @Test
  public void testEquals() {

    int id = 0;
    String name = "Armory";
    List<Integer> validCoOrdinates = getValidCoOrdinates();

    Space testObj1 = kdlSpace(id, name, validCoOrdinates);
    Space testObj2 = kdlSpace(id, name, validCoOrdinates);

    assertTrue(testObj1.equals(testObj2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullPet() {
    Space space = kdlSpace(0, "Armory", getValidCoOrdinates());
    space.addPet(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEmptyPet() {
    Space space = kdlSpace(0, "Armory", getValidCoOrdinates());
    space.addPet("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddValidPet() {
    Space space = kdlSpace(0, "Armory", getValidCoOrdinates());
    space.addPet("Cat");
    String expected = "Space Info \n"
            + "Name of the Space: Armory\n"
            + "Id of the Space: 0\n"
            + "No neighbors for this space. \n"
            + "No weapons on this space. \n"
            + "No players in this space. \n"
            + "Pet: Cat \n";
    assertEquals(expected, space.getSpaceInfo(true));
  }

  /**
   * Test to make sure HashCode is working for spaceImpl.
   */
  @Test
  public void testHashCode() {
    int id = 0;
    String name = "Armory";
    List<Integer> validCoOrdinates = getValidCoOrdinates();

    Space testObj1 = kdlSpace(id, name, validCoOrdinates);
    int expectedHash = Objects.hash(name, validCoOrdinates);

    assertEquals(expectedHash, testObj1.hashCode());
  }
}
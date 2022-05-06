import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gameworld.model.Target;
import gameworld.model.TargetImpl;
import java.util.Objects;
import org.junit.Test;

/**
 * Test for Target implementation.
 */
public class TargetImplTest {

  /**
   * Function for creating TargetImpl object for testing.
   *
   * @param name   Name of the target
   * @param health Health of the target
   * @return returns TargetImpl object
   */
  protected Target kdlTarget(String name, int health) {
    return new TargetImpl(name, health);
  }

  /**
   * Test to make sure health is a valid positive number.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHelath() {
    kdlTarget("Dr Lucky", -5);
  }

  /**
   * Test to make sure health is a valid positive number.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testZeroHelath() {
    kdlTarget("Dr Lucky", 0);
  }

  /**
   * Test to make sure Equals is working.
   */
  @Test
  public void testEquals() {

    String name = "Dr. Lukcy";
    int health = 50;

    Target testObj1 = kdlTarget(name, health);
    Target testObj2 = kdlTarget(name, health);

    assertTrue(testObj1.equals(testObj2));
  }

  /**
   * Test to make sure HashCode is working for targetImpl.
   */
  @Test
  public void testHashCode() {
    String name = "Dr. Lukcy";
    int health = 50;

    Target testObj1 = kdlTarget(name, health);
    int expectedHash = Objects.hash(name, health);

    assertEquals(expectedHash, testObj1.hashCode());
  }
}
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gameworld.model.Pet;
import gameworld.model.PetImpl;
import org.junit.Test;

/**
 * Tests for the pet implementation, which validates the pet data and make sure no
 * breakage happens in the future, if there is a change to the base class.
 */
public class PetImplTest {
  /**
   * Method to created pet object.
   * @param petName name of pet
   * @param spaceIndex spaceIndex where pet has to be placed.
   * @return Pet object
   */
  protected Pet createPet(String petName, int spaceIndex) {
    return new PetImpl(petName, spaceIndex);
  }

  /**
   * Test invalid name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPetInvalidName() {
    createPet("", 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerInvalidSpace() {
    createPet("gani", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPetNullName() {
    createPet(null, 1);
  }

  @Test
  public void testPetCreation() {
    Pet gani = createPet("gani", 1);
    String expected = "Pet Info: \n"
            + "Name: gani\n"
            + "Space: 1";
    assertEquals(expected, gani.toString());
  }

  @Test
  public void testSpaceUpdate() {
    Pet gani = createPet("gani", 1);
    String expected = "Pet Info: \n"
            + "Name: gani\n"
            + "Space: 1";
    assertEquals(expected, gani.toString());
    gani.updateSpace(2);
    expected = "Pet Info: \n"
            + "Name: gani\n"
            + "Space: 2";
    assertEquals(expected, gani.toString());
  }


  @Test
  public void testEquals() {
    Pet pet1 = createPet("cat", 1);
    Pet pet2 = createPet("cat", 1);
    assertTrue(pet1.equals(pet2));
  }
}
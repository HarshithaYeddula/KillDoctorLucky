package gameworld.model;

/**
 * This interface represents all the operations offered by a Pet.
 * This is a basic interface which stores name and space index.
 */
public interface Pet {
  /**
   * Returns the name of the Pet, which is required for the display purpose.
   * @return name of the pet.
   */
  public String getName();

  /**
   * Returns the name of the spaceIndex, which is required for the pet location tracking.
   * @return index of the space.
   */
  public int getSpaceIndex();

  /**
   * Updates the location of the pet to the new space index.
   * @param spaceIdx new location where pets wants to move.
   */
  public void updateSpace(int spaceIdx);
}

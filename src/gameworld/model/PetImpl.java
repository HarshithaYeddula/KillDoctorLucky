package gameworld.model;

import java.util.Objects;


/**
 * Implementation of the pet interface, this handles all the reqiured operation regarding pet.
 */
public class PetImpl implements Pet {
  private final String name;
  private int spaceIndex;

  /**
   * Constructor of the pet implementation, which takes the pet name,
   * space where pet is getting started at.
   * @param name name of the pet.
   * @param spaceIndex index of the space where pet is getting started.
   */
  public PetImpl(String name, int spaceIndex) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Name can't be null or empty!!!");
    }
    this.name = name;
    this.spaceIndex = spaceIndex;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getSpaceIndex() {
    return spaceIndex;
  }

  @Override
  public void updateSpace(int spaceIdx) {
    if (spaceIdx < 0) {
      throw new IllegalArgumentException("Space index is negative.");
    }

    this.spaceIndex = spaceIdx;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Pet Info: \n");
    sb.append(String.format("Name: %s\n", this.name));
    sb.append(String.format("Space: %d", spaceIndex));

    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { // backward compatibility with default equals
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Pet)) {
      return false;
    }

    Pet that = (Pet) o;

    return this.name.equals(that.getName())
            && this.spaceIndex == that.getSpaceIndex();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.spaceIndex);
  }
}

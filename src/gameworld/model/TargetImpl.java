package gameworld.model;

import java.util.Objects;

/**
 * Implementation of Target class, where we have target related operations.
 */
public class TargetImpl implements Target {
  private final String name;
  private int health;
  private Space space;

  /**
   * Constructor which takes target name and health.
   *
   * @param name   Name of the target
   * @param health Health of the target
   */
  public TargetImpl(String name, int health) {
    validateHealth(name, health);
    this.name = name;
    this.health = health;
  }

  /**
   * Constructor which takes target name and health.
   *
   * @param name   Name of the target
   * @param health Health of the target
   * @param space  space where target is present
   */
  public TargetImpl(String name, int health, Space space) {
    validateHealth(name, health);
    this.name = name;
    this.health = health;
    this.space = space;
  }

  /**
   * Function for making health is in number format.
   *
   * @param name   Name of the target.
   * @param health Health of the target
   * @throws IllegalArgumentException thrown if there is wrong health format.
   */
  private void validateHealth(String name, int health) throws IllegalArgumentException {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Target name can't be empty!!!");
    }

    if (health <= 0) {
      throw new IllegalArgumentException("Health of target "
              + "upon initialization can't be zero or negative!!!");
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setSpace(Space space) {
    if (space == null) {
      throw new IllegalArgumentException("Space object is null");
    }
    this.space = space;
  }

  @Override
  public Space getSpace() {
    return this.space;
  }

  @Override
  public int getHealth() {
    return this.health;
  }

  @Override
  public void reduceHealth(int damage) {
    if (damage <= 0) {
      throw new IllegalArgumentException("Damage can't be zero or negative!!!");
    }
    this.health = Math.max(0, this.health - damage);
  }

  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { // backward compatibility with default equals
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Target)) {
      return false;
    }

    Target that = (Target) o;

    // comparing gears and speed of the car to decide equality.
    return this.getName().equals(that.getName())
            && this.getHealth() == that.getHealth();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getName(), this.getHealth());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Name: %s\n", this.name));
    sb.append(String.format("Space Id: %d, Space name: %s\n", space.getIndex(), space.getName()));
    sb.append(String.format("Health: %d\n", this.health));
    return sb.toString();
  }
}

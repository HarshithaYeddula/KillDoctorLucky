package gameworld.model;

import java.util.Objects;

/**
 * Implementation of the weapon interface, which handles all the operation regarding weaopn.
 */
public class WeaponImpl implements Weapon {
  private final String name;
  private final Space spaceItBelongsTo;
  private final int damageAmt;

  /**
   * Constructor for weapon implementation, which takes name of the weapon,
   * space where weapon placed at and damage it can cause on the target.
   *
   * @param name             Name of the weapon
   * @param spaceItBelongsTo Space it belongs to
   * @param damageAmt        damage it can cause
   */
  public WeaponImpl(String name, Space spaceItBelongsTo, int damageAmt) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Weapon name is null or empty");
    }

    if (spaceItBelongsTo == null) {
      throw new IllegalArgumentException("Space object is null");
    }

    if (damageAmt < 0) {
      throw new IllegalArgumentException("DamageAmt is negative");
    }

    this.name = name;
    this.spaceItBelongsTo = spaceItBelongsTo;
    this.damageAmt = damageAmt;
  }

  /**
   * Copy constructor which is used for deep copy.
   * @param weapon weapon object.
   */
  public WeaponImpl(Weapon weapon) {
    if (weapon == null) {
      throw new IllegalArgumentException("Weapon object is null");
    }
    this.name = weapon.getName();
    this.spaceItBelongsTo = weapon.getSpaceItBelongsTo();
    this.damageAmt = weapon.getDamageAmt();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getDamageAmt() {
    return this.damageAmt;
  }

  @Override
  public Space getSpaceItBelongsTo() {
    Space spaceItBelongsTo = this.spaceItBelongsTo;
    return new SpaceImpl(spaceItBelongsTo.getIndex(), spaceItBelongsTo.getName(),
            spaceItBelongsTo.getCoOrdinates());
  }

  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { // backward compatibility with default equals
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Weapon)) {
      return false;
    }

    Weapon that = (Weapon) o;

    return this.getName().equals(that.getName())
            && this.getDamageAmt() == that.getDamageAmt();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getName(), this.getDamageAmt());
  }

  @Override
  public String toString() {
    return String.format("Weapon Name: %s,", this.name);
  }

}

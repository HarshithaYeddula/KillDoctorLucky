package gameworld.model;

/**
 * This interface represents all the operations offered by a Weapon.
 * This is simple interface which handle all the necessary weapon transactions,
 * like creating weapons and updating damage.
 */
public interface Weapon {
  /**
   * Returns the name of the weapon, which is required during displaying of which weapon to choose.
   * @return name of the weapon.
   */
  public String getName();

  /**
   * Returns the damage the weapon can cause upon attacking by player on the target.
   * @return damage amoutn which is of type number.
   */
  public int getDamageAmt();

  /**
   * Returns the space where weapon belongs to during initialization of the weapon object.
   * @return Object of space which is used to determine from which space weapons being picked.
   */
  public Space getSpaceItBelongsTo();


}

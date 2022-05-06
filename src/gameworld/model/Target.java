package gameworld.model;

/**
 * This interface represents all the operations offered by a Target.
 * Target interface take cares of location of target, name of the target, health and damage.
 */
public interface Target {

  /**
   * Getter for name, needed for displaying information about target.
   * @return Returns name in text format.
   */
  public String getName();

  /**
   * Update space after parsing through the input file.
   * @param space Space is needed as argument.
   */
  public void setSpace(Space space);

  /**
   * Getter for space, to know where the target is present.
   * @return Returns the space object where target is.
   */
  public Space getSpace();

  /**
   * Returns the health of the target, which is necessary for game continuation.
   * @return Returns the health in the number format.
   */
  public int getHealth();

  /**
   * Reduces the target health, upon being attacked by the player.
   * @param damage weapons strength which is used on the target.
   */
  public void reduceHealth(int damage);
}

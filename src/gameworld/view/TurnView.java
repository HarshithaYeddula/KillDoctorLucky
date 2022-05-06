package gameworld.view;

/**
 * This interface provides all the functionalities that has to be provided by
 * the Turn View.
 *
 */
public interface TurnView extends View {

  /**
   * To Initialize all the components of the view.
   */
  void setupView();

  /**
   * Initializes the game Play view with the turn, target and the image
   * information.
   */
  void updateView();

  /**
   * Prompts the view to display a dialog box to accept the user input of what
   * weapon to be picked.
   * 
   * @return the weapon to be picked as a String. If user didn't provide any
   *         input, null is returned.
   */
  String getInputWeaponToBePicked();

  /**
   * Updates the game Play view with the turn, target and the image information.
   */
  void performUpdatesOnView();

  /**
   * Prompts View to display a dialog box to take the space index to move the pet
   * to.
   * 
   * @return selected space index by the user, null if no input is provided by the
   *         user
   */
  String getInputToMovePet();

  /**
   * Prompts the view to select the weapon with which the target has to be
   * attacked with.
   * 
   * @return the weapon selected as a String. If user din't provide any input,
   *         null is returned.
   */
  String getInputToKillTarget();

  /**
   * Updates the image panel with the new created buffered image.
   */
  void updateImagePanel();
}

package gameworld.view;

/**
 * This interface handles all the operations of the EndGame Screen.
 *
 */
public interface EndGame extends View {

  /**
   * To Initialize all the components of the view.
   */
  void setupView();

  /**
   * To update the winner and display it on the frame.
   */
  void updateWinner();
}

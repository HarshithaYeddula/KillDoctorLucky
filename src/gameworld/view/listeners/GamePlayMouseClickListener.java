package gameworld.view.listeners;

import gameworld.controller.Features;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * A simple Mouse click Listener for the Game.
 */
public class GamePlayMouseClickListener extends MouseAdapter {
  private final Features listener;

  /**
   * Creates an object of type GamePlayMouseClickListener.
   * 
   * @param listener Features object
   * @throws IllegalArgumentException if Features object is null
   */
  public GamePlayMouseClickListener(Features listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Controller is null!!!");
    }

    this.listener = listener;
  }

  /**
   * Handles the mouse click event.
   *
   * @param e The mouse event.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    List<Integer> coords = List.of(e.getX(), e.getY());
    int x = coords.get(0);
    int y = coords.get(1);
    listener.decodeMoveCoOrdinates(x, y);
  }
}

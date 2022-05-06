package gameworld.view;

import gameworld.controller.Features;

/**
 * Interface that provides common functionality to all the classes implementing
 * this.
 *
 */
public interface View {

  /**
   * To make the JFrame visible to the user.
   */
  void makeVisible();

  /**
   * To repaint the JFrame if there are any changes done to it.
   */
  void refresh();

  /**
   * To make the JFrame invisible to the user.
   */
  void makeInvisible();

  /**
   * To set the Features to the frame and add action listeners if present.
   * 
   * @param features Object of type Features.
   * @throws IllegalArgumentException if Features object is Null.
   */
  void setFeatures(Features features);

}

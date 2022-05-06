package gameworld.view;

import java.util.List;

/**
 * This interface represents all the operations offered by the Screen that takes
 * the preRequisite input - input file path, max turns count or both.
 *
 */
public interface PreReqsInfoView extends View {

  /**
   * To Initialize the components of the view.
   */
  void setupView();

  /**
   * This returns the input provided by the user in the file path and max turns
   * text field from the frame.
   * 
   * @return a list of type String containing the file path and the max turns
   *         count.
   */
  List<String> promptToGetInputInformation();

}

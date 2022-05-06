package gameworld.view;

import gameworld.controller.Features;
import gameworld.service.ReadOnlyGameWorld;

/**
 * Abstract class to validate Parameters.
 */
public class ValidateParameterHelper {

  /**
   * To validate Features object.
   * 
   * @param features object to be validated
   */
  protected void validateFeatures(Features features) {
    if (features == null) {
      throw new IllegalArgumentException("Features cannot be null!!!");
    }
  }

  /**
   * to validate {@link ReadOnlyGameWorld} object.
   * 
   * @param model object to be validated.
   */
  protected void validateReadOnlyModel(ReadOnlyGameWorld model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!!!");
    }
  }

}

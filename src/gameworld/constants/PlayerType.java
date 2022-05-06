package gameworld.constants;

/**
 * Enum for separating PlayerType.
 * HUMAN -> HUMAN player
 * COMP -> Computer player
 */
public enum PlayerType {
  HUMAN("HUMAN"), COMP("COMP");

  private final String disp;

  private PlayerType(String disp) {
    this.disp = disp;
  }

  @Override
  public String toString() {
    return disp;
  }
}

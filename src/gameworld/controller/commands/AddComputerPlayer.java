package gameworld.controller.commands;

import gameworld.controller.WorldCommand;
import gameworld.service.GameWorld;
import gameworld.service.RandomGenerator;

/**
 * This command implements add computer player. Add computer player takes randomGenerator,
 * from which decision of where the computer player as to start is been taken.
 */
public class AddComputerPlayer implements WorldCommand {
  private final RandomGenerator randomGenerator;

  /**
   * Constructor for addComputer player.
   * @param randomGenerator randomGenerator object.
   */
  public AddComputerPlayer(RandomGenerator randomGenerator) {
    if (randomGenerator == null) {
      throw new IllegalArgumentException("Invalid random generator!!!");
    }
    this.randomGenerator = randomGenerator;
  }

  @Override
  public String execute(GameWorld gameWorld) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("GameWorld models object is null");
    }
    return gameWorld.addComputerPlayer(randomGenerator);
  }
}

package gameworld;

import gameworld.controller.CommandController;
import gameworld.controller.GameController;
import gameworld.service.GameWorld;
import gameworld.service.KillDoctorLuckyImpl;
import gameworld.service.RandomGenerator;
import gameworld.view.GamePlayView;
import gameworld.view.GamePlayViewImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class acting as driver for execution.
 */
public class KillDoctorLuckyDriver {
  /**
   * Main function.
   * 
   * @param args CLI arguments
   */
  public static void main(String[] args) {

    try {
      Readable fileReader = new InputStreamReader(new FileInputStream(args[0]));
      int maxTurns = Integer.parseInt(args[1]);
      GameWorld gameWorld = new KillDoctorLuckyImpl(fileReader, maxTurns);
      RandomGenerator randomGenerator = new RandomGenerator();
      GamePlayView gameView = new GamePlayViewImpl(gameWorld);
      GameController controller = new CommandController(gameWorld, gameView, randomGenerator);
      controller.startGame();
    } catch (IOException e) {
      System.out.println("File is missing!!!");
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      System.out.println(String.format("Please handle: %s", e.getMessage()));
    }
  }
}

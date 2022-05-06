import static org.junit.Assert.assertEquals;

import gameworld.controller.CommandController;
import gameworld.controller.Features;
import gameworld.controller.GameController;
import gameworld.service.GameWorld;
import gameworld.service.RandomGenerator;
import gameworld.view.GamePlayView;
import java.util.ArrayList;
import java.util.List;
import model.MockWorldModel;
import org.junit.Before;
import org.junit.Test;
import view.MockWorldView;

/**
 * Tests for controller based GUI.
 * 
 * @author mail2
 *
 */
public class CommandControllerGuiTest {

  private GameWorld gameWorld;
  private GamePlayView gameView;
  private StringBuilder log;
  private Features controller;
  private RandomGenerator randomGenerator;

  /**
   * PreSetup method for object creation.
   */
  @Before
  public void setUp() {
    log = new StringBuilder();
    gameWorld = new MockWorldModel(log, "123456789!");
    gameView = new MockWorldView(log, "123456789!");
    randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
    controller = new CommandController(gameWorld, gameView, randomGenerator);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
    GameController gameController = new CommandController(null, gameView, randomGenerator);
    gameController.startGame();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
    GameController gameController = new CommandController(gameWorld, null, randomGenerator);
    gameController.startGame();
  }

  @Test
  public void testCreateBufferedImage() {
    controller.createBufferedImage();
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Created Buffer Image successfully!!!\n";
    assertEquals(expected, log.toString());
  }

  /*
   * @Test public void testInitGame() { controller.initGame(); String expected =
   * "Reached getWorldDesc\n" + "Reached getWinner!!!\n"; assertEquals(expected,
   * log.toString()); }
   */

  @Test
  public void testPlayGame() {
    controller.playGame();
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached getPlayers!!!\n" + "Initializing turn\n"
        + "Created Buffer Image successfully!!!\n"
        + "Initialize turn screen!!!Reached view-enableGamePlayScreenReached isGameOver!!!\n"
        + "Created Buffer Image successfully!!!\n" + "Reached getTurnsType!!!\n"
        + "Turn updates on view method is called!!!";
    assertEquals(expected, log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDecodeMoveCoOrdinatesNegValue() {
    controller.decodeMoveCoOrdinates(-1, -1);
  }

  @Test
  public void testDecodeMoveCoOrdinates() {
    controller.decodeMoveCoOrdinates(100, 200);
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached getTurnsType!!!\n" + "Player moved successfully\n"
        + "Message 123456789! displayed on viewReached isGameOver!!!\n"
        + "Created Buffer Image successfully!!!\n" + "Reached getTurnsType!!!\n"
        + "Turn updates on view method is called!!!";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testCheckPlayerInTurn() {
    controller.checkPlayerInTurn();
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached isGameOver!!!\n" + "Created Buffer Image successfully!!!\n"
        + "Reached getTurnsType!!!\n" + "Turn updates on view method is called!!!";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testAddPlayer() {
    controller.addPlayer();
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached view-getPlayerTypeToBeAddedComputer player is added\n"
        + "Message 123456789! displayed on viewReached view-enablingStartGame";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testStartWithDefaultMap() {
    controller.startWithDefaultMap();
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached view-enableDefaultMap";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testStartCustomMap() {
    controller.startCustomMap();
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached view-enableCustomMap";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testUpdatePreReqsNull() {
    String expected = "Using Default values as parameters passed are null";
    assertEquals(expected, controller.updatePreReqs(null));

  }

  @Test
  public void testUpdatePreReqs() {
    String expected = "123456789!";
    List<String> params = new ArrayList<>();
    params.add("1");
    params.add("2");
    assertEquals(expected, controller.updatePreReqs(params));
  }

  @Test
  public void testPromptToGetPreReqInfo() {
    controller.promptToGetPreReqInfo(true);
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached view-promptToGetPreReqs: trueUpdated Prerequisites with 1 and 2\n"
        + "Message 123456789! displayed on viewReached view-enablePreGameScreen";
    assertEquals(expected, log.toString());
  }

  @Test
  public void testGetSpaceDescription() {
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached getInputSpaceIndexForSpaceDescription method!!!Space is Armory\n";
    controller.getSpaceDescription();
    assertEquals(expected, log.toString());
  }

  @Test
  public void testPickWeapon() {
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached getTurnsType!!!\n" + "Reached isPlayerItemThresholdReached!!!\n"
        + "Reached isGameOver!!!\n" + "Created Buffer Image successfully!!!\n"
        + "Reached getTurnsType!!!\n" + "Turn updates on view method is called!!!";
    controller.pickWeapon();
    assertEquals(expected, log.toString());
  }

  @Test
  public void testLookAround() {
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached getTurnsType!!!\n" + "Reached LookAround method\n"
        + "Message 123456789! displayed on viewReached isGameOver!!!\n"
        + "Created Buffer Image successfully!!!\n" + "Reached getTurnsType!!!\n"
        + "Turn updates on view method is called!!!";
    controller.lookAround();
    assertEquals(expected, log.toString());
  }

  @Test
  public void testKillTarget() {
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached getTurnsType!!!\n"
        + "Reached view-getInputToKillTargetReached getWeaponsOfPlayers\n"
        + "Reached isGameOver!!!\n" + "Created Buffer Image successfully!!!\n"
        + "Reached getTurnsType!!!\n" + "Turn updates on view method is called!!!";
    controller.killTarget();
    assertEquals(expected, log.toString());
  }

  @Test
  public void testMovePet() {
    String expected = "Reached getWorldDesc\n" + "Reached getWinner!!!\n"
        + "Reached getTurnsType!!!\n" + "Reached getInputToMovePet method!!!Reached getSpaces!!!\n"
        + "Message Provided SpaceIndex doesn't exist! displayed on viewReached isGameOver!!!\n"
        + "Created Buffer Image successfully!!!\n" + "Reached getTurnsType!!!\n"
        + "Turn updates on view method is called!!!";
    controller.movePet();
    assertEquals(expected, log.toString());
  }
}

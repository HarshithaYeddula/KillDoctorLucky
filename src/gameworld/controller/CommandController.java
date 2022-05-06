package gameworld.controller;

import gameworld.constants.PlayerType;
import gameworld.controller.commands.AddComputerPlayer;
import gameworld.controller.commands.AddPlayer;
import gameworld.controller.commands.AttackTarget;
import gameworld.controller.commands.CreateGraphicalWorld;
import gameworld.controller.commands.DisplayPlayerInfo;
import gameworld.controller.commands.DisplaySpaceInfo;
import gameworld.controller.commands.DisplayWorldInfo;
import gameworld.controller.commands.LookAround;
import gameworld.controller.commands.MovePet;
import gameworld.controller.commands.MovePlayer;
import gameworld.controller.commands.PickWeapon;
import gameworld.service.GameWorld;
import gameworld.service.RandomGenerator;
import gameworld.view.GamePlayView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Command controller implementation. Commands which are needed for game
 * implementation.
 */
public class CommandController implements GameController, Features {

  private Scanner in;
  private Appendable out;
  private final List<String> beforeCmds;
  private final Map<String, Runnable> beforeGameCmds;
  private final List<String> playCmds;
  private final Map<String, Runnable> playGameCmds;
  private final List<String> afterCmds;
  private final Map<String, Runnable> afterGameCmds;
  private RandomGenerator randomGenerator;
  private final GameWorld gameWorld;
  private final GamePlayView gameView;

  /**
   * Constructor for controller.
   * 
   * @param gameWorld gameWorld object as a part of MVC.
   * @param gameView gameView object as a part of MVC.
   * @param randomGenerator RandomGenerator object.
   */
  public CommandController(GameWorld gameWorld, GamePlayView gameView,
      RandomGenerator randomGenerator) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("Model object is null");
    }

    if (gameView == null) {
      throw new IllegalArgumentException("View object is null");
    }

    this.gameWorld = gameWorld;
    this.gameView = gameView;

    beforeGameCmds = new HashMap<>();
    beforeCmds = new ArrayList<>();
    playGameCmds = new HashMap<>();
    playCmds = new ArrayList<>();
    afterGameCmds = new HashMap<>();
    afterCmds = new ArrayList<>();
    this.randomGenerator = new RandomGenerator();
  }

  /**
   * Constructor for controller.
   * @param gameWorld gameWorld object as a part of MVC.
   * @param gameView gameView object as a part of MVC.
   * @param in Scanner input.
   * @param out Appendable output.
   * @param randomGenerator RandomGenerator for computer player.
   */
  public CommandController(GameWorld gameWorld, GamePlayView gameView, Readable in, Appendable out,
                           RandomGenerator randomGenerator) {
    if (gameWorld == null) {
      throw new IllegalArgumentException("Model object is null");
    }

    if (gameView == null) {
      throw new IllegalArgumentException("View object is null");
    }

    if (in == null) {
      throw new IllegalArgumentException("Readable input is null");
    }

    if (out == null) {
      throw new IllegalArgumentException("Appendable out is null");
    }

    if (randomGenerator == null) {
      throw new IllegalArgumentException("RandomGenerator object is null");
    }

    this.in = new Scanner(in);
    this.out = out;

    this.gameWorld = gameWorld;
    this.gameView = gameView;

    this.in = new Scanner(System.in);
    this.out = System.out;

    beforeGameCmds = new HashMap<>();
    beforeCmds = new ArrayList<>();
    playGameCmds = new HashMap<>();
    playCmds = new ArrayList<>();
    afterGameCmds = new HashMap<>();
    afterCmds = new ArrayList<>();
    this.randomGenerator = new RandomGenerator();
  }

  /*
   ************************************* Command CallBack methods***********************************
   */
  /**
   * Display world info callback method.
   * 
   * @param gameWorld model object
   */
  private void displayWorldInfoCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Loading World Information ----------------\n");
      this.out.append(new DisplayWorldInfo().execute(gameWorld)).append("\n\n");
      this.out.append("----------------- End World Information -----------------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Add player callback.
   * 
   * @param gameWorld model object
   */
  private void addPlayerCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Adding Player is in Progress ----------------\n");
      this.out.append("\nEnter the name of the player : ");
      String playerName = in.next();
      Map<Integer, String> spaces = gameWorld.getSpaces();

      this.out.append("Available spaces: \n");
      displayMap(spaces);

      this.out.append(String.format(
          "\nEnter the space index where you want player: " + "%s to be placed: ", playerName));
      int ipSpaceIdx = in.nextInt();
      if (!spaces.containsKey(ipSpaceIdx - 1)) {
        this.out.append("Invalid space index as been entered \n\n");
        return;
      }

      this.out.append("\nEnter the maxItems count for : ");
      int maxItems = in.nextInt();
      this.out.append(new AddPlayer(playerName, ipSpaceIdx - 1, maxItems).execute(gameWorld))
          .append("\n\n");
      this.out.append("----------------- End Adding Player ----------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Move player callback.
   *
   * @param gameWorld model object
   */
  private void movePlayerCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Moving Player is in Progress ----------------\n");
      this.out.append(String.format("Currently player: %s is standing at %s.\n",
          gameWorld.getTurnName(), gameWorld.getTurnsLocation()));
      this.out.append("Available neighbor space you can move player to: \n");

      Map<Integer, String> turnNeighborSpaces = gameWorld.getTurnNeighborSpaces();

      displayMap(turnNeighborSpaces);

      this.out.append("\nEnter the space index you want the player to move to for : ");
      int ipSpaceIdx = in.nextInt();
      if (!turnNeighborSpaces.containsKey(ipSpaceIdx - 1)) {
        this.out.append("Invalid space index as been entered \n\n");
        return;
      }

      this.out.append("----------------- End Move Player Operation ----------------\n");

    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Display player callback.
   * 
   * @param gameWorld model object
   */
  private void displayPlayerInfoCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Display Player Info is in Progress ----------------\n");
      this.out.append(new DisplayPlayerInfo().execute(gameWorld));
      this.out.append("----------------- End Player Info ------------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Add computer player callback.
   * 
   * @param gameWorld model object
   */
  private void addComputerPlayerCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Adding Computer Player in Progress ----------------\n");
      this.out.append(new AddComputerPlayer(randomGenerator).execute(gameWorld));
      this.out.append("----------------- End Adding Computer Player ----------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Display space info callback.
   * 
   * @param gameWorld model object
   */
  private void displaySpaceInfoCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Loading Spaces Information ----------------\n");
      Map<Integer, String> spaces = gameWorld.getSpaces();

      this.out.append("Available spaces: \n");
      displayMap(spaces);

      this.out.append("\nEnter the space index you want the description for : ");
      int ipSpaceIdx = in.nextInt();
      if (!spaces.containsKey(ipSpaceIdx - 1)) {
        this.out.append("Invalid space index as been entered \n\n");
        return;
      }

      this.out.append(new DisplaySpaceInfo(ipSpaceIdx - 1).execute(gameWorld)).append("\n\n");
      this.out.append("----------------- End Space Information ----------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Look Around callback.
   * 
   * @param gameWorld model object
   */
  private void lookAroundCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- LookAround Command in Progress ----------------------\n");
      this.out.append(new LookAround().execute(gameWorld)).append("\n\n");
      this.out.append("----------------- End LookAround Command -----------------------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  private void movePetCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Moving Pet Command in Progress ----------------------\n");
      Map<Integer, String> spaces = gameWorld.getSpaces();
      this.out.append("Available spaces: \n");
      displayMap(spaces);

      this.out.append("\nEnter the space index where you want pet to be placed: ");
      int ipSpaceIdx = in.nextInt();
      if (!spaces.containsKey(ipSpaceIdx - 1)) {
        this.out.append("Invalid space index as been entered \n\n");
        return;
      }
      this.out.append(new MovePet(ipSpaceIdx - 1).execute(gameWorld));
      this.out.append("----------------- End Moving Pet Command ----------------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  private void attackTargetCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Attack target Command in Progress -------------------\n");
      Map<Integer, String> weaponsOfPlayer = gameWorld.getWeaponsOfPlayers();

      this.out.append("Available weapons in the space: \n");
      displayMap(weaponsOfPlayer);

      this.out.append("\nEnter the weapon index that you want to pick: ");
      int ipWeaponIdx = in.nextInt();
      if (!weaponsOfPlayer.containsKey(ipWeaponIdx - 1)) {
        this.out.append("Invalid weapon index as been entered \n\n");
        return;
      }

      this.out.append(new AttackTarget(weaponsOfPlayer.get(ipWeaponIdx - 1)).execute(gameWorld))
          .append("\n\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Pick weapon callback.
   * 
   * @param gameWorld model object
   */
  private void pickWeaponCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Picking weapon in progress ------------------------\n");
      Map<Integer, String> weaponsOfPlayerSpace = gameWorld.getWeaponsOfPlayerSpace();

      if (weaponsOfPlayerSpace.size() <= 0) {
        this.out.append("No weapons available to pick in this space.");
        return;
      }

      if (gameWorld.isPlayerItemThresholdReached()) {
        this.out.append("No space for new weapons, please drop weapon to pick new ones.");
        return;
      }

      this.out.append("Available weapons in the space: \n");
      displayMap(weaponsOfPlayerSpace);

      this.out.append("\nEnter the weapon index that you want to pick: ");
      int ipWeaponIdx = in.nextInt();
      if (!weaponsOfPlayerSpace.containsKey(ipWeaponIdx - 1)) {
        this.out.append("Invalid weapon index as been entered \n\n");
        return;
      }
      this.out.append(new PickWeapon(weaponsOfPlayerSpace.get(ipWeaponIdx - 1)).execute(gameWorld))
          .append("\n\n");

    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * GUI callback.
   * 
   * @param gameWorld model object
   */
  private void createGuiCallBack(GameWorld gameWorld) {
    try {
      this.out.append("----------------- Creating GUI of gameWorld in progress --------------\n");
      this.out.append(new CreateGraphicalWorld().execute(gameWorld)).append("\n\n");
      this.out.append("----------------- Ending GUI creation -----------------------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /*
   ************************************* CallBack methods*************************************
   */

  /**
   * Function to update command names that'll be displayed for the user.
   */
  private void loadCmdLabels() {
    // Keeping Display space info, so that players will know more about spaces
    // before adding player.
    beforeCmds.addAll(Arrays.asList("Display GameWorld Info", "Display Space Info",
        "Create GameWorld GUI", "Add Player", "Add Computer Player", "Start Game"));
    playCmds.addAll(Arrays.asList("Move Player", "Pick Weapon", "Look Around", "Move Pet",
        "Attack Target", "Display Player Info (will not count as turn)"));
    afterCmds.addAll(Arrays.asList("Display Space Info", "Display World Info"));
  }

  /**
   * Function to attach callback funcs.
   * 
   * @param gameWorld model object
   */
  private void loadCommandFuncs(GameWorld gameWorld) {
    beforeGameCmds.putAll(new HashMap<String, Runnable>() {
      {
        put("1", () -> displayWorldInfoCallBack(gameWorld));
        put("2", () -> displaySpaceInfoCallBack(gameWorld));
        put("3", () -> createGuiCallBack(gameWorld));
        put("4", () -> addPlayerCallBack(gameWorld));
        put("5", () -> addComputerPlayerCallBack(gameWorld));
        put("6", () -> initGameCallBack(gameWorld));
      }
    });

    playGameCmds.putAll(new HashMap<String, Runnable>() {
      {
        put("1", () -> movePlayerCallBack(gameWorld));
        put("2", () -> pickWeaponCallBack(gameWorld));
        put("3", () -> lookAroundCallBack(gameWorld));
        put("4", () -> movePetCallBack(gameWorld));
        put("5", () -> attackTargetCallBack(gameWorld));
        put("6", () -> displayPlayerInfoCallBack(gameWorld));
      }
    });

    afterGameCmds.putAll(new HashMap<String, Runnable>() {
      {
        put("1", () -> displaySpaceInfoCallBack(gameWorld));
        put("2", () -> displayWorldInfoCallBack(gameWorld));
      }
    });
  }

  /*
   ************************************* View methods*************************************
   */
  /**
   * Functions for parsing and displaying maps.
   * 
   * @param listToDisplay list input.
   */
  private void displayMap(Map<Integer, String> listToDisplay) {
    try {

      for (Integer key : listToDisplay.keySet()) {
        out.append(String.format("%d => %s\n", (key + 1), listToDisplay.get(key)));
      }
      this.out.append("Q => Quit\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Function for displaying list.
   * 
   * @param stringList String list input.
   */
  private void displayList(List<String> stringList) {
    try {
      for (int i = 0; i < stringList.size(); i++) {
        this.out.append(String.format("%d => %s\n", (i + 1), stringList.get(i)));
      }
      this.out.append("Q => Quit\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /*
   ************************************* View methods*************************************
   */

  private void initGameCallBack(GameWorld gameWorld) {
    try {
      if (gameWorld.getPlayers().size() == 0) {
        this.out.append("No players are added, to start the game players are needed!!!");
      }
      gameWorld.initializeTurn();
      createBufferedImage();
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Execute commands private function.
   * 
   * @param gameWorld gameworld model object
   */
  private void readExecuteCommands(GameWorld gameWorld) {
    String input = "";
    try {
      // since start game is part of the GameSetup using do-while
      do {
        // pre game state
        this.out.append("GameSetup: Available commands are: \n");
        displayList(beforeCmds);
        input = in.next();

        if (input.toLowerCase().equals("q")) {
          this.out.append("Quit!!! Ending Game\n");
          break;
        }
        executor(beforeGameCmds, input);
      } while (!"6".equalsIgnoreCase(input));

      // loop for post game, supporting only display info.
      while (true) {
        this.out.append("PostGame: Available commands are: \n");
        displayList(afterCmds);
        input = in.next();

        if (input.toLowerCase().equals("q")) {
          this.out.append("Quit!!! Ending Game\n");
          break;
        }
        executor(afterGameCmds, input);
      }

    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * handles gamerunner private function.
   * 
   * @param gameWorld gameworld model object
   */
  private void gameRunner(GameWorld gameWorld) {
    try {
      String turnName = gameWorld.getTurnName();
      while (!gameWorld.isGameOver()) {
        PlayerType turnsType = gameWorld.getTurnsType();
        if (turnsType.equals(PlayerType.HUMAN)) {
          this.out.append("---------------------------------------------------------------\n");
          this.out.append(gameWorld.getTargetInfo());
          this.out.append("---------------------------------------------------------------\n");
          this.out.append("Available playing commands are: \n");
          displayList(playCmds);
          this.out.append(
              String.format("Enter command for Player %s's turn\n", gameWorld.getTurnName()));
          String input = this.in.next();
          if (input.toLowerCase().equals("q")) {
            this.out.append("Quit!!! Running Game\n");
            break;
          }
          executor(playGameCmds, input);
        } else {
          this.out
              .append(String.format("It's %s Turn, which is automatically performed.", turnName));
          this.out.append(gameWorld.computersTurn(randomGenerator));
        }
      }

      if (gameWorld.isGameOver()) {
        this.out.append(gameWorld.getWinner());
      }

    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Command Executor.
   * 
   * @param cmds commands
   * @param ip   input
   */
  private void executor(Map<String, Runnable> cmds, String ip) {
    try {
      Runnable runnable = cmds.get(ip);
      if (runnable == null) {
        this.out.append(String.format("Invalid command: %s. Please check once again!!!", ip));
        return;
      }
      // execute the command
      runnable.run();
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  @Override
  public void startGame() {
    if (gameWorld == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    loadCmdLabels();
    loadCommandFuncs(this.gameWorld);
    this.createBufferedImage();
    this.gameView.setupComponent();
    this.gameView.setFeatures(this);
    this.gameView.enableWelcomeScreen();
  }

  @Override
  public void playGame() {
    this.initGameCallBack(this.gameWorld);
    gameView.initTurnScreen();
    gameView.enableGamePlayScreen();
    this.checkPlayerInTurn();
  }

  @Override
  public void createBufferedImage() {
    new CreateGraphicalWorld().execute(gameWorld);

  }

  @Override
  public void performComputerPlayerTurn() {
    String turnName = gameWorld.getTurnName();
    String output = gameWorld.computersTurn(randomGenerator);
    gameView.setEchoOutput(
        String.format("It's %s Turn, which is automatically performed, %s", turnName, output));
    checkPlayerInTurn();

  }

  @Override
  public String updatePreReqs(List<String> params) {
    if (params == null || params.isEmpty()) {
      return "Using Default values as parameters passed are null or empty";
    }

    if (params.get(1) == null || "".equals(params.get(1))) {
      return "Provided Invalid Turns Count, So using default value!!";
    }

    try {
      int turnsCount = Integer.parseInt(params.get(1));
      String output = gameWorld.updatePreReqs(params.get(0), turnsCount);
      gameView.refresh();
      return output;
    } catch (NumberFormatException nfe) {
      return "Provided Invalid Parameters, So using default value!!";

    }

  }

  @Override
  public void startWithDefaultMap() {
    gameView.enableDefaultMap();
  }

  @Override
  public void startCustomMap() {
    gameView.enableCustomMap();
  }

  @Override
  public void promptToGetPreReqInfo(boolean isDefaultRoute) {
    List<String> params = gameView.promptToGetPreReqs(isDefaultRoute);
    gameView.setEchoOutput(this.updatePreReqs(params));
    gameView.enablePreGameScreen();
  }

  @Override
  public void getSpaceDescription() {
    int inputSpaceIndex = gameView.getInputSpaceIndexForSpaceDescription();
    if (inputSpaceIndex == -1) {
      gameView.setEchoOutput("Invalid space index provided!!");
    } else {
      gameView.setEchoOutput(new DisplaySpaceInfo(inputSpaceIndex).execute(gameWorld));
    }

  }

  @Override
  public void addPlayer() {
    PlayerType playerType = gameView.getPlayerTypeToBeAdded();
    if (playerType == null) {
      gameView.setEchoOutput("Invalid PlayerType selected!!");
    } else if (playerType.equals(PlayerType.HUMAN)) {
      addHumanPlayer();
    } else {
      addComputerPlayer();
    }
  }

  private void addHumanPlayer() {
    List<String> params = gameView.getInputForAddHumanPlayer();
    if (params == null) {
      gameView.setEchoOutput("Input cannot be null!!!");
    }
    gameView.setEchoOutput(new AddPlayer(params.get(0), Integer.parseInt(params.get(1)),
        Integer.parseInt(params.get(2))).execute(gameWorld));
    gameView.enablingStartGame();

  }

  private void addComputerPlayer() {
    gameView.setEchoOutput(new AddComputerPlayer(new RandomGenerator()).execute(gameWorld));
    gameView.enablingStartGame();
  }

  private boolean checkIfPlayerInTurnIsHuman() {
    if (gameWorld.getTurnsType().equals(PlayerType.HUMAN)) {
      return true;
    } else {
      return false;
    }

  }

  @Override
  public void checkPlayerInTurn() {
    if (checkGameOver() == false) {
      createBufferedImage();
      if (gameWorld.getTurnsType().equals(PlayerType.COMP)) {
        gameView.turnUpdatesOnView();
        performComputerPlayerTurn();
      } else {
        gameView.turnUpdatesOnView();
      }
    }

  }

  private boolean checkGameOver() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      throw new IllegalArgumentException("Error while puuting to sleep!!!");
    }
    if (gameWorld.isGameOver()) {
      gameView.enableEndGamePlayScreen();
      gameView.updateWinner();
      return true;
    } else {
      return false;
    }

  }

  @Override
  public void pickWeapon() {
    if (checkIfPlayerInTurnIsHuman()) {
      String output = getWeaponsInSpace();
      if ("".equals(output)) {
        String getWeaponToBePicked = gameView.getInputWeaponToBePicked();
        gameView.setEchoOutput(pickWeapon(getWeaponToBePicked));
      } else {
        gameView.setEchoOutput(output);
      }
      checkPlayerInTurn();
    }

  }

  private String pickWeapon(String getWeaponToBePicked) {
    if (gameWorld.getWeaponsOfPlayerSpace().containsValue(getWeaponToBePicked)) {
      return new PickWeapon(getWeaponToBePicked).execute(gameWorld);

    } else {
      return "Invalid Weapon Selected!";
    }

  }

  private String getWeaponsInSpace() {
    if (gameWorld.isPlayerItemThresholdReached()) {
      return "Oops! Weapons Threshold reached, Cannot pick one";
    }
    if (gameWorld.getWeaponsOfPlayerSpace().size() <= 0) {
      return "Oops! No weapons available to pick";
    }
    return "";
  }

  @Override
  public void lookAround() {
    if (checkIfPlayerInTurnIsHuman()) {
      gameView.setEchoOutput(new LookAround().execute(gameWorld));
      checkPlayerInTurn();
    }
  }

  @Override
  public void movePet() {
    if (checkIfPlayerInTurnIsHuman()) {
      String spaceIndex = gameView.getInputToMovePet();
      movePet(spaceIndex);
      checkPlayerInTurn();
    }

  }

  private void movePet(String spaceIndex) {
    Map<Integer, String> spaces = gameWorld.getSpaces();

    try {
      final int ipSpaceIdx = Integer.parseInt(spaceIndex);
      if (spaces.containsKey(ipSpaceIdx)) {
        String output = new MovePet(ipSpaceIdx).execute(gameWorld);
        gameView.setEchoOutput(output);
      } else {
        gameView.setEchoOutput("Provided SpaceIndex doesn't exist!");
      }
    } catch (NumberFormatException nfe) {
      throw new NumberFormatException("Invalid Space Index Provided!");
    }

  }

  @Override
  public void killTarget() {
    if (checkIfPlayerInTurnIsHuman()) {
      String weaponToUse = gameView.getInputToKillTarget();
      attackTarget(weaponToUse);
      checkPlayerInTurn();
    }

  }

  private void attackTarget(String weapon) {
    Map<Integer, String> weaponsOfPlayer = gameWorld.getWeaponsOfPlayers();

    if (!weaponsOfPlayer.containsValue(weapon)) {
      gameView.setEchoOutput("Invalid weapon provided!");
    } else {
      gameView.setEchoOutput(new AttackTarget(weapon).execute(gameWorld));
    }

  }

  @Override
  public void decodeMoveCoOrdinates(int x, int y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("X or Y coordinates of move player are negative!!!");
    }

    if (checkIfPlayerInTurnIsHuman()) {
      String output = new MovePlayer(x, y).execute(gameWorld);
      gameView.setEchoOutput(output);
      checkPlayerInTurn();
    }

  }

  @Override
  public void restartGame() {
    // gameView.enableWelcomeScreen();
    startGame();

  }

  @Override
  public void promptToQuitGame() {
    if (gameView.promptToQuitGame()) {
      System.exit(0);
    }

  }

}

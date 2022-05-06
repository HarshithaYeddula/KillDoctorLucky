/**
 * Test class for validating CommandController implementation, uses mock models.
 */
public class CommandControllerTest {
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testNullModel() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n3\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(null);
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testNullAppendable() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n3\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, null, randomGenerator);
//  }
//
//  @Test
//  public void testAddPlayer() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n3\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//
//    String exp = "Enter the maxItems count for : 123456789!\n"
//            + "\n" + "----------------- End Adding Player ----------------\n";
//    assertTrue(log.toString().contains(exp));
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Player is in Progress ----------------\n"
//            + "\n"
//            + "Enter the name of the player : Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want player: ganesh to be placed: \n"
//            + "Enter the maxItems count for : 123456789!\n"
//            + "\n"
//            + "----------------- End Adding Player ----------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Creating GUI of gameWorld in progress --------------\n"
//            + "Graphical representation of the world is created successfully!!!\n"
//            + "\n"
//            + "----------------- Ending GUI creation -----------------------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    gameController.startGame(gameWorld);
//    String actualLog = log.toString();
//    String actualUnique = out.toString();
//    String expectedlog = "Player: ganesh added successfully on space: 1";
//    assertEquals(expectedUnique, actualUnique);
//    assertEquals(expectedlog, actualLog);
//  }
//
//  @Test
//  public void testAddPlayerToNonSpace() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n4\n6\n3\nq\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//
//    String expectedlog = "Player: ganesh added successfully on space: 1";
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Player is in Progress ----------------\n"
//            + "\n"
//            + "Enter the name of the player : Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want player: ganesh to be placed: "
//            + "Invalid space index as been entered \n"
//            + "\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "---------------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "----------------- LookAround Command in Progress ----------------------\n"
//            + "123456789!\n"
//            + "\n"
//            + "----------------- End LookAround Command -----------------------------\n"
//            + "---------------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "Quit!!! Running Game\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//
//    gameController.startGame(gameWorld);
//    String actualUnique = out.toString();
//    assertEquals(expectedUnique, actualUnique);
//  }
//
//  @Test
//  public void testMovePlayer() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n3\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//
//    String expectedlog = "Player: ganesh added successfully on space: 1";
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Player is in Progress ----------------\n"
//            + "\n"
//            + "Enter the name of the player : Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want player: ganesh to be placed: \n"
//            + "Enter the maxItems count for : 123456789!\n"
//            + "\n"
//            + "----------------- End Adding Player ----------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Creating GUI of gameWorld in progress --------------\n"
//            + "Graphical representation of the world is created successfully!!!\n"
//            + "\n"
//            + "----------------- Ending GUI creation -----------------------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//
//    gameController.startGame(gameWorld);
//    String actualUnique = out.toString();
//    assertEquals(expectedUnique, actualUnique);
//  }
//
//  @Test
//  public void testMovePet() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n6\n4\n2\n\nq\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(gameWorld);
//    String actualUnique = out.toString();
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Player is in Progress ----------------\n"
//            + "\n"
//            + "Enter the name of the player : Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want player: ganesh to be placed: \n"
//            + "Enter the maxItems count for : 123456789!\n"
//            + "\n"
//            + "----------------- End Adding Player ----------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "---------------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "----------------- Moving Pet Command in Progress ----------------------\n"
//            + "Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want pet to be placed: 123456789!"
//            + "----------------- End Moving Pet Command ----------------------\n"
//            + "---------------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "Quit!!! Running Game\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    assertEquals(expectedUnique, actualUnique);
//    String expectedLog = "Player: ganesh added successfully on space: 1Initializing turn\n"
//            + "Reached MovePet method";
//    String actualLog = log.toString();
//    assertEquals(expectedLog, actualLog);
//  }
//
//  @Test
//  public void testAttackTarget() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n6\n5\n2\n\nq\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(gameWorld);
//    String actualUnique = out.toString();
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Player is in Progress ----------------\n"
//            + "\n"
//            + "Enter the name of the player : Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want player: ganesh to be placed: \n"
//            + "Enter the maxItems count for : 123456789!\n"
//            + "\n"
//            + "----------------- End Adding Player ----------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "---------------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "----------------- Attack target Command in Progress -------------------\n"
//            + "Available weapons in the space: \n"
//            + "2 => Revolver\n"
//            + "3 => Gun\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the weapon index that you want to pick: 123456789!\n"
//            + "\n"
//            + "---------------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "Quit!!! Running Game\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    assertEquals(expectedUnique, actualUnique);
//    String expectedLog = "Player: ganesh added successfully on space: 1Initializing turn\n"
//            + "Reached AttackTarget method";
//    String actualLog = log.toString();
//    assertEquals(expectedLog, actualLog);
//  }
//
//  @Test
//  public void testMoveToInvalidNeighbor() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n3\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//
//    String expectedlog = "Player: ganesh added successfully on space: 1";
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Player is in Progress ----------------\n"
//            + "\n"
//            + "Enter the name of the player : Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want player: ganesh to be placed: \n"
//            + "Enter the maxItems count for : 123456789!\n"
//            + "\n"
//            + "----------------- End Adding Player ----------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Creating GUI of gameWorld in progress --------------\n"
//            + "Graphical representation of the world is created successfully!!!\n"
//            + "\n"
//            + "----------------- Ending GUI creation -----------------------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//
//    gameController.startGame(gameWorld);
//    String actualUnique = out.toString();
//    assertEquals(expectedUnique, actualUnique);
//  }
//
//  @Test
//  public void testDisplayWorldInfo() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("1\nq\nq");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(gameWorld);
//
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Loading World Information ----------------\n"
//            + "123456789!\n"
//            + "\n"
//            + "----------------- End World Information -----------------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    String expectedLog = "World is full of spaces";
//    String actualLog = log.toString();
//    String actualUnique = out.toString();
//    assertEquals(expectedUnique, actualUnique);
//    assertEquals(expectedLog, actualLog);
//  }
//
//  @Test
//  public void testDisplaySpaceInfo() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("2\n2\nq\nq");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(gameWorld);
//
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Loading Spaces Information ----------------\n"
//            + "Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index you want the description for : 123456789!\n"
//            + "\n"
//            + "----------------- End Space Information ----------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    String expectedLog = "Space is Armory";
//    String actualLog = log.toString();
//    String actualUnique = out.toString();
//    assertEquals(expectedUnique, actualUnique);
//    assertEquals(expectedLog, actualLog);
//  }
//
//  @Test
//  public void testDisplaySpaceInfoInvalidSpace() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("2\n1\nq\nq");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(gameWorld);
//
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Loading Spaces Information ----------------\n"
//            + "Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index you want the description for :"
//            + " Invalid space index as been entered \n"
//            + "\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    String actualUnique = out.toString();
//    assertEquals(expectedUnique, actualUnique);
//  }
//
//  @Test
//  public void testDisplayPlayerInfo() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n6\n6\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(gameWorld);
//
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Player is in Progress ----------------\n"
//            + "\n"
//            + "Enter the name of the player : Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want player: ganesh to be placed: \n"
//            + "Enter the maxItems count for : 123456789!\n"
//            + "\n"
//            + "----------------- End Adding Player ----------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "---------------------------------------------------------------\n"
//            + "Target info---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "----------------- Display Player Info is in Progress ----------------\n"
//            + "123456789!----------------- End Player Info ------------------\n"
//            + "---------------------------------------------------------------\n"
//            + "Target info---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "Quit!!! Running Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    String actualUnique = out.toString();
//    assertEquals(expectedUnique, actualUnique);
//    String expectedLog = "Player: ganesh added successfully on space: 1Initializing turn\n"
//            + "Reached playerInfo method";
//    String actualLog = log.toString();
//    assertEquals(expectedLog, actualLog);
//  }
//
//  @Test
//  public void testPickItem() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n6\n2\n2\n\nq\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(gameWorld);
//    String actualUnique = out.toString();
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Player is in Progress ----------------\n"
//            + "\n"
//            + "Enter the name of the player : Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want player: ganesh to be placed: \n"
//            + "Enter the maxItems count for : 123456789!\n"
//            + "\n"
//            + "----------------- End Adding Player ----------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "---------------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "----------------- Picking weapon in progress ------------------------\n"
//            + "No space for new weapons, please drop weapon to pick new ones.--------"
//            + "-------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "----------------- Picking weapon in progress ------------------------\n"
//            + "No space for new weapons, please drop weapon to pick new ones.---------"
//            + "------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "Quit!!! Running Game\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    assertEquals(expectedUnique, actualUnique);
//    String expectedLog = "Player: ganesh added successfully on space: 1"
//            + "Initializing turnReached Limit!!!!Reached Limit!!!!";
//    String actualLog = log.toString();
//    assertEquals(expectedLog, actualLog);
//  }
//
//  @Test
//  public void testLookAround() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("4\nganesh\n2\n6\n6\n3\nq\n\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(1, 2, 3, 4, 5);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(gameWorld);
//    String actualUnique = out.toString();
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Player is in Progress ----------------\n"
//            + "\n"
//            + "Enter the name of the player : Available spaces: \n"
//            + "2 => Armory\n"
//            + "3 => Drawing Room\n"
//            + "Q => Quit\n"
//            + "\n"
//            + "Enter the space index where you want player: ganesh to be placed: \n"
//            + "Enter the maxItems count for : 123456789!\n"
//            + "\n"
//            + "----------------- End Adding Player ----------------\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "---------------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "----------------- LookAround Command in Progress ----------------------\n"
//            + "123456789!\n"
//            + "\n"
//            + "----------------- End LookAround Command -----------------------------\n"
//            + "---------------------------------------------------------------\n"
//            + "null---------------------------------------------------------------\n"
//            + "Available playing commands are: \n"
//            + "1 => Move Player\n"
//            + "2 => Pick Weapon\n"
//            + "3 => Look Around\n"
//            + "4 => Move Pet\n"
//            + "5 => Attack Target\n"
//            + "6 => Display Player Info (will not count as turn)\n"
//            + "Q => Quit\n"
//            + "Enter command for Player ganesh's turn\n"
//            + "Quit!!! Running Game\n"
//            + "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    assertEquals(expectedUnique, actualUnique);
//    String expectedLog = "Player: ganesh added successfully on space: 1Initializing turn\n"
//            + "Look around is working";
//    String actualLog = log.toString();
//    assertEquals(expectedLog, actualLog);
//  }
//
//  @Test
//  public void testComputerPlayer() {
//    StringBuffer out = new StringBuffer();
//    Readable in = new StringReader("5\n\nq\n\nq\nq\n");
//    StringBuilder log = new StringBuilder();
//    GameWorld gameWorld = new MockWorldModel(log, "123456789!");
//    RandomGenerator randomGenerator = new RandomGenerator(100, 1, 2, 1);
//    GameController gameController = new CommandController(in, out, randomGenerator);
//    gameController.startGame(gameWorld);
//    String actualUnique = out.toString();
//    String expectedUnique = "GameSetup: Available commands are: \n"
//            + "1 => Display GameWorld Info\n"
//            + "2 => Display Space Info\n"
//            + "3 => Create GameWorld GUI\n"
//            + "4 => Add Player\n"
//            + "5 => Add Computer Player\n"
//            + "6 => Start Game\n"
//            + "Q => Quit\n"
//            + "----------------- Adding Computer Player in Progress ----------------\n"
//            + "123456789!----------------- End Adding Computer Player ----------------\n"
//            + "PostGame: Available commands are: \n"
//            + "1 => Display Space Info\n"
//            + "2 => Display World Info\n"
//            + "Q => Quit\n"
//            + "Quit!!! Ending Game\n";
//    assertEquals(expectedUnique, actualUnique);
//    String expectedLog = "Computer player is added";
//    String actualLog = log.toString();
//    assertEquals(expectedLog, actualLog);
//  }
}

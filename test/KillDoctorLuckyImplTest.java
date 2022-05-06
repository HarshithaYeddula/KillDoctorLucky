import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import gameworld.model.Pet;
import gameworld.model.PetImpl;
import gameworld.model.Space;
import gameworld.model.SpaceImpl;
import gameworld.model.Target;
import gameworld.model.TargetImpl;
import gameworld.model.Weapon;
import gameworld.model.WeaponImpl;
import gameworld.service.GameWorld;
import gameworld.service.KillDoctorLuckyImpl;
import gameworld.service.RandomGenerator;
import gameworld.util.Graph;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing KillDoctorLucky Implementation.
 */
public class KillDoctorLuckyImplTest {
  private static final String fileToTest = "res/tests/fileToTest.txt";
  private static final String POKE = "poke";
  private int maxX;
  private int maxY;
  private int maxSpaces;
  private int maxWeapons;
  private List<Space> spacesList;
  private List<Weapon> weaponsList;
  private Target target;
  private Pet pet;
  private Map<Weapon, Boolean> evidenceMap;
  private Graph graph;
  private List<Integer> dfsPetMap;
  private int petLocIndex;
  private String shortMansion;

  /**
   * PreSetup method for object creation.
   */
  @Before
  public void setUp() {
    spacesList = new ArrayList<>();
    weaponsList = new ArrayList<>();
    evidenceMap = new HashMap<>();
    dfsPetMap = new ArrayList<>();
    shortMansion = "36 30 Kurukshetra\n" + "6 Duryodhana\n" + "Ashva\n" + "5\n"
        + "22 19 23 26 Arjuna\n" + "16 21 21 28 Bheema\n" + "12 11 21 20 Yudistira\n"
        + "22 13 25 18 Nakula\n" + "26 13 27 18 Sahadeva\n" + "6\n" + "0 3 Desire\n"
        + "0 3 Arrogance\n" + "1 3 Anger\n" + "2 3 Delusion\n" + "3 3 Greed\n" + "4 3 Miserliness";
    try {
      readFile(fileToTest);
    } catch (IOException e) {
      System.out.println("Make sure fileToTest exists inside res/tests directory!!!");
    }
  }

  /**
   * Local method for mansion calculation.
   *
   * @param firstLine FirstLine of input file
   */
  private void fetchMansionDetails(String firstLine) {
    String[] strings = firstLine.trim().split("\\s+");
    this.maxY = Integer.parseInt(strings[0]);
    this.maxX = Integer.parseInt(strings[1]);
  }

  /**
   * Local method for mansion calculation.
   *
   * @param secondLine SecondLine of input file
   */
  private void fetchTargetDetails(String secondLine) {
    String[] strings = secondLine.trim().split("\\s+");
    String[] nameArray = Arrays.copyOfRange(strings, 1, strings.length);
    int targetHealth = Integer.parseInt(strings[0]);
    String targetName = String.join(" ", nameArray);
    /*
     * initializing target without space details.
     */
    this.target = new TargetImpl(targetName, targetHealth);
  }

  /**
   * Local method for pet location calculation.
   *
   * @param thirdLine ThirdLine of input file
   */
  private void fetchPetDetails(String thirdLine) throws IllegalFormatException {
    String petName = thirdLine.trim();
    if (petName.length() < 1) {
      throw new IllegalArgumentException("Error while creating Pet" + ": please check input file.");
    }

    pet = new PetImpl(petName, 0);
  }

  /**
   * Local function for initialize target at 0th indexed space.
   */
  private void initializeTargetWithSpace() {
    this.target.setSpace(this.spacesList.get(0));
  }

  /**
   * Function for creating space from the textfile.
   *
   * @param spaceLine Line from which we have to extract space information
   * @param id        space index
   */
  private void createSpaces(String spaceLine, int id) {
    String[] strings = spaceLine.trim().split("\\s+");
    int y11 = Integer.parseInt(strings[0]);
    int x11 = Integer.parseInt(strings[1]);
    int y21 = Integer.parseInt(strings[2]);
    int x21 = Integer.parseInt(strings[3]);

    if (x11 > maxX || x21 > maxX || y11 > maxY || y21 > maxY) {
      throw new IllegalArgumentException("CoOrdinates are out of bounds!!!");
    }

    List<Integer> coOrdinates = new ArrayList<>(Arrays.asList(y11, x11, y21, x21));

    String[] nameArray = Arrays.copyOfRange(strings, 4, strings.length);
    String spaceName = String.join(" ", nameArray);

    Space space = new SpaceImpl(id, spaceName, coOrdinates);
    this.spacesList.add(space);
  }

  /**
   * Method for finding neighbors.
   *
   * @param space1 Space1, for which we are finding neighbor.
   * @param space2 Space2, to check if it's neighbor to space1.
   * @return Returns true if space2 is space1's neighbor
   */
  private Boolean isNeighBor(Space space1, Space space2) {
    List<Integer> coOrdinates1 = space1.getCoOrdinates();
    int y11 = coOrdinates1.get(0);
    int x11 = coOrdinates1.get(1);
    int y21 = coOrdinates1.get(2);
    int x21 = coOrdinates1.get(3);

    List<Integer> coOrdinates2 = space2.getCoOrdinates();
    int y31 = coOrdinates2.get(0);
    int x31 = coOrdinates2.get(1);
    int y41 = coOrdinates2.get(2);
    int x41 = coOrdinates2.get(3);

    boolean checkY = ((y11 >= y31 && y11 <= y41 && y21 >= y31 && y21 >= y41)
        || (y11 <= y31 && y11 <= y41 && y21 >= y31 && y21 <= y41)
        || (y11 <= y31 && y11 <= y41 && y21 >= y31 && y21 >= y41)
        || (y11 >= y31 && y11 <= y41 && y21 >= y31 && y21 <= y41));
    boolean checkX = ((x11 >= x31 && x11 <= x41 && x21 >= x31 && x21 >= x41)
        || (x11 <= x31 && x11 <= x41 && x21 >= x31 && x21 <= x41)
        || (x11 <= x31 && x11 <= x41 && x21 >= x31 && x21 >= x41)
        || (x11 >= x31 && x11 <= x41 && x21 >= x31 && x21 <= x41));

    if ((y11 - 1) == y41) {
      return checkX;
    } else if ((y21 + 1) == y31) {
      return checkX;
    } else if ((x11 - 1) == x41) {
      return checkY;
    } else if ((x21 + 1) == x31) {
      return checkY;
    }

    return false;
  }

  /**
   * Function for creating neighbors.
   */
  private void createNeighbours() {
    for (Space space : this.spacesList) {
      List<Space> neighborSpaces = new ArrayList<>();

      for (int i = 0; i < spacesList.size(); i++) {
        if (space.getIndex() != i) {
          Space selectedSpace = this.spacesList.get(i);
          if (isNeighBor(space, selectedSpace)) {
            neighborSpaces.add(selectedSpace);
          }
        }
      }
      space.updateNeighbours(neighborSpaces);
    }
  }

  /**
   * Function for Weapons extraction from the input text file.
   *
   * @param weaponLine Line from txt file
   * @param id         Index of the line
   * @throws IllegalFormatException Exception thrown on error in weapon text
   *                                format.
   */
  private void createWeapons(String weaponLine, int id) {
    String[] strings = weaponLine.trim().split("\\s+");
    int spaceIdx = Integer.parseInt(strings[0]);
    int damageAmt = Integer.parseInt(strings[1]);
    String[] nameArray = Arrays.copyOfRange(strings, 2, strings.length);
    String weaponName = String.join(" ", nameArray);

    Space space = this.spacesList.get(spaceIdx);
    Weapon weapon = new WeaponImpl(weaponName, space, damageAmt);
    space.addWeapons(weapon);
    this.weaponsList.add(weapon);
  }

  /**
   * Function for reading input file.
   *
   * @param fileName FileName from which we have to create world
   * @throws IOException If file is missing, IO exception is thrown.
   */
  private void readFile(String fileName) throws IOException {
    Reader fileReader = new FileReader(fileName);
    Scanner sc = new Scanner(fileReader);
    // since we are testing a co-ordinates of file which we have created for
    // testing,
    // not adding any validation.
    this.fetchMansionDetails(sc.nextLine());
    this.fetchTargetDetails(sc.nextLine());
    this.fetchPetDetails(sc.nextLine());

    maxSpaces = Integer.parseInt(sc.nextLine());

    for (int i = 0; i < maxSpaces; i++) {
      this.createSpaces(sc.nextLine(), i);
    }

    this.initializeTargetWithSpace();

    this.createNeighbours();

    if (sc.hasNextInt()) {
      maxWeapons = Integer.parseInt(sc.nextLine());
    }

    this.initializeTarget();

    for (int i = 0; i < maxWeapons; i++) {
      this.createWeapons(sc.nextLine(), i);
    }

    if (sc.hasNextLine()) {
      throw new IllegalArgumentException("Illegal data entry");
    }

    sc.close();
  }

  /**
   * Function to initialize target at zeroth space.
   */
  private void initializeTarget() {
    this.target.setSpace(this.spacesList.get(0));
  }

  /**
   * Function to add Player with turn.
   * 
   * @param gameWorld GameWorld Object.
   */
  private void addPlayerWithTurn(GameWorld gameWorld) {
    gameWorld.addPlayer("ganesh", 3, 2);
    gameWorld.addPlayer("gunesh", 1, 1);
    gameWorld.initializeTurn();
  }

  /**
   * Function for GameWorld Object creation.
   *
   * @param fileName Input File needed for world creation.
   * @return GameWorld Object
   * @throws IOException Exception thrown if file is missing res directory
   */
  protected GameWorld killDoctorLucky(String fileName) {
    Readable fileReader = null;
    try {
      fileReader = new InputStreamReader(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      System.err.println("File creation is failing.");
    }
    GameWorld doctorLucky = new KillDoctorLuckyImpl(fileReader, 20);
    addPlayerWithTurn(doctorLucky);
    return doctorLucky;
  }

  /**
   * Test to make sure any space overlaps in x-axis.
   */

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapX() {
    GameWorld world = killDoctorLucky("res/tests/x-overlap.txt");
  }

  /**
   * Test to make sure any space overlaps in y-axis.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapY() {
    GameWorld world = killDoctorLucky("res/tests/y-overlap.txt");
  }

  /**
   * Test to make sure any space overlaps in xy-axis.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapxy() {
    GameWorld world = killDoctorLucky("res/tests/xy-overlap.txt");
  }

  /**
   * Test to make sure any space overlaps with spaceInSpace.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapSpaceInSpace() {
    GameWorld world = killDoctorLucky("res/tests/spaceInSpaceOverlap.txt");
  }

  /**
   * Test to make sure any space overlaps in xy-axis.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTargetHealth() {
    GameWorld world = killDoctorLucky("res/tests/zeroTargetHealth.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTargetHealth() {
    GameWorld world = killDoctorLucky("res/tests/negativeTargetHealth.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoreSpacesThanExcepted() {
    GameWorld world = killDoctorLucky("res/tests/moreSpaces.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoreWeaponsThanExcepted() {
    GameWorld world = killDoctorLucky("res/tests/moreWeapons.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSameCoOrdinates() {
    GameWorld world = killDoctorLucky("res/tests/sameCoOrdinates.txt");
  }

  /**
   * Test to make sure target is initialized in the 0th index space.
   */
  @Test
  public void testTargetInitializedAtZero() {
    GameWorld world = killDoctorLucky("res/tests/fileToTest.txt");
    Space locationOfTarget = world.getLocationOfTarget();
    String expectedSpaceName = "Armory";
    /*
     * String petInfo = world.getPetInfo(); String expectedPet = "Pet Info: \n" +
     * "Name: Fortune the Cat\n" + "Space: 0";
     */
    assertEquals(locationOfTarget.getName(), expectedSpaceName);
    // assertEquals(expectedPet, petInfo);
  }

  /**
   * Test to make sure target movement based on the space.
   */
  @Test
  public void testTargetMovement() {
    GameWorld world = killDoctorLucky("res/tests/fileToTest.txt");
    Space initialLocationOfTarget = world.getLocationOfTarget();
    String expectedInitialSpaceName = spacesList.get(0).getName();
    assertEquals(initialLocationOfTarget.getName(), expectedInitialSpaceName);

    world.moveTarget();

    Space newLocationOfTargetAfterMov = world.getLocationOfTarget();
    String expectedSpaceNameAfterMovement = spacesList.get(1).getName();
    assertEquals(newLocationOfTargetAfterMov.getName(), expectedSpaceNameAfterMovement);
  }

  /**
   * Test to make sure target movement, and throw exception once it reached last
   * space.
   */
  @Test
  public void testTargetReachedLastSpace() {
    GameWorld world = killDoctorLucky("res/tests/fileToTest.txt");
    Space initialLocationOfTarget = world.getLocationOfTarget();
    String expectedInitialSpaceName = spacesList.get(0).getName();
    assertEquals(initialLocationOfTarget.getName(), expectedInitialSpaceName);

    world.moveTarget();

    Space newLocationOfTargetAfterMov = world.getLocationOfTarget();
    String expectedSpaceNameAfterMovement = spacesList.get(1).getName();
    assertEquals(newLocationOfTargetAfterMov.getName(), expectedSpaceNameAfterMovement);

    for (int i = 1; i < (maxSpaces - 1); i++) {
      world.moveTarget();
    }

    Space lastLocationOfTarget = world.getLocationOfTarget();
    String expectedLastSpaceName = spacesList.get(maxSpaces - 1).getName();
    assertEquals(lastLocationOfTarget.getName(), expectedLastSpaceName);
  }

  /**
   * Test to make sure target movement, and throw exception once it reached last
   * space.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTargetMovementToMax() {
    GameWorld world = killDoctorLucky("res/tests/fileToTest.txt");
    Space initialLocationOfTarget = world.getLocationOfTarget();
    String expectedInitialSpaceName = spacesList.get(0).getName();
    assertEquals(initialLocationOfTarget.getName(), expectedInitialSpaceName);

    world.moveTarget();

    for (int i = 0; i < maxSpaces; i++) {
      world.moveTarget();
    }
  }

  /**
   * Test to make sure neighbours for all spaces. Through this we are also
   * validating even spaces created are in proper order.
   */
  @Test
  public void validateNeighboursForAllSpaces() {
    GameWorld world = killDoctorLucky("res/tests/fileToTest.txt");
    for (int i = 0; i < maxSpaces; i++) {
      if (!(spacesList.get(i).getNeighbours().size() == world.getNeighboursOfSpace(i).size())) {
        fail(String.format("Get neighbours is failing for space with ID: %d", i));
      }
    }
  }

  /**
   * Test to make sure neighbours for all spaces.
   */
  @Test
  public void validateDataNeighboursForAspace() {

    GameWorld world = killDoctorLucky("res/tests/fileToTest.txt");
    Random r = new Random();

    int index = r.nextInt(21);

    List<Space> expectedNeighbours = spacesList.get(index).getNeighbours();
    List<Space> neighboursOfSpace = world.getNeighboursOfSpace(index);

    String expectedName = expectedNeighbours.get(0).getName();
    boolean isPresent = false;

    for (Space space : neighboursOfSpace) {
      if (space.getName().equals(expectedName)) {
        isPresent = true;
        break;
      }
    }

    if (!isPresent) {
      fail(String.format("Neigbours are not matching for space index: %d", index));
    }
  }

  /**
   * ----------------------Input File
   * Validations-----------------------------------------------. Test to make sure
   * target entry is there in the input file.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMissingTarget() {
    GameWorld world = killDoctorLucky("res/tests/missingTarget.txt");
  }

  /**
   * Test to make sure target entry format in inputfile.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTargetFormat() {
    GameWorld world = killDoctorLucky("res/tests/exchangedTarget.txt");
  }

  /**
   * Test to make sure number of space entry is correct.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectNumSpaces() {
    GameWorld world = killDoctorLucky("res/tests/incorrectNumOfSpaces.txt");
  }

  /**
   * Test to make sure max spaces entry is present in input file.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testmissingMaxSpacesEntry() {
    GameWorld world = killDoctorLucky("res/tests/missingMaxSpacesEntry.txt");
  }

  /**
   * Test to make sure max weapons entry is present in input file.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testmissingMaxWeaponsEntry() {
    GameWorld world = killDoctorLucky("res/tests/missingMaxWeaponsEntry.txt");
  }

  /**
   * Test to make sure number of weapons entry is matching with max weapons entry.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testincorrectNumOfWeapons() {
    GameWorld world = killDoctorLucky("res/tests/incorrectNumOfWeapons.txt");
  }

  /**
   * Validate input file, and make sure space entries are coming after weapons.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testswappedSpacesnWeapons() {
    GameWorld world = killDoctorLucky("res/tests/swappedSpacesnWeapons.txt");
  }

  /**
   * Health of the target should be number, test to make sure this.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectTargetHealthFormat() {
    GameWorld world = killDoctorLucky("res/tests/incorrectTargetHealthFormat.txt");
  }

  /**
   * Test to make sure spaces entries in the weapons are valid spaces.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSpaceInWeapons() {
    GameWorld world = killDoctorLucky("res/tests/invalidSpaceInWeapons.txt");
  }

  /**
   * Test to make sure weapon power format is number.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWeaponPowerFormat() {
    GameWorld world = killDoctorLucky("res/tests/incorrectFormatOfWeaponPower.txt");
  }

  /**
   * Test to make sure space coordinates are numbers.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCoOrdinateFormat() {
    GameWorld world = killDoctorLucky("res/tests/incorrectCoOrdinateFormat.txt");
  }

  /**
   * Test to make sure Display world info works.
   */
  @Test
  public void testDisplayWorldInfo() {
    GameWorld world = killDoctorLucky("res/tests/fileToTest.txt");
    String expected = "Info about the Game World: \n" + "Name: Doctor Lucky's Mansion\n"
        + "Target name: Doctor Lucky\n" + "Target location: 0).Armory\n"
        + "Total Number of spaces: 21\n" + "Total Number of weapons: 20\n"
        + "Total Number of players: 2\n";

    assertEquals(expected, world.getWorldInfo());
  }

  /**
   * Test to make sure look around works.
   */
  @Test
  public void testLookAround() {
    // no neighbors
    Readable in = new StringReader("40 40 Mansion\n" + "100 Lucky\n" + "Fortune the Cat\n" + "1\n"
        + "10 10 20 20 Attic\n" + "1\n" + "0 4 Bola");
    GameWorld gameWorld1 = new KillDoctorLuckyImpl(in, 10);
    gameWorld1.addPlayer("ganesh", 0, 1);
    gameWorld1.addPlayer("gunesh", 0, 1);
    gameWorld1.initializeTurn();

    // no neighbors
    String expected = "Space Info \n" + "Name of the Space: Attic\n" + "Id of the Space: 0\n"
        + "No neighbors for this space. \n" + "Weapons: Bola \n" + "Player: ganesh, gunesh \n"
        + "No neighbors for this space. \n" + "----------------------------\n";
    assertEquals(expected, gameWorld1.lookAround());

    // 1 neighbor
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    // this player is added on to the space which has only one neighbor
    gameWorld.addPlayer("test1", 6, 1);
    gameWorld.addPlayer("test2", 5, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    expected = "Space Info \n" + "Name of the Space: Green House\n" + "Id of the Space: 6\n"
        + "No neighbors for this space. \n" + "Weapons: Trowel, Pinking Shears \n"
        + "Player: test1 \n" + "Details on neighbor spaces of player are: \n"
        + "----------------------------\n" + "Space Info \n" + "Name of the Space: Hedge Maze\n"
        + "Id of the Space: 7\n" + "No neighbors for this space. \n" + "Weapons: Loud Noise \n"
        + "No players in this space. \n" + "----------------------------\n";
    assertEquals(expected, gameWorld.lookAround());

    // more than 1 neighbor
    expected = "Space Info \n" + "Name of the Space: Foyer\n" + "Id of the Space: 5\n"
        + "No neighbors for this space. \n" + "Weapons: Monkey Hand \n" + "Player: test2 \n"
        + "Details on neighbor spaces of player are: \n" + "----------------------------\n"
        + "Space Info \n" + "Name of the Space: Drawing Room\n" + "Id of the Space: 4\n"
        + "No neighbors for this space. \n" + "Weapons: Letter Opener \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Piazza\n" + "Id of the Space: 15\n"
        + "No neighbors for this space. \n" + "Weapons: Civil War Cannon \n"
        + "No players in this space. \n" + "----------------------------\n";
    assertEquals(expected, gameWorld.lookAround());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRoomIdAddPlayer() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("Gani", 30, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeRoomIdAddPlayer() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("Gani", -30, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyPlayerName() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("", 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullPlayerNameAddPlayer() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer(null, 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void duplicatePlayerName() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 3, 1);
    gameWorld.addPlayer("test", 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMaxLimit() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 3, -11);
  }

  @Test
  public void validateAddPlayer() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 3, 1);
    List<String> players = gameWorld.getPlayers();
    assertTrue(players.contains("test"));
  }

  @Test
  public void addingMultiplePlayerToSpace() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 3, 1);
    gameWorld.addPlayer("test2", 3, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    String expected = "Player Info:\n" + "Name: tes\n" + "Not carrying any weapons.\n"
        + "MaxWeapons: \n" + "Space Info\n" + "Name of the Space: Dining Hal\n"
        + "Id of the Space: \n"
        + "Neighbors: Armory, Billiard Room, Drawing Room, Kitchen, Parlor, Tennessee Room, \n"
        + "Trophy Room, Wine Cellar\n" + "No weapons on this space.\n"
        + "Player: ganesh, test, test2";
    assertEquals(expected, gameWorld.getTurnInfo());
    gameWorld.lookAround();
    String expected1 = "Player Info:  \n" + "Name: test2 \n" + "Not carrying any weapons.  \n"
        + "MaxWeapons: 1 \n" + "Space Info  \n" + "Name of the Space: Dining Hall \n"
        + "Id of the Space: 3 \n" + "Neighbors: Armory, Billiard Room, "
        + "Drawing Room, Kitchen, Parlor, Tennessee Room, Trophy Room, Wine Cellar  \n"
        + "No weapons on this space.  \n" + "Player: ganesh, test, test2  \n";
    assertEquals(expected1, gameWorld.getTurnInfo());
  }

  /**
   * Test to validate item once picked not available in space anymore.
   */
  @Test
  public void validatePickItem() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.pickAnItem("Letter Opener");
    String expected = "Space Info  \n" + "Name of the Space: Drawing Room \n"
        + "Id of the Space: 4 \n" + "Neighbors: Armory, Dining Hall, Foyer, Wine Cellar  \n"
        + "No weapons on this space.  \n" + "Player: test ";
    assertEquals(expected, gameWorld.displaySpaceDescription(4));
    gameWorld.lookAround();
    gameWorld.lookAround();
    expected = "Player Info: \n" + "Name: tes \n" + "Weapons: Letter Opener \n" + "MaxWeapons:  \n"
        + "Space Info \n" + "Name of the Space: Drawing Roo \n" + "Id of the Space:  \n"
        + "Neighbors: Armory, Dining Hall, Foyer, Wine Cellar \n" + "No weapons on this space. \n"
        + "Player: test\n";
    assertEquals(expected, gameWorld.getTurnInfo());
  }

  /**
   * Wrong item name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidItemName() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.pickAnItem("Letter Opener1");
  }

  /**
   * Null item name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void nullItemName() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.pickAnItem(null);
  }

  /**
   * empty item name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void emptyItemName() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.pickAnItem("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void multiplePlayersPickSameItem() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.addPlayer("test1", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.pickAnItem("Letter Opener1");
    gameWorld.lookAround();
    gameWorld.pickAnItem("Letter Opener1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void pickingItemsThatAreNotAvailable() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 8, 1);
    gameWorld.addPlayer("test1", 8, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.displaySpaceDescription(8);
    gameWorld.pickAnItem("Letter Opener");
    gameWorld.lookAround();
    gameWorld.pickAnItem("Letter Opener");
  }

  @Test
  public void multiplePlayersPickMultipleItemsFromSameSpace() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 8, 1);
    gameWorld.addPlayer("test1", 8, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.pickAnItem("Crepe Pan");
    gameWorld.lookAround();
    gameWorld.pickAnItem("Sharp Knife");
    String expected = "Player Info: \n" + "Name: test\n" + "Weapons: Crepe Pan \n"
        + "MaxWeapons: 1\n" + " Space: Kitchen\n";
    assertEquals(expected, gameWorld.getTurnInfo());
    expected = "Player Info: \n" + "Name: test\n" + "Weapons: Sharp Knife \n" + "MaxWeapons: 1\n"
        + " Space: Kitchen\n";
    assertEquals(expected, gameWorld.getTurnInfo());
  }

  @Test(expected = IllegalArgumentException.class)
  public void pickitemAfterMax() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.pickAnItem("Letter Opener");
    gameWorld.pickAnItem("Crepe Pan");
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToInvalidSpace() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.movePlayer(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveNegavtiveSpaceId() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.movePlayer(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveSameSpaceId() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.movePlayer(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveHighSpaceId() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 4, 1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.movePlayer(40);
  }

  @Test
  public void multipleMovesAndValidateMove() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    gameWorld.addPlayer("test", 4, 1);
    String expected = "Player Info: \n" + "Name: ganesh\n" + "Not carrying any weapons. \n"
        + "MaxWeapons: 2\n" + " Space: Dining Hall\n";

    assertEquals(expected, gameWorld.getTurnInfo());
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.movePlayer(0);
    gameWorld.lookAround();
    gameWorld.lookAround();
    gameWorld.movePlayer(4);
    assertEquals(expected, gameWorld.getTurnInfo());
  }

  @Test
  public void validateTurnSwitch() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    gameWorld.addPlayer("test", 4, 1);
    String expected = "ganesh";
    assertEquals(expected, gameWorld.getTurnName());
    gameWorld.lookAround();
    expected = "gunesh";
    assertEquals(expected, gameWorld.getTurnName());
    gameWorld.lookAround();
    expected = "test";
    assertEquals(expected, gameWorld.getTurnName());
    gameWorld.movePlayer(0);
    expected = "ganesh";
    assertEquals(expected, gameWorld.getTurnName());
    System.out.println(gameWorld.getTurnInfo());
    gameWorld.pickAnItem("Letter Opener");
    expected = "gunesh";
    assertEquals(expected, gameWorld.getTurnName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void validateDisplayWorldInfoWithNoSpace() {
    Readable in = new StringReader("30 30 Mansion\n" + "11 Ganesh\n");
    GameWorld gameWorld1 = new KillDoctorLuckyImpl(in, 10);

    gameWorld1.getWorldInfo();
  }

  @Test(expected = IllegalArgumentException.class)
  public void validateInitTurnWithOutPlayers() {
    Readable in = new StringReader(
        "20 20 Mansion\n" + "100 Ganesh\n" + "1\n" + "10 10 20 20 Attic\n" + "1\n" + "0 4 Bola");
    GameWorld gameWorld1 = new KillDoctorLuckyImpl(in, 10);

    gameWorld1.initializeTurn();
  }

  @Test(expected = IllegalArgumentException.class)
  public void validateInitTurnStart() {
    Readable in = new StringReader(
        "20 20 Mansion\n" + "100 Ganesh\n" + "1\n" + "10 10 20 20 Attic\n" + "1\n" + "0 4 Bola");
    GameWorld gameWorld1 = new KillDoctorLuckyImpl(in, 10);
    gameWorld1.addPlayer("ganesh", 0, 1);
    gameWorld1.addPlayer("gunesh", 0, 1);
    gameWorld1.initializeTurn();
    String expected = "ganesh";
    assertEquals(expected, gameWorld1.getTurnName());
  }

  @Test
  public void vaildateDisplaySpaceInfo() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");

    // with no players and one item
    String expected = "Space Info \n" + "NameOfSpace: Armory\n"
        + "Neighbors: Billiard Room, Dining Hall, Drawing Room \n" + "Weapons: Revolver \n"
        + "No players in this space. \n";
    assertEquals(expected, gameWorld.displaySpaceDescription(0));

    // with one player and no item
    expected = "Space Info \n" + "NameOfSpace: Dining Hall\n"
        + "Neighbors: Armory, Billiard Room, Drawing Room, Kitchen, "
        + "Parlor, Tennessee Room, Trophy Room, Wine Cellar \n" + "No weapons on this space. \n"
        + "Player: ganesh \n";
    assertEquals(expected, gameWorld.displaySpaceDescription(3));
    gameWorld.addPlayer("test", 3, 1);

    // with more than one player
    expected = "Space Info \n" + "NameOfSpace: Dining Hall\n"
        + "Neighbors: Armory, Billiard Room, Drawing Room, Kitchen, "
        + "Parlor, Tennessee Room, Trophy Room, Wine Cellar \n" + "No weapons on this space. \n"
        + "Player: ganesh, test \n";
    assertEquals(expected, gameWorld.displaySpaceDescription(3));

    // multiple items and no players
    expected = "Space Info \n" + "NameOfSpace: Kitchen\n"
        + "Neighbors: Dining Hall, Parlor, Wine Cellar \n" + "Weapons: Crepe Pan, Sharp Knife \n"
        + "No players in this space. \n";
    assertEquals(expected, gameWorld.displaySpaceDescription(8));

    gameWorld.addPlayer("test12", 8, 1);
    gameWorld.addPlayer("test22", 8, 1);
    // multiple items and multiple players
    expected = "Space Info \n" + "NameOfSpace: Kitchen\n"
        + "Neighbors: Dining Hall, Parlor, Wine Cellar \n" + "Weapons: Crepe Pan, Sharp Knife \n"
        + "Player: test12, test22 \n";
    assertEquals(expected, gameWorld.displaySpaceDescription(8));
  }

  @Test(expected = IllegalArgumentException.class)
  public void validateMaxTurnCount() {
    Readable in = new StringReader(
        "20 20 Mansion\n" + "100 Ganesh\n" + "1\n" + "10 10 20 20 Attic\n" + "1\n" + "0 4 Bola");
    GameWorld gameWorld1 = new KillDoctorLuckyImpl(in, 2);
    gameWorld1.addPlayer("ganesh", 0, 1);
    gameWorld1.addPlayer("gunesh", 0, 1);
    gameWorld1.initializeTurn();
    gameWorld1.lookAround();
    gameWorld1.lookAround();
    gameWorld1.lookAround();
  }

  /**
   * Invalid input to computer player.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputComputerPlayerInput() {
    Readable in = new StringReader(
        "20 20 Mansion\n" + "100 Ganesh\n" + "1\n" + "1 10 2 20 Attic\n" + "1\n" + "0 4 Bola");

    GameWorld gameWorld1 = new KillDoctorLuckyImpl(in, 10);

    RandomGenerator randomObj = new RandomGenerator(0, 0);
    gameWorld1.addComputerPlayer(randomObj);
  }

  @Test
  public void testValidMoveComputerPlayer() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    RandomGenerator randomGenerator = new RandomGenerator(100, 1, 2);
    gameWorld.addComputerPlayer(randomGenerator);
    gameWorld.lookAround();
    gameWorld.lookAround();
    String expected = "Player Info: \n" + "Name: computer-100\n" + "Not carrying any weapons. \n"
        + "MaxWeapons: 2\n" + "Space Info \n" + "Name of the Space: Billiard Room\n"
        + "Id of the Space: 1\n" + "Neighbors: Armory, Dining Hall, Trophy Room \n"
        + "Weapons: Billiard Cue \n" + "Player: gunesh, computer-100 \n" + "\n";
    assertEquals(expected, gameWorld.getTurnInfo());
  }

  @Test
  public void testValidMultipleComputerPlayer() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    RandomGenerator randomGenerator = new RandomGenerator(100, 1, 2, 3, 0);
    RandomGenerator randomGenerator1 = new RandomGenerator(200, 1, 2);
    gameWorld.addComputerPlayer(randomGenerator);
    gameWorld.addComputerPlayer(randomGenerator1);
    gameWorld.lookAround();
    gameWorld.lookAround();
    String expected = "Player Info: \n" + "Name: computer-100\n" + "Not carrying any weapons. \n"
        + "MaxWeapons: 2\n" + "Space Info \n" + "Name of the Space: Billiard Room\n"
        + "Id of the Space: 1\n" + "Neighbors: Armory, Dining Hall, Trophy Room \n"
        + "Weapons: Billiard Cue \n" + "Player: gunesh, computer-100, computer-200 \n" + "\n";
    String expected1 = "Player Info: \n" + "Name: computer-200\n" + "Not carrying any weapons. \n"
        + "MaxWeapons: 2\n" + "Space Info \n" + "Name of the Space: Billiard Room\n"
        + "Id of the Space: 1\n" + "Neighbors: Armory, Dining Hall, Trophy Room \n"
        + "No weapons on this space. \n" + "Player: gunesh, computer-100, computer-200 \n" + "\n";
    assertEquals(expected, gameWorld.getTurnInfo());
    gameWorld.computersTurn(randomGenerator);
    assertEquals(expected1, gameWorld.getTurnInfo());
  }

  @Test
  public void testPickUpItemComputerPlayer() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    System.out.println(gameWorld.displaySpaceDescription(4));
    RandomGenerator randomGenerator = new RandomGenerator(100, 1, 3, 3, 0);
    gameWorld.addComputerPlayer(randomGenerator);

    gameWorld.lookAround();
    gameWorld.lookAround();
    String expected = "Player Info: \n" + "Name: computer-100\n" + "Weapons: Billiard Cue \n"
        + "MaxWeapons: 3\n" + "Space Info \n" + "Name of the Space: Billiard Room\n"
        + "Id of the Space: 1\n" + "Neighbors: Armory, Dining Hall, Trophy Room \n"
        + "No weapons on this space. \n" + "Player: gunesh, computer-100 \n" + "\n";
    gameWorld.computersTurn(randomGenerator);
    gameWorld.lookAround();
    gameWorld.lookAround();
    assertEquals(expected, gameWorld.getTurnInfo());
  }

  /**
   * this GUI creation of world.
   */
  @Test
  public void testCreateGui() {
    try {
      if (Paths.get("image.png").toFile().isFile()) {
        Files.delete(Paths.get("image.jpg"));
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("file error testing.");
    }

    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");

    String expected = "Graphical representation of the world is created successfully!!!";
    assertEquals(expected, gameWorld.createBufferedImage());
    assertTrue(Paths.get("image.jpg").toFile().isFile());
  }

  /**
   * Attack Target with empty weaponName.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAttackTargetEmpty() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.attackTarget("");
  }

  /**
   * Attack Target with null weaponName.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAttackTargetNull() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.attackTarget(null);
  }

  /**
   * Attack Target with invalid weapon which is not in world.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAttackTargetInvalidWeapon() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.attackTarget("ZYX");
  }

  /**
   * Attack Target with weapon player don't have.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAttackTargetPlayerNoWeapon() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.attackTarget("Revolver");
  }

  /**
   * Attack Target which is in different space.
   */
  @Test(expected = IllegalStateException.class)
  public void testAttackTargetPlayerDiffSpace() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.attackTarget("Revolver");
  }

  /**
   * Attack Target in presence of other player.
   */
  @Test
  public void testAttackTargetInPlayersPresencePoke() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    // player test is placed in the same space of ganesh
    gameWorld.addPlayer("test", 3, 2);

    gameWorld.lookAround();
    gameWorld.lookAround();
    System.out.println(gameWorld.lookAround());

    String att = gameWorld.attackTarget(POKE);
    assertEquals("Attack is unsuccessful!!!", att);
  }

  /**
   * Attack Target in presence of other player with weapon.
   */
  @Test
  public void testAttackTargetInPlayersPresence() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    // player test is placed in the same space of ganesh
    gameWorld.addPlayer("test", 3, 2);

    gameWorld.pickAnItem("Duck Decoy");
    gameWorld.lookAround();
    gameWorld.lookAround();
    String expected = "Attack failed as player can be seen by others";
    assertEquals(expected, gameWorld.attackTarget("Duck Decoy"));
  }

  @Test
  public void testLookAroundWithItemsNeighbor() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    // player test is placed in the same space of ganesh
    gameWorld.addPlayer("test", 3, 2);

    System.out.println(gameWorld.pickAnItem("Duck Decoy"));
    gameWorld.movePet(3);
    String lookAroundExpected = "Space Info \n" + "Name of the Space: Dining Hall\n"
        + "Id of the Space: 3\n" + "No neighbors for this space. \n"
        + "No weapons on this space. \n" + "Player: ganesh, test \n" + "Pet: Fortune the Cat \n"
        + "Details on neighbor spaces of player are: \n" + "----------------------------\n"
        + "Space Info \n" + "Name of the Space: Armory\n" + "Id of the Space: 0\n"
        + "No neighbors for this space. \n" + "Weapons: Revolver \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Billiard Room\n" + "Id of the Space: 1\n"
        + "No neighbors for this space. \n" + "Weapons: Billiard Cue \n" + "Player: gunesh \n"
        + "----------------------------\n" + "Space Info \n" + "Name of the Space: Drawing Room\n"
        + "Id of the Space: 4\n" + "No neighbors for this space. \n" + "Weapons: Letter Opener \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Kitchen\n" + "Id of the Space: 8\n"
        + "No neighbors for this space. \n" + "Weapons: Crepe Pan, Sharp Knife \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Parlor\n" + "Id of the Space: 14\n"
        + "No neighbors for this space. \n" + "No weapons on this space. \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Tennessee Room\n" + "Id of the Space: 17\n"
        + "No neighbors for this space. \n" + "No weapons on this space. \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Trophy Room\n" + "Id of the Space: 18\n"
        + "No neighbors for this space. \n" + "No weapons on this space. \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Wine Cellar\n" + "Id of the Space: 19\n"
        + "No neighbors for this space. \n" + "Weapons: Rat Poison, Piece of Rope \n"
        + "No players in this space. \n" + "----------------------------\n";
    assertEquals(lookAroundExpected, gameWorld.lookAround());
  }

  @Test
  public void testLookAroundWithNoItemsNeighbor() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    // player test is placed in the same space of ganesh
    gameWorld.addPlayer("test", 19, 2);

    gameWorld.pickAnItem("Duck Decoy");
    gameWorld.movePet(3);

    String lookAroundExpected = "Space Info \n" + "Name of the Space: Wine Cellar\n"
        + "Id of the Space: 19\n" + "No neighbors for this space. \n"
        + "Weapons: Rat Poison, Piece of Rope \n" + "Player: test \n"
        + "Details on neighbor spaces of player are: \n" + "----------------------------\n"
        + "Space Info \n" + "Name of the Space: Drawing Room\n" + "Id of the Space: 4\n"
        + "No neighbors for this space. \n" + "No weapons on this space. \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Kitchen\n" + "Id of the Space: 8\n"
        + "No neighbors for this space. \n" + "No weapons on this space. \n"
        + "No players in this space. \n" + "----------------------------\n";

    assertEquals(lookAroundExpected, gameWorld.lookAround());
  }

  /**
   * Attack Target in presence of other player with weapon with pet.
   */
  @Test
  public void testAttackTargetInPlayersPresenceWithPet() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    // player test is placed in the same space of ganesh
    gameWorld.addPlayer("test", 3, 2);

    System.out.println(gameWorld.pickAnItem("Duck Decoy"));
    gameWorld.movePet(3);
    String lookAroundExpected = "Space Info \n" + "Name of the Space: Dining Hall\n"
        + "Id of the Space: 3\n" + "No neighbors for this space. \n"
        + "No weapons on this space. \n" + "Player: ganesh, test \n" + "Pet: Fortune the Cat \n"
        + "Details on neighbor spaces of player are: \n" + "----------------------------\n"
        + "Space Info \n" + "Name of the Space: Armory\n" + "Id of the Space: 0\n"
        + "No neighbors for this space. \n" + "Weapons: Revolver \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Billiard Room\n" + "Id of the Space: 1\n"
        + "No neighbors for this space. \n" + "Weapons: Billiard Cue \n" + "Player: gunesh \n"
        + "----------------------------\n" + "Space Info \n" + "Name of the Space: Drawing Room\n"
        + "Id of the Space: 4\n" + "No neighbors for this space. \n" + "Weapons: Letter Opener \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Kitchen\n" + "Id of the Space: 8\n"
        + "No neighbors for this space. \n" + "Weapons: Crepe Pan, Sharp Knife \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Parlor\n" + "Id of the Space: 14\n"
        + "No neighbors for this space. \n" + "No weapons on this space. \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Tennessee Room\n" + "Id of the Space: 17\n"
        + "No neighbors for this space. \n" + "No weapons on this space. \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Trophy Room\n" + "Id of the Space: 18\n"
        + "No neighbors for this space. \n" + "No weapons on this space. \n"
        + "No players in this space. \n" + "----------------------------\n" + "Space Info \n"
        + "Name of the Space: Wine Cellar\n" + "Id of the Space: 19\n"
        + "No neighbors for this space. \n" + "Weapons: Rat Poison, Piece of Rope \n"
        + "No players in this space. \n" + "----------------------------\n";
    assertEquals(lookAroundExpected, gameWorld.lookAround());
    String expected = "Attack failed as player can be seen by others";
    assertEquals(expected, gameWorld.attackTarget("Duck Decoy"));
  }

  /**
   * Attack target with players presence in neighbor space with pet.
   */
  @Test
  public void testAttackTargetWithPetPresenceNeighbor() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 7, 2);

    gameWorld.pickAnItem("Duck Decoy");
    gameWorld.lookAround();
    gameWorld.movePet(1);
    String expected = "Attack failed as player can be seen by others";
    assertEquals(expected, gameWorld.attackTarget("Duck Decoy"));
  }

  /**
   * Attack target without pet with player and no player presence.
   */
  @Test
  public void attackTargetWihtoutPetAndNoPresence() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 5, 2);

    gameWorld.pickAnItem("Duck Decoy");
    gameWorld.movePet(1);
    gameWorld.pickAnItem("Monkey Hand");
    gameWorld.movePlayer(0);
    gameWorld.lookAround();
    String expected = "Attack is successful!!!";
    assertEquals(expected, gameWorld.attackTarget("Monkey Hand"));
  }

  /**
   * Attack target without pet with player and no player presence.
   */
  @Test
  public void attackTargetWihtPetAndNoPresence() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 5, 2);

    gameWorld.pickAnItem("Duck Decoy");
    gameWorld.movePet(1);
    gameWorld.pickAnItem("Monkey Hand");
    gameWorld.movePlayer(0);
    gameWorld.movePet(5);
    String expected = "Attack is successful!!!";
    assertEquals(expected, gameWorld.attackTarget("Monkey Hand"));
  }

  /**
   * Attack target without pet with player and no player presence.
   */
  @Test
  public void attackTargetWihtPetAndPlayerPresence() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 6, 2);
    gameWorld.addPlayer("test1", 7, 2);
    gameWorld.pickAnItem("Duck Decoy");
    gameWorld.movePet(1);
    gameWorld.pickAnItem("Trowel");
    gameWorld.pickAnItem("Loud Noise");
    gameWorld.movePlayer(0);
    gameWorld.lookAround();
    gameWorld.movePet(7);
    String expected = "Attack is successful!!!";
    assertEquals(expected, gameWorld.attackTarget("Loud Noise"));
  }

  /**
   * Attack target after game is over.
   */
  @Test(expected = IllegalStateException.class)
  public void attackTargetAfterGameOver() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.addPlayer("test", 6, 2);
    gameWorld.addPlayer("test1", 7, 2);
    gameWorld.pickAnItem("Duck Decoy");
    gameWorld.movePet(1);
    gameWorld.pickAnItem("Trowel");
    gameWorld.pickAnItem("Loud Noise");
    gameWorld.movePlayer(0);
    gameWorld.lookAround();
    gameWorld.movePet(7);
    gameWorld.attackTarget("Loud Noise");
    gameWorld.attackTarget("Duck Decoy");
  }

  /**
   * Attack target with already dropped weapon.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAttackTargetWithAlreadyDroppedWeapon() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 20);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("yudistira", 2, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    kurukshethra.pickAnItem("Delusion");
    kurukshethra.pickAnItem("Greed");
    kurukshethra.movePet(0);
    kurukshethra.attackTarget("Desire");
    String expectedHealth = "Target Info \n" + "Name: Duryodhana\n"
        + "Space Id: 1, Space name: Bheema\n" + "Health: 3\n";

    // health reduce validation
    assertEquals(expectedHealth, kurukshethra.getTargetInfo());
    kurukshethra.lookAround();
    kurukshethra.lookAround();
    kurukshethra.lookAround();
    kurukshethra.movePet(0);
    // reusing same weapon
    kurukshethra.attackTarget("Desire");
  }

  /**
   * Does attack target result in health reduction.
   */
  @Test
  public void testAttackTargetReducingHealth() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 20);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("yudistira", 2, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    kurukshethra.pickAnItem("Delusion");
    kurukshethra.pickAnItem("Greed");
    kurukshethra.movePet(0);
    kurukshethra.attackTarget("Desire");
    String expectedHealth = "Target Info \n" + "Name: Duryodhana\n"
        + "Space Id: 1, Space name: Bheema\n" + "Health: 3\n";

    // health reduce validation
    assertEquals(expectedHealth, kurukshethra.getTargetInfo());
  }

  /**
   * Does poke work with other weapons.
   */
  @Test
  public void testPokeWithOtherWeaponsInDocket() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 20);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("yudistira", 2, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    kurukshethra.pickAnItem("Delusion");
    kurukshethra.pickAnItem("Greed");
    kurukshethra.movePet(0);
    kurukshethra.attackTarget(POKE);
    String expectedHealth = "Target Info \n" + "Name: Duryodhana\n"
        + "Space Id: 0, Space name: Arjuna\n" + "Health: 5\n";

    // health reduce validation
    assertEquals(expectedHealth, kurukshethra.getTargetInfo());
  }

  /**
   * Move pet with invalid spaceIndex.
   */
  @Test(expected = IllegalArgumentException.class)
  public void movePetWithHighSpaceIndex() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.movePet(10000);
  }

  /**
   * Move pet with invalid spaceIndex.
   */
  @Test(expected = IllegalArgumentException.class)
  public void movePetWithNegativeIndex() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.movePet(-2);
  }

  /**
   * Move pet with non neighbor space.
   */
  @Test
  public void movePetToNonNeighbor() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.movePet(19);
    String expected = "Space Info \n" + "Name of the Space: Wine Cellar\n" + "Id of the Space: 19\n"
        + "Neighbors: Dining Hall, Drawing Room, Kitchen \n"
        + "Weapons: Rat Poison, Piece of Rope \n" + "No players in this space. \n"
        + "Pet: Fortune the Cat \n";
    assertEquals(expected, gameWorld.displaySpaceDescription(19));
  }

  @Test
  public void movePetToNeighbor() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.movePet(19);
    String expected = "Space Info \n" + "Name of the Space: Wine Cellar\n" + "Id of the Space: 19\n"
        + "Neighbors: Dining Hall, Drawing Room, Kitchen \n"
        + "Weapons: Rat Poison, Piece of Rope \n" + "No players in this space. \n"
        + "Pet: Fortune the Cat \n";
    assertEquals(expected, gameWorld.displaySpaceDescription(19));
    gameWorld.movePet(3);
    expected = "Space Info \n" + "Name of the Space: Dining Hall\n" + "Id of the Space: 3\n"
        + "Neighbors: Armory, Billiard Room, Drawing Room, Kitchen, Parlor, "
        + "Tennessee Room, Trophy Room, Wine Cellar \n" + "Weapons: Duck Decoy \n"
        + "Player: ganesh \n" + "Pet: Fortune the Cat \n";
    assertEquals(expected, gameWorld.displaySpaceDescription(3));
  }

  @Test
  public void movePetToSpaceWhereTargetIsPresent() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.movePet(19);
    gameWorld.movePet(2);
    String targetExpected = "Target Info \n" + "Name: Doctor Lucky\n"
        + "Space Id: 2, Space name: Carriage House\n" + "Health: 10\n";
    String targetInfo = gameWorld.getTargetInfo();
    assertEquals(targetExpected, targetInfo);
    String petSpaceDescription = "Space Info \n" + "Name of the Space: Carriage House\n"
        + "Id of the Space: 2\n" + "Neighbors: Winter Garden \n"
        + "Weapons: Chain Saw, Big Red Hammer \n" + "No players in this space. \n"
        + "Pet: Fortune the Cat \n";
    assertEquals(petSpaceDescription, gameWorld.displaySpaceDescription(2));
  }

  @Test
  public void movePetToSpaceWhereThereArePlayers() {
    GameWorld gameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    gameWorld.movePet(1);
    gameWorld.movePet(3);
    String petSpaceDescription = "Space Info \n" + "Name of the Space: Dining Hall\n"
        + "Id of the Space: 3\n" + "Neighbors: Armory, Billiard Room, Drawing Room, Kitchen, "
        + "Parlor, Tennessee Room, Trophy Room, Wine Cellar \n" + "Weapons: Duck Decoy \n"
        + "Player: ganesh \n" + "Pet: Fortune the Cat \n";
    assertEquals(petSpaceDescription, gameWorld.displaySpaceDescription(3));
  }

  /**
   * Move Pet after game is over.
   */
  @Test(expected = IllegalStateException.class)
  public void testMovePetAfterGameOver() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 20);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("yudistira", 2, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    kurukshethra.pickAnItem("Delusion");
    kurukshethra.pickAnItem("Greed");
    kurukshethra.movePet(0);
    kurukshethra.attackTarget("Desire");
    kurukshethra.movePet(2);
    kurukshethra.attackTarget("Delusion");
    kurukshethra.movePet(2);
  }

  /**
   * Test isGameOver return false when check in middle.
   */
  @Test
  public void testIsGameOverInMiddle() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 20);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("yudistira", 2, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    kurukshethra.pickAnItem("Delusion");
    kurukshethra.pickAnItem("Greed");
    kurukshethra.movePet(0);
    kurukshethra.attackTarget("Desire");
    assertFalse(kurukshethra.isGameOver());
  }

  @Test
  public void testIsGameOverReturnTrueMaxTurns() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 2);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("yudistira", 2, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    assertTrue(kurukshethra.isGameOver());
  }

  /**
   * Kill target with just one player.
   */
  @Test
  public void testIsGameoverTargetKilled() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("Bheema", 0, 3);
    kurukshethra.addPlayer("Yudhistira", 0, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    kurukshethra.movePet(0);
    kurukshethra.attackTarget("Desire");
    kurukshethra.movePlayer(2);
    kurukshethra.lookAround();
    kurukshethra.movePet(1);
    kurukshethra.attackTarget("Delusion");
    assertTrue(kurukshethra.isGameOver());
  }

  /**
   * Kill target with multiple player.
   */
  @Test
  public void testIsGameoverTargetKilledMultiplePlayers() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 20);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("yudistira", 2, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    kurukshethra.pickAnItem("Delusion");
    kurukshethra.pickAnItem("Greed");
    kurukshethra.movePet(0);
    kurukshethra.attackTarget("Desire");
    kurukshethra.movePet(2);
    kurukshethra.attackTarget("Delusion");
    assertTrue(kurukshethra.isGameOver());
  }

  @Test
  public void testGameWinnerTargetKilledMultiplePlayers() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 20);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("yudistira", 2, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    kurukshethra.pickAnItem("Delusion");
    kurukshethra.pickAnItem("Greed");
    kurukshethra.movePet(0);
    kurukshethra.attackTarget("Desire");
    kurukshethra.movePet(2);
    kurukshethra.attackTarget("Delusion");
    String expected = "Game winner is: yudistira, type: HUMAN";
    assertEquals(expected, kurukshethra.getWinner());
  }

  @Test
  public void testGetWinnerMaxTurns() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 2);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("yudistira", 2, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    String expected = "Game ended in tie, as we reached maxTurns";
    assertEquals(expected, kurukshethra.getWinner());
  }

  /**
   * Make computer player win the game.
   */
  @Test
  public void testGetWinnerComputerPlayer() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    RandomGenerator randomGenerator = new RandomGenerator(100, 2, 2, 3, 0);
    kurukshethra.addComputerPlayer(randomGenerator);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.pickAnItem("Anger");
    kurukshethra.computersTurn(randomGenerator);
    kurukshethra.pickAnItem("Greed");
    kurukshethra.movePet(0);
    kurukshethra.attackTarget("Desire");
    kurukshethra.movePet(2);
    kurukshethra.computersTurn(randomGenerator);
    String expected = "Game winner is: computer-100, type: COMP";
    assertEquals(expected, kurukshethra.getWinner());
  }

  /**
   * Computer players default action is attack when target is present in the same
   * space. If there are players presence.
   */
  @Test
  public void testDefaultActionAttackComp() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    RandomGenerator randomGenerator = new RandomGenerator(100, 2, 2, 3, 0);
    kurukshethra.addComputerPlayer(randomGenerator);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(2);
    String expected = "Computer player attacked target\n";
    assertEquals(expected, kurukshethra.computersTurn(randomGenerator));
  }

  /**
   * Test if comp player attack reducing health.
   */
  @Test
  public void testComputerPlayerAttack() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    RandomGenerator randomGenerator = new RandomGenerator(100, 2, 2, 3, 0);
    kurukshethra.addComputerPlayer(randomGenerator);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(2);
    String expected = "Target Info \n" + "Name: Duryodhana\n"
        + "Space Id: 2, Space name: Yudistira\n" + "Health: 5\n";
    assertEquals(expected, kurukshethra.getTargetInfo());
  }

  /**
   * Comp player should choose other options if there is player presence.
   */
  @Test
  public void testNonDefaultCmdsComp() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    RandomGenerator randomGenerator = new RandomGenerator(100, 2, 2, 3, 0);
    kurukshethra.addComputerPlayer(randomGenerator);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(4);
    String expected = "Computer player performed: PickUp Item\n";
    assertEquals(expected, kurukshethra.computersTurn(randomGenerator));
    kurukshethra.lookAround();
    kurukshethra.lookAround();
    kurukshethra.lookAround();
    kurukshethra.lookAround();
    expected = "Player Info: \n" + "Name: computer-100\n" + "Weapons: Delusion \n"
        + "MaxWeapons: 2\n" + "Space Info \n" + "Name of the Space: Yudistira\n"
        + "Id of the Space: 2\n" + "Neighbors: Arjuna, Bheema, Nakula \n"
        + "No weapons on this space. \n" + "Player: computer-100 \n" + "Pet: Ashva \n" + "\n";
    // validating if weapon picked or not.
    assertEquals(expected, kurukshethra.getTurnInfo());
  }

  /**
   * Moving pet through comp player.
   */
  @Test
  public void testMovePetComp() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    RandomGenerator randomGenerator = new RandomGenerator(100, 2, 2, 2, 0);
    kurukshethra.addComputerPlayer(randomGenerator);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(4);
    String expected = "Computer player performed: Move Pet\n";
    assertEquals(expected, kurukshethra.computersTurn(randomGenerator));
  }

  /**
   * Attack target with a player presence in the same space.
   */
  @Test
  public void testCompAttackWithPlayerPresence() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    RandomGenerator randomGenerator = new RandomGenerator(100, 2, 2, 2, 0);
    kurukshethra.addComputerPlayer(randomGenerator);
    kurukshethra.addPlayer("nakula", 2, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(4);
    String expected = "Computer player performed: Move Pet\n";
    assertEquals(expected, kurukshethra.computersTurn(randomGenerator));
  }

  /**
   * Turn info with No weapons.
   */
  @Test
  public void testTurnsInfoWithNoWeapons() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.initializeTurn();
    String expected = "Player Info: \n" + "Name: arjuna\n" + "Not carrying any weapons. \n"
        + "MaxWeapons: 3\n" + "Space Info \n" + "Name of the Space: Arjuna\n"
        + "Id of the Space: 0\n" + "Neighbors: Bheema, Yudistira, Nakula \n"
        + "Weapons: Desire, Arrogance \n" + "Player: arjuna \n" + "\n";
    assertEquals(expected, kurukshethra.getTurnInfo());
  }

  /**
   * Turn info with weapons.
   */
  @Test
  public void testTurnsInfoWithWeapons() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.lookAround();
    String expected = "Player Info: \n" + "Name: arjuna\n" + "Weapons: Desire \n"
        + "MaxWeapons: 3\n" + "Space Info \n" + "Name of the Space: Arjuna\n"
        + "Id of the Space: 0\n" + "Neighbors: Bheema, Yudistira, Nakula \n"
        + "Weapons: Arrogance \n" + "Player: arjuna \n" + "\n";
    assertEquals(expected, kurukshethra.getTurnInfo());
  }

  /**
   * Turn info with pet in space.
   */
  @Test
  public void testTurnsInfoWithPet() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    String expected = "Player Info: \n" + "Name: arjuna\n" + "Weapons: Desire \n"
        + "MaxWeapons: 3\n" + "Space Info \n" + "Name of the Space: Arjuna\n"
        + "Id of the Space: 0\n" + "Neighbors: Bheema, Yudistira, Nakula \n"
        + "Weapons: Arrogance \n" + "Player: arjuna \n" + "Pet: Ashva \n" + "\n";
    assertEquals(expected, kurukshethra.getTurnInfo());
  }

  /**
   * Validate.move player with x-coord and y-coord.
   */
  @Test
  public void testCheckMovexy() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(4);
    String expected = "Player moved successfully to space 0 - Arjuna";
    assertEquals(expected, kurukshethra.checkMoveDisplayPlayerInfo(480, 680));
  }

  /**
   * Validate.move player with invalid move commands.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckMovexyInvalidMove() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(4);
    String expected = "Player moved successfully to space 0 - Arjuna";
    kurukshethra.checkMoveDisplayPlayerInfo(480, 680);
    kurukshethra.checkMoveDisplayPlayerInfo(480, 680);
  }

  /**
   * Validate.update preReqs.
   */
  @Test
  public void testUpdatePreReqs() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(4);
    String expected = "Info about the Game World: \n"
            + "Name: Kurukshetra\n"
            + "Target name: Duryodhana\n"
            + "Target location: 2).Yudistira\n"
            + "Total Number of spaces: 5\n"
            + "Total Number of weapons: 6\n"
            + "Total Number of players: 4\n";
    assertEquals(expected, kurukshethra.getWorldInfo());
    kurukshethra.updatePreReqs(fileToTest, 10);
    expected = "Info about the Game World: \n"
            + "Name: Doctor Lucky's Mansion\n"
            + "Target name: Doctor Lucky\n"
            + "Target location: 0).Armory\n"
            + "Total Number of spaces: 21\n"
            + "Total Number of weapons: 20\n"
            + "Total Number of players: 0\n";
    assertEquals(expected, kurukshethra.getWorldInfo());
  }

  /**
   * Validate.update preReqs.
   */
  @Test
  public void testUpdatePreReqsEmptyFile() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(4);
    String expected = "Info about the Game World: \n"
            + "Name: Kurukshetra\n"
            + "Target name: Duryodhana\n"
            + "Target location: 2).Yudistira\n"
            + "Total Number of spaces: 5\n"
            + "Total Number of weapons: 6\n"
            + "Total Number of players: 4\n";
    assertEquals(expected, kurukshethra.getWorldInfo());
    kurukshethra.updatePreReqs("", 10);
    assertEquals(expected, kurukshethra.getWorldInfo());
  }

  /**
   * Validate.update preReqs with negative maxturns.
   */
  @Test
  public void testUpdatePreReqsNegMaxTurns() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(4);
    String expected = "Maxturns is negative!!!";
    assertEquals(expected, kurukshethra.updatePreReqs(expected, -10));
  }

  /**
   * Validate.move player with negative values.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckMovexyNegativeValue() {
    Readable in = new StringReader(shortMansion);
    GameWorld kurukshethra = new KillDoctorLuckyImpl(in, 10);
    kurukshethra.addPlayer("arjuna", 0, 3);
    kurukshethra.addPlayer("bheema", 1, 3);
    kurukshethra.addPlayer("nakula", 3, 3);
    kurukshethra.addPlayer("sahadeva", 4, 3);
    kurukshethra.initializeTurn();
    kurukshethra.pickAnItem("Desire");
    kurukshethra.movePet(4);
    kurukshethra.checkMoveDisplayPlayerInfo(-480, -680);
  }
}
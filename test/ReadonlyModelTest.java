import static org.junit.Assert.assertEquals;

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
import gameworld.service.ReadOnlyGameWorld;
import gameworld.util.Graph;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ReadOnlyModel class.
 * 
 * @author mail2
 *
 */
public class ReadonlyModelTest {
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
  protected ReadOnlyGameWorld killDoctorLucky(String fileName) {
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

  @Test
  public void testReadOnlyGameWorld() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");

    String targetExpected = "Name: Doctor Lucky\n" + "Space Id: 0, Space name: Armory\n"
        + "Health: 10\n";
    assertEquals(targetExpected, readOnlyGameWorld.getTargetInfo());
  }

  @Test
  public void testGetTurnInfo() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");

    String targetExpected = "Player Name: ganesh\n" + "Player Type: HUMAN\n"
        + "Not carrying any weapons. \n" + "MaxWeapons: 2\n" + "Located Space Name: Dining Hall\n"
        + "Located Space Index: 3\n" + "Neighbors: Armory, Billiard Room, Drawing Room,"
        + " Kitchen, Parlor, Tennessee Room, Trophy Room, Wine Cellar \n" + "Weapons: Duck Decoy \n"
        + "Players Present: ganesh \n" + "\n";
    assertEquals(targetExpected, readOnlyGameWorld.getTurnInfo());
  }

  @Test
  public void testGetTotalTurnsCount() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");

    int turnCount = 20;
    assertEquals(turnCount, readOnlyGameWorld.getTotalTurnsCount());
  }

  @Test
  public void testGetCurrentTurnsCount() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");

    int turnCount = 0;
    assertEquals(turnCount, readOnlyGameWorld.getCurrentTurnsCount());
  }

  @Test
  public void testGetSpaceIndexes() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
        17, 18, 19, 20);
    assertEquals(expected, readOnlyGameWorld.getSpaceIndexes());
  }

  @Test
  public void testGetWorldDesc() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    String expected = "Doctor Lucky's Mansion";
    assertEquals(expected, readOnlyGameWorld.getWorldDesc());
  }

  @Test
  public void testGetTurnName() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    String expected = "ganesh";
    assertEquals(expected, readOnlyGameWorld.getTurnName());
  }

  @Test
  public void testGetTurnsType() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    String expected = "HUMAN";
    assertEquals(expected, readOnlyGameWorld.getTurnsType());
  }

  @Test
  public void testIsGameOver() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    boolean expected = false;
    assertEquals(expected, readOnlyGameWorld.isGameOver());
  }

  @Test
  public void testGetWinner() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    String expected = "";
    assertEquals(expected, readOnlyGameWorld.getWinner());
  }

  @Test
  public void testGetWeaponsOfPlayerSpace() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    String expected = "Duck Decoy";
    Map<Integer, String> weaponsOfPlayerSpace = readOnlyGameWorld.getWeaponsOfPlayerSpace();
    assertEquals(expected, weaponsOfPlayerSpace.get(0));
  }

  @Test
  public void testGetWeaponsOfPlayers() {
    ReadOnlyGameWorld readOnlyGameWorld = killDoctorLucky("res/tests/fileToTest.txt");
    String expected = "poke";
    Map<Integer, String> weaponsOfPlayerSpace = readOnlyGameWorld.getWeaponsOfPlayers();
    assertEquals(expected, weaponsOfPlayerSpace.get(0));
  }
}

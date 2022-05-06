package model;

import gameworld.constants.PlayerType;
import gameworld.model.Space;
import gameworld.model.SpaceImpl;
import gameworld.service.GameWorld;
import gameworld.service.RandomGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock model for validating controller.
 */
public class MockWorldModel implements GameWorld {
  private StringBuilder log;
  private final String uniqueCode;

  /**
   * Constructor of mock model.
   * 
   * @param log        StringBuilder to update the info
   * @param uniqueCode UniqueCode based on which we decide controller reached mock
   *                   model.
   */
  public MockWorldModel(StringBuilder log, String uniqueCode) {
    this.uniqueCode = uniqueCode;
    this.log = log;
  }

  /**
   * Function for creating buffer image of world, which will be invoked by the
   * createGUI method.
   *
   * @return Acknowledgement of the function, which specifies the file location.
   */
  @Override
  public String createBufferedImage() {
    log.append("Created Buffer Image successfully!!!\n");
    return uniqueCode;
  }

  @Override
  public void moveTarget() {
    log.append("Reached moveTarget\n");
  }

  @Override
  public String displaySpaceDescription(int spaceIdx) {
    log.append("Space is Armory\n");
    return uniqueCode;
  }
  
  @Override
  public String displaySpaceDescription(String spaceIdx) {
    log.append(String.format("Getting information of space %s\n", spaceIdx));
    return uniqueCode;
  }

  @Override
  public Space getLocationOfTarget() {
    log.append("Reached location of target\n");
    return new SpaceImpl(1, "testSpace", new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
  }

  @Override
  public List<Space> getNeighboursOfSpace(int idx) {
    log.append("Reached get neighbours!!!\n");
    SpaceImpl testSpace1 = new SpaceImpl(1, "testSpace1",
        new ArrayList<>(Arrays.asList(5, 6, 7, 8)));
    return (List<Space>) testSpace1;
  }

  @Override
  public String movePlayer(int spaceIndex) {
    log.append(String.format("MovePlayer to space: %d\n", spaceIndex));
    return uniqueCode;
  }

  @Override
  public String addPlayer(String playerName, int spaceIndex, int maxItemsLimit) {
    log.append(String.format("Player: %s added successfully on space: %d\n", 
        playerName, spaceIndex));
    return uniqueCode;
  }

  @Override
  public String lookAround() {
    log.append("Reached LookAround method\n");
    return uniqueCode;
  }

  @Override
  public Map<Integer, String> getWeaponsOfPlayerSpace() {
    log.append("Reached getWeaponsOfPlayerSpace\n");
    return new HashMap<Integer, String>() {
      {
        put(1, "Revolver");
        put(2, "Gun");
      }
    };
  }

  /**
   * Get weapons of player space.
   *
   * @return Map of weapon and index
   */
  @Override
  public Map<Integer, String> getWeaponsOfPlayers() {
    log.append("Reached getWeaponsOfPlayers\n");
    return new HashMap<Integer, String>() {
      {
        put(1, "Revolver");
        put(2, "Gun");
      }
    };
  }

  @Override
  public String pickAnItem(String weaponName) {
    log.append("Reached pickAnItem method\n");
    return uniqueCode;
  }

  @Override
  public String getTurnInfo() {
    log.append("Reached playerInfo method\n");
    return uniqueCode;
  }

  @Override
  public String getWorldInfo() {
    log.append("World is full of spaces\n");
    return uniqueCode;
  }

  @Override
  public String addComputerPlayer(RandomGenerator randomGenerator) {
    log.append("Computer player is added\n");
    return uniqueCode;
  }

  @Override
  public Map<Integer, String> getSpaces() {
    log.append("Reached getSpaces!!!\n");
    return new HashMap<Integer, String>() {
      {
        put(1, "Armory");
        put(2, "Drawing Room");
      }
    };
  }

  @Override
  public String getTurnName() {
    log.append("Reached getTurnName!!!\n");
    return "ganesh";
  }

  @Override
  public PlayerType getTurnsType() {
    log.append("Reached getTurnsType!!!\n");
    return PlayerType.HUMAN;
  }

  @Override
  public String getTurnsLocation() {
    log.append("Reached getTurnsLocation!!!\n");
    return "Armory";
  }

  @Override
  public boolean isPlayerItemThresholdReached() {
    log.append("Reached isPlayerItemThresholdReached!!!\n");
    return true;
  }

  @Override
  public Map<Integer, String> getTurnNeighborSpaces() {
    log.append("Reached getTurnNeighborSpaces!!!\n");
    return new HashMap<Integer, String>() {
      {
        put(1, "Armory");
        put(2, "Drawing Room");
      }
    };
  }

  @Override
  public List<String> getPlayers() {
    log.append("Reached getPlayers!!!\n");
    return Arrays.asList("ganesh", "gunesh");
  }

  /**
   * Fucntionto check Maxturn threshold reached or not.
   *
   * @return True of false.
   */
  @Override
  public boolean isGameOver() {
    log.append("Reached isGameOver!!!\n");
    return false;
  }

  @Override
  public String getWinner() {
    log.append("Reached getWinner!!!\n");
    return uniqueCode;
  }

  @Override
  public String computersTurn(RandomGenerator randomGenerator) {
    log.append("Reached computers turn method\n");
    return uniqueCode;
  }

  @Override
  public void initializeTurn() {
    log.append("Initializing turn\n");
  }

  @Override
  public String movePet(int spaceIndex) {
    log.append("Reached MovePet method\n");
    return uniqueCode;
  }

  @Override
  public String attackTarget(String weaponName) {
    log.append("Reached AttackTarget method\n");
    return uniqueCode;
  }

  @Override
  public String getTargetInfo() {
    log.append("Reached getTargetInfo\n");
    return "Target info\n";
  }

  /*
   * @Override public String getPetInfo() { return "Reached pet info method!!!"; }
   */

  @Override
  public List<Integer> getSpaceIndexes() {
    log.append("Reached getSpaceIndexes\n");
    List<Integer> listOfIndexes = new ArrayList<Integer>();
    listOfIndexes.add(1);
    listOfIndexes.add(2);
    return listOfIndexes;
  }

  @Override
  public String updatePreReqs(String filePath, int maxTurns) {
    log.append(String.format("Updated Prerequisites with %s and %d\n", filePath, maxTurns));
    return uniqueCode;
  }

  @Override
  public String checkMoveDisplayPlayerInfo(int x, int y) {
    log.append("Player moved successfully\n");
    return uniqueCode;
  }

  @Override
  public int getTotalTurnsCount() {
    return 3;
  }

  @Override
  public int getCurrentTurnsCount() {
    return 1;
  }

  @Override
  public String getWorldDesc() {
    log.append("Reached getWorldDesc\n");
    return uniqueCode;
  }
}

package gameworld.service;

import gameworld.constants.PlayerType;
import gameworld.model.Pet;
import gameworld.model.PetImpl;
import gameworld.model.Player;
import gameworld.model.PlayerImpl;
import gameworld.model.Space;
import gameworld.model.SpaceImpl;
import gameworld.model.Target;
import gameworld.model.TargetImpl;
import gameworld.model.Weapon;
import gameworld.model.WeaponImpl;
import gameworld.util.DfsGraphImpl;
import gameworld.util.Graph;
import gameworld.util.Node;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;

/**
 * GameWorld implementation which is KillDoctorLucky.
 */
public class KillDoctorLuckyImpl implements GameWorld {
  // if we have more instances like this, we can create constant file for this.
  private static final String POKE = "poke";
  private static int cntTurns;
  private int maxX;
  private int maxY;
  private int maxSpaces;
  private int maxTurns;
  private Target target;
  private String worldDesc;
  private final List<Space> spacesList;
  private final List<Weapon> weaponsList;
  private Player turn;
  private final List<Player> listOfPlayers;
  private Pet pet;
  private final Map<Weapon, Boolean> evidenceMap;
  private Graph graph;
  private List<Integer> dfsPetMap;
  private int petLocIndex;
  private Player gameWinner;
  private final int scaleFactorRectX;
  private final int scaleFactorRectY;

  /**
   * Constructor for KillDoctorLucky Implementation.
   *
   * @param fileReader FileReader based on which world is created.
   * @param maxTurns   Storing maximum turns for the game.
   * @throws IllegalArgumentException On wrong file format.
   */
  public KillDoctorLuckyImpl(Readable fileReader, int maxTurns) {
    if (fileReader == null) {
      throw new IllegalArgumentException("FileReader shouldn't be null");
    }
    if (maxTurns < 1) {
      throw new IllegalArgumentException("MaxTurns needs to be more than 0");
    }
    this.spacesList = new ArrayList<>();
    this.weaponsList = new ArrayList<>();
    this.listOfPlayers = new ArrayList<>();
    this.maxSpaces = 0;
    this.maxTurns = maxTurns;
    cntTurns = 0;
    evidenceMap = new HashMap<>();
    graph = new DfsGraphImpl(false);
    dfsPetMap = new ArrayList<>();
    petLocIndex = 0;
    scaleFactorRectX = 24;
    scaleFactorRectY = 30;
    this.readFile(fileReader);

  }

  /**
   * Local method for mansion calculation.
   *
   * @param firstLine FirstLine of input file
   */
  private void fetchMansionDetails(String firstLine) throws IllegalArgumentException {
    String[] strings = firstLine.trim().split("\\s+");
    if (strings.length < 2) {
      throw new IllegalArgumentException(
              "Error while fetching mansion details" + ": please check input file.");
    }
    this.maxY = Integer.parseInt(strings[0]);
    this.maxX = Integer.parseInt(strings[1]);
    String[] nameArray = Arrays.copyOfRange(strings, 2, strings.length);
    this.worldDesc = String.join(" ", nameArray);
  }

  /**
   * Local method for target calculation.
   *
   * @param secondLine SecondLine of input file
   */
  private void fetchTargetDetails(String secondLine) throws IllegalFormatException {
    String[] strings = secondLine.trim().split("\\s+");
    if (strings.length < 1) {
      throw new IllegalArgumentException(
              "Error while creating target" + ": please check input file.");
    }
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

    this.pet = new PetImpl(petName, 0);
  }

  /**
   * Function for creating space from the textfile.
   *
   * @param spaceLine Line from which we have to extract space information
   * @param id        space index
   * @throws IllegalFormatException Exception is thrown if the text is out of
   *                                format.
   */
  private void createSpaces(String spaceLine, int id) throws IllegalFormatException {
    String[] strings = spaceLine.trim().split("\\s+");
    if (strings.length < 4) {
      throw new IllegalArgumentException(
              "Error while creating spaces" + ": please check input file.");
    }

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
   * Function to initialize target at zeroth space.
   */
  private void initializeTargetWithSpace() {
    basicValidationOfWorld();
    this.target.setSpace(this.spacesList.get(0));
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
    if (strings.length < 2) {
      throw new IllegalArgumentException(
              "Error while creating weapons list" + ": please check input file.");
    }
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
   * Function for creating neighbours.
   */
  private void createNeighbours() {
    for (Space space : this.spacesList) {
      List<Space> neighborSpaces = new ArrayList<>();

      for (int i = 0; i < spacesList.size(); i++) {
        if (space.getName().equals("Ashwathama") && i == 4) {
          System.out.println(space);
        }
        Node currSpaceNode = new Node(space.getIndex(), space.getName());
        if (space.getIndex() != i) {
          Space selectedSpace = this.spacesList.get(i);
          this.anyOverLapp(space, selectedSpace);
          if (isNeighBor(space, selectedSpace)) {
            neighborSpaces.add(selectedSpace);
            if (currSpaceNode.getIndex() < selectedSpace.getIndex()) {
              this.graph.addEdge(currSpaceNode,
                      new Node(selectedSpace.getIndex(), selectedSpace.getName()));
            }
          }
        }
      }
      space.updateNeighbours(neighborSpaces);
    }
  }

  private Space getSpacefromName(String name) throws IllegalArgumentException {
    for (Space space : this.spacesList) {
      if (space.getName().equals(name)) {
        return space;
      }
    }
    throw new IllegalArgumentException("There is no space with matching name!!!");
  }

  private void dfsPath() {
    // DFS initialization
    Space initSpace = spacesList.get(0);
    Node startNode = new Node(initSpace.getIndex(), initSpace.getName());
    this.graph.depthFirstSearchModified(startNode);
    dfsPetMap = this.graph.getDfsNodes();
  }

  /**
   * Function for parsing input file.
   *
   * @param fileReader FileReader from which we have to create world
   */
  private void readFile(Readable fileReader) {
    Scanner sc = new Scanner(fileReader);

    if (sc.hasNextLine()) {
      this.fetchMansionDetails(sc.nextLine());
    }

    if (sc.hasNextLine()) {
      this.fetchTargetDetails(sc.nextLine());
    }

    if (sc.hasNextLine()) {
      this.fetchPetDetails(sc.nextLine());
    }

    if (sc.hasNextInt()) {
      maxSpaces = Integer.parseInt(sc.nextLine());
    }

    for (int i = 0; i < maxSpaces; i++) {
      this.createSpaces(sc.nextLine(), i);
    }

    this.initializeTargetWithSpace();

    this.createNeighbours();

    this.dfsPath();

    int numOfWeapons = 0;

    if (sc.hasNextInt()) {
      numOfWeapons = Integer.parseInt(sc.nextLine());
    }

    for (int i = 0; i < numOfWeapons; i++) {
      this.createWeapons(sc.nextLine(), i);
    }

    if (sc.hasNextLine()) {
      throw new IllegalArgumentException(String.format("Illegal data entry"));
    }

    sc.close();
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
   * Function for validating if there are any overlaps.
   *
   * @param space1 Space1 for comparing.
   * @param space2 Space2 to validate overlapping.
   * @throws IllegalArgumentException Exception on overlapping detection.
   */
  private void anyOverLapp(Space space1, Space space2) throws IllegalArgumentException {
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

    if (y11 == y31 && y21 == y41 && x11 == x31 && x21 == x41) {
      throw new IllegalArgumentException("Multiple rooms with same co-ordinates");
    }

    boolean checkY = ((y11 >= y31 && y11 <= y41 && y21 >= y31 && y21 >= y41)
            || (y11 <= y31 && y11 <= y41 && y21 >= y31 && y21 <= y41)
            || (y11 <= y31 && y11 <= y41 && y21 >= y31 && y21 >= y41)
            || (y11 >= y31 && y11 <= y41 && y21 >= y31 && y21 <= y41));
    boolean checkX = ((x11 >= x31 && x11 <= x41 && x21 >= x31 && x21 >= x41)
            || (x11 <= x31 && x11 <= x41 && x21 >= x31 && x21 <= x41)
            || (x11 <= x31 && x11 <= x41 && x21 >= x31 && x21 >= x41)
            || (x11 >= x31 && x11 <= x41 && x21 >= x31 && x21 <= x41));

    if (checkX && checkY) {
      if ((y11 + 1) < y41 || (y21 + 1) < y31) {
        throw new IllegalArgumentException(
                String.format("Overlapping between at x %s, %s", 
                    space1.toString(), space2.toString()));
      } else if ((x11 + 1) < x41 || (x21 + 1) < x31) {
        throw new IllegalArgumentException(
                String.format("Overlapping between at y %s, %s", 
                    space1.toString(), space2.toString()));
      }
    }
  }

  /**
   * Function for creating image of the world.
   */
  @Override
  public String createBufferedImage() {

    List<String> imagePaths = getImageFiles();
    if (imagePaths == null) {
      throw new IllegalArgumentException("Images Path cannot be null!!!");
    }

    Random r = new Random();

    BufferedImage bufferedImage = new BufferedImage(this.maxX * 70, this.maxY * 50,
            BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = bufferedImage.createGraphics();

    try {
      URL targetFile = this.getClass().getResource("/img/target.png");
      BufferedImage targetImage = ImageIO.read(targetFile);
      Image scaledTargetImage = targetImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);

      URL petFile = this.getClass().getResource("/img/pet.png");
      BufferedImage petImage = ImageIO.read(petFile);
      Image scaledPetImage = petImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);

      g2d.setBackground(Color.white);
      g2d.fillRect(0, 0, this.maxX * 60, this.maxY * 40);


      for (Space space : this.spacesList) {
        int i = 0;
        List<Integer> coords = space.getCoOrdinates();
        int x1 = coords.get(0);
        int y1 = coords.get(1);
        int x2 = coords.get(2);
        int y2 = coords.get(3);
        int scaledY1 = y1 * scaleFactorRectY;
        int scaledX1 = x1 * scaleFactorRectX;
        int rectangleWidth = (y2 - y1 + 1) * scaleFactorRectY;
        int rectangleHeight = (x2 - x1 + 1) * scaleFactorRectX;

        final Rectangle rectangle = new Rectangle(rectangleWidth, rectangleHeight);
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(scaledY1, scaledX1, rectangleWidth, rectangleHeight);

        final String text = String.format("%s - %s", space.getIndex(), space.getName());
        int fontSize = 17;
        FontMetrics metrics;
        do {
          fontSize -= 2;
          g2d.setFont(g2d.getFont().deriveFont((float) fontSize));
          metrics = g2d.getFontMetrics(g2d.getFont());
        } while (metrics.stringWidth(text) > rectangle.width);

        final int x = scaledY1 + (rectangleWidth - metrics.stringWidth(text) + 3) / 2;
        final int y = scaledX1 + ((rectangleHeight - metrics.getHeight()) / 2)
                + metrics.getAscent();
        g2d.setFont(new Font("Serif", Font.BOLD, 15));
        g2d.drawString(text, x, y);
        // drawing target
        if (target.getSpace().getIndex().equals(space.getIndex())) {
          g2d.drawImage(scaledTargetImage, x, y, Color.white, null);
        }

        // drawing players, need to refine
        int numberOfPlayers = space.getPlayers().size();

        // choose player icon which will be added here.
        int imageGapFactor = 20;
        int prevX = 0;

        for (Player player : space.getPlayers()) {
          BufferedImage playerImage = ImageIO.read(new File(imagePaths.get(i)));
          Image scaledPlayerImage = playerImage.getScaledInstance(20, 18, Image.SCALE_SMOOTH);
          int coord1 = r.nextInt((x2 - x1) + (x1)) + imageGapFactor + prevX;
          prevX = coord1;
          g2d.drawImage(scaledPlayerImage, x + coord1, y, Color.white, null);
          imagePaths.remove(i);
          i++;
          if (player.getPlayerName().equals(getTurnName())
                  && space.getIndex().equals(this.pet.getSpaceIndex())) {
            int coord2 = r.nextInt((x2 - x1) + (x1)) + imageGapFactor + prevX;
            prevX = coord2;
            g2d.drawImage(scaledPetImage, x + coord2, y, Color.white, null);
          }
        }

      }
      final File file = new File("image.png");

      ImageIO.write(bufferedImage, "png", file);
      g2d.dispose();
      return "Image Created!!";
    } catch (IOException e) {
      g2d.dispose();
      return "Couldn't create a BufferedImage!";
    }
  }

  private List<String> getImageFiles() {
    List<String> images = new ArrayList<String>();

    URL filePaths = this.getClass().getResource("/img/players");
    try {
      String iconDirectory = Path.of(filePaths.toURI()).toString();
      System.out.println(iconDirectory);
      File[] files = new File(iconDirectory).listFiles();
      // If this pathname does not denote a directory, then listFiles() returns null.
      for (File file : files) {
        if (file.isFile()) {
          images.add(file.getAbsolutePath());
        }
      }

      return images;
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("File path is missing in resource root!!!");
    }
  }

  /**
   * Function to move target through the spaces.
   */
  @Override
  public void moveTarget() {
    Integer index = this.target.getSpace().getIndex();
    if (index >= maxSpaces - 1) {
      index = -1;
    }
    index += 1;
    this.target.setSpace(this.spacesList.get(index));
  }

  /**
   * Function for which we have to display space description based on spaceId.
   *
   * @param spaceIdx space index
   * @return space info
   */
  @Override
  public String displaySpaceDescription(int spaceIdx) {
    validateSpaceIndex(spaceIdx);
    Space space = this.spacesList.get(spaceIdx);
    return space.getSpaceInfo(true);
  }
  
  @Override
  public String displaySpaceDescription(String spaceIndex) {

    if (spaceIndex == null || "".equals(spaceIndex)) {
      return "Space index is null!!!";
    }

    try {
      int ipSpaceIdx = Integer.parseInt(spaceIndex);
      if (ipSpaceIdx < 0 || ipSpaceIdx > spacesList.size()) {
        return "Invalid space index!!!";
      }
      if (ipSpaceIdx > maxSpaces) {
        return "Trying to access invalid space!!!";
      }
      Space space = this.spacesList.get(ipSpaceIdx);
      return space.getSpaceInfo(true);
    } catch (NumberFormatException nfe) {
      return "Invalid Space Index Provided!";
    }
  }

  /**
   * Function for fetching location of the Target.
   *
   * @return Returns the Space where the Target is located.
   */
  @Override
  public Space getLocationOfTarget() {
    return this.target.getSpace();
  }

  /**
   * Function for getting neigbours for a specified space with index.
   *
   * @param index Index of the space for which we have get neighbors
   * @return Returns spaces list
   */
  @Override
  public List<Space> getNeighboursOfSpace(int index) {
    validateSpaceIndex(index);
    List<Space> copyNeighbors = new ArrayList<>();
    Space space = this.spacesList.get(index);
    for (Space neighbour : space.getNeighbours()) {
      copyNeighbors.add(
              new SpaceImpl(neighbour.getIndex(), neighbour.getName(), neighbour.getCoOrdinates()));
    }
    return copyNeighbors;
  }

  private void updatePet(boolean isMovePet) {
    // if the current command is not move pet then only move the pet
    if (!isMovePet) {
      if (petLocIndex == (dfsPetMap.size() - 1)) {
        petLocIndex = 0;
      } else {
        petLocIndex += 1;
      }
      updatePetPosition(dfsPetMap.get(petLocIndex));
    } else {
      // update pet location to the location selected by player.
      if (dfsPetMap.contains(pet.getSpaceIndex())) {
        petLocIndex = dfsPetMap.indexOf(pet.getSpaceIndex());
      }
    }
  }

  private void updateTurn(boolean isMovePet) {
    if (target.getHealth() == 0) {
      gameWinner = new PlayerImpl(turn.getPlayerName(), turn.getSpace(), turn.getItemLimit(),
              turn.getPlayerType());
    } else if (KillDoctorLuckyImpl.cntTurns == maxTurns) {
      gameWinner = null;
    }

    int playerIndex = listOfPlayers.indexOf(turn);
    if (playerIndex == (listOfPlayers.size() - 1)) {
      playerIndex = 0;
    } else {
      playerIndex += 1;
    }
    KillDoctorLuckyImpl.cntTurns += 1;
    turn = listOfPlayers.get(playerIndex);
    // on every turn perform target & pet movement
    if (spacesList.size() > 1) {
      moveTarget();
      updatePet(isMovePet);
    }

    System.out.println(this.target.getSpace());
  }

  private void restoreSpaceAndPlayer(Space currentSpace, Space newSpace) {
    currentSpace.addPlayer(turn);
    newSpace.removePlayer(turn);
  }

  private void validateMaxTurns() {
    if (isGameOver()) {
      throw new IllegalStateException("Game is already over!!!");
    }
  }

  private void validateSpaceIndex(int spaceIndex) {
    if (spaceIndex < 0 || spaceIndex > spacesList.size()) {
      throw new IllegalArgumentException("Invalid space index!!!");
    }
    if (spaceIndex > maxSpaces) {
      throw new IllegalArgumentException("Trying to access invalid space!!!");
    }
  }

  private void basicValidationOfWorld() {
    if (maxSpaces == 0) {
      throw new IllegalArgumentException("Spaces are missing!!!");
    }
  }

  private void validateNeighborsBeforeMove(int spaceIndex, List<Space> neighbors) {
    for (Space space : neighbors) {
      if (space.getIndex().equals(spaceIndex)) {
        return;
      }
    }
    throw new IllegalArgumentException("Illegal non neighbors movement");
  }

  /**
   * Local method to validate playerName is 1. Not empty or null 2. Is unique 3.
   * Not equal to targetName.
   *
   * @param playerName input playerName.
   */
  private void validatePlayer(String playerName) {
    if (playerName == null || "".equals(playerName)) {
      throw new IllegalArgumentException("Player name can't be ");
    }

    if (playerName.equals(target.getName())) {
      throw new IllegalArgumentException("Player name matches with TargetName!!!");
    }

    for (Player player : listOfPlayers) {
      if (player.getPlayerName().equals(playerName)) {
        throw new IllegalArgumentException("Player with this name already exists!!!");
      }
    }
  }

  private Weapon getWeaponWithValidation(String weaponName, List<Weapon> weapons) {
    for (Weapon weapon : weapons) {
      if (weapon.getName().equals(weaponName)) {
        return new WeaponImpl(weapon);
      }
    }
    throw new IllegalArgumentException("WeaponName doesn't match with any weapons!!!");
  }

  @Override
  public String movePlayer(int spaceIndex) {
    validateMaxTurns();
    validateSpaceIndex(spaceIndex);

    int currentSpaceIndex = turn.getSpace();

    Space currentSpace = spacesList.get(currentSpaceIndex);
    List<Space> neighbours = currentSpace.getNeighbours();
    validateNeighborsBeforeMove(spaceIndex, neighbours);
    Space newSpace = spacesList.get(spaceIndex);

    try {
      // 1. update the space index
      turn.movePlayer(spaceIndex);

      // 2. add player to the new space
      newSpace.addPlayer(turn);

      // After first 2 steps
      currentSpace.removePlayer(turn);
    } catch (IllegalArgumentException exception) {
      restoreSpaceAndPlayer(currentSpace, newSpace);
      throw new IllegalArgumentException(String.format("Error: %s", exception.getCause()));
    }

    // flip the turn only after everything is succeeded.
    updateTurn(false);
    return "Player Moved Successfully";
  }

  @Override
  public String addPlayer(String playerName, int spaceIndex, int maxItemsLimit) {

    if (listOfPlayers.size() == 10) {
      return "Cannot Add Player as the maximum limit of 10 has been reached!";
    }

    if (playerName == null || "".equals(playerName)) {
      throw new IllegalArgumentException("Player name can't be null!!!");
    }

    if (maxItemsLimit < 0) {
      throw new IllegalArgumentException("Item threshold can't be negative!!!");
    }

    validateSpaceIndex(spaceIndex);
    validatePlayer(playerName);

    Player player = new PlayerImpl(playerName, spaceIndex, maxItemsLimit, PlayerType.HUMAN);
    this.listOfPlayers.add(player);
    Space space = spacesList.get(spaceIndex);
    space.addPlayer(player);
    return "Player added successfully";
  }

  @Override
  public String lookAround() {
    validateMaxTurns();
    int spaceIdx = this.turn.getSpace();
    validateSpaceIndex(spaceIdx);
    Space space = spacesList.get(spaceIdx);
    List<Space> neighbours = space.getNeighbours();

    StringBuilder sb = new StringBuilder();
    sb.append(space.getSpaceInfo(false));
    if (neighbours.size() > 0) {
      sb.append("Details on neighbor spaces of player are: \n");
      sb.append("----------------------------\n");
      for (Space neighbor : neighbours) {
        if (!neighbor.isPetPresent()) {
          sb.append(neighbor.getSpaceInfo(false));
          sb.append("----------------------------\n");
        }
      }
    } else {
      sb.append("No neighbors for this space. \n");
      sb.append("----------------------------\n");
    }
    updateTurn(false);
    return sb.toString();
  }

  private Map<Integer, String> createWeaponsMap(List<Weapon> weapons) {
    Map<Integer, String> weaponsMap = new HashMap<>();
    if (weapons.size() > 0) {
      for (int i = 0; i < weapons.size(); i++) {
        weaponsMap.put(i, weapons.get(i).getName());
      }
    }
    return weaponsMap;
  }

  @Override
  public Map<Integer, String> getWeaponsOfPlayerSpace() {
    int spaceIdx = this.turn.getSpace();
    validateSpaceIndex(spaceIdx);
    Space space = spacesList.get(spaceIdx);
    List<Weapon> weapons = space.getWeapons();
    return createWeaponsMap(weapons);
  }

  /**
   * Get weapons of player space.
   *
   * @return Map of weapon and index
   */
  @Override
  public Map<Integer, String> getWeaponsOfPlayers() {
    List<Weapon> weapons = turn.getWeapons();
    Map<Integer, String> weaponsMap = createWeaponsMap(weapons);
    weaponsMap.put(weaponsMap.size(), POKE);
    return weaponsMap;
  }

  @Override
  public String pickAnItem(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Null weapon name is not acceptable!!!");
    }

    validateMaxTurns();

    // rest of the validation happens inside getWeapon
    Weapon weapon = getWeaponWithValidation(weaponName, weaponsList);
    int spaceIdx = turn.getSpace();
    Space space = spacesList.get(spaceIdx);
    getWeaponWithValidation(weaponName, space.getWeapons());
    turn.addWeaponsInfo(weapon);
    space.deleteWeapons(weapon);
    updateTurn(false);
    return "Weapon is picked successfully!!!!";
  }

  @Override
  public String getWorldInfo() {
    basicValidationOfWorld();
    StringBuilder sb = new StringBuilder("Info about the Game World: \n");
    sb.append(String.format("Name: %s\n", worldDesc));
    sb.append(String.format("Target name: %s\n", target.getName()));
    sb.append(String.format("Target location: %d).%s\n", target.getSpace().getIndex(),
            target.getSpace().getName()));
    sb.append(String.format("Total Number of spaces: %d\n", maxSpaces));
    sb.append(String.format("Total Number of weapons: %d\n", weaponsList.size()));
    sb.append(String.format("Total Number of players: %d\n", listOfPlayers.size()));
    return sb.toString();
  }

  @Override
  public String addComputerPlayer(RandomGenerator randomGenerator) {
    String name = String.format("computer-%d", randomGenerator.getRandomInteger(1000));
    int spaceIdx = randomGenerator.getRandomInteger(maxSpaces - 1);
    // hardcoding maxItems upper bound for computer player.
    int maxItems = randomGenerator.getRandomInteger(5);
    Player player = new PlayerImpl(name, spaceIdx, maxItems, PlayerType.COMP);
    this.listOfPlayers.add(player);
    Space space = spacesList.get(spaceIdx);
    space.addPlayer(player);
    return "Computer player is added successfully";
  }

  @Override
  public Map<Integer, String> getSpaces() {
    Map<Integer, String> spaces = new HashMap<>();
    for (Space space : spacesList) {
      spaces.put(space.getIndex(), space.getName());
    }
    return spaces;
  }

  @Override
  public String getTurnName() {
    return turn.getPlayerName();
  }

  @Override
  public PlayerType getTurnsType() {
    return turn.getPlayerType();
  }

  @Override
  public String getTurnsLocation() {
    int spaceIdx = turn.getSpace();
    validateSpaceIndex(spaceIdx);

    return spacesList.get(spaceIdx).getName();
  }

  @Override
  public boolean isPlayerItemThresholdReached() {
    return turn.itemLimitReached();
  }

  @Override
  public Map<Integer, String> getTurnNeighborSpaces() {
    int spaceIdx = turn.getSpace();
    validateSpaceIndex(spaceIdx);
    Space space = spacesList.get(spaceIdx);
    Map<Integer, String> neighbors = new HashMap<>();
    for (Space space1 : space.getNeighbours()) {
      neighbors.put(space1.getIndex(), space1.getName());
    }
    return neighbors;
  }

  @Override
  public List<String> getPlayers() {
    List<String> players = new ArrayList<>();
    for (Player player : listOfPlayers) {
      players.add(player.getPlayerName());
    }
    return players;
  }


  @Override
  public String getWorldDesc() {
    return this.worldDesc;
  }

  /**
   * Fucntionto check Maxturn threshold reached or not.
   *
   * @return True of false.
   */
  @Override
  public boolean isGameOver() {
    if (target.getHealth() == 0) {
      gameWinner = new PlayerImpl(turn.getPlayerName(), turn.getSpace(), turn.getItemLimit(),
              turn.getPlayerType());
      return true;
    } else if (KillDoctorLuckyImpl.cntTurns == maxTurns) {
      gameWinner = null;
      return true;
    }

    return false;
  }

  @Override
  public String getWinner() {
    if (gameWinner == null && KillDoctorLuckyImpl.cntTurns == maxTurns) {
      return "Game ended in tie, as we reached maxTurns\n";
    } else if (gameWinner != null) {
      return String.format("Game winner is: %s, type: %s\n", gameWinner.getPlayerName(),
              gameWinner.getPlayerType());
    } else {
      return "";
    }
  }

  @Override
  public String computersTurn(RandomGenerator randomGenerator) {
    String operation = "";
    int spaceIdx = turn.getSpace();

    // attack target should be first preference
    if (target.getSpace().getIndex() == spaceIdx && !failedAttack(spacesList.get(spaceIdx))) {
      List<Weapon> weapons = turn.getWeapons();
      if (weapons.size() > 0) {
        attackTarget(weapons.get(0).getName());
      } else {
        attackTarget(POKE);
      }
      return "Computer player attacked target\n";
    }

    int randomInteger = randomGenerator.getRandomInteger(3);
    switch (randomInteger) {
      case 1: {
        // move player
        List<Space> neighbours = spacesList.get(spaceIdx).getNeighbours();
        this.movePlayer(neighbours.get(0).getIndex());
      }
        operation = "Move Player";
        break;
      case 2: {
        // move pet
        int randomMoveIdx = randomGenerator.getRandomInteger(spacesList.size() - 1);
        this.movePet(randomMoveIdx);
      }
        operation = "Move Pet";
        break;
      case 5: {
        // pickup item
        Map<Integer, String> weaponsOfPlayerSpace = this.getWeaponsOfPlayerSpace();
        // int weaponIdx = randomGenerator.getRandomInteger(weaponsOfPlayerSpace.size()
        // - 1);
        String weaponName = weaponsOfPlayerSpace.get(0);
        if (weaponName == null && "".equals(weaponName)) {
          break;
        }
        this.pickAnItem(weaponName);
      }
        operation = "PickUp Item";
        break;
      case 3:
        // pickup item
        this.lookAround();
        operation = "Look Around";
        break;
      default: {
        operation = "Invalid command";
      }
    }
    return String.format("Computer player performed: %s\n", operation);
  }
  private String moveComputerControlledPlayer(RandomGenerator randomGenerator, int spaceIdx) {
    List<Space> neighbours = spacesList.get(spaceIdx).getNeighbours();
    int randomMoveIdx = randomGenerator.getRandomInteger(neighbours.size() - 1);
    this.movePlayer(neighbours.get(randomMoveIdx).getIndex());
    return "Move Player";
  }

  private String movePetOfComputerControlledPlayer(RandomGenerator randomGenerator) {
    int randomMoveIdx = randomGenerator.getRandomInteger(spacesList.size() - 1);
    this.movePet(randomMoveIdx);
    return "Move Pet";
  }

  private String lookAroundOfComputerControlledPlayer() {
    this.lookAround();
    return "Look Around";
  }

  private String pickItemOfComputerControlledPlayer(RandomGenerator randomGenerator) {
    Map<Integer, String> weaponsOfPlayerSpace = this.getWeaponsOfPlayerSpace();
    // pick the strongest element in the deck.
    String weaponName = weaponsOfPlayerSpace.get(0);
    this.pickAnItem(weaponName);
    return "PickUp Item";
  }

  @Override
  public void initializeTurn() {
    if (listOfPlayers.size() == 0) {
      throw new IllegalArgumentException("No players missing!!!");
    }

    turn = this.listOfPlayers.get(0);
  }

  private String updatePetPosition(int spaceIndex) {
    Space currentPetSpace = spacesList.get(this.pet.getSpaceIndex());
    currentPetSpace.removePet();
    Space newPetSpace = spacesList.get(spaceIndex);
    newPetSpace.addPet(pet.getName());
    this.pet.updateSpace(spaceIndex);
    return String.format("Pet is moved successfully from %d - %s to %d - %s\n",
            currentPetSpace.getIndex(), currentPetSpace.getName(), newPetSpace.getIndex(),
            newPetSpace.getName());
  }

  @Override
  public String movePet(int spaceIndex) {
    validateSpaceIndex(spaceIndex);
    validateMaxTurns();
    updateTurn(true);
    return updatePetPosition(spaceIndex);
  }

  private boolean canBeSeenByOthers(Space currentSpace) {
    boolean canBeSeen = false;
    List<Space> neighbours = currentSpace.getNeighbours();
    for (Space space : neighbours) {
      if (space.isPlayersPresent()) {
        canBeSeen = true;
        break;
      }
    }
    return canBeSeen;
  }

  private boolean failedAttack(Space currentSpace) {
    List<Player> players = currentSpace.getPlayers();
    if (players.size() > 1) {
      return true;
    } else if (currentSpace.isPetPresent()) {
      return false;
    } else {
      return canBeSeenByOthers(currentSpace);
    }
  }

  @Override
  public String attackTarget(String weaponName) {
    if (weaponName == null || "".equals(weaponName)) {
      throw new IllegalArgumentException("Null weapon name is not acceptable!!!");
    }

    validateMaxTurns();

    int turnSpaceIdx = turn.getSpace();
    Space currentSpace = spacesList.get(turnSpaceIdx);

    if (target.getSpace().getIndex() != turn.getSpace()) {
      throw new IllegalArgumentException("Player and Target are in different spaces");
    }

    if (weaponName.equals(POKE) && !failedAttack(currentSpace)) {
      target.reduceHealth(1);
      updateTurn(false);
      return "Attack is successful!!!";
    } else if (weaponName.equals(POKE)) {
      return "Attack failed as player can be seen by others";
    }

    // rest of the validation happens inside getWeapon
    Weapon weapon = getWeaponWithValidation(weaponName, weaponsList);

    List<Weapon> turnWeapons = turn.getWeapons();

    // make sure weapon is available in the turns weapons list
    getWeaponWithValidation(weaponName, turnWeapons);

    if (failedAttack(currentSpace)) {
      // update evidence map to keep track of weapons which are used.
      evidenceMap.put(weapon, false);
      // remove weapon from player as its used.
      turn.removeWeapon(weapon);
      return "Attack failed as player can be seen by others";
    }

    // reduce weapons health
    target.reduceHealth(weapon.getDamageAmt());

    // update evidence map to keep track of weapons which are used.
    evidenceMap.put(weapon, true);

    // remove weapon from player as its used.
    turn.removeWeapon(weapon);

    // update turn
    updateTurn(false);

    return "Attack is successful!!!";
  }

  @Override
  public String getTargetInfo() {
    return target.toString();
  }

  @Override
  public String getTurnInfo() {
    if (listOfPlayers.size() == 0) {
      throw new IllegalArgumentException("No players missing!!!");
    }

    int spaceIdx = turn.getSpace();
    validateSpaceIndex(spaceIdx);
    Space currentSpace = spacesList.get(spaceIdx);
    return String.format("%s\n%s\n", turn.toString(), currentSpace.getSpaceInfo(true));
  }

  @Override
  public List<Integer> getSpaceIndexes() {
    List<Integer> spaceIndexList = new ArrayList<>();
    for (Space space : spacesList) {
      spaceIndexList.add(space.getIndex());
    }
    return spaceIndexList;
  }

  @Override
  public String updatePreReqs(String filePath, int maxTurns) {
    if (maxTurns < 1) {
      return "Maxturns is negative!!!";
    }

    if (filePath == null || "".equals(filePath)) {
      return updateMaxTurns(maxTurns);
    } else {
      try {
        Readable fileReader = new InputStreamReader(new FileInputStream(filePath));
        this.spacesList.clear();
        this.weaponsList.clear();
        this.listOfPlayers.clear();
        this.maxSpaces = 0;
        this.maxTurns = maxTurns;
        cntTurns = 0;
        evidenceMap.clear();
        petLocIndex = 0;
        this.maxTurns = maxTurns;
        this.readFile(fileReader);
        this.createBufferedImage();
      } catch (FileNotFoundException e) {
        return "Provided File Couldn't be processed, So using the default Map!!!";
      }
    }
    return "Game prerequisites updated successfully!!!";
  }

  private String updateMaxTurns(int maxTurns) {
    if (maxTurns < 1) {
      return "Invalid Max Turns Count Provided, So using the default Count!!!";
    }
    this.maxTurns = maxTurns;
    cntTurns = 0;
    return "Max Turns Count updated Successfully!!!";

  }

  private boolean findPoint(List<Integer> coOrdinates) {
    return coOrdinates.get(4) > coOrdinates.get(1) && coOrdinates.get(4) < coOrdinates.get(3)
            && coOrdinates.get(5) > coOrdinates.get(0) && coOrdinates.get(5) < coOrdinates.get(2);
  }

  private List<Integer> scaleUpCoOrd(List<Integer> coOrdinates) {
    List<Integer> newCoOrdinates = new ArrayList<>();
    Integer y1 = coOrdinates.get(0);
    newCoOrdinates.add((y1 * scaleFactorRectY));
    Integer x1 = coOrdinates.get(1);
    newCoOrdinates.add((x1 * scaleFactorRectX));

    Integer y2 = coOrdinates.get(2);
    // adding 1 to maintain the inclusiveness
    newCoOrdinates.add(((y2 + 1) * scaleFactorRectY));

    Integer x2 = coOrdinates.get(3);
    // adding 1 to maintain the inclusiveness
    newCoOrdinates.add(((x2 + 1) * scaleFactorRectX));
    return newCoOrdinates;
  }

  @Override
  public String checkMoveDisplayPlayerInfo(int x, int y) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid x or y");
    }

    int playerSpace = this.turn.getSpace();

    List<Integer> playerSpaceCoOr = spacesList.get(playerSpace).getCoOrdinates();
    List<Integer> scaledCurrCoOr = scaleUpCoOrd(playerSpaceCoOr);
    scaledCurrCoOr.add(x);
    scaledCurrCoOr.add(y);

    if (findPoint(scaledCurrCoOr)) {
      return getTurnInfo();
    } else {
      List<Space> neighboursOfSpace = getNeighboursOfSpace(playerSpace);
      for (Space space : neighboursOfSpace) {
        List<Integer> spaceCoOr = space.getCoOrdinates();
        List<Integer> scaledNeighbourCoOr = scaleUpCoOrd(spaceCoOr);
        scaledNeighbourCoOr.add(x);
        scaledNeighbourCoOr.add(y);
        if (findPoint(scaledNeighbourCoOr)) {
          this.movePlayer(space.getIndex());
          return String.format("Player moved successfully to space %d - %s", space.getIndex(),
                  space.getName());
        }
      }
    }
    throw new IllegalArgumentException("Invalid neighboring space co-ordinates!!!");
  }

  @Override
  public int getTotalTurnsCount() {
    return this.maxTurns;
  }

  @Override
  public int getCurrentTurnsCount() {
    return cntTurns;
  }

}

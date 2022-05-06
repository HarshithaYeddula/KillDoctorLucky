package gameworld.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Space class implementation to handle space related operations.
 */
public class SpaceImpl implements Space {
  private final String name;
  private final int id;
  private List<Weapon> weapons;
  private final List<Integer> coordinates;
  private List<Space> neighborSpaces;
  private List<Player> players;
  private String petName;
  private boolean isPetPresent;

  /**
   * Constructor which creates Space object including weapons.
   *
   * @param id Index of the space
   * @param name Name of the space
   * @param weapon Weapons belong to the space
   * @param coordinates Coordinates of the space
   */
  public SpaceImpl(int id, String name, Weapon weapon, List<Integer> coordinates) {
    if (id < 0) {
      throw new IllegalArgumentException("Invalid space index");
    }

    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Name can't be empty");
    }

    if (weapon == null) {
      throw new IllegalArgumentException("Received illegal weapon object!!");
    }

    this.validateCoOrdinates(coordinates);

    this.id = id;
    this.name = name;
    this.weapons = new ArrayList<>(Arrays.asList(weapon));
    this.coordinates = coordinates;
    this.neighborSpaces = new ArrayList<>();
    this.players = new ArrayList<>();
    this.isPetPresent = false;
  }

  /**
   * Constructor which creates Space object without weapons.
   *
   * @param id Index of the space
   * @param name Name of the space
   * @param coordinates Coordinates of the space
   */
  public SpaceImpl(int id, String name, List<Integer> coordinates) {
    if (id < 0) {
      throw new IllegalArgumentException("Invalid space index");
    }

    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Name can't be empty");
    }

    this.validateCoOrdinates(coordinates);

    this.id = id;
    this.name = name;
    this.weapons = new ArrayList<>();
    this.coordinates = coordinates;
    this.neighborSpaces = new ArrayList<>();
    this.players = new ArrayList<>();
  }

  /**
   * Copy constructor for deep copy.
   * @param space Space Object that needed deep copy
   */
  public SpaceImpl(SpaceImpl space) {
    if (space == null) {
      throw new IllegalArgumentException("Space object is null");
    }

    this.validateCoOrdinates(space.getCoOrdinates());

    this.id = space.id;
    this.name = space.name;
    this.weapons = space.weapons;
    this.coordinates = space.coordinates;
    this.neighborSpaces = space.neighborSpaces;
    this.players = space.players;
  }


  /**
   * Function for coOrdinates validation and throws error if there's any issue.
   * @param coOrdinates Validating this set of coordinates which
   *                    is in [leftTopCorner, RightBottomCorner] format
   * @throws IllegalArgumentException Exception is thrown if x-coord and y-coord are not proper.
   */
  private void validateCoOrdinates(List<Integer> coOrdinates) throws IllegalArgumentException {
    if (coOrdinates == null) {
      throw new IllegalArgumentException("Invalid cooridinates");
    }

    if (coOrdinates.size() != 4) {
      throw new IllegalArgumentException("Invalid 2D cooridinates");
    }

    int y11 = coOrdinates.get(0);
    int x11 = coOrdinates.get(1);
    int y21 = coOrdinates.get(2);
    int x21 = coOrdinates.get(3);

    if (y11 > y21 || x11 > x21) {
      throw new IllegalArgumentException("Left Corner is not on the "
              + "lower end compare to Right Corner");
    }

    if (y11 == y21 || x11 == x21) {
      throw new IllegalArgumentException("Corners should not have same "
              + "co-ordiantes for box to be created!!!");
    }
  }

  @Override
  public void updateNeighbours(List<Space> neighbours) {
    if (neighbours == null) {
      throw new IllegalArgumentException("Invalid neighbours array");
    }

    // no neighbors
    if (neighbours.size() == 0) {
      return;
    }

    List<Space> copyNeighbors = new ArrayList<>();
    for (Space neighbour : this.neighborSpaces) {
      copyNeighbors.add(new SpaceImpl(neighbour.getIndex(), neighbour.getName(),
              neighbour.getCoOrdinates()));
    }

    this.neighborSpaces = neighbours;
  }

  @Override
  public List<Space> getNeighbours() {
    List<Space> copyNeighbors = new ArrayList<>();
    for (Space neighbour : this.neighborSpaces) {
      copyNeighbors.add(new SpaceImpl(neighbour.getIndex(), neighbour.getName(),
              neighbour.getCoOrdinates()));
    }

    return this.neighborSpaces.stream().collect(toList());
  }

  @Override
  public void addWeapons(Weapon weapon) {
    if (weapon == null) {
      throw new IllegalArgumentException("Weapon object is null");
    }
    this.weapons.add(weapon);
  }

  @Override
  public void deleteWeapons(Weapon weapon) {
    if (weapon == null) {
      throw new IllegalArgumentException("Weapon object is null");
    }
    for (int i = 0; i < this.weapons.size(); i++) {
      if (this.weapons.get(i).equals(weapon)) {
        this.weapons.remove(i);
      }
    }
  }

  @Override
  public List<Weapon> getWeapons() {
    List<Weapon> newWeapons = new ArrayList<>();
    for (Weapon weapon : this.weapons) {
      newWeapons.add(new WeaponImpl(weapon.getName(), weapon.getSpaceItBelongsTo(),
              weapon.getDamageAmt()));
    }
    return newWeapons;
  }

  @Override
  public List<Integer> getCoOrdinates() {
    List<Integer> coOrdinatesCopy = new ArrayList<>();
    coOrdinatesCopy = this.coordinates.stream().collect(toList());
    return coOrdinatesCopy;
  }

  @Override
  public Integer getIndex() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean isPlayersPresent() {
    return (this.players.size() > 0);
  }

  @Override
  public List<Player> getPlayers() {
    List<Player> palyersCopy = new ArrayList<>();
    for (Player player : this.players) {
      palyersCopy.add(new PlayerImpl(player.getPlayerName(), player.getSpace(),
              player.getItemLimit(), player.getPlayerType()));
    }
    return palyersCopy;
  }

  @Override
  public void addPlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cna't be null");
    }
    this.players.add(player);
  }

  @Override
  public void removePlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player can't be null");
    }

    for (int i = 0; i < this.players.size(); i++) {
      Player player1 = this.players.get(i);

      if (player1.equals(player)) {
        players.remove(i);
        return;
      }
    }
  }

  @Override
  public String getSpaceInfo(boolean noNeighbors) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Located Space Name: %s\n", this.name));
    sb.append(String.format("Located Space Index: %d\n", this.id));

    if (this.neighborSpaces.size() > 0 && noNeighbors) {
      sb.append(String.format("Neighbors: %s \n", this.neighborSpaces.stream()
              .map(Space::getName)
              .collect(Collectors.joining(", "))));
    } else {
      sb.append("No neighbors for this space. \n");
    }

    if (this.weapons.size() > 0) {
      sb.append(String.format("Weapons: %s \n", this.weapons.stream()
              .map(Weapon::getName)
              .collect(Collectors.joining(", "))));
    } else {
      sb.append("No weapons on this space. \n");
    }

    if (this.players.size() > 0) {
      sb.append(String.format("Players Present: %s \n", this.players.stream()
              .map(Player::getPlayerName)
              .collect(Collectors.joining(", "))));
    } else {
      sb.append("No players in this space. \n");
    }

    if (isPetPresent) {
      sb.append(String.format("Pet: %s \n", this.petName));
    }

    return sb.toString();
  }

  @Override
  public void addPet(String petName) {
    if (petName == null || "".equals(petName)) {
      throw new IllegalArgumentException("Pet name is empty or null!!!");
    }
    this.petName = petName;
    this.isPetPresent = true;
  }

  @Override
  public void removePet() {
    this.isPetPresent = false;
  }

  @Override
  public boolean isPetPresent() {
    return this.isPetPresent;
  }

  @Override
  public String toString() {
    return String.format("Space details name: %s, id: %d", this.name, this.id);
  }

  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { // backward compatibility with default equals
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Space)) {
      return false;
    }

    Space that = (Space) o;

    // comparing gears and speed of the car to decide equality.
    return this.getName().equals(that.getName())
            && this.getCoOrdinates().equals(that.getCoOrdinates());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getName(), this.getCoOrdinates());
  }
}

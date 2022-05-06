package gameworld.model;

import gameworld.constants.PlayerType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class which handles all player related operations.
 */
public class PlayerImpl implements Player {
  private final String playerName;
  private int spaceIndex;
  private final int maxItems;
  private List<Weapon> weapons;
  private final PlayerType playerType;

  /**
   * Constructor for player Implementation.
   * @param playerName nameOfthePlayer
   * @param spaceIndex space index
   * @param maxItems max items threshold
   * @param playerType playerType either human or computer
   */
  public PlayerImpl(String playerName, int spaceIndex, int maxItems, PlayerType playerType) {
    if (playerName == null || "".equals(playerName)) {
      throw new IllegalArgumentException("PlayerName can't be empty");
    }

    if (spaceIndex < 0) {
      throw new IllegalArgumentException("Space index can't be negative");
    }

    if (maxItems < 0) {
      throw new IllegalArgumentException("MaxItems limit can't be negative");
    }

    if (playerType == null) {
      throw new IllegalArgumentException("PlayerType can't be null");
    }

    this.playerName = playerName;
    this.spaceIndex = spaceIndex;
    this.maxItems = maxItems;
    this.playerType = playerType;
    this.weapons = new ArrayList<>();
  }

  @Override
  public String getPlayerName() {
    return this.playerName;
  }

  @Override
  public void addWeaponsInfo(Weapon weapon) throws IllegalArgumentException {
    if (weapon == null) {
      throw new IllegalArgumentException("Null weapon object is sent!!!");
    }

    if (this.weapons.size() >= this.maxItems) {
      throw new IllegalArgumentException("MaxItems threshold is reached for this player"
              + ", drop an item to pick new items!!!");
    }

    this.weapons.add(weapon);
  }

  @Override
  public List<Weapon> getWeapons() {
    List<Weapon> weaponsCopy = new ArrayList<>();
    for (Weapon weapon : this.weapons) {
      weaponsCopy.add(new WeaponImpl(weapon));
    }
    //Arrange weapons in increasing order of their damage.
    weaponsCopy.sort(Comparator.comparingInt(Weapon::getDamageAmt));
    return weaponsCopy;
  }

  @Override
  public int getSpace() {
    return this.spaceIndex;
  }

  @Override
  public void movePlayer(int spaceIdx) {
    if (spaceIdx < 0) {
      throw new IllegalArgumentException("Invalid space index!!!");
    }

    if (spaceIdx == this.spaceIndex) {
      throw new IllegalArgumentException("Current space and new movable space are same!!!");
    }

    this.spaceIndex = spaceIdx;
  }

  @Override
  public PlayerType getPlayerType() {
    return this.playerType;
  }

  @Override
  public boolean itemLimitReached() {
    return (this.maxItems == weapons.size());
  }

  @Override
  public int getItemLimit() {
    return this.maxItems;
  }

  @Override
  public void removeWeapon(Weapon weapon) {
    if (weapon == null) {
      throw new IllegalArgumentException("Player can't be null");
    }

    for (int i = 0; i < this.weapons.size(); i++) {
      Weapon tempWeapon = this.weapons.get(i);

      if (tempWeapon.equals(weapon)) {
        weapons.remove(i);
        return;
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Player Name: %s\n", this.playerName));
    sb.append(String.format("Player Type: %s\n", this.playerType));

    if (this.weapons.size() > 0) {
      sb.append(String.format("Weapons: %s \n", this.weapons.stream()
              .map(Weapon::getName)
              .collect(Collectors.joining(", "))));
    } else {
      sb.append("Not carrying any weapons. \n");
    }

    sb.append(String.format("MaxWeapons: %d", maxItems));

    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { // backward compatibility with default equals
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Player)) {
      return false;
    }

    Player that = (Player) o;

    return this.getPlayerName().equals(that.getPlayerName())
            && this.getSpace() == that.getSpace();
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(this.playerName, this.spaceIndex);
  }
}

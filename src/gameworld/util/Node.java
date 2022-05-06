package gameworld.util;

/**
 * Node represents each node of the dfs graph.
 * And the content of the node will be space index and space name.
 */
public class Node {
  private final int index;
  private final String name;
  private boolean visited;

  /**
   * Constructor for the graph node, which validates the space index and name.
   * @param index represents the index of the space.
   * @param name represents the name of the space.
   */
  public Node(int index, String name) {
    if (name == null || "".equals(name)) {
      throw new IllegalArgumentException("Name is empty in the graph node!!!");
    }

    if (index < 0) {
      throw new IllegalArgumentException("Node value is negative!!!");
    }

    this.index = index;
    this.name = name;
    visited = false;
  }

  /**
   * Get the space index of the node, which is used for dfs calculation.
   * @return node index as a part of getter.
   */
  public int getIndex() {
    return index;
  }

  /**
   * Get the visited flag of the node, which is used for dfs calculation.
   * @return visited flag as a part of getter.
   */
  public boolean getVisited() {
    return visited;
  }

  /**
   * Get the space name of the node, which is used for dfs calculation.
   * @return space name as a part of getter.
   */
  public String getName() {
    return name;
  }

  /**
   * Method to update visit flag, that's required for traversal.
   */
  public void visit() {
    visited = true;
  }

  /**
   * Method to update visit flag, that's required for traversal.
   */
  public void unvisit() {
    visited = false;
  }
}

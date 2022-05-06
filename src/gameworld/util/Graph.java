package gameworld.util;

import java.util.List;

/**
 * This interface represents all the operations offered by Depth-First Search path calculation.
 * These functions are used by the gameWorld for calculating pet movement instead of leaving,
 * the pet idle during other turns.
 */
public interface Graph {
  /**
   * Creates edge between source node and destination node.
   * @param source the source node from which you have to start edge from.
   * @param destination the destination of the edge.
   */
  public void addEdge(Node source, Node destination);

  /**
   * Generates depth first search by taking backtracking into consideration.
   * @param node the source node from which dfs generation is started.
   */
  public void depthFirstSearchModified(Node node);

  /**
   * Get the depth first search path generated during dfs calculation in the above function.
   * @return dfs path which is used in the gameworld for pet movement.
   */
  public List<Integer> getDfsNodes();

  /**
   * This function will be called to regenerate dfs path
   * if the player moves the pet to a new location.
   */
  public void resetNodesVisited();
}

package gameworld.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * DFSGrpahImpl implementation of Graph class, which implement methods
 * for calculating Depth first search, which is used for pet movement.
 */
public class DfsGraphImpl implements Graph {
  // Each node maps to a list of all his neighbors
  private Map<Node, LinkedList<Node>> adjacencyMap;
  private boolean directed;
  private List<Integer> dfsPath;

  /**
   * Constructor for the dfs implementation.
   * @param directed parameter which determines if graph is directed or not.
   */
  public DfsGraphImpl(boolean directed) {
    this.directed = directed;
    adjacencyMap = new HashMap<>();
    dfsPath = new ArrayList<>();
  }

  private void addEdgeHelper(Node a, Node b) {
    LinkedList<Node> tmp = adjacencyMap.get(a);

    if (tmp != null) {
      tmp.remove(b);
    } else {
      tmp = new LinkedList<>();
    }
    tmp.add(b);
    adjacencyMap.put(a, tmp);
  }

  @Override
  public void addEdge(Node source, Node destination) {

    if (source == null) {
      throw new IllegalArgumentException("Source on addEdge is null");
    }

    if (destination == null) {
      throw new IllegalArgumentException("Destination on addEdge is null");
    }

    // We make sure that every used node shows up in our .keySet()
    if (!adjacencyMap.containsKey(source)) {
      adjacencyMap.put(source, null);
    }

    if (!adjacencyMap.containsKey(destination)) {
      adjacencyMap.put(destination, null);
    }

    addEdgeHelper(source, destination);

    // If a graph is undirected, we want to add an edge from destination to source as well
    if (!directed) {
      addEdgeHelper(destination, source);
    }
  }

  private boolean hasEdge(Node source, Node destination) {
    return adjacencyMap.containsKey(source) && adjacencyMap.get(source).contains(destination);
  }

  @Override
  public void resetNodesVisited() {
    for (Node node : adjacencyMap.keySet()) {
      node.unvisit();
    }
  }

  @Override
  public void depthFirstSearchModified(Node node) {
    if (node == null) {
      throw new IllegalArgumentException("Entry node object is null for DFS calculation");
    }

    depthFirstSearch(node);

    for (Node n : adjacencyMap.keySet()) {
      if (!n.getVisited()) {
        depthFirstSearch(n);
      }
    }
  }

  private void depthFirstSearch(Node node) {
    node.visit();
    this.dfsPath.add(node.getIndex());

    LinkedList<Node> allNeighbors = adjacencyMap.get(node);
    if (allNeighbors == null) {
      return;
    }

    for (Node neighbor : allNeighbors) {
      if (!neighbor.getVisited()) {
        depthFirstSearch(neighbor);
      }
    }
  }

  @Override
  public List<Integer> getDfsNodes() {
    return new ArrayList<>(this.dfsPath);
  }
}

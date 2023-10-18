import java.util.*;

public class AutoSolve {
  Puzzle p;
  Game controls;
  int[][] arr;
  int blankX, blankY;

  public AutoSolve(Puzzle p, Game game) {
    this.p = p;
    this.controls = game;
    this.blankX = p.x;
    this.blankY = p.y;
    this.arr = p.getArr();
    solve();
  }

  private void solve() {
    PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
    Map<int[], Node> nodeMap = new HashMap<>();
    Node startNode = new Node(arr, 0, calculateHeuristic(arr));
    openSet.add(startNode);
    nodeMap.put(arr[0], startNode);

    while (!openSet.isEmpty()) {
      Node current = openSet.poll();

      if (puzzleSolved(current.getState())) {
        reconstructPath(current);
        return;
      }

      for (int[] neighbor : getNeighbors(current.getState())) {
        int tentativeG = current.getG() + 1;
        Node neighborNode = nodeMap.get(neighbor);
        if (neighborNode == null || tentativeG < neighborNode.getG()) {
          if (neighborNode == null) {
            neighborNode = new Node(new int[][]{neighbor}, tentativeG, calculateHeuristic(new int[][]{neighbor}));
            nodeMap.put(neighbor, neighborNode);
          } else {
            openSet.remove(neighborNode);
            neighborNode.setG(tentativeG);
          }
          neighborNode.setParent(current);
          openSet.add(neighborNode);
        }
      }
    }
  }

  private boolean puzzleSolved(int[][] state) {
    int value = 1;
    for (int i = 0; i < state.length; i++) {
      for (int j = 0; j < state[i].length; j++) {
        if (state[i][j] != value && (i != state.length - 1 || j != state[i].length - 1)) {
          return false;
        }
        value = (value + 1) % (state.length * state[0].length);
      }
    }
    return true;
  }

  private int calculateHeuristic(int[][] state) {
    int heuristic = 0;
    for (int i = 0; i < state.length; i++) {
      for (int j = 0; j < state[i].length; j++) {
        int value = state[i][j];
        if (value != 0) {
          int targetX = (value - 1) / state.length;
          int targetY = (value - 1) % state.length;
          heuristic += Math.abs(i - targetX) + Math.abs(j - targetY);
        }
      }
    }
    return heuristic;
  }

  private void reconstructPath(Node node) {
    while (node.getParent() != null) {
      int[][] currentState = node.getState();
      int[][] parentState = node.getParent().getState();
      int[] blankPosition = findBlankPosition(currentState);
      int[] parentBlankPosition = findBlankPosition(parentState);

      if (blankPosition[0] < parentBlankPosition[0]) {
        controls.moveDown();
      } else if (blankPosition[0] > parentBlankPosition[0]) {
        controls.moveUp();
      } else if (blankPosition[1] < parentBlankPosition[1]) {
        controls.moveRight();
      } else if (blankPosition[1] > parentBlankPosition[1]) {
        controls.moveLeft();
      }

      node = node.getParent();
    }
  }

  private int[] findBlankPosition(int[][] state) {
    for (int i = 0; i < state.length; i++) {
      for (int j = 0; j < state[i].length; j++) {
        if (state[i][j] == 0) {
          return new int[]{i, j};
        }
      }
    }
    return null;
  }
    private List<int[]> getNeighbors(int[][] state) {
      List<int[]> neighbors = new ArrayList<>();
      int[] blankPosition = findBlankPosition(state);
      int row = blankPosition[0];
      int col = blankPosition[1];

      if (row > 0) {
        neighbors.add(swap(state, row, col, row - 1, col));
      }
      if (row < state.length - 1) {
        neighbors.add(swap(state, row, col, row + 1, col));
      }
      if (col > 0) {
        neighbors.add(swap(state, row, col, row, col - 1));
      }
      if (col < state[0].length - 1) {
        neighbors.add(swap(state, row, col, row, col + 1));
      }

      return neighbors;
    }

  private int[] swap(int[][] state, int x1, int y1, int x2, int y2) {
    int[][] newState = cloneState(state);
    int temp = newState[x1][y1];
    newState[x1][y1] = newState[x2][y2];
    newState[x2][y2] = temp;
    return newState[x1]; // Return the modified row as a 1D array
  }

  private int[][] cloneState(int[][] state) {
    int[][] newState = new int[state.length][state[0].length];
    for (int i = 0; i < state.length; i++) {
      System.arraycopy(state[i], 0, newState[i], 0, state[i].length);
    }
    return newState;
  }

  private static class Node {
    private int[][] state;
    private int g;
    private int h;
    private Node parent;

    public Node(int[][] state, int g, int h) {
      this.state = state;
      this.g = g;
      this.h = h;
    }

    public int getF() {
      return g + h;
    }

    public int[][] getState() {
      return state;
    }

    public int getG() {
      return g;
    }

    public void setG(int g) {
      this.g = g;
    }

    public Node getParent() {
      return parent;
    }

    public void setParent(Node parent) {
      this.parent = parent;
    }
  }
}

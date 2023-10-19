public class PuzzleState {
  private int[][] puzzle;
  private int cost; // Cost to reach this state from the initial state
  private int heuristic; // Heuristic value estimating the cost to reach the goal state

  public PuzzleState(int[][] puzzle, int cost, int heuristic) {
    this.puzzle = puzzle;
    this.cost = cost;
    this.heuristic = heuristic;
  }

  public int[][] getPuzzle() {
    return puzzle;
  }

  public int getCost() {
    return cost;
  }

  public int getHeuristic() {
    return heuristic;
  }
}

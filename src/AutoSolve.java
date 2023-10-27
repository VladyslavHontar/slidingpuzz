import java.util.*;

public class AutoSolve {
  private Puzzle puzzle;
  private Game game;
  private int index = 1;
  public AutoSolve(Puzzle puzzle, Game game) {
    this.puzzle = puzzle;
    this.game = game;
    solvePuzzle();
  }

  private int[] determineCoordinates() {
    int[] coor = {0, 0};
    for (int[] i : puzzle.getArr()) {
      coor[0]++;
      for (int j : i) {
        coor[1]++;
        if (index == i[j]) {
          return coor;
        }
      }
    }
    return coor;
  }
  private void solvePuzzle() {
    while (game.isPuzzleSolved(puzzle.getArr(), game.solvedPuzzle)) {
      if (puzzle.y < determineCoordinates()[1]) {
        game.moveUp();
      } else if (puzzle.y == determineCoordinates()[1] - 1) {
        if (puzzle.x < determineCoordinates()[0]) {
          game.moveLeft();
        } else if (puzzle.x == determineCoordinates()[0] - 1) {
          if (puzzle.y > determineCoordinates()[1]) {
            game.moveDown();
          } else if (puzzle.y == determineCoordinates()[1] + 1) {
            if (puzzle.x > determineCoordinates()[0]) {
              game.moveRight();
            }
          }
        }
      } else {
        game.moveDown();
      }
    }
  }
}

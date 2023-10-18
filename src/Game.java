import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
  View view;
  Puzzle p;
  int[][] arr;
  List<Integer> solvedPuzzle = new ArrayList<>();
  public Game(View view, Puzzle p) {
    this.view = view;
    this.p = p;
    this.arr = p.getArr();
    play();
  }
  public void play() {
    for (int i = 1; i <= 15; i++) {
      solvedPuzzle.add(i);
    }
    solvedPuzzle.add(0);
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter move (W/A/S/D to move up/left/down/right, Q to quit): ");
    while (!isPuzzleSolved(arr, solvedPuzzle)) {
      String move = sc.next().toUpperCase();

      switch (move) {
        case "W":
          moveUp();
          break;
        case "A":
          moveLeft();
          break;
        case "S":
          moveDown();
          break;
        case "D":
          moveRight();
          break;
          case "AUTO":
          AutoSolve auto = new AutoSolve(p, this);
        case "Q":
          System.out.println("Quitting the game. Goodbye!");
          return;
        default:
          System.out.println("Invalid move. Try again.");
          break;
      }
    }
    System.out.println("Congratulations! You solved the puzzle!");
  }

  protected void moveUp() {
    int blankSpot = p.x;
    if (p.x > 0) {
      swap(blankSpot, blankSpot - 1);
      view.showPuzzle(arr);
      p.x--;
    } else {
      System.out.println("Invalid move. Cannot move up.");
    }
  }

  protected void moveLeft() {
    int blankSpot = p.y;
    if (blankSpot > 0) {
      swap(blankSpot, blankSpot - 1, true);
      view.showPuzzle(arr);
      p.y--;
    } else {
      System.out.println("Invalid move. Cannot move left.");
    }
  }

  protected void moveDown() {
    int blankSpot = p.x;
    if (blankSpot < arr.length - 1) {
      swap(blankSpot, blankSpot + 1);
      view.showPuzzle(arr);
      p.x++;
    } else {
      System.out.println("Invalid move. Cannot move down.");
    }
  }

  protected void moveRight() {
    int blankSpot = p.y;
    if (blankSpot < arr[0].length - 1) {
      swap(blankSpot, blankSpot + 1, true);
      view.showPuzzle(arr);
      p.y++;
    } else {
      System.out.println("Invalid move. Cannot move right.");
    }
  }

  private void swap(int blankSpot, int newBlankSpot) {
    arr[blankSpot][p.y] = arr[newBlankSpot][p.y];
    arr[newBlankSpot][p.y] = 0;
  }
  private void swap(int blankSpot, int newBlankSpot, boolean isHorizontal) {
    arr[p.x][blankSpot] = arr[p.x][newBlankSpot];
    arr[p.x][newBlankSpot] = 0;
  }
  private boolean isPuzzleSolved(int[][] arr, List<Integer> solvedPuzzle) {
    int rows = arr.length;
    int cols = arr[0].length;
    int index = 0; // Index to track the position in the sequence list

    // Iterate through the 2D array
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // If the current element matches the expected number in the sequence
        if (arr[i][j] == solvedPuzzle.get(index)) {
          index++; // Move to the next number in the sequence list
          // If the entire sequence is found
          if (index == solvedPuzzle.size()) {
            return true; // Sequence found in the 2D array
          }
        }
      }
    }
    // Sequence not found in the 2D array
    return false;
  }
}

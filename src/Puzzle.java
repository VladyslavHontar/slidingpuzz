import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Puzzle {

  private static final int ARRAY_SIZE = 4;
  private int[][] arr = new int[ARRAY_SIZE][ARRAY_SIZE];
  View view;
  int x,y;
  public Puzzle(View view) {
    this.view = view;
    setUp();
  }
  public int[][] setUp() {
    List<Integer> numbers = new ArrayList<>();

    for (int i = 0; i <= 15; i++) {
      numbers.add(i);
    }

    Collections.shuffle(numbers);

    System.out.println(numbers);

    System.out.println();

    int index = 0;
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        arr[i][j] = numbers.get(index++);
        if (arr[i][j] == 0) {
          x = i;
          y = j;
        }
      }
    }

    if (isSolvable(arr)) {
      view.showPuzzle(arr);
      System.out.println("Puzzle is solvable.");
      return arr;
    } else {
      System.out.println("Puzzle is not solvable.");
      setUp();
    }
    return arr;
  }

  private boolean isSolvable(int[][] puzzle) {
    int inversionCount = 0;
    List<Integer> flatPuzzle = new ArrayList<>();

    // Flatten the 2D array into a list for easier calculation
    for (int[] row : puzzle) {
      for (int num : row) {
        flatPuzzle.add(num);
      }
    }
    // Count inversions
    for (int i = 0; i < flatPuzzle.size() - 1; i++) {
      for (int j = i + 1; j < flatPuzzle.size(); j++) {
        if (flatPuzzle.get(i) > flatPuzzle.get(j) && flatPuzzle.get(i) != 0 && flatPuzzle.get(j) != 0) {
          inversionCount++;
        }
      }
    }

    // Check solvability based on inversion count and puzzle size
    int blankRow = findBlankRow(puzzle);
    if (puzzle.length % 2 == 1) {
      return inversionCount % 2 == 0;
    } else {
      if (blankRow % 2 == 0) {
        return inversionCount % 2 != 0;
      } else {
        return inversionCount % 2 == 0;
      }
    }
  }

  private int findBlankRow(int[][] puzzle) {
    for (int i = 0; i < puzzle.length; i++) {
      for (int j = 0; j < puzzle[i].length; j++) {
        if (puzzle[i][j] == 0) {
          return i;
        }
      }
    }
    return -1; // Blank not found
  }
  public int[][] getArr() {
    return arr;
  }
}
import java.util.Arrays;

public class View {
  public void showPuzzle(int[][] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        System.out.print(arr[i][j] + "\t"); // Use \t for tab spacing
      }
      System.out.println(); // Move to the next line for the next row
    }
  }
}

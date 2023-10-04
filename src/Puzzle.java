import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Puzzle {
  private int[][] arr = new int[4][4];

  public void setUp() {
    List<Integer> numbers = new ArrayList<>();
    for (int i = 0; i <= 15; i++) {
      numbers.add(i);
    }

    Collections.shuffle(numbers);

    int index = 0;
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        arr[i][j] = numbers.get(index++);
      }
    }

    System.out.println(Arrays.deepToString(arr));
  }
}

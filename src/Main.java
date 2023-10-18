public class Main {
  public static void main(String[] args) {
    View view = new View();
    Puzzle p = new Puzzle(view);
    Game game = new Game(view, p);
    p.setUp();
  }
}
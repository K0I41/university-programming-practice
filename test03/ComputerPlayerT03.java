import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ComputerPlayerT03 extends PlayerT03 {
  private final Random rand = new Random();

  public ComputerPlayerT03(String name, int len) {
    super(name, len);
  }

  protected int selectTarget(Scanner sc, BoardT03 enemy) {
    ArrayList<Integer> list = new ArrayList<>();
    for (int i = 0; i < boardLength; i++) {
      if (!enemy.isAttacked(i))
        list.add(i);
    }
    return list.get(rand.nextInt(list.size()));
  }
}

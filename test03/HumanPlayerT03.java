import java.util.Scanner;

public class HumanPlayerT03 extends PlayerT03 {

  public HumanPlayerT03(String name, int len) {
    super(name, len);
  }

  protected int selectTarget(Scanner sc, BoardT03 enemy) {
    char last = (char) ('a' + boardLength - 1);

    while (true) {
      System.out.println("攻撃ラベルを入力してください[a-" + last + "]");
      String s = sc.nextLine().trim();

      if (s.length() != 1) {
        System.out.println("無効な座標またはすでに攻撃済みです");
        System.out.println("もう一度入力してください");
        continue;
      }

      char c = Character.toLowerCase(s.charAt(0));
      if (c < 'a' || c > last) {
        System.out.println("無効な座標またはすでに攻撃済みです");
        System.out.println("もう一度入力してください");
        continue;
      }

      int idx = super.labelToIndex(c);
      if (enemy.isAttacked(idx)) {
        System.out.println("無効な座標またはすでに攻撃済みです");
        System.out.println("もう一度入力してください");
        continue;
      }
      return idx;
    }
  }
}

import java.util.Scanner;

public class Test03 {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("使用法:java Test03 <player.csv> <cpu.csv>");
      return;
    }

    Scanner sc = new Scanner(System.in);

    try {
      SubmarineGameT03 game = new SubmarineGameT03(args[0], args[1], sc);
      game.play();
    } catch (InvalidCSVExceptionT03 e) {
      System.out.println("CSVの読み込みに失敗しました:" + e.getMessage());
    }

    sc.close();
  }
}

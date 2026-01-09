import java.util.Scanner;

public class SubmarineGameT03 {
  private final BoardT03 playerBoard;
  private final BoardT03 cpuBoard;
  private final HumanPlayerT03 player;
  private final ComputerPlayerT03 cpu;
  private final Scanner sc;

  public SubmarineGameT03(String p, String c, Scanner sc) throws InvalidCSVExceptionT03 {
    this.sc = sc;

    System.out.println(p + "を読み込んでいます");
    playerBoard = BoardT03.fromCSV(p);
    System.out.println("[ボード長]" + playerBoard.getLength());
    System.out.println("[playerの潜水艦]" + playerBoard.getRemaining() + "隻");
    System.out.println();

    System.out.println(c + "を読み込んでいます");
    cpuBoard = BoardT03.fromCSV(c);
    if (playerBoard.getLength() != cpuBoard.getLength())
      throw new InvalidCSVExceptionT03("ボード長が一致しません");
    System.out.println("[cpuの潜水艦]" + cpuBoard.getRemaining() + "隻");
    System.out.println();

    player = new HumanPlayerT03("player", playerBoard.getLength());
    cpu = new ComputerPlayerT03("cpu", cpuBoard.getLength());

    for (SubmarineT03 s : playerBoard.getSubmarines()) {
      s.toString();
    }

  }

  public void play() {
    printBoard();
    int turn = 1;

    while (true) {
      player.takeTurn(turn, sc, cpuBoard);
      if (cpuBoard.getRemaining() == 0) {
        end("player");
        return;
      }

      cpu.takeTurn(turn, sc, playerBoard);
      if (playerBoard.getRemaining() == 0) {
        end("cpu");
        return;
      }

      printBoard();
      turn++;
    }
  }

  private void printBoard() {
    StringBuilder sb = new StringBuilder("---");
    for (int i = 0; i < playerBoard.getLength(); i++) {
      sb.append((char) ('a' + i));
      if (i < playerBoard.getLength() - 1)
        sb.append(" ");
    }
    System.out.println(sb);
    System.out.println(playerBoard.row(true, "[p]"));
    System.out.println(cpuBoard.row(false, "[c]"));
    System.out.println("[player]撃沈" + player.getSunkEnemy()
        + "残" + playerBoard.getRemaining()
        + "[cpu]撃沈" + cpu.getSunkEnemy()
        + "残" + cpuBoard.getRemaining());
    System.out.println("[記号]~=海,数字=潜水艦,x=命中,o=外れ,#=撃沈");
  }

  private void end(String winner) {
    System.out.println("ゲーム終了");
    System.out.println(winner + "の勝利");
    System.out.println("[最終ボード]");
    printBoard();
  }
}

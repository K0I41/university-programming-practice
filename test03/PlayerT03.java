import java.util.Scanner;

public abstract class PlayerT03 {
  protected final String name;
  protected final int boardLength;
  private int sunkEnemy;

  public PlayerT03(String name, int len) {
    this.name = name;
    this.boardLength = len;
  }

  public int getSunkEnemy() {
    return sunkEnemy;
  }

  protected void addSunk() {
    sunkEnemy++;
  }

  protected int labelToIndex(char c) {
    return c - 'a';
  }

  protected char indexToLabel(int i) {
    return (char) ('a' + i);
  }

  public void takeTurn(int turn, Scanner sc, BoardT03 enemy) {
    System.out.println("[" + name + "のターン]" + turn);
    int idx = selectTarget(sc, enemy);
    System.out.println(name + "が[" + indexToLabel(idx) + "]を攻撃");

    BoardT03.AttackResult r = enemy.receiveAttack(idx);
    if (r == BoardT03.AttackResult.MISS)
      System.out.println("結果[外れ]");
    else if (r == BoardT03.AttackResult.HIT)
      System.out.println("結果[命中]");
    else if (r == BoardT03.AttackResult.SUNK) {
      System.out.println("結果[命中]->撃沈");
      addSunk();
    }
    System.out.println();
  }

  protected abstract int selectTarget(Scanner sc, BoardT03 enemy);
}

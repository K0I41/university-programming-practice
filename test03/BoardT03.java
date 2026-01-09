import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BoardT03 {
  public enum AttackResult {
    MISS, HIT, SUNK, ALREADY
  }

  private final int length;
  private final int[] layout;
  private final char[] marks;
  private final ArrayList<SubmarineT03> submarines;
  private int sunk;

  private BoardT03(int length, int[] layout, ArrayList<SubmarineT03> subs) {
    this.length = length;
    this.layout = layout;
    this.submarines = subs;
    this.marks = new char[length];
    this.sunk = 0;
  }

  public static BoardT03 fromCSV(String file) throws InvalidCSVExceptionT03 {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

      String line = br.readLine();
      if (line == null)
        throw new InvalidCSVExceptionT03("CSV読み込みエラー");
      if (br.readLine() != null)
        throw new InvalidCSVExceptionT03("CSV読み込みエラー");

      String[] t = line.split(",");
      if (t.length > 26)
        throw new InvalidCSVExceptionT03("ボード長が26を超えています");

      int[] layout = new int[t.length];
      boolean hasSub = false;

      for (int i = 0; i < t.length; i++) {
        if (t[i].trim().isEmpty())
          throw new InvalidCSVExceptionT03("CSV読み込みエラー");
        int v = Integer.parseInt(t[i].trim());
        if (v < 0)
          throw new InvalidCSVExceptionT03("CSV読み込みエラー");
        layout[i] = v;
        if (v > 0)
          hasSub = true;
      }
      if (!hasSub)
        throw new InvalidCSVExceptionT03("CSV読み込みエラー");

      ArrayList<SubmarineT03> subs = new ArrayList<>();

      for (int i = 0; i < layout.length; i++) {
        if (layout[i] == 0)
          continue;

        if (i == 0 || layout[i - 1] != layout[i]) {
          ArrayList<Integer> cells = new ArrayList<>();
          int j = i;
          while (j < layout.length && layout[j] == layout[i]) {
            cells.add(j);
            j++;
          }
          subs.add(new SubmarineT03(layout[i], cells));
          i = j - 1;
        }
      }

      return new BoardT03(layout.length, layout, subs);

    } catch (FileNotFoundException e) {
      throw new InvalidCSVExceptionT03(file + "が存在しません");
    } catch (NumberFormatException | IOException e) {
      throw new InvalidCSVExceptionT03("CSV読み込みエラー");
    }
  }

  public ArrayList<SubmarineT03> getSubmarines() {
    return submarines;
  }

  public int getLength() {
    return length;
  }

  public int getRemaining() {
    return submarines.size() - sunk;
  }

  public boolean isAttacked(int i) {
    return marks[i] != '\0';
  }

  public AttackResult receiveAttack(int i) {
    if (marks[i] != '\0')
      return AttackResult.ALREADY;

    if (layout[i] == 0) {
      marks[i] = 'o';
      return AttackResult.MISS;
    }

    for (SubmarineT03 s : submarines) {
      if (s.contains(i)) {
        s.hitAt(i);
        marks[i] = 'x';
        if (s.isSunk()) {
          for (int c : s.getCells())
            marks[c] = '#';
          sunk++;
          return AttackResult.SUNK;
        }
        return AttackResult.HIT;
      }
    }
    return AttackResult.MISS;
  }

  public String row(boolean showShip, String prefix) {
    StringBuilder sb = new StringBuilder(prefix);
    for (int i = 0; i < length; i++) {
      if (marks[i] != '\0')
        sb.append(" ").append(marks[i]);
      else if (showShip && layout[i] > 0)
        sb.append(" ").append(layout[i]);
      else
        sb.append(" ~");
    }
    return sb.toString();
  }
}

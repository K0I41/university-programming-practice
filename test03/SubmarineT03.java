import java.util.ArrayList;

public class SubmarineT03 {
  private final int id;
  private final ArrayList<Integer> cells;
  private final boolean[] hit;

  public SubmarineT03(int id, ArrayList<Integer> cells) {
    this.id = id;
    this.cells = cells;
    this.hit = new boolean[cells.size()];
  }

  public ArrayList<Integer> getCells() {
    return cells;
  }

  public boolean contains(int index) {
    for (int c : cells) {
      if (c == index)
        return true;
    }
    return false;
  }

  public void hitAt(int index) {
    for (int i = 0; i < cells.size(); i++) {
      if (cells.get(i) == index) {
        hit[i] = true;
      }
    }
  }

  public boolean isSunk() {
    for (boolean b : hit) {
      if (!b)
        return false;
    }
    return true;
  }

  public String toString() {
    return "Submarine{id=" + id + ", cells=" + cells + ", sunk=" + isSunk() + "}";
  }
}

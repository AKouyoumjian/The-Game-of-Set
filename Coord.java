package cs3500.set.model.hw02;

import java.util.Objects;

/**
 * Represents a two-dimensional coordinate.
 * Rows extend downward; columns extend rightward.
 */
public class Coord {
  public final int row;
  public final int col;

  public Coord(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public String toString() {
    return String.format("(r%d,c%d)", row, col);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return row == coord.row && col == coord.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }


}


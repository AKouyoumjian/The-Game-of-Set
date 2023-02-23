package cs3500.set.model.hw02;

/**
 * This is a class for the cards in the SetThree game.
 * number, filling, and shape are public so AbstractSetGameModel can access them
 * Set to final to keep them from being mutated.
 */
public class Card {
  public final int number;
  public final String filling;
  public final String shape;
  protected Coord position;

  /**
   * Constructor for Card.
   * @param number must be 1, 2, or 3
   * @param filling must be E (empty), S (striped), F (filled)
   * @param shape must be O (oval), Q (squiggly), D (diamond)
   */
  public Card(int number, String filling, String shape) {
    this.number = number;
    this.filling = filling;
    this.shape = shape;
    this.position = new Coord(0,0);


    if (this.filling == null || this.shape == null) {
      throw new IllegalArgumentException("Fields of a cs3500.set.model.hw02.Card cannot be null");
    }

    if (this.number > 3 || this.number < 1) {
      throw new IllegalArgumentException("Counts can only be 1, 2, or 3");
    }


    if (!(this.filling.equals("E")
            || this.filling.equals("S")
            || this.filling.equals("F"))) {
      throw new IllegalArgumentException("Fillings can only be E, S, or F");
    }

    if (!(this.shape.equals("O")
            || this.shape.equals("Q")
            || this.shape.equals("D"))) {
      throw new IllegalArgumentException("Shapes can only be O, Q, or D");
    }
  }

  /**
   * Makes the string representation of the Card.
   * @return the string for number, filling, and shape: "NFS"
   */
  @Override
  public String toString() {
    return this.number + this.filling + this.shape;
  }
}


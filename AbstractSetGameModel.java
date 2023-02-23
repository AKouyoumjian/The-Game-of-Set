package cs3500.set.model.hw03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;

/**
 * This is an abstract class for all Set Games. It has similar code that any game of set would use.
 * Access modifiers of fields are protected so that classes that extend this one can have access.
 */
public abstract class AbstractSetGameModel implements SetGameModel<Card> {
  protected List<Card> deck;
  protected ArrayList<Card> board;
  protected int score; // score of the game
  protected boolean gameStarted; // flag for if game has started. True = yes. False = not going.
  protected boolean gameEnded; // flag for if the game has ended. True = yes. False = not going.
  protected int height; // height of the game
  protected int width; // width of the game

  /**
   * Constructor for SetThreeGameModel class.
   */
  public AbstractSetGameModel() {
    this.board = new ArrayList<Card>();
    this.score = 0;
    this.gameStarted = false;
    this.gameEnded = false;
    this.deck = this.getCompleteDeck();
    this.height = 0; // initialize to 0 for now, but will be mutated when controller receives input.
    this.width = 0; // while 0, controller has not changed height and width correctly.
  }





  /**
   * Determines the width of the game.
   *
   * @throws IllegalStateException if the method is called before the game has started
   * @returns the integer for the width of the game
   */
  @Override
  public int getWidth() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Do not call getWidth() before game has started");
    } else {
      return this.width;
    }
  }

  /**
   * Determines the height of the game.
   *
   * @throws IllegalStateException if the method is called before the game has started
   * @returns the integer for the height of the game
   */
  @Override
  public int getHeight() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Do not call getHeight() before game has started");
    } else {
      return this.height;
    }
  }

  /**
   * Starts game with given deck, height, and width.
   *
   * @param newDeck the list of cards in the order they will be played
   * @param height  the height of the board for this game
   * @param width   the width of the board for this game
   * @throws IllegalArgumentException if height/width are not 3,
   *                                  deck size is too small put 9 cards on board,
   *                                  or if given deck is null.
   */
  protected void startGameWithDeckHelper(List<Card> newDeck, int height, int width)
          throws IllegalArgumentException {

    // throwing exceptions not specific to any set game.
    if (newDeck == null) {
      throw new IllegalArgumentException("Value for deck cannot be null");
    }
    if (newDeck.size() < height * width) {
      throw new IllegalArgumentException("Not enough cards in deck to fill the grid");
    }

    this.gameStarted = true; // set flag to true because game has begun.
    this.deck = newDeck; // the game's deck is now the inputted deck

    for (int i = 0; i < this.height * this.width; i++) {
      this.board.add(this.deck.get(0));
      this.deck.remove(0);
    }
  }


  /**
   * Attempts to claim the Cards as a set and changes the grid appropriately.
   * Effect: removes cards if set is valid and replaces their place with 3 new from top of deck
   * and removes those three new ones from the deck.
   * also sets cantReplace to true if not enough cards in deck to replace claimed set.
   *
   * @param coord1 the coordinates of the first card
   * @param coord2 the coordinates of the second card
   * @param coord3 the coordinates of the third card
   * @throws IllegalArgumentException if cards have row/column values not = to 0, 1, 2 or if the
   *                                  cards attempted to be claimed are not a set.
   * @throws IllegalStateException    if the method is called before the game has started.
   */
  @Override
  public void claimSet(Coord coord1, Coord coord2, Coord coord3)
          throws IllegalArgumentException, IllegalStateException {
    if (!this.areValidCoords(coord1, coord2, coord3)) {
      throw new IllegalArgumentException("Coords must have row and column values 0, 1, or 2");
    }
    if (!(isValidSet(coord1, coord2, coord3))) {
      throw new IllegalArgumentException("The Cards attempted to be claimed are not a set");
    }
    if (!gameStarted) {
      throw new IllegalStateException("Do not call claimSet before game has started");

    }
    if (this.deck.size() < 3) {
      this.score += 1;
      this.gameEnded = true;

    } else {
      this.score += 1;

      int place1 = (coord1.row * 3) + coord1.col;
      this.board.remove(place1);
      this.board.add(place1, this.deck.get(0));
      this.deck.remove(0);

      int place2 = (coord2.row * 3) + coord2.col;
      this.board.remove(place2);
      this.board.add(place2, this.deck.get(0));
      this.deck.remove(0);

      int place3 = (coord3.row * 3) + coord3.col;
      this.board.remove(place3);
      this.board.add(place3, this.deck.get(0));
      this.deck.remove(0);
    }
  }

  /**
   * This method finds the score of the game, accessing the score field of the class.
   *
   * @throws IllegalStateException if the game has not started yet
   * @returns the score of the game
   */
  @Override
  public int getScore() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Do not call getScore() before game has started");
    } else {
      return this.score;
    }
  }


  /**
   * This method determines if there are any valid sets present on the board.
   *
   * @return returns true if there is a valid set, false if not.
   */
  @Override
  public boolean anySetsPresent() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Do not call anySetsPresent() before game has started");
    } else {
      for (int i = 0; i < (this.getHeight() * this.getWidth()); i++) {
        for (int j = 0; j < (this.getHeight() * this.getWidth()); j++) {
          for (int k = 0; k < (this.getHeight() * this.getWidth()); k++) {

            // This determines if the Cards at indices i, j, and k are a valid set
            if (isValidSetHelper(this.board.get(i), this.board.get(j), this.board.get(k))) {
              return true;
            }
          }
        }
      }
      return false;
    }
  }

  /**
   * This method determines if the Cards at the given coordinates are a valid set.
   *
   * @param coord1 the coordinates of the first card
   * @param coord2 the coordinates of the second card
   * @param coord3 the coordinates of the third card
   * @throws IllegalStateException    if game has not begun yet
   * @throws IllegalArgumentException if Coords are not valid.
   * @returns the boolean true if the Cards are a valid set, false if not.
   */
  @Override
  public boolean isValidSet(Coord coord1, Coord coord2, Coord coord3)
          throws IllegalStateException, IllegalArgumentException {
    if (!gameStarted) {
      throw new IllegalStateException("Do not call isValidSet before game has started");
    }
    if (!this.areValidCoords(coord1, coord2, coord3)) {
      throw new IllegalArgumentException("Coords must have row and column values 0, 1, or 2");
    } else {
      Card c1 = this.getCardAtCoord(coord1);
      Card c2 = this.getCardAtCoord(coord2);
      Card c3 = this.getCardAtCoord(coord3);

      // Tells if the three Cards at the given coords are all alike/diff in all three attributes
      boolean b = isValidSetHelper(c1, c2, c3);
      return b;
    }
  }

  /**
   * Determines if the three cards are a set.
   * Protected cause its a helper method and client does not need access.
   *
   * @param c1 first card
   * @param c2 second card
   * @param c3 third card
   * @returns true if the three cards are a set.
   */
  protected static boolean isValidSetHelper(Card c1, Card c2, Card c3)
          throws IllegalArgumentException {

    // duplicate cards do not make a valid set
    if (c1.toString().equals(c2.toString()) || c1.toString().equals(c3.toString())) {
      return false;
    } else {
      return   // true if all numbers being either all same or all different
              ((((c1.number == c2.number) && (c1.number == c3.number))
                      || ((c1.number != c2.number)
                      && (c1.number != c3.number)
                      && (c2.number != c3.number)))

                      &&
                      // ture if all fillings being either all same or all different
                      ((c1.filling.equals(c2.filling) && c1.filling.equals(c3.filling))
                              || ((!(c1.filling.equals(c2.filling)))
                              && (!c1.filling.equals(c3.filling))
                              && (!c2.filling.equals(c3.filling))))

                      &&
                      // true if all shapes being either all same or all different
                      ((c1.shape.equals(c2.shape) && c1.shape.equals(c3.shape))
                              || ((!(c1.shape.equals(c2.shape)))
                              && (!c1.shape.equals(c3.shape))
                              && (!c2.shape.equals(c3.shape)))));

    }
  }

  /**
   * This method determines the Card at the given row and column.
   *
   * @param row the row of the desired card
   * @param col the column of the desired card
   * @return the Card wanted
   * @throws IllegalStateException if called before game has started
   */
  @Override
  public Card getCardAtCoord(int row, int col) throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Do not call getCardAtCoord before game has started");
    } else {
      int index = (row * this.getWidth()) + col;
      return this.board.get(index);
    }
  }

  /**
   * This method determines the Card at the given Coord.
   *
   * @param coord the coordinates of the desired card
   * @returns the Card wanted
   */
  @Override
  public Card getCardAtCoord(Coord coord) throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Do not call getCardAtCoord before game has started");
    } else {
      int index = (coord.row * this.getWidth()) + coord.col;
      return this.board.get(index);
    }
  }


  /**
   * This method makes an ArrayList of Cards of all 27 possible card having unique attributes.
   *
   * @returns the list of all possible cards
   */
  @Override
  public List<Card> getCompleteDeck() {

    ArrayList<Card> allCards = new ArrayList<>(27);
    ArrayList<String> fillings = new ArrayList<>(Arrays.asList("E", "S", "F"));
    ArrayList<String> shapes = new ArrayList<>(Arrays.asList("O", "Q", "D"));


    for (int i = 1; i < 4; i++) {
      for (int j = 0; j < 3; j++) {
        for (int k = 0; k < 3; k++) {
          Card thisCard = new Card(i, fillings.get(j), shapes.get(k));
          allCards.add(thisCard); // add the card to the list
        }
      }
    }

    return allCards;
  }


  /**
   * Determines if the provided Coords are valid.
   * This means they are positive and less than height/row - 1.
   * @param coord1 First coordinate provided
   * @param coord2 Second coordinate provided
   * @param coord3 Third coordinate provided
   * @returns true if they are valid, false if not.
   */
  protected boolean areValidCoords(Coord coord1, Coord coord2, Coord coord3) {
    int maxHeight = this.getHeight() - 1;
    int maxWidth = this.getWidth() - 1;
    return (!(coord1.row < 0 || coord1.row > maxHeight
            || coord1.col < 0 || coord1.col > maxWidth)
            && !(coord2.row < 0 || coord2.row > maxHeight
            || coord2.col < 0 || coord2.col > maxWidth)
            && !(coord3.row < 0 || coord3.row > maxHeight
            || coord3.col < 0 || coord3.col > maxWidth));
  }

  /**
   * This method determines if the game is over
   * (i.e. not enough cards replace claimed set or no possible set on grid)
   * Effect: if game is over, mutates the boolean flag to false.
   *
   * @returns the boolean true if game is over, false if game is not.
   */
  @Override
  public boolean isGameOver() {
    // gameEnded field will be true when not enough cards to replace claimed set
    // will also be true if not enough cards to add a new row to the grid.
    if (this.gameEnded) {
      return true;
    } else {
      return this.gameStarted && !this.anySetsPresent();
    }
  }




}

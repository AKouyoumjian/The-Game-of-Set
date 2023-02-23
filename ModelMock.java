import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;

/**
 * Class for mock of model made to test controller to model relay.
 */
public class ModelMock implements SetGameModel<Card> {
  StringBuilder log;

  /**
   * Constructor for this mock.
   *
   * @param log log to keep track of inputs.
   */
  ModelMock(StringBuilder log) {
    this.log = log;
  }

  /**
   * Mock for claimSet to see its inputs passed to model correctly.
   *
   * @param coord1 the coordinates of the first card
   * @param coord2 the coordinates of the second card
   * @param coord3 the coordinates of the third card
   */
  @Override
  public void claimSet(Coord coord1, Coord coord2, Coord coord3) {
    this.log.append("Set claimed: " + coord1.toString() + "," + coord2.toString()
            + "," + coord3.toString());
  }

  /**
   * Mock for startGameWithDeck to see its inputs passed to model correctly.
   *
   * @param deck   the list of cards in the order they will be played
   * @param height the height of the board for this game
   * @param width  the width of the board for this game
   * @throws IllegalArgumentException exception
   */
  @Override
  public void startGameWithDeck(List<Card> deck, int height, int width)
          throws IllegalArgumentException {

    this.log.append("deck: " + deck + " height, width:" + height + "," + width);


  }

  /**
   * Mock for getWidth. No arguments so we do not care about this method.
   *
   * @return integer
   * @throws IllegalStateException exception
   */
  @Override
  public int getWidth() throws IllegalStateException {
    return 0;
  }

  /**
   * Mock for getHeight. No arguments so we do not care about this method.
   *
   * @return integer
   * @throws IllegalStateException exception
   */
  @Override
  public int getHeight() throws IllegalStateException {
    return 0;
  }

  /**
   * Mock for getScore.
   * No arguments so we do not care about this method.
   *
   * @return score integer
   * @throws IllegalStateException exception
   */
  @Override
  public int getScore() throws IllegalStateException {
    return 0;
  }

  /**
   * Mock for anySetsPresent.
   * No arguments so we do not care about this method.
   *
   * @return false
   */
  @Override
  public boolean anySetsPresent() {
    return false;
  }

  /**
   * Mock for isValidSet to see its inputs passed to model correctly.
   *
   * @param coord1 the coordinates of the first card
   * @param coord2 the coordinates of the second card
   * @param coord3 the coordinates of the third card
   * @return false
   * @throws IllegalArgumentException exception
   */
  @Override
  public boolean isValidSet(Coord coord1, Coord coord2, Coord coord3) throws
          IllegalArgumentException {

    this.log.append(coord1.toString() + "," + coord2.toString() + "," + coord3.toString());

    return false;
  }

  /**
   * Mock for getCardAtCoord to see its inputs passed to model correctly.
   *
   * @param row the row of the desired card
   * @param col the column of the desired card
   * @return null
   */
  @Override
  public Card getCardAtCoord(int row, int col) {

    this.log.append(row + "," + col);

    return null;
  }

  /**
   * Mock for getCardAtCoord to see its inputs passed to model correctly.
   *
   * @param coord the coord of the desired card
   * @return null
   */
  @Override
  public Card getCardAtCoord(Coord coord) {

    this.log.append(coord.toString());

    return null;
  }

  /**
   * Mock for isGameOver.
   * No arguments so we do not care about this method.
   *
   * @return false
   */
  @Override
  public boolean isGameOver() {
    return false;
  }

  /**
   * Mock for getCompleteDeck.
   * No arguments however we need it to make a complete deck so we can test model mock and not have
   * a null deck.
   *
   * @return the complete deck.
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
}

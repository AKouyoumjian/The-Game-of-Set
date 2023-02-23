package cs3500.set.model.hw02;

import java.util.List;

/**
 * Interface for the state of running game of Set.
 * This interface allows observation of the game's current grid, cards,
 * and deck, but not the mutation of these elements.
 *
 * @param <Card> The card type used for the current game
 */
public interface SetGameModelState<Card> {
  /**
   * Return the width of the grid for this game of Set.
   *
   * @return the width of the grid
   * @throws IllegalStateException if the game has not yet started
   */
  int getWidth() throws IllegalStateException;

  /**
   * Return the height of the grid for this game of Set.
   *
   * @return the height of the grid
   * @throws IllegalStateException if the game has not yet started
   */
  int getHeight() throws IllegalStateException;

  /**
   * Returns how many sets the player has collected so far.
   *
   * @return the player's current score
   * @throws IllegalStateException if the game has not yet started
   */
  int getScore() throws IllegalStateException;

  /**
   * Returns true if and only if there are any sets in the currently dealt board.
   *
   * @return if there are any sets available
   * @throws IllegalStateException if the game has not yet started
   */
  boolean anySetsPresent();

  /**
   * Returns true if cards at the specified coordinates form a valid set.
   *
   * @param coord1 the coordinates of the first card
   * @param coord2 the coordinates of the second card
   * @param coord3 the coordinates of the third card
   * @return whether the cards form a valid set
   * @throws IllegalArgumentException if any of the coordinates are invalid for the given
   *                                  implementation of Set
   */
  boolean isValidSet(Coord coord1, Coord coord2, Coord coord3) throws IllegalArgumentException;

  /**
   * Returns the card at the specified row and column coordinates.
   *
   * @param row the row of the desired card
   * @param col the column of the desired card
   * @return the card at the specified coordinates
   * @throws IllegalStateException if the game has not yet started
   */
  Card getCardAtCoord(int row, int col);

  /**
   * Returns the card at the specified coordinates.
   *
   * @param coord the coordinates of the desired card
   * @return the card at the specified coordinates
   * @throws IllegalStateException if the game has not yet started
   */
  Card getCardAtCoord(Coord coord);

  /**
   * Return true when the game is over. That happens when
   * <ul>
   *   <li>there no sets on the current board OR</li>
   *   <li>there are not enough cards remaining in the deck to deal after claiming a set</li>
   * </ul>
   *
   * @return whether the game is over
   */
  boolean isGameOver();

  /**
   * Craft and return a new complete deck for the implemented game.
   * This deck must contain every possible card possible in the implemented game
   * exactly once.
   * There is no required/sorted order for the cards in this deck.
   *
   * @return the complete deck of cards
   */
  List<Card> getCompleteDeck();

}

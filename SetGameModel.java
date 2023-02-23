package cs3500.set.model.hw02;

import java.util.List;

/**
 * The model for any version of a game of Set. This interface allows
 * for the game to be played, including
 * <ul>
 * <li> starting a game of Set</li>
 * <li> making moves that can mutate the grid of cards </li>
 * <li> checking for sets in the game</li>
 * </ul>
 *
 * @param <Card> The card type used for the current game
 */
public interface SetGameModel<Card> extends SetGameModelState<Card> {

  /**
   * If the cards at the specified coordinates form a valid set, claim it,
   * and replace those cards with new cards from the deck, if possible.
   *
   * @param coord1 the coordinates of the first card
   * @param coord2 the coordinates of the second card
   * @param coord3 the coordinates of the third card
   * @throws IllegalArgumentException if any of the coordinates are invalid for the particular
   *                                  implementation of Set OR the cards at those coordinates
   *                                  do not form a set
   * @throws IllegalStateException    if the game has not started or the game has already ended
   */
  void claimSet(Coord coord1, Coord coord2, Coord coord3);

  /**
   * Begins the game using the cards given by the deck creates a grid specified
   * by the height and width parameters.
   * <p>
   * Specifically, the model deals the cards onto the board from left to right and top to bottom,
   * filling the grid.
   * </p>
   *
   * @param deck   the list of cards in the order they will be played
   * @param height the height of the board for this game
   * @param width  the width of the board for this game
   * @throws IllegalArgumentException if the deck does not have enough cards to deal to the grid
   *                                  OR the deck is null
   *                                  OR the width and height are invalid
   *                                  for a particular implementation of Set
   */
  void startGameWithDeck(List<Card> deck, int height, int width) throws IllegalArgumentException;

}

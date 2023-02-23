package cs3500.set.view;

import java.io.IOException;

/**
 * The interface for a client's perspective of the model of
 * a game of Set.
 */

public interface SetGameView {

  /**
   * Produces a textual view of the grid of cards of the current game.
   * Each card is displayed as initials of all of its attributes.
   * For instance, if a card has a single red oval, the card is displayed as 1RO.
   * If a card has three squiggly purple shapes, the card is displayed as 3PS.
   *
   * @return representation of the current state of the game
   */
  String toString();

  /**
   * Renders the grid to the data output in the implementation.
   * The format of the grid is exactly that of the toString method.
   *
   * @throws IOException if the transmission of the grid to the data output fails
   */
  void renderGrid() throws IOException;

  /**
   * Renders a given message to the data output in the implementation.
   *
   * @param message the message to be printed
   * @throws IOException if the transmission of the message to the data output fails
   */
  void renderMessage(String message) throws IOException;
}

package cs3500.set.view;

import java.io.IOException;

import cs3500.set.model.hw02.SetGameModelState;

/**
 * Class for SetGameTextView, the view of the Set Game.
 */
public class SetGameTextView implements SetGameView {
  private SetGameModelState state;
  private Appendable destination;


  /**
   * Constructor for SetGameModelState class with only model state.
   *
   * @param state inputted SetGameModel state
   */
  public SetGameTextView(SetGameModelState state) {
    this.state = state;
    this.destination = System.out;
    if (this.state == null) {
      throw new IllegalArgumentException("Do not use null arguments");
    }
  }

  /**
   * New Constructor with model state and appendable destination.
   *
   * @param state       inputted SetGameModel state
   * @param destination Appendable object that is used as a destination
   */
  public SetGameTextView(SetGameModelState state, Appendable destination) {
    this.state = state;
    this.destination = destination;
    if (this.state == null || this.destination == null) {
      throw new IllegalArgumentException("Do not use null arguments");
    }

  }

  /**
   * Produces a textual view of the grid of cards of the current game.
   * Each card is displayed as initials of all of its attributes.
   * For instance, if a card has a single red oval, the card is displayed as 1RO.
   * If a card has three squiggly purple shapes, the card is displayed as 3PS.
   *
   * @return representation of the current state of the game
   */
  public String toString() {

    StringBuilder str = new StringBuilder();
    int height = this.state.getHeight();
    int width = this.state.getWidth();


    for (int i = 0; i < height; i++) {

      for (int k = 0; k < width; k++) {

        String card = this.state.getCardAtCoord(i, k).toString();


        // this is so there is no space at end of string
        if ((i == height - 1) && (k == width - 1)) {
          str.append(card);
        } // this below is for new line
        else if (k == width - 1 || k % height == width - 1) {
          str.append(card).append("\n");
        } else {
          // this is for adding spaces between Cards
          str.append(card).append(" ");
        }
      }
    }
    return str.toString();

  }


  /**
   * Renders the grid to the data output in the implementation.
   * The format of the grid is exactly that of the toString method.
   *
   * @throws IOException if the transmission of the grid to the data output fails
   */
  @Override
  public void renderGrid() throws IOException {
    try {
      this.destination.append(this.toString());
    } catch (IOException ignore) {
      throw new IOException(
              "The transmission of the grid to the data output has failed in method renderGrid");
    }
  }


  /**
   * Renders a given message to the data output in the implementation.
   *
   * @param message the message to be printed
   * @throws IOException if the transmission of the message to the data output fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.destination.append(message);
    } catch (IOException ignore) {
      throw new IOException(
              "The transmission of the grid to the data output has failed in method renderMessage");
    }
  }
}




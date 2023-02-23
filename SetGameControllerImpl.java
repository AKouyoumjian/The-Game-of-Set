package cs3500.set.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.view.SetGameView;

/**
 * Class for the controller of the Set Game.
 */
public class SetGameControllerImpl implements SetGameController {
  private SetGameModel model;
  private SetGameView view;
  private Readable input;

  /**
   * Constructor for controller.
   *
   * @param model model
   * @param view  view
   * @param input a readable
   */
  public SetGameControllerImpl(SetGameModel model, SetGameView view, Readable input) {
    this.model = model;
    this.view = view;
    this.input = input; // Readable object from which to read input
    if (this.model == null || this.view == null || this.input == null) {
      throw new IllegalArgumentException("Model, view, and readable input cannot be null");
    }
  }

  /**
   * This method plays a new game of Set.
   *
   * @throws IllegalStateException if controller isn't able to read input or transmit output.
   */
  @Override
  public void playGame() throws IllegalStateException {
    Scanner scan = new Scanner(input);
    int height = 3;
    int width = 3;
    boolean hasQuit = false;

    try {
      this.view.renderMessage(
              "Welcome to the game of Set! Enter height.\n");
    } catch (IOException e) {
      throw new IllegalStateException("IOException thrown.");
    }

    // while loop for getting the height.
    while (!hasQuit) {


      if (!scan.hasNext()) {
        throw new IllegalStateException("Controller has run out of inputs");
      }


      String current = scan.next(); // current string that scanner is on

      // does the input contain q
      if (isQ(current)) {

        // quit the game.
        try {
          this.view.renderMessage("Game quit!\nScore: 0\n");
        } catch (IOException e) {
          throw new IllegalStateException("IOException thrown.");
        }
        hasQuit = true;
        break;
      }

      int tryHeight = 0;
      try {
        tryHeight = Integer.parseInt(current);
      } catch (NumberFormatException e) {
        // if caught, this means there is no integer in the string.
        // So we catch and ask to try again
        try {
          this.view.renderMessage(
                  /*"Invalid input. Please enter height as an " +
                          "integer greater than 0 or press q to quit.\n"
                          */
                  "Invalid height/width. Try again.\n");

        } catch (IOException ignore) {
          throw new IllegalStateException("IOException thrown.");
        }
      }

      try {
        height = tryHeight;
        try {
          this.view.renderMessage(
                  "Height is: " + height
                          + "\nPlease enter the width (number of rows) for the game.\n");
          break;
        } catch (IOException e) {
          throw new IllegalStateException("IOException thrown.");
        }
      } catch (IllegalArgumentException e) {
        try {
          this.view.renderMessage(
                  "Height is invalid. Must be positive integer.\n");
        } catch (IOException e1) {
          throw new IllegalStateException("IOException thrown.");
        }
      }
    }


    // while loop for getting the width.
    while (!hasQuit) {

      if (!scan.hasNext()) {
        throw new IllegalStateException("Controller has run out of inputs");
      }

      String current = scan.next(); // current string that scanner is on

      // does the input contain q
      if (isQ(current)) {
        // quit the game.
        try {
          this.view.renderMessage("Game quit!\nScore: 0\n");
        } catch (IOException e) {
          throw new IllegalStateException("IOException thrown.");
        }
        hasQuit = true;
        break;
      }

      int tryWidth = 0;

      try {
        tryWidth = Integer.parseInt(current);
      } catch (NumberFormatException e) {
        // if caught, this means there is no integer in the string.
        // So we catch and ask to try again
        try {
          this.view.renderMessage(
                  /*"Invalid input. Please enter width as an integer greater than 0" +
                          " or press q to quit.\n"
                    */

                  "Invalid height/width. Try again.\n");
        } catch (IOException ignore) {
          throw new IllegalStateException("IOException thrown.");
        }
      }


      try {
        width = tryWidth;
        try {
          this.view.renderMessage(
                  "Width is: " + width + "\n");

          break;
        } catch (IOException e) {
          throw new IllegalStateException("IOException thrown.");
        }
      } catch (IllegalArgumentException e) {
        try {
          this.view.renderMessage(
                  "Width is invalid. Must be positive integer.\n");
        } catch (IOException e1) {
          throw new IllegalStateException("IOException thrown.");
        }
      }
    }

    if (!hasQuit) {
      try {
        // Now create the grid with given inputted height and width.
        this.model.startGameWithDeck(this.model.getCompleteDeck(), height, width);
      }
      catch (IllegalArgumentException iae) {
        this.playGame();
        return;
      }
      try {
        this.view.renderGrid();
        this.view.renderMessage("\nPlease input the row then column of the 3 cards you would " +
                "like to claim separated by pressing enter button.\n");
      } catch (IOException ignore) {
        throw new IllegalStateException("IOException thrown.");
      }
    }

    // list to store row and column inputs.
    ArrayList<Integer> coordInputs = new ArrayList<Integer>();

    // WHILE LOOP FOR USER CLAIMING SETS
    while (!hasQuit) {

      if (!scan.hasNext()) {
        throw new IllegalStateException("Controller has run out of inputs");
      }

      String next = scan.next();


      if (isQ(next)) {
        try {
          view.renderMessage("Game quit!\nState of game when quit:\n" + view.toString()
                  + "\nScore: " + model.getScore() + "\n");
          hasQuit = true;
          break;
        } catch (IOException e) {
          throw new IllegalStateException("IOException thrown.");
        }
      }

      try {
        int number = Integer.parseInt(next);

        coordInputs.add(number - 1);

        // claims set when 6 ints have been inputted
        if (coordInputs.size() > 5) {
          Coord c1 = new Coord(coordInputs.get(0), coordInputs.get(1));
          Coord c2 = new Coord(coordInputs.get(2), coordInputs.get(3));
          Coord c3 = new Coord(coordInputs.get(4), coordInputs.get(5));

          // Attempt to claim the set
          // if unsuccessful, catch the error and render appropriate message
          try { // try for the IOException
            try {
              this.model.claimSet(c1, c2, c3);

              this.updateGrid();

              /*
              Conditions for game over:
              1. Score >= 7. as the max score is 7, can not get higher as not enough
              cards in deck to replace.
              2. No sets present on the board. aka isGameOver().
              */
              try {
                if (this.model.getScore() >= 7) {
                  this.view.renderMessage("Game over!" +
                          "\nScore: " + this.model.getScore() + "\n");
                  hasQuit = true;
                  break;
                }

                if (this.model.getScore() < 7 && this.model.isGameOver()) {
                  this.view.renderMessage("\n\n\nOh no! No sets are present on the board! " +
                          "You Lose!\nScore: " + this.model.getScore() + "\n");
                  hasQuit = true;
                  break;
                }
              } catch (IOException e) {
                throw new IllegalStateException("Game threw an IOException");
              }

            } catch (IllegalArgumentException e) {
              this.view.renderMessage("Invalid claim attempted. Please try again\n");
              coordInputs.clear();
              continue;
            }
          } catch (IOException e) {
            throw new IllegalStateException("IOException thrown.");
          }
          coordInputs.clear();
        }
      } catch (NumberFormatException e) {
        try {
          this.view.renderMessage("Input must be a non negative integer " +
                  "and no more than height.\n");
        } catch (IOException ignore) {
          throw new IllegalStateException("IOException thrown.");
        }
      }
      if (coordInputs.size() > 5) {
        break;
      }
    }

  }

  /**
   * Determines if the given input contains Q or q.
   *
   * @param s string
   * @return the boolean
   */
  private static boolean isQ(String s) {
    return s.equalsIgnoreCase("q");
  }

  /**
   * Updates the grid with new state and score message.
   */
  private void updateGrid() {
    try {
      this.view.renderGrid();

      this.view.renderMessage("\nScore: " + model.getScore() + "\n");
    } catch (IOException ignore) {
      throw new IllegalStateException("IOException thrown.");
    }
  }
}

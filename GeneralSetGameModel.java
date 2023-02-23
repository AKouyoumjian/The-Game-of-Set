package cs3500.set.model.hw03;

import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;

/*
Changes made to SetThreeGameModel during A3:

- Access modifier of helper "areValidCoords()" is changed to protected to allow use in this class.

- To utilize code reuse and reduce redundant code, methods that are the same regardless of the size
of the game have been removed from SetThreeGameModel and GeneralSetGameModel and
have been implemented in the AbstractSetGameModel abstract class. SetThreeGameModel now extends
this abstract class.


- Since this model is not in HW2 package, need to access Coord's fields (row and col)
and Card's fields (number, filling, and shape).
So I changed their access modifiers to be public. To make sure fields are not
messed with (mutated) now that they are public, I set them all to be final.

- Added fields for height and width in SetThreeGameModel and GeneralSetGameModel and abstract class
I did this to be able to mutate the height and width field with user input for h and w,
along w claim set method changing the height of the game when no sets are present.

- Improved testing coverage of SetThreeGameModel tests for
claimSet(): claiming bad coords in second and third claim slot

 */


/**
 * General set class which allows the Game of Set to be played in any (pos int) height and width.
 * This class extends the abstract class for set game model so it can inherit methods that
 * do not regard the height and width of a game. It will override methods that do like claimSet.
 */
public class GeneralSetGameModel extends AbstractSetGameModel {


  /**
   * Default constructor for General set game model class that initializes fields of class.
   */
  public GeneralSetGameModel() {
    super();
  }


  /**
   * Attempts to claim the Cards as a set and changes the grid appropriately.
   * Effect: removes cards if set is valid and replaces their place with 3 new from top of deck
   * and removes those three new ones from the deck.
   * Sets cantReplace to true if not enough cards in deck to replace claimed set.
   * New to this class (different than SetThreeGameModel impl):
   * Once set is claimed, if no sets are present on new grid and enough cards are available from
   * deck, this method adds an extra row to the grid from the top of the deck.
   * If not enough cards to replace, game will end (aka gameEnd field mutates to true).
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

    // use the super class' claimSet, using the helper method after.
    super.claimSet(coord1, coord2, coord3);
    // This helper method will check for any sets present and then add rows until there is
    // a set present or deck does not have enough cards
    this.whileNoSetsPresent();
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
  @Override
  public void startGameWithDeck(List<Card> newDeck, int height, int width)
          throws IllegalArgumentException {

    // throwing exceptions specific to general set game.
    if (height < 0 | width < 0) {
      throw new IllegalArgumentException("Height/width inputs must be positive");
    }
    if (height * width < 3) {
      throw new IllegalArgumentException("Height/width must produce a board that can hold at" +
              "least 3 cards");
    }

    this.height = height;
    this.width = width;

    // use startGameWithDeck from super to reduce code redundancy.
    super.startGameWithDeckHelper(newDeck, height, width);
    this.whileNoSetsPresent();


  }



  /**
   * Helper method to implement feature: while no sets are present, add a new row until
   * there is a set present or there are not enough cards from deck.
   * Not in abstract class since it is specific to GeneralSetGameModel.
   */
  private void whileNoSetsPresent() {
    // while not any sets present, if enough cards from deck, new row is made.
    while (!this.anySetsPresent()) {

      if (this.deck.size() >= this.width) {
        this.height += 1;
        for (int i = 0; i < width; i++) {
          this.board.add(this.deck.get(0));
          this.deck.remove(0);
        }
      } else {
        this.gameEnded = true;
        break;
      }

    }

  }
}




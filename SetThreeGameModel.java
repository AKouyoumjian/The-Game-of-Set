package cs3500.set.model.hw02;


import java.util.List;

import cs3500.set.model.hw03.AbstractSetGameModel;


/**
 * Class for the SetThreeGameModel that is in charge of the functionality of the game.
 */
public class SetThreeGameModel extends AbstractSetGameModel {

  /**
   * Constructor for SetThreeGameModel class.
   */
  public SetThreeGameModel() {
    super();
    this.height = 3;
    this.width = 3;
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


    // throw exception if height or width != 3
    if (height != 3 | width != 3) {
      throw new IllegalArgumentException("Height/width inputs must be equal to 3");
    }

    super.startGameWithDeckHelper(newDeck, height, width);

  }

}

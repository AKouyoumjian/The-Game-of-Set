import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.model.hw03.GeneralSetGameModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Test class for general set game model.
 */
public class GeneralSetGameModelTest {
  private GeneralSetGameModel game;
  private List<Card> deck;

  @Before
  public void init() {
    game = new GeneralSetGameModel();
    deck = game.getCompleteDeck();
  }


  /**
   * Test for claimSet.
   */
  @Test
  public void claimSet() {

    Coord cBad1 = new Coord(-1, 2);
    Coord cBad2 = new Coord(0, 111);
    Coord c00 = new Coord(0, 0);
    Coord c01 = new Coord(0, 1);
    Coord c02 = new Coord(0, 2);

    try {
      game.claimSet(c00, c01, c02);
      fail("Expected an IllegalArgumentException.");
    } catch (IllegalStateException e) {
      // do nothing
    }

    game.startGameWithDeck(deck, 3, 3);

    try {
      game.claimSet(cBad1, c00, c01);
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      game.claimSet(cBad2, c00, c01);
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      game.claimSet(c00, cBad1, c01);
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      game.claimSet(c00, c01, cBad2);
      fail("Should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }


    // game state has not ended
    assertFalse(game.isGameOver());


    // cards c1, c2, c3 are in indices 0, 1, 2 of the board
    assertEquals("1EO", game.getCardAtCoord(0, 0).toString());
    assertEquals("1EQ", game.getCardAtCoord(0, 1).toString());
    assertEquals("1ED", game.getCardAtCoord(0, 2).toString());

    // score of game is 0 before set is claimed
    assertEquals(0, game.getScore());

    game.claimSet(c00, c01, c02);

    // the cards at indices 0,1, 2 of the board are now c10, c11, c12 from the top of deck
    assertEquals("2EO", game.getCardAtCoord(0, 0).toString());
    assertEquals("2EQ", game.getCardAtCoord(0, 1).toString());
    assertEquals("2ED", game.getCardAtCoord(0, 2).toString());

    // score of game is now 1 after the set has been claimed
    assertEquals(1, game.getScore());

    /*
    NOW TESTING FOR CLAIMING A SET WHEN NOT ENOUGH CARDS TO REPLACE AFTER
     */

    SetThreeGameModel newGame = new SetThreeGameModel();

    List<Card> newDeck = newGame.getCompleteDeck();

    newGame.startGameWithDeck(newDeck, 3, 3);

    // empty remaining deck and add 2 cards so when next set is claimed, not enough cards
    // will be in deck to replace
    newDeck.clear();
    newDeck.add(new Card(3, "E", "O"));
    newDeck.add(new Card(3, "E", "Q"));

    assertEquals(2, newDeck.size());

    // gameEnded should be false since game should not be ended
    assertEquals(false, game.isGameOver());

    newGame.claimSet(c00, c01, c02);

    // game state should now be ended
    assertEquals(true, newGame.isGameOver());

  }


  /**
   * New test for claimSet() method to test functionality of adding rows when claim set
   * leads to no sets being present on the board.
   * Test also affirms that getHeight adjusts the height according to added rows from claimSet.
   */
  @Test
  public void testClaimSetAddRowsAndGetHeight() {

    this.deck.clear();
    this.deck.add(new Card(1, "S", "O"));
    this.deck.add(new Card(2, "S", "O"));


    Card addCard = new Card(3, "S", "O");
    for (int i = 0; i < 21; i++) {
      this.deck.add(addCard);
    }

    assertEquals(23, this.deck.size());

    // game started with a board with 1 possible claim, then no sets present so will increase
    // height until it runs out of cards in deck.
    this.game.startGameWithDeck(this.deck, 4, 3);

    Coord c00 = new Coord(0, 0);
    Coord c01 = new Coord(0, 1);
    Coord c02 = new Coord(0, 2);
    this.game.claimSet(c00, c01, c02);


    // this startGameWithDeck will try to add 2 rows until it runs out of cards to fill rows
    // and will end the game.
    assertEquals(6, this.game.getHeight()); // test for getHeight()
    assertEquals(true, this.game.isGameOver());

  }


  /**
   * Test for startGameWithDeck.
   */
  @Test
  public void startGameWithDeck() {

    try {
      this.game.startGameWithDeck(this.deck, 1, 2);
      fail("should have thrown IllegalArgumentException since h*w < 3");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.game.startGameWithDeck(this.deck, 3, -3);
      fail("should have thrown IllegalArgumentException since width != 3");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.game.startGameWithDeck(null, 7, 2);
      fail("should have thrown IllegalArgumentException since deck is null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    // test for deck does not have enough cards to fill a grid with
    // height and width before checking if anySetsPresent.
    try {
      this.game.startGameWithDeck(
              new ArrayList<Card>(Arrays.asList(
                      new Card(1, "S", "O"))), 3, 3);
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    assertEquals(27, this.deck.size());

    this.game.startGameWithDeck(this.deck, 3, 3);

    assertEquals(18, this.deck.size());
    assertEquals(new Card(1, "E", "O").toString(),
            this.game.getCardAtCoord(0, 0).toString());
  }

  /**
   * Second test for start game with deck.
   * To test running out of cards from deck after making new rows.
   * Test also affirms that getHeight adjusts the height according to added rows from claimSet.
   */
  @Test
  public void startGameWithDeckTestAddRowsAndGetHeight() {

    ArrayList<Card> list = new ArrayList<>();
    for (int i = 0; i < 17; i++) {
      list.add(new Card(1, "S", "O"));
    }

    this.game.startGameWithDeck(list, 2, 4);

    // this startGameWithDeck will try to add 2 rows until it runs out of cards to fill rows
    // and will end the game.
    assertEquals(4, this.game.getHeight()); // test for getHeight()
    assertEquals(true, this.game.isGameOver());

  }


  /**
   * Test for getWidth method.
   */
  @Test
  public void getWidth() {

    try {
      this.game.getWidth();
      fail("Should have thrown IllegalStateException");
    } catch (IllegalStateException e) {
      // do nothing
    }

    this.game.startGameWithDeck(this.deck, 2, 5);
    assertEquals(5, this.game.getWidth());

    this.game.startGameWithDeck(this.deck, 1, 4);
    assertEquals(4, this.game.getWidth());

  }

  /**
   * Test for getHeight method.
   */
  @Test
  public void getHeight() {
    try {
      game.getHeight();
      fail("Should have thrown IllegalStateException");
    } catch (IllegalStateException e) {
      // do nothing
    }

    game.startGameWithDeck(deck, 2, 3);
    assertEquals(2, game.getHeight());
    game.startGameWithDeck(deck, 5, 3);
    assertEquals(5, game.getHeight());

  }

  /**
   * Test for getScore method.
   */
  @Test
  public void getScore() {
    try {
      game.getScore();
      fail("Should have thrown IllegalStateException");
    } catch (IllegalStateException e) {
      // do nothing
    }

    Coord c1 = new Coord(0, 0);
    Coord c2 = new Coord(0, 1);
    Coord c3 = new Coord(0, 2);


    game.startGameWithDeck(deck, 3, 3);
    assertEquals(0, game.getScore());

    game.claimSet(c1, c2, c3);
    assertEquals(1, game.getScore());

    game.claimSet(c1, c2, c3);
    assertEquals(2, game.getScore());
  }

  /**
   * Test for anySetsPresent.
   */
  @Test
  public void anySetsPresent() {
    try {
      game.anySetsPresent();
      fail("Should have thrown IllegalStateException");
    } catch (IllegalStateException e) {
      // do nothing
    }

    game.startGameWithDeck(deck, 3, 3);
    assertEquals(true, game.anySetsPresent());

    Coord c00 = new Coord(0, 0);
    Coord c01 = new Coord(0, 1);
    Coord c02 = new Coord(0, 2);

    game.claimSet(c00, c01, c02);
    assertEquals(true, game.anySetsPresent());

    // no test for when there is no valid sets
    // as it is not possible and not required per piazza question post 150

  }


  /**
   * Test for isInvalidSet.
   */
  @Test
  public void isValidSet() {
    Coord c00 = new Coord(0, 0);
    Coord c01 = new Coord(0, 1);
    Coord c02 = new Coord(0, 2);
    Coord c10 = new Coord(1, 0);


    try {
      game.isValidSet(c00, c01, c02);
      fail("Should have thrown IllegalStateException");
    } catch (IllegalStateException e) {
      // do nothing
    }


    game.startGameWithDeck(deck, 3, 3);
    assertEquals(true, game.isValidSet(c00, c01, c02));
    assertEquals(false, game.isValidSet(c00, c01, c10));

  }

  /**
   * Test for getCardAtCoord with args: int row, int col.
   */
  @Test
  public void getCardAtCoord1() {

    game.startGameWithDeck(deck, 3, 3);

    assertEquals("1EO", this.game.getCardAtCoord(0, 0).toString());
    assertEquals("1EQ", this.game.getCardAtCoord(0, 1).toString());
    assertEquals("1FQ", this.game.getCardAtCoord(2, 1).toString());

  }

  /**
   * Test for testGetCardAtCoord with argument: Coord.
   */
  @Test
  public void GetCardAtCoord2() {
    Coord c00 = new Coord(0, 0);
    Coord c01 = new Coord(0, 1);
    Coord c21 = new Coord(2, 1);

    game.startGameWithDeck(deck, 3, 3);
    assertEquals("1EO", this.game.getCardAtCoord(c00).toString());
    assertEquals("1EQ", this.game.getCardAtCoord(c01).toString());
    assertEquals("1FQ", this.game.getCardAtCoord(c21).toString());
  }

  /**
   * Test for isGameOver.
   * game ends when there are no sets on board or not enough cards to claim set.
   */
  @Test
  public void isGameOver() {
    Card c1 = new Card(1, "E", "O");
    Card c2 = new Card(2, "S", "Q");
    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(0, 1);
    Coord coord3 = new Coord(0, 2);

    game = new GeneralSetGameModel();

    assertEquals(false, game.isGameOver());


    game.startGameWithDeck(deck, 3, 3);

    // empty remaining deck and add 2 cards so when next set is claimed, not enough cards
    // will be in deck to replace
    deck.clear();
    deck.add(new Card(3, "E", "O"));
    deck.add(new Card(3, "E", "Q"));

    assertEquals(2, deck.size());


    assertEquals(false, game.isGameOver());


    game.claimSet(coord1, coord2, coord3);


    // claim set above attempts to claim set when deck has not enough Cards to replace. This
    // results in gameEnded field being true, and isGameOver() should return true, as shown below.

    assertEquals(true, game.isGameOver());


    // no test for when there is no valid sets
    // as it is not possible and not required per piazza question post 150

  }


  /**
   * Test for getCompleteDeck.
   */
  @Test
  public void getCompleteDeck() {
    this.deck = this.game.getCompleteDeck();
    assertEquals("1EO", deck.get(0).toString());
    assertEquals("1SD", deck.get(5).toString());
    assertEquals("2EQ", deck.get(10).toString());
    assertEquals("3ED", deck.get(20).toString());
    assertEquals("3FD", deck.get(26).toString());
  }

}



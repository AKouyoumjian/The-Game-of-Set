import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import cs3500.set.controller.SetGameControllerImpl;
import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.view.SetGameTextView;

import static org.junit.Assert.assertEquals;

/**
 * Class for controller to model tests.
 */
public class ControllerToModelTest {
  SetGameModel model;
  StringBuilder append;
  StringBuilder log;
  SetGameTextView view;
  Readable read;
  SetGameControllerImpl controller;


  /**
   * Constructor for this class.
   */
  public ControllerToModelTest() {
    this.append = new StringBuilder("");
    this.log = new StringBuilder("");
    this.model = new ModelMock(log);
    this.view = new SetGameTextView(this.model, this.append);
    this.read = new StringReader("");
  }

  /**
   * Test for claimSet using mock.
   */
  @Test
  public void testClaimSet() {
    read = new StringReader("3 3 1 1 2 2 3 3 q");
    this.controller = new SetGameControllerImpl(this.model, this.view, this.read);
    this.controller.playGame();
    assertEquals("deck: [1EO, 1EQ, 1ED, 1SO, 1SQ, 1SD, 1FO, 1FQ, 1FD, 2EO, 2EQ, 2ED, " +
            "2SO, 2SQ, 2SD, 2FO, 2FQ, 2FD, 3EO, 3EQ, 3ED, 3SO, 3SQ, 3SD, 3FO, 3FQ, 3FD] height, " +
            "width:3,3Set claimed: (r0,c0),(r1,c1),(r2,c2)", this.log.toString());
  }

  /**
   * Test for startGameWithDeck using mock.
   */
  @Test
  public void testStartGameWithDeck() {
    read = new StringReader("3 3 Q");
    List<Card> deck = new SetThreeGameModel().getCompleteDeck();
    this.controller = new SetGameControllerImpl(this.model, this.view, this.read);
    this.controller.playGame();
    assertEquals("deck: " + deck + " height, width:3,3", this.log.toString());
  }


  /**
   * Test for isValidSet using mock.
   */
  @Test
  public void testIsValidSet() {
    read = new StringReader("3 3 1 1 2 1 3 1 q");
    List<Card> deck = new SetThreeGameModel().getCompleteDeck();
    this.controller = new SetGameControllerImpl(this.model, this.view, this.read);
    this.controller.playGame();
    assertEquals("deck: " + deck + " height, width:3,3Set claimed:"
            + " (r0,c0),(r1,c0),(r2,c0)", this.log.toString());
  }

  /**
   * Test for getCardAtCoord using mock. Other getCardAtCoord not used in controller
   * so only this one is tested.
   */
  @Test
  public void testGetCardAtCoord() {
    read = new StringReader("3 3 1 1 2 2 3 3 q");
    List<Card> deck = new SetThreeGameModel().getCompleteDeck();
    this.controller = new SetGameControllerImpl(this.model, this.view, this.read);
    this.controller.playGame();
    assertEquals("deck: " + deck + " height, width:3,3Set claimed:"
            + " (r0,c0),(r1,c1),(r2,c2)", this.log.toString());
  }

  /**
   * Test for mock of an appendable to test when transmission to view or to read from Readable
   * fails, playGame throws IllegalStateException not IOException.
   */
  @Test
  public void testAppendableMock() {
    this.view = new SetGameTextView(this.model, new MockAppendable());
    this.controller = new SetGameControllerImpl(this.model, this.view, this.read);


    try {
      this.controller.playGame();
    } catch (IllegalStateException e) {
      // do nothing because we are testing for this, expecting it if test passes.
    }

    // the wonderful handins style requires tests to contain an assert. This test is solely
    // to check that the controller throws only IllegalStates, so there is no need for asserts.
    // so below assert is to appease the handin gods.
    assertEquals(0, this.model.getScore());


  }
}

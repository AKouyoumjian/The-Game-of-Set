import org.junit.Test;

import java.io.IOException;

import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.view.SetGameTextView;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class for testing SetGameTextViewTest class.
 */
public class SetGameTextViewTest {

  /**
   * Test for toString.
   */
  @Test
  public void testToString() {
    SetThreeGameModel game = new SetThreeGameModel();
    game.startGameWithDeck(game.getCompleteDeck(), 3, 3);
    SetGameTextView view = new SetGameTextView(game, new StringBuilder());

    assertEquals("1EO 1EQ 1ED" + "\n" + "1SO 1SQ 1SD" + "\n" + "1FO 1FQ 1FD",
            view.toString());

  }

  /**
   * Test for invalid constructor.
   */
  @Test
  public void testInvalidConstructor() {
    // test for constructor with 1 argument
    try {
      SetGameTextView view = new SetGameTextView(null);
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }


    // test for constructor with 2 arguments
    try {
      SetGameTextView view = new SetGameTextView(new SetThreeGameModel(), null);
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      SetGameTextView view = new SetGameTextView(null, new StringBuilder());
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      SetGameTextView view = new SetGameTextView(null, null);
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }


  /**
   * Test for renderGrid method.
   */
  @Test
  public void testRenderGrid() {
    SetThreeGameModel game = new SetThreeGameModel();
    game.startGameWithDeck(game.getCompleteDeck(), 3, 3);
    Appendable out = new StringBuilder();
    SetGameTextView view = new SetGameTextView(game, out);

    assertEquals("", out.toString());

    try {
      view.renderGrid();
    } catch (IOException e) {
      // do nothing
    }
    assertEquals(view.toString(), out.toString());
  }

  /**
   * Test for renderMessage method.
   */
  @Test
  public void testRenderMessage() {
    SetThreeGameModel game = new SetThreeGameModel();
    game.startGameWithDeck(game.getCompleteDeck(), 3, 3);
    Appendable out = new StringBuilder();
    SetGameTextView view = new SetGameTextView(game, out);
    assertEquals(new StringBuilder().toString(), out.toString());
    try {
      view.renderMessage("Hello World");
    } catch (IOException e) {
      // do nothing
    }
    assertEquals("Hello World", out.toString());

    try {
      view.renderMessage(" whats up!");
    } catch (IOException e) {
      // do nothing
    }
    assertEquals("Hello World whats up!", out.toString());


  }


}
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import cs3500.set.controller.SetGameControllerImpl;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.view.SetGameTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for controller for SetGame.
 */
public class ControllerToViewTest {
  SetThreeGameModel model;
  Appendable append;
  SetGameTextView view;
  Readable read;

  /**
   * Method to go before all tests initializing model, view, and readable.
   */
  @Before
  public void init() {
    model = new SetThreeGameModel();
    append = new StringBuilder();
    view = new SetGameTextView(model, append);
    read = new StringReader("");

  }

  /**
   * Test for invalid constructor of Controller.
   */
  @Test
  public void testInvalidConstructor() {
    read = new StringReader("q");

    try {
      SetGameControllerImpl cont1 = new SetGameControllerImpl(new SetThreeGameModel(),
              new SetGameTextView(new SetThreeGameModel(), new StringBuilder()), null);
      fail("should have thrown IllegalArgumentException with null readable");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      SetGameControllerImpl cont2 = new SetGameControllerImpl(null,
              new SetGameTextView(new SetThreeGameModel(), new StringBuilder()), read);
      fail("should have thrown IllegalArgumentException with null model");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      SetGameControllerImpl cont3 = new SetGameControllerImpl(new SetThreeGameModel(),
              null, read);
      fail("should have thrown IllegalArgumentException with null view");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }

  /**
   * Test for playGame method for initial start message and quitting before user starts playing.
   */
  @Test
  public void testInitialRenderAndQuitMessage() {

    // other tests use lowercase q to quit. this test uses Q to cover both cases.
    read = new StringReader("Q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Game quit!\n" +
                    "Score: 0\n",
            append.toString());


  }


  /**
   * Test for valid height and width inputs.
   */
  @Test
  public void testValidHeightAndWidthInput() {

    read = new StringReader("3 3 q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Width is: 3\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Please input the row then column of the 3 cards you would like to " +
                    "claim separated by pressing enter button.\n" +
                    "Game quit!\n" +
                    "State of game when quit:\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Score: 0\n",
            append.toString());

  }


  /**
   * Test for invalid height with a valid width input.
   */
  @Test
  public void testInvalidLetterInput() {

    read = new StringReader("a 3 q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Invalid height/width. Try again.\n" +
                    "Invalid height/width. Try again.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Game quit!\n" +
                    "Score: 0\n",
            append.toString());

  }

  /**
   * Test for invalid height input.
   */
  @Test
  public void testInvalidHeightInput() {

    read = new StringReader("-3 Q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Invalid height/width. Try again.\n" +
                    "Game quit!\n" +
                    "Score: 0\n",
            append.toString());

  }

  /**
   * Test for valid height with an invalid width input.
   * Test to show doubles are invalid as game asks for ints.
   */
  @Test
  public void testInvalidWidthInput() {

    read = new StringReader("3 5.3 q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Invalid height/width. Try again.\n" +
                    "Invalid height/width. Try again.Game quit!\n" +
                    "Score: 0\n",
            append.toString());

  }

  /**
   * Test for valid claim input and message prompting user to claim, followed by successful
   * claim message with correct score.
   */
  @Test
  public void testClaimMessageAndValidClaim() {

    read = new StringReader("3 3 1 1 2 2 3 3 q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Width is: 3\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Please input the row then column of the 3 cards you would like to " +
                    "claim separated by pressing enter button.\n" +
                    "2EO 1EQ 1ED\n" +
                    "1SO 2EQ 1SD\n" +
                    "1FO 1FQ 2ED\n" +
                    "Score: 1\n" +
                    "Game quit!\n" +
                    "State of game when quit:\n" +
                    "2EO 1EQ 1ED\n" +
                    "1SO 2EQ 1SD\n" +
                    "1FO 1FQ 2ED\n" +
                    "Score: 1\n",
            append.toString());

  }

  /**
   * Test for quitting while playing the game, trying to claim a set.
   * Test shows that game quits when q isn't necessarily the first input trying to claim a set.
   */
  @Test
  public void testQuitDuringGame() {

    read = new StringReader("3 3 1 1 2 q 3 3");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Width is: 3\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Please input the row then column of the 3 cards you would like to " +
                    "claim separated by pressing enter button.\n" +
                    "Game quit!\n" +
                    "State of game when quit:\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Score: 0\n",
            append.toString());

  }

  /**
   * Test for wrong claim set attempt (3 cards that are not a set).
   */
  @Test
  public void testWrongClaim() {

    read = new StringReader("3 3 1 1 2 2 3 1 q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Width is: 3\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Please input the row then column of the 3 cards you would like to " +
                    "claim separated by pressing enter button.\n" +
                    "Invalid claim attempted. Please try again\n" +
                    "Game quit!\n" +
                    "State of game when quit:\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Score: 0\n",
            append.toString());

  }

  /**
   * Test for invalid claim set attempt (invalid input not int).
   */
  @Test
  public void testInvalidClaim() {

    read = new StringReader("3 3 1 1 2 a 3 4 q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Width is: 3\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Please input the row then column of the 3 cards you would like to " +
                    "claim separated by pressing enter button.\n" +
                    "Input must be a non negative integer and no more than height.\n" +
                    "Inputs must be non negative and no more than height.\n" +
                    "Game quit!\n" +
                    "State of game when quit:\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Score: 0\n",
            append.toString());

  }

  /**
   * Another test for invalid claim set attempt (invalid input negative int).
   */
  @Test
  public void testInvalidClaim2() {

    read = new StringReader("3 3 1 1 2 2 3 -3 q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Width is: 3\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Please input the row then column of the 3 cards you would like to " +
                    "claim separated by pressing enter button.\n" +
                    "Inputs must be non negative and no more than height.\n" +
                    "Game quit!\n" +
                    "State of game when quit:\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Score: 0\n",
            append.toString());

  }

  /**
   * Test for invalid claim set attempt (invalid input int too large).
   */
  @Test
  public void testInvalidClaim3() {

    read = new StringReader("3 3 1 1 2 a 3 70 q");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Width is: 3\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Please input the row then column of the 3 cards you would like to " +
                    "claim separated by pressing enter button.\n" +
                    "Input must be a non negative integer and no more than height.\n" +
                    "Inputs must be non negative and no more than height.\n" +
                    "Game quit!\n" +
                    "State of game when quit:\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Score: 0\n",
            append.toString());

  }


  /**
   * Test for when game has been won and score = 7.
   */
  @Test
  public void testWinGame() {

    read = new StringReader("3 3 1 1 2 2 3 3" +
            " 1 1 2 2 3 3" +
            " 1 1 2 2 3 3" +
            " 1 1 2 2 3 3" +
            " 1 1 2 2 3 3" +
            " 1 1 2 2 3 3" +
            " 1 1 2 2 3 3");
    SetGameControllerImpl controller = new SetGameControllerImpl(model, view, read);

    controller.playGame();
    assertEquals("Welcome to the game of Set! Enter height.\n" +
                    "Height is: 3\n" +
                    "Please enter the width (number of rows) for the game.\n" +
                    "Width is: 3\n" +
                    "1EO 1EQ 1ED\n" +
                    "1SO 1SQ 1SD\n" +
                    "1FO 1FQ 1FD\n" +
                    "Please input the row then column of the 3 cards you would like to " +
                    "claim separated by pressing enter button.\n" +
                    "2EO 1EQ 1ED\n" +
                    "1SO 2EQ 1SD\n" +
                    "1FO 1FQ 2ED\n" +
                    "Score: 1\n" +
                    "2SO 1EQ 1ED\n" +
                    "1SO 2SQ 1SD\n" +
                    "1FO 1FQ 2SD\n" +
                    "Score: 2\n" +
                    "2FO 1EQ 1ED\n" +
                    "1SO 2FQ 1SD\n" +
                    "1FO 1FQ 2FD\n" +
                    "Score: 3\n" +
                    "3EO 1EQ 1ED\n" +
                    "1SO 3EQ 1SD\n" +
                    "1FO 1FQ 3ED\n" +
                    "Score: 4\n" +
                    "3SO 1EQ 1ED\n" +
                    "1SO 3SQ 1SD\n" +
                    "1FO 1FQ 3SD\n" +
                    "Score: 5\n" +
                    "3FO 1EQ 1ED\n" +
                    "1SO 3FQ 1SD\n" +
                    "1FO 1FQ 3FD\n" +
                    "Score: 6\n" +
                    "3FO 1EQ 1ED\n" +
                    "1SO 3FQ 1SD\n" +
                    "1FO 1FQ 3FD\n" +
                    "Score: 7\n" +
                    "Game over!\n" +
                    "Score: 7\n",
            append.toString());

  }

  /*
  No test for if no sets are present during playGame() since it is not possible
  according to Piazza question post 150.
   */

}

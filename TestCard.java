import org.junit.Before;

import cs3500.set.model.hw02.Card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class for testing Card.
 */
public class TestCard {
  Card c1;
  Card c2;
  Card c3;


  @Before
  public void init() {
    this.c1 = new Card(1, "E", "O");
    this.c2 = new Card(2, "S", "Q");
    this.c3 = new Card(3, "F", "D");
  }

  // This test is for an invalid constructor.
  @org.junit.Test
  public void testInvalidConstructor() {
    try {
      Card cBad1 = new Card(1, null, "O");
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      Card cBad2 = new Card(1, "E", null);
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      Card cBad3 = new Card(-1, "S", "O");
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      Card cBad = new Card(4, "E", "D");
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    try {
      Card cBad = new Card(2, "E", "d");
      fail("should have thrown IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }

  // This tests the toString() method.
  @org.junit.Test
  public void testToString() {
    assertEquals("1EO", this.c1.toString());
    assertEquals("2SQ", this.c2.toString());
    assertEquals("3FD", this.c3.toString());
  }


}


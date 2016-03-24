package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the Octave enum.
 * <p>
 * Created by Ben on 3/2/16.
 */
public class OctaveTest {
  /**
   * Test that octaves are correctly shown as arabic numerals.
   */
  @Test public void testToString() {
    assertEquals("1", new Octave(1).toString());
  }

  /**
   * Test that octaves are correctly shown as arabic numerals.
   * Negative number.
   */
  @Test public void testToStringNeg() {
    assertEquals("-2", new Octave(-2).toString());
  }

  /**
   * Test that octaves are correctly shown as arabic numerals.
   * Two digit number.
   */
  @Test public void testToStringTwoDigit() {
    assertEquals("10", new Octave(10).toString());
  }

  /**
   * Test the out of bounds octave negative.
   */
  @Test public void testNegativeBounds() {
    String message = "";
    try {
      Octave value = new Octave(-10);
    } catch (IllegalArgumentException exp) {
      message = exp.getMessage();
    }
    assertEquals("This octave is out of bounds.", message);
  }

  /**
   * Test the out of bounds octave positive.
   */
  @Test public void testPositiveBounds() {
    String message = "";
    try {
      Octave value = new Octave(100);
    } catch (IllegalArgumentException exp) {
      message = exp.getMessage();
    }
    assertEquals("This octave is out of bounds.", message);
  }

  /**
   * Test next octave
   */
  @Test public void testNext1() {
    Octave test = new Octave(0);
    assertEquals("0", test.toString());
    test.nextOctave();
    assertEquals("1", test.toString());
  }

  /**
   * Test next octave wrap
   * Octave should wrap from 99 to -9 when increased.
   */
  @Test public void testNext2() {
    Octave test = new Octave(99);
    assertEquals("99", test.toString());
    test.nextOctave();
    assertEquals("-9", test.toString());
  }

  /**
   * Test get value
   */
  @Test public void testGetValue() {
    assertEquals(5, new Octave(5).getValue());
  }
}

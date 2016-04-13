package cs3500.music.viewGiven.text;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for protected and package visible methods of TextView
 */
public class TextViewTest {

  @Test
  public void padLeftTest() {
    assertEquals("     hello", TextView.padLeft("hello", 10));
    assertEquals("          ", TextView.padLeft("", 10));
    assertEquals("hello", TextView.padLeft("hello", 3));
  }

  @Test
  public void padRightTest() {
    assertEquals("hello     ", TextView.padRight("hello", 10));
    assertEquals("          ", TextView.padRight("", 10));
    assertEquals("hello", TextView.padRight("hello", 3));
  }

  @Test
  public void padCenterTest() {
    assertEquals("   hello  ", TextView.padCenter("hello", 10));
    assertEquals("          ", TextView.padCenter("", 10));
    assertEquals("hello", TextView.padCenter("hello", 3));

    assertEquals("   helo   ", TextView.padCenter("helo", 10));
    assertEquals("          ", TextView.padCenter("", 10));
    assertEquals("helo", TextView.padCenter("helo", 3));
  }
}

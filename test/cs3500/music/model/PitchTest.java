package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that the pitch enum is working
 * <p>
 * Created by Ben on 3/2/16.
 */
public class PitchTest {
  /**
   * Test that the pitch is correctly shown for sharp notes.
   */
  @Test public void testToStringSharp() {
    assertEquals("C#", Pitch.CSHARP.toString());
  }

  /**
   * Test that the pitch is correctly shown for natural notes.
   */
  @Test public void testToStringNormal() {
    assertEquals("C", Pitch.C.toString());
  }

  /**
   * Due to the small finite set. All twelve are quickly tested here.
   */
  @Test public void testAll() {
    String allPitches =
      Pitch.C.toString() + Pitch.CSHARP.toString() + Pitch.D.toString() + Pitch.DSHARP.toString()
        + Pitch.E.toString() + Pitch.F.toString() + Pitch.FSHARP.toString() + Pitch.G.toString()
        + Pitch.GSHARP.toString() + Pitch.A.toString() + Pitch.ASHARP.toString() + Pitch.B
        .toString();

    assertEquals("CC#DD#EFF#GG#AA#B", allPitches);
  }

  /**
   * Test next pitch
   */
  @Test public void testNext1() {
    assertEquals("D", Pitch.CSHARP.nextPitch().toString());
  }

  /**
   * Test next pitch wrap
   */
  @Test public void testNext2() {
    assertEquals("C", Pitch.B.nextPitch().toString());
  }
}

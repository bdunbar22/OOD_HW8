package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that the pitch and octave comparator correctly compares by pitch and octave.
 * <p>
 * Created by Ben on 3/4/16.
 */
public class PitchAndOctaveComparatorTest {
  /**
   * Test that a note with a lower octave compares as less than a note with a higher
   * octave.
   */
  @Test public void testLowerOctave() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.A, new Octave(5), 0, 5);
    assertEquals(-1, new PitchAndOctaveComparator().compare(note1, note2));
  }

  /**
   * Test that a note with a higher octave compares as more than a note with a lower
   * octave.
   */
  @Test public void testHigherOctave() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.A, new Octave(5), 0, 5);
    assertEquals(1, new PitchAndOctaveComparator().compare(note2, note1));
  }

  /**
   * Test that a note with a lower pitch compares as less than a note with a higher
   * pitch.
   */
  @Test public void testLowerPitch() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.B, new Octave(4), 0, 5);
    assertEquals(-1, new PitchAndOctaveComparator().compare(note1, note2));
  }

  /**
   * Test that a note with a higher pitch compares as more than a note with a lower
   * pitch.
   */
  @Test public void testHigherPitch() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.B, new Octave(4), 0, 5);
    assertEquals(1, new PitchAndOctaveComparator().compare(note2, note1));
  }

  /**
   * Test that notes with the same comparison parameters return a comparison of 0
   */
  @Test public void testEqual() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.A, new Octave(4), 0, 5);
    assertEquals(0, new PitchAndOctaveComparator().compare(note2, note1));
  }

  /**
   * Test that notes with different starting beats compare as equal as long as the pitch and
   * octave are equal
   */
  @Test public void testStartingBeatNotImportant() {
    INote note1 = new Note(Pitch.A, new Octave(4), 1, 5);
    INote note2 = new Note(Pitch.A, new Octave(4), 0, 5);
    assertEquals(0, new PitchAndOctaveComparator().compare(note2, note1));
  }

  /**
   * Test that notes with different durations compare as equal as long as the pitch and
   * octave are equal
   */
  @Test public void testDurationNotImportant() {
    INote note1 = new Note(Pitch.A, new Octave(4), 1, 5);
    INote note2 = new Note(Pitch.A, new Octave(4), 1, 7);
    assertEquals(0, new PitchAndOctaveComparator().compare(note2, note1));
  }
}

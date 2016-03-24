package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that the card comparator works as expected.
 * <p>
 * Created by Ben on 3/4/16.
 */
public class NoteComparatorTest {
  /**
   * Test that a note with a lower starting beat compares as less than a note with a higher
   * starting beat.
   */
  @Test public void testStartBeatLess() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.A, new Octave(4), 1, 5);
    assertEquals(-1, new NoteComparator().compare(note1, note2));
  }

  /**
   * Test that a note with a higher starting beat compares as more than a note with a lower
   * starting beat.
   */
  @Test public void testStartBeatMore() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.A, new Octave(4), 1, 5);
    assertEquals(1, new NoteComparator().compare(note2, note1));
  }

  /**
   * Test that a note with a lower octave compares as less than a note with a higher
   * octave.
   */
  @Test public void testLowerOctave() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.A, new Octave(5), 0, 5);
    assertEquals(-1, new NoteComparator().compare(note1, note2));
  }

  /**
   * Test that a note with a higher octave compares as more than a note with a lower
   * octave.
   */
  @Test public void testHigherOctave() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.A, new Octave(5), 0, 5);
    assertEquals(1, new NoteComparator().compare(note2, note1));
  }

  /**
   * Test that a note with a lower pitch compares as less than a note with a higher
   * pitch.
   */
  @Test public void testLowerPitch() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.B, new Octave(4), 0, 5);
    assertEquals(-1, new NoteComparator().compare(note1, note2));
  }

  /**
   * Test that a note with a higher pitch compares as more than a note with a lower
   * pitch.
   */
  @Test public void testHigherPitch() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.B, new Octave(4), 0, 5);
    assertEquals(1, new NoteComparator().compare(note2, note1));
  }

  /**
   * Test that notes with the same comparison parameters return a comparison of 0
   */
  @Test public void testEqual() {
    INote note1 = new Note(Pitch.A, new Octave(4), 0, 5);
    INote note2 = new Note(Pitch.A, new Octave(4), 0, 5);
    assertEquals(0, new NoteComparator().compare(note2, note1));
  }
}

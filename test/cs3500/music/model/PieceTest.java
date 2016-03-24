package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test that the IPiece interface is implemented correctly and that there are not changes to
 * original pieces of cs3500.music when new pieces are created.
 * <p>
 * Created by Ben on 3/2/16.
 */
public class PieceTest {
  /**
   * Test that serial merge works
   */
  @Test public void testSerial() {
    IPiece piece1 = testPieceHelper1();
    IPiece piece2 = testPieceHelper2();
    IPiece merge = piece1.serialMerge(piece2);
    assertTrue(merge.getNotes().contains(new Note(Pitch.C, new Octave(4), 0, 1)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.B, new Octave(4), 4, 1)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.DSHARP, new Octave(4), 5, 2)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.F, new Octave(3), 6, 6)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.E, new Octave(9), 9, 5)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.ASHARP, new Octave(90), 13, 2)));
  }

  /**
   * Test serial merge when empty. There should be no notes
   */
  @Test public void testSerialEmpty() {
    IPiece piece1 = new Piece();
    IPiece piece2 = new Piece();
    IPiece merge = piece1.serialMerge(piece2);
    assertEquals("There are no musical notes to display.", merge.musicOutput());
  }

  /**
   * Test serial merge doesn't affect originals
   */
  @Test public void testSerialSecure() {
    IPiece piece1 = testPieceHelper1();
    IPiece piece2 = testPieceHelper2();
    IPiece merge = piece1.serialMerge(piece2);
    //edit notes coming from piece 2
    merge.removeNote(new Note(Pitch.C, new Octave(4), 0, 1));
    merge.removeNote(new Note(Pitch.F, new Octave(3), 6, 6));
    merge
      .changeNote(new Note(Pitch.B, new Octave(4), 4, 1), new Note(Pitch.B, new Octave(0), 0, 6));
    assertTrue(piece1.getNotes().contains(new Note(Pitch.C, new Octave(4), 0, 1)));
    assertTrue(piece1.getNotes().contains(new Note(Pitch.B, new Octave(4), 4, 1)));
    assertTrue(piece2.getNotes().contains(new Note(Pitch.F, new Octave(3), 0, 6)));
  }

  /**
   * Test that parallel merge works
   */
  @Test public void testParallel() {
    IPiece piece1 = testPieceHelper1();
    IPiece piece2 = testPieceHelper2();
    IPiece merge = piece1.parallelMerge(piece2);
    assertTrue(merge.getNotes().contains(new Note(Pitch.C, new Octave(4), 0, 1)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.B, new Octave(4), 4, 1)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.DSHARP, new Octave(4), 5, 2)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.F, new Octave(3), 0, 6)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.E, new Octave(9), 3, 5)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.ASHARP, new Octave(90), 7, 2)));
  }

  /**
   * Test parallel merge when empty piece 1 is empty. only have piece 2 notes.
   */
  @Test public void testParallelEmpty() {
    IPiece piece1 = new Piece();
    IPiece piece2 = testPieceHelper2();
    IPiece merge = piece1.parallelMerge(piece2);
    assertEquals(3, merge.getNotes().size());
    assertTrue(merge.getNotes().contains(new Note(Pitch.F, new Octave(3), 0, 6)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.E, new Octave(9), 3, 5)));
    assertTrue(merge.getNotes().contains(new Note(Pitch.ASHARP, new Octave(90), 7, 2)));
  }

  /**
   * Test parallel merge doesn't affect originals
   */
  @Test public void testParallelSecure() {
    IPiece piece1 = testPieceHelper1();
    IPiece piece2 = testPieceHelper2();
    IPiece merge = piece1.parallelMerge(piece2);
    //edit notes from piece 2
    merge.removeNote(new Note(Pitch.C, new Octave(4), 0, 1));
    merge.removeNote(new Note(Pitch.F, new Octave(3), 0, 6));
    merge.changeNote(new Note(Pitch.B, new Octave(4), 4, 1),
      new Note(Pitch.B, new Octave(89), 0, 6));
    merge.changeNote(new Note(Pitch.E, new Octave(9), 3, 5),
      new Note(Pitch.B, new Octave(91), 0, 6));
    assertTrue(piece1.getNotes().contains(new Note(Pitch.C, new Octave(4), 0, 1)));
    assertTrue(piece1.getNotes().contains(new Note(Pitch.B, new Octave(4), 4, 1)));
    assertTrue(piece2.getNotes().contains(new Note(Pitch.F, new Octave(3), 0, 6)));
  }

  /**
   * Test change field.
   * Note: as the Note.increment() has been tested for wrapping and for all Note fields,
   * this should focus on testing that the change is applied to ALL notes!
   */
  @Test public void testChangeField() {
    IPiece piece1 = testPieceHelper1();
    IPiece newPiece = piece1.changeField(NoteField.PITCH);
    assertEquals("   C4  C#4   D4  D#4   E4 \n" + "0       X                 \n"
      + "1                         \n" + "2                         \n"
      + "3                         \n" + "4  X                      \n"
      + "5                      X  \n" + "6                      |  \n", newPiece.musicOutput());
  }

  /**
   * Test change field when empty, should remain empty.
   */
  @Test public void testChangeFieldEmpty() {
    IPiece piece1 = new Piece();
    IPiece newPiece = piece1.changeField(NoteField.DURATION);
    assertEquals("There are no musical notes to display.", newPiece.musicOutput());
  }

  /**
   * Test change field doesn't affect original
   */
  public void testChangeFieldSecure() {
    IPiece piece1 = testPieceHelper1();
    IPiece newPiece = piece1.changeField(NoteField.START);
    //newPiece has all diff notes now. piece one should still remain unchanged.
    assertEquals("", piece1.musicOutput());
  }

  /**
   * Test change field multiple.
   * Uses change field (single). Just make sure multiple works.
   */
  @Test public void testChangeFieldMultiple() {
    IPiece piece1 = testPieceHelper2();
    IPiece newPiece = piece1.changeField(NoteField.OCTAVE, 5);
    assertTrue(newPiece.getNotes().contains(new Note(Pitch.F, new Octave(8), 0, 6)));
    assertTrue(newPiece.getNotes().contains(new Note(Pitch.E, new Octave(14), 3, 5)));
    assertTrue(newPiece.getNotes().contains(new Note(Pitch.ASHARP, new Octave(95), 7, 2)));
  }

  /**
   * Test copy works
   */
  @Test public void testCopy() {
    IPiece piece1 = testPieceHelper1();
    IPiece piece2 = piece1.copy();
    assertEquals(3, piece2.getNotes().size());
    assertTrue(piece2.getNotes().contains(new Note(Pitch.C, new Octave(4), 0, 1)));
    assertTrue(piece2.getNotes().contains(new Note(Pitch.B, new Octave(4), 4, 1)));
    assertTrue(piece2.getNotes().contains(new Note(Pitch.DSHARP, new Octave(4), 5, 2)));
  }

  /**
   * Test copy empty
   */
  @Test public void testCopyEmpty() {
    IPiece piece1 = new Piece();
    IPiece piece2 = piece1.copy();
    assertEquals(0, piece2.getNotes().size());
  }

  /**
   * Test copy is deep copy.
   */
  @Test public void testCopySecure() {
    IPiece piece1 = testPieceHelper1();
    IPiece piece2 = piece1.copy();
    assertEquals(3, piece2.getNotes().size());
    piece2.removeNote(new Note(Pitch.B, new Octave(4), 4, 1));
    piece2
      .changeNote(new Note(Pitch.C, new Octave(4), 0, 1), new Note(Pitch.B, new Octave(0), 0, 6));
    assertEquals(2, piece2.getNotes().size());
    //Should not affect piece 1
    assertEquals(3, piece1.getNotes().size());
    assertTrue(piece1.getNotes().contains(new Note(Pitch.C, new Octave(4), 0, 1)));
  }

  /**
   * Test reverse piece works
   */
  @Test public void testReverse() {
    IPiece piece1 = testPieceHelper1();
    IPiece piece2 = piece1.reversePiece();
    assertEquals(3, piece2.getNotes().size());
    assertTrue(piece2.getNotes().contains(new Note(Pitch.C, new Octave(4), 6, 1)));
    assertTrue(piece2.getNotes().contains(new Note(Pitch.B, new Octave(4), 2, 1)));
    assertTrue(piece2.getNotes().contains(new Note(Pitch.DSHARP, new Octave(4), 0, 2)));
  }

  /**
   * Test reverse empty
   */
  @Test public void testReverseEmpty() {
    IPiece piece1 = new Piece();
    IPiece piece2 = piece1.reversePiece();
    assertEquals(0, piece2.getNotes().size());
  }

  /**
   * Test reverse does not affect original.
   */
  @Test public void testReverseOriginalNotChanged() {
    IPiece piece1 = testPieceHelper1();
    IPiece piece2 = piece1.reversePiece();
    assertTrue(piece1.getNotes().contains(new Note(Pitch.C, new Octave(4), 0, 1)));
    assertTrue(piece1.getNotes().contains(new Note(Pitch.B, new Octave(4), 4, 1)));
    assertTrue(piece1.getNotes().contains(new Note(Pitch.DSHARP, new Octave(4), 5, 2)));
  }

  /**
   * Show output working for a piece of cs3500.music.
   */
  @Test public void testMusicOutputForAPieceWorks() {
    IPiece piece1 = testPieceHelper1();
    IPiece piece2 = testPieceHelper2();
    IPiece merge = piece1.serialMerge(piece2);
    //Just removing this so output isn't insanely long from ~90 octaves.
    merge.removeNote(new Note(Pitch.ASHARP, new Octave(90), 13, 2));
    //Add some more notes
    merge = merge.serialMerge(piece1);
    //Get some more playing time on each note.
    merge = merge.changeField(NoteField.DURATION, 3);
    assertEquals("    F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4 "
      + " G#4   A4  A#4   B4   C5  C#5   D5  D#5   E5   F5  F#5   G5  G#5   A5  A#5   B5   C6"
      + "  C#6   D6  D#6   E6   F6  F#6   G6  G#6   A6  A#6   B6   C7  C#7   D7  D#7   E7   "
      + "F7  F#7   G7  G#7   A7  A#7   B7   C8  C#8   D8  D#8   E8   F8  F#8   G8  G#8   A8  "
      + "A#8   B8   C9  C#9   D9  D#9   E9 \n"
      + " 0                                     X                                            "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + " 1                                     |                                            "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + " 2                                     |                                            "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + " 3                                     |                                            "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + " 4                                                                                  "
      + "          X                                                                         "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + " 5                                                    X                             "
      + "          |                                                                         "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + " 6  X                                                 |                             "
      + "          |                                                                         "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + " 7  |                                                 |                             "
      + "          |                                                                         "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + " 8  |                                                 |                             "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + " 9  |                                                 |                             "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                       X  \n"
      + "10  |                                                                               "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                       |  \n"
      + "11  |                                                                               "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                       |  \n"
      + "12  |                                                                               "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                       |  \n"
      + "13  |                                  X                                            "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                       |  \n"
      + "14  |                                  |                                            "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                       |  \n"
      + "15                                     |                                            "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                       |  \n"
      + "16                                     |                                            "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                       |  \n"
      + "17                                                                                  "
      + "          X                                                                         "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + "18                                                    X                             "
      + "          |                                                                         "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + "19                                                    |                             "
      + "          |                                                                         "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + "20                                                    |                             "
      + "          |                                                                         "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + "21                                                    |                             "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n"
      + "22                                                    |                             "
      + "                                                                                    "
      + "                                                                                    "
      + "                                                                                    "
      + "                          \n", merge.musicOutput());
  }

  /*
   * Make a piece for testing
   * Has:
   * C4 start beat 0 duration 1
   * B4 start beat 4 duration 1
   * D#4 start beat 5 duration 2
   */
  private IPiece testPieceHelper1() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.C, new Octave(4), 0, 1));
    piece.addNote(new Note(Pitch.B, new Octave(4), 4, 1));
    piece.addNote(new Note(Pitch.DSHARP, new Octave(4), 5, 2));
    return piece;
  }

  /*
   * Make a piece for testing
   * Has:
   * F3 start beat 0 duration 6
   * E9 start beat 3 duration 5
   * A#90 start beat 7 duration 2
   */
  private IPiece testPieceHelper2() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.F, new Octave(3), 0, 6));
    piece.addNote(new Note(Pitch.E, new Octave(9), 3, 5));
    piece.addNote(new Note(Pitch.ASHARP, new Octave(90), 7, 2));
    return piece;
  }
}

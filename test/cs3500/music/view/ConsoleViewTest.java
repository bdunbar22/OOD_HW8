package cs3500.music.view;

import cs3500.music.model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ben on 3/23/16.
 */
public class ConsoleViewTest {
  /**
   * Test the Midi View using a mock
   */
  @Test public void testMidiView() {
    Appendable appendable = new StringBuffer();
    IMusicView consoleView = getConsoleViewHelper(appendable);
    consoleView.viewMusic();

    assertEquals("    C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4 "
      + " D#4   E4   F4 \n"
      + " 0                                               X    X                             "
      + "        \n"
      + " 1  X                                            |    |    X                        "
      + "        \n"
      + " 2  |                                                      |    X    X              "
      + "        \n"
      + " 3                                                              |    |    X    X    "
      + "        \n"
      + " 4                                                                        |    |    "
      + "X    X  \n"
      + " 5                                               X    X                             "
      + "|    |  \n"
      + " 6  X                                            |    |    X                        "
      + "        \n"
      + " 7  |                                                      |    X    X              "
      + "        \n"
      + " 8                                                              |    |    X    X    "
      + "        \n"
      + " 9                                                                        |    |    "
      + "X    X  \n"
      + "10                                                                                  "
      + "|    |  \n", appendable.toString());
  }

  IMusicView getConsoleViewHelper(Appendable appendable) {
    IPiece piece = getPieceHelper();
    IViewPiece viewPiece = new ViewPiece(piece);
    //Don't use the creator because we want the special convenience constructor
    return new ConsoleView(viewPiece, appendable);
  }

  IPiece getPieceHelper() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 2));
    piece.addNote(new Note(Pitch.B, new Octave(3), 1, 2));
    piece.addNote(new Note(Pitch.C, new Octave(4), 2, 2));
    piece.addNote(new Note(Pitch.D, new Octave(4), 3, 2));
    piece.addNote(new Note(Pitch.E, new Octave(4), 4, 2));
    piece = piece.parallelMerge(piece.changeField(NoteField.PITCH));
    piece = piece.serialMerge(piece);
    return piece;
  }
}

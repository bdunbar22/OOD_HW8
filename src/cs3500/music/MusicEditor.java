package cs3500.music;

import cs3500.music.model.*;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
//import cs3500.music.view.MidiViewImpl;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    /* Sam: Use musicReader to get a noteList() and then use it in each of the
    Views. I think, at least.
     */
    MusicReader musicReader;
    IPiece piece = testBuildPiece();
    //GuiViewFrame view = new GuiViewFrame();
    //view.viewMusic(piece);
    MidiViewImpl midiView = new MidiViewImpl();
    midiView.viewMusic(piece);
    //ConsoleView consoleView = new ConsoleView();
    // You probably need to connect these views to your model, too...
  }

  private static IPiece testBuildPiece() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 3));
    piece.addNote(new Note(Pitch.A, new Octave(3), 3, 2, 1, 10));
//    piece.addNote(new Note(Pitch.A, new Octave(3), 5, 1));
//    piece.addNote(new Note(Pitch.C, new Octave(3), 2, 1));
//    piece.addNote(new Note(Pitch.D, new Octave(3), 3, 1));
//    piece.addNote(new Note(Pitch.A, new Octave(2), 0, 4));
//    piece.addNote(new Note(Pitch.B, new Octave(2), 0, 4));
//    piece = piece.serialMerge(piece);
//    IPiece slowPiece = piece.changeField(NoteField.DURATION, 1);
//    piece = piece.serialMerge(slowPiece);
//    piece = piece.serialMerge(piece.reversePiece());
//    System.out.print(piece.musicOutput());
    return piece;
  }
}

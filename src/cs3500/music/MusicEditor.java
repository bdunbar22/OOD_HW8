package cs3500.music;

import cs3500.music.model.*;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.*;
import cs3500.music.view.MidiViewImpl;

import javax.sound.midi.InvalidMidiDataException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class MusicEditor {
  /**
   * Arguments to main should be the text file name and the view to use
   * @param args
   * @throws IOException
   * @throws InvalidMidiDataException
     */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    String fileName = "mystery-1.txt";
    String desiredView = "console";
    BufferedReader in = new BufferedReader(new FileReader("text/" + fileName));
    IPiece piece = MusicReader.parseFile(in, new CompositionBuilder());

    IViewPiece viewPiece = new ViewPiece(piece);
    GuiViewFrame view = new GuiViewFrame(viewPiece);
    view.viewMusic();
    MidiViewImpl midiView = new MidiViewImpl(viewPiece);
    midiView.viewMusic();
    ConsoleView consoleView = new ConsoleView(viewPiece);
    consoleView.viewMusic();

//    IPiece piece = testBuildPieceMelody();
//    IViewPiece viewPiece = new ViewPiece(piece);
//    GuiViewFrame view = new GuiViewFrame(viewPiece);
//    view.viewMusic();
//    MidiViewImpl midiView = new MidiViewImpl(viewPiece);
//    midiView.viewMusic();
//    ConsoleView consoleView = new ConsoleView(viewPiece);
//    consoleView.viewMusic();
  }

  private static IPiece testBuildPiece() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 3));
    piece.addNote(new Note(Pitch.A, new Octave(3), 3, 2, 1, 10));
    piece.addNote(new Note(Pitch.A, new Octave(3), 5, 1));
    piece.addNote(new Note(Pitch.C, new Octave(3), 2, 1));
    //piece.addNote(new Note(Pitch.D, new Octave(20), 3, 1));
    piece.addNote(new Note(Pitch.A, new Octave(2), 0, 4));
    piece.addNote(new Note(Pitch.B, new Octave(2), 0, 4));
    piece = piece.serialMerge(piece);
    IPiece slowPiece = piece.changeField(NoteField.DURATION, 1);
    piece = piece.serialMerge(slowPiece);
    piece.addNote(new Note(Pitch.FSHARP, new Octave(3), 22, 3));
    piece = piece.serialMerge(piece.reversePiece());
    piece.addNote(new Note(Pitch.GSHARP, new Octave(2), 49, 16));
    System.out.print(piece.musicOutput());
    return piece;
  }

  private static IPiece testBuildPieceMelody() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 2));
    piece.addNote(new Note(Pitch.B, new Octave(3), 1, 2));
    piece.addNote(new Note(Pitch.C, new Octave(4), 2, 2));
    piece.addNote(new Note(Pitch.D, new Octave(4), 3, 2));
    piece.addNote(new Note(Pitch.E, new Octave(4), 4, 2));
    IPiece piece2 = piece.serialMerge(piece.reversePiece());
    piece = piece2.serialMerge(piece2);
    piece = piece.serialMerge(piece.changeField(NoteField.OCTAVE));
    piece = piece.parallelMerge(piece2.changeField(NoteField.START, 23));
    return piece;
  }

  private static IPiece buildPiece(String[] args) {
    IPiece piece = new Piece();
    for (String s : args) {
      System.out.print(s);
    }
    return piece;
  }
}

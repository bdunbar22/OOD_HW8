package cs3500.music;

import cs3500.music.model.*;
import cs3500.music.util.MusicReader;
import cs3500.music.view.*;
import cs3500.music.view.MidiViewImpl;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    MusicReader musicReader;
    IPiece piece = testBuildPiece();
    IPieceView pieceView = new PieceView(piece);
    GuiViewFrame view = new GuiViewFrame(pieceView);
    view.viewMusic();
    MidiViewImpl midiView = new MidiViewImpl(pieceView);
    midiView.viewMusic();
    ConsoleView consoleView = new ConsoleView(pieceView);
    consoleView.viewMusic();
  }

  private static IPiece testBuildPiece() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 3));
    piece.addNote(new Note(Pitch.A, new Octave(3), 3, 2, 1, 10));
    piece.addNote(new Note(Pitch.A, new Octave(3), 5, 1));
    piece.addNote(new Note(Pitch.C, new Octave(3), 2, 1));
    piece.addNote(new Note(Pitch.D, new Octave(20), 3, 1));
    piece.addNote(new Note(Pitch.A, new Octave(2), 0, 4));
    piece.addNote(new Note(Pitch.B, new Octave(2), 0, 4));
    piece = piece.serialMerge(piece);
    IPiece slowPiece = piece.changeField(NoteField.DURATION, 1);
    piece = piece.serialMerge(slowPiece);
    piece = piece.serialMerge(piece.reversePiece());
    System.out.print(piece.musicOutput());
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

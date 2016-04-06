package cs3500.music.view;

import cs3500.music.model.*;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import org.junit.Test;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;

public class MidiViewPerBeatTest {
  /**
   * Test the Midi View using a mock
   */
  @Test public void testMidiView() {
    Synthesizer synthesizer = new MockSynthesizer();
    Receiver receiver;
    try {
      receiver = synthesizer.getReceiver();
    } catch (MidiUnavailableException e) {
      receiver = new MockReceiver();
      System.out.print("Error getting receiver during tests.");
    }
    IMusicView midi = getMidiViewHelper(receiver, synthesizer);
    midi.viewMusic();

    assertEquals(
      "Adding to Midi:\n" + "Note: Status=ON Instrument=0 Pitch=57 Volume=64 Time=1000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=57 Volume=64 Time=2000000\n"
        + "Note: Status=ON Instrument=0 Pitch=69 Volume=64 Time=1000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=69 Volume=64 Time=2000000\n",
      receiver.toString());
  }

  /**
   * Test the middle of the piece
   */
  @Test public void testMidiView2() {
    Synthesizer synthesizer = new MockSynthesizer();
    Receiver receiver;
    try {
      receiver = synthesizer.getReceiver();
    } catch (MidiUnavailableException e) {
      receiver = new MockReceiver();
      System.out.print("Error getting receiver during tests.");
    }
    IMusicView midi = getMidiViewHelper2(receiver, synthesizer);
    midi.viewMusic();

    assertEquals(
      "Adding to Midi:\n" + "Note: Status=ON Instrument=0 Pitch=60 Volume=64 Time=1500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=60 Volume=64 Time=2500000\n",
      receiver.toString());
  }

  /**
   * Test the end of the piece
   */
  @Test public void testMidiView3() {
    Synthesizer synthesizer = new MockSynthesizer();
    Receiver receiver;
    try {
      receiver = synthesizer.getReceiver();
    } catch (MidiUnavailableException e) {
      receiver = new MockReceiver();
      System.out.print("Error getting receiver during tests.");
    }
    IMusicView midi = getMidiViewHelper3(receiver, synthesizer);
    midi.viewMusic();

    assertEquals(
      "Adding to Midi:\n" + "Note: Status=ON Instrument=0 Pitch=60 Volume=64 Time=2500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=60 Volume=64 Time=3000000\n" + "Closed\n",
      receiver.toString());
  }

  IMusicView getMidiViewHelper(Receiver receiver, Synthesizer synth) {
    IPiece piece = getPieceHelper();
    IViewPiece viewPiece = new ViewPiece(piece);
    //Don't use the creator because we want the special convenience constructor
    return new MidiViewPerBeat(synth, receiver, viewPiece);
  }

  IPiece getPieceHelper() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 2));
    piece.addNote(new Note(Pitch.B, new Octave(3), 1, 2));
    piece.addNote(new Note(Pitch.C, new Octave(4), 2, 2));
    piece.addNote(new Note(Pitch.D, new Octave(4), 3, 2));
    piece.addNote(new Note(Pitch.E, new Octave(4), 4, 2));
    piece = piece.parallelMerge(piece.changeField(NoteField.OCTAVE));
    piece = piece.serialMerge(piece);
    return piece;
  }

  IMusicView getMidiViewHelper2(Receiver receiver, Synthesizer synth) {
    IPiece piece = getPieceHelper2();
    IViewPiece viewPiece = new ViewPiece(piece);
    //Don't use the creator because we want the special convenience constructor
    return new MidiViewPerBeat(synth, receiver, viewPiece);
  }

  IPiece getPieceHelper2() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 4));
    piece.addNote(new Note(Pitch.C, new Octave(3), 0, 2));
    piece.addNote(new Note(Pitch.C, new Octave(4), 1, 2));
    piece.addNote(new Note(Pitch.C, new Octave(4), 3, 1));
    piece.addNote(new Note(Pitch.C, new Octave(4), 3, 1));
    piece.setBeat(1);
    return piece;
  }

  IMusicView getMidiViewHelper3(Receiver receiver, Synthesizer synth) {
    IPiece piece = getPieceHelper3();
    IViewPiece viewPiece = new ViewPiece(piece);
    //Don't use the creator because we want the special convenience constructor
    return new MidiViewPerBeat(synth, receiver, viewPiece);
  }

  IPiece getPieceHelper3() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 2));
    piece.addNote(new Note(Pitch.B, new Octave(3), 1, 2));
    piece.addNote(new Note(Pitch.C, new Octave(4), 2, 2));
    piece.addNote(new Note(Pitch.C, new Octave(4), 3, 1));
    piece.addNote(new Note(Pitch.C, new Octave(4), 3, 1));
    piece.setBeat(3);
    return piece;
  }
}

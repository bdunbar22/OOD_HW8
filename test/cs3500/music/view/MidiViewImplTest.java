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

public class MidiViewImplTest {
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
        + "Note: Status=ON Instrument=0 Pitch=59 Volume=64 Time=1500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=59 Volume=64 Time=2500000\n"
        + "Note: Status=ON Instrument=0 Pitch=60 Volume=64 Time=2000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=60 Volume=64 Time=3000000\n"
        + "Note: Status=ON Instrument=0 Pitch=62 Volume=64 Time=2500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=62 Volume=64 Time=3500000\n"
        + "Note: Status=ON Instrument=0 Pitch=64 Volume=64 Time=3000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=64 Volume=64 Time=4000000\n"
        + "Note: Status=ON Instrument=0 Pitch=69 Volume=64 Time=1000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=69 Volume=64 Time=2000000\n"
        + "Note: Status=ON Instrument=0 Pitch=71 Volume=64 Time=1500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=71 Volume=64 Time=2500000\n"
        + "Note: Status=ON Instrument=0 Pitch=72 Volume=64 Time=2000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=72 Volume=64 Time=3000000\n"
        + "Note: Status=ON Instrument=0 Pitch=74 Volume=64 Time=2500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=74 Volume=64 Time=3500000\n"
        + "Note: Status=ON Instrument=0 Pitch=76 Volume=64 Time=3000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=76 Volume=64 Time=4000000\n"
        + "Note: Status=ON Instrument=0 Pitch=57 Volume=64 Time=3500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=57 Volume=64 Time=4500000\n"
        + "Note: Status=ON Instrument=0 Pitch=59 Volume=64 Time=4000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=59 Volume=64 Time=5000000\n"
        + "Note: Status=ON Instrument=0 Pitch=60 Volume=64 Time=4500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=60 Volume=64 Time=5500000\n"
        + "Note: Status=ON Instrument=0 Pitch=62 Volume=64 Time=5000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=62 Volume=64 Time=6000000\n"
        + "Note: Status=ON Instrument=0 Pitch=64 Volume=64 Time=5500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=64 Volume=64 Time=6500000\n"
        + "Note: Status=ON Instrument=0 Pitch=69 Volume=64 Time=3500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=69 Volume=64 Time=4500000\n"
        + "Note: Status=ON Instrument=0 Pitch=71 Volume=64 Time=4000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=71 Volume=64 Time=5000000\n"
        + "Note: Status=ON Instrument=0 Pitch=72 Volume=64 Time=4500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=72 Volume=64 Time=5500000\n"
        + "Note: Status=ON Instrument=0 Pitch=74 Volume=64 Time=5000000\n"
        + "Note: Status=OFF Instrument=0 Pitch=74 Volume=64 Time=6000000\n"
        + "Note: Status=ON Instrument=0 Pitch=76 Volume=64 Time=5500000\n"
        + "Note: Status=OFF Instrument=0 Pitch=76 Volume=64 Time=6500000\n" + "Closed\n",
      receiver.toString());
  }

  /**
   * Test that Midi is working with the text files
   */
  @Test public void testMaryFile() {
    Synthesizer synthesizer = new MockSynthesizer();
    Receiver receiver;
    try {
      receiver = synthesizer.getReceiver();
    } catch (MidiUnavailableException e) {
      receiver = new MockReceiver();
      System.out.print("Error getting receiver during tests.");
    }
    IMusicView midi = getMidiViewMary(receiver, synthesizer);
    midi.viewMusic();
    assertEquals(
      "Adding to Midi:\n" + "Note: Status=ON Instrument=1 Pitch=64 Volume=72 Time=1000000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=72 Time=1400000\n"
        + "Note: Status=ON Instrument=1 Pitch=55 Volume=70 Time=1000000\n"
        + "Note: Status=OFF Instrument=1 Pitch=55 Volume=70 Time=2400000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=72 Time=1400000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=72 Time=1800000\n"
        + "Note: Status=ON Instrument=1 Pitch=60 Volume=71 Time=1800000\n"
        + "Note: Status=OFF Instrument=1 Pitch=60 Volume=71 Time=2200000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=79 Time=2200000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=79 Time=2600000\n"
        + "Note: Status=ON Instrument=1 Pitch=55 Volume=79 Time=2600000\n"
        + "Note: Status=OFF Instrument=1 Pitch=55 Volume=79 Time=4000000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=85 Time=2600000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=85 Time=3000000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=78 Time=3000000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=78 Time=3400000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=74 Time=3400000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=74 Time=4000000\n"
        + "Note: Status=ON Instrument=1 Pitch=55 Volume=77 Time=4200000\n"
        + "Note: Status=OFF Instrument=1 Pitch=55 Volume=77 Time=5800000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=75 Time=4200000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=75 Time=4600000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=77 Time=4600000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=77 Time=5000000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=75 Time=5000000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=75 Time=5800000\n"
        + "Note: Status=ON Instrument=1 Pitch=55 Volume=79 Time=5800000\n"
        + "Note: Status=OFF Instrument=1 Pitch=55 Volume=79 Time=6200000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=82 Time=5800000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=82 Time=6200000\n"
        + "Note: Status=ON Instrument=1 Pitch=67 Volume=84 Time=6200000\n"
        + "Note: Status=OFF Instrument=1 Pitch=67 Volume=84 Time=6600000\n"
        + "Note: Status=ON Instrument=1 Pitch=67 Volume=75 Time=6600000\n"
        + "Note: Status=OFF Instrument=1 Pitch=67 Volume=75 Time=7400000\n"
        + "Note: Status=ON Instrument=1 Pitch=55 Volume=78 Time=7400000\n"
        + "Note: Status=OFF Instrument=1 Pitch=55 Volume=78 Time=9000000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=73 Time=7400000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=73 Time=7800000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=69 Time=7800000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=69 Time=8200000\n"
        + "Note: Status=ON Instrument=1 Pitch=60 Volume=71 Time=8200000\n"
        + "Note: Status=OFF Instrument=1 Pitch=60 Volume=71 Time=8600000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=80 Time=8600000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=80 Time=9000000\n"
        + "Note: Status=ON Instrument=1 Pitch=55 Volume=79 Time=9000000\n"
        + "Note: Status=OFF Instrument=1 Pitch=55 Volume=79 Time=10600000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=84 Time=9000000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=84 Time=9400000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=76 Time=9400000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=76 Time=9800000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=74 Time=9800000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=74 Time=10200000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=77 Time=10200000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=77 Time=10600000\n"
        + "Note: Status=ON Instrument=1 Pitch=55 Volume=78 Time=10600000\n"
        + "Note: Status=OFF Instrument=1 Pitch=55 Volume=78 Time=12200000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=75 Time=10600000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=75 Time=11000000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=74 Time=11000000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=74 Time=11400000\n"
        + "Note: Status=ON Instrument=1 Pitch=64 Volume=81 Time=11400000\n"
        + "Note: Status=OFF Instrument=1 Pitch=64 Volume=81 Time=11800000\n"
        + "Note: Status=ON Instrument=1 Pitch=62 Volume=70 Time=11800000\n"
        + "Note: Status=OFF Instrument=1 Pitch=62 Volume=70 Time=12200000\n"
        + "Note: Status=ON Instrument=1 Pitch=52 Volume=72 Time=12200000\n"
        + "Note: Status=OFF Instrument=1 Pitch=52 Volume=72 Time=13800000\n"
        + "Note: Status=ON Instrument=1 Pitch=60 Volume=73 Time=12200000\n"
        + "Note: Status=OFF Instrument=1 Pitch=60 Volume=73 Time=13800000\n" + "Closed\n",
      receiver.toString());
  }

  /**
   * Test parameters to notes
   * Instrument tested
   * Volume tested
   * Pitch tested
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
      "Adding to Midi:\n" + "Note: Status=ON Instrument=1 Pitch=57 Volume=64 Time=1000000\n"
        + "Note: Status=OFF Instrument=1 Pitch=57 Volume=64 Time=2000000\n"
        + "Note: Status=ON Instrument=1 Pitch=48 Volume=70 Time=1000000\n"
        + "Note: Status=OFF Instrument=1 Pitch=48 Volume=70 Time=2000000\n"
        + "Note: Status=ON Instrument=1 Pitch=60 Volume=80 Time=1500000\n"
        + "Note: Status=OFF Instrument=1 Pitch=60 Volume=80 Time=2500000\n"
        + "Note: Status=ON Instrument=2 Pitch=60 Volume=60 Time=2500000\n"
        + "Note: Status=OFF Instrument=2 Pitch=60 Volume=60 Time=3000000\n" + "Closed\n",
      receiver.toString());
  }

  IMusicView getMidiViewHelper(Receiver receiver, Synthesizer synth) {
    IPiece piece = getPieceHelper();
    IViewPiece viewPiece = new ViewPiece(piece);
    //Don't use the creator because we want the special convenience constructor
    return new MidiViewImpl(synth, receiver, viewPiece);
  }

  IMusicView getMidiViewMary(Receiver receiver, Synthesizer synth) {
    try {
      BufferedReader in = new BufferedReader(new FileReader("text/mary-little-lamb.txt"));
      IPiece piece = MusicReader.parseFile(in, new CompositionBuilder());
      IViewPiece viewPiece = new ViewPiece(piece);

      //Don't use the creator because we want the special convenience constructor
      return new MidiViewImpl(synth, receiver, viewPiece);
    } catch (FileNotFoundException e) {
      System.out.print("File not found in testing");
    }
    return getMidiViewHelper(receiver, synth);
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
    return new MidiViewImpl(synth, receiver, viewPiece);
  }

  IPiece getPieceHelper2() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 2, 1, 64));
    piece.addNote(new Note(Pitch.C, new Octave(3), 0, 2, 1, 70));
    piece.addNote(new Note(Pitch.C, new Octave(4), 1, 2, 1, 80));
    piece.addNote(new Note(Pitch.C, new Octave(4), 3, 1, 2, 60));
    piece.addNote(new Note(Pitch.C, new Octave(4), 3, 1, 2, 90));
    return piece;
  }
}

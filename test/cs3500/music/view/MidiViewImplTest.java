package cs3500.music.view;

import cs3500.music.model.*;
import org.junit.Test;

import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;

import static org.junit.Assert.*;

/**
 * Created by Ben on 3/23/16.
 */
public class MidiViewImplTest {
    /**
     * Test the Midi View using a mock
     */
    @Test
    public void testMidiView() {
        Synthesizer synthesizer = new MockSynthesizer();
        Receiver receiver = synthesizer.getReceiver();
        IMusicView midi = getMidiViewHelper(receiver, synthesizer);
        midi.viewMusic();

        assertEquals("Adding note to Midi: [B@52d455b8 0\n"
            + "Adding note to Midi: [B@4f4a7090 1000000\n"
            + "Adding note to Midi: [B@18ef96 500000\n"
            + "Adding note to Midi: [B@6956de9 1500000\n"
            + "Adding note to Midi: [B@769c9116 1000000\n"
            + "Adding note to Midi: [B@6aceb1a5 2000000\n"
            + "Adding note to Midi: [B@2d6d8735 1500000\n"
            + "Adding note to Midi: [B@ba4d54 2500000\n"
            + "Adding note to Midi: [B@12bc6874 2000000\n"
            + "Adding note to Midi: [B@de0a01f 3000000\n" + "Adding note to Midi: [B@4c75cab9 0\n"
            + "Adding note to Midi: [B@1ef7fe8e 1000000\n"
            + "Adding note to Midi: [B@6f79caec 500000\n"
            + "Adding note to Midi: [B@67117f44 1500000\n"
            + "Adding note to Midi: [B@5d3411d 1000000\n"
            + "Adding note to Midi: [B@2471cca7 2000000\n"
            + "Adding note to Midi: [B@5fe5c6f 1500000\n"
            + "Adding note to Midi: [B@6979e8cb 2500000\n"
            + "Adding note to Midi: [B@763d9750 2000000\n"
            + "Adding note to Midi: [B@5c0369c4 3000000\n"
            + "Adding note to Midi: [B@2be94b0f 2500000\n"
            + "Adding note to Midi: [B@d70c109 3500000\n"
            + "Adding note to Midi: [B@17ed40e0 3000000\n"
            + "Adding note to Midi: [B@50675690 4000000\n"
            + "Adding note to Midi: [B@31b7dea0 3500000\n"
            + "Adding note to Midi: [B@3ac42916 4500000\n"
            + "Adding note to Midi: [B@47d384ee 4000000\n"
            + "Adding note to Midi: [B@2d6a9952 5000000\n"
            + "Adding note to Midi: [B@22a71081 4500000\n"
            + "Adding note to Midi: [B@3930015a 5500000\n"
            + "Adding note to Midi: [B@629f0666 2500000\n"
            + "Adding note to Midi: [B@1bc6a36e 3500000\n"
            + "Adding note to Midi: [B@1ff8b8f 3000000\n"
            + "Adding note to Midi: [B@387c703b 4000000\n"
            + "Adding note to Midi: [B@224aed64 3500000\n"
            + "Adding note to Midi: [B@c39f790 4500000\n"
            + "Adding note to Midi: [B@71e7a66b 4000000\n"
            + "Adding note to Midi: [B@2ac1fdc4 5000000\n"
            + "Adding note to Midi: [B@5f150435 4500000\n"
            + "Adding note to Midi: [B@1c53fd30 5500000\n" + "Closed\n", receiver.toString());
    }

    IMusicView getMidiViewHelper(Receiver receiver, Synthesizer synth) {
        IPiece piece = getPieceHelper();
        IViewPiece viewPiece = new ViewPiece(piece);
        //Don't use the creator because we want the special convenience constructor
        return new MidiViewImpl(synth, receiver, viewPiece);
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
}

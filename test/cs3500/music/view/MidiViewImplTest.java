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
        Receiver receiver;
        try {
            receiver = synthesizer.getReceiver();
        }
        catch (MidiUnavailableException e) {
            receiver = new MockReceiver();
            System.out.print("Error getting receiver during tests.");
        }
        IMusicView midi = getMidiViewHelper(receiver, synthesizer);
        midi.viewMusic();

        assertEquals("Adding note to Midi: [B@1e81f4dc 1000000\n"
            + "Adding note to Midi: [B@4d591d15 2000000\n"
            + "Adding note to Midi: [B@65ae6ba4 1500000\n"
            + "Adding note to Midi: [B@48cf768c 2500000\n"
            + "Adding note to Midi: [B@59f95c5d 2000000\n"
            + "Adding note to Midi: [B@5ccd43c2 3000000\n"
            + "Adding note to Midi: [B@4aa8f0b4 2500000\n"
            + "Adding note to Midi: [B@7960847b 3500000\n"
            + "Adding note to Midi: [B@6a6824be 3000000\n"
            + "Adding note to Midi: [B@5c8da962 4000000\n"
            + "Adding note to Midi: [B@512ddf17 1000000\n"
            + "Adding note to Midi: [B@2c13da15 2000000\n"
            + "Adding note to Midi: [B@77556fd 1500000\n"
            + "Adding note to Midi: [B@368239c8 2500000\n"
            + "Adding note to Midi: [B@9e89d68 2000000\n"
            + "Adding note to Midi: [B@3b192d32 3000000\n"
            + "Adding note to Midi: [B@16f65612 2500000\n"
            + "Adding note to Midi: [B@311d617d 3500000\n"
            + "Adding note to Midi: [B@7c53a9eb 3000000\n"
            + "Adding note to Midi: [B@ed17bee 4000000\n"
            + "Adding note to Midi: [B@2a33fae0 3500000\n"
            + "Adding note to Midi: [B@707f7052 4500000\n"
            + "Adding note to Midi: [B@11028347 4000000\n"
            + "Adding note to Midi: [B@14899482 5000000\n"
            + "Adding note to Midi: [B@21588809 4500000\n"
            + "Adding note to Midi: [B@2aae9190 5500000\n"
            + "Adding note to Midi: [B@2f333739 5000000\n"
            + "Adding note to Midi: [B@77468bd9 6000000\n"
            + "Adding note to Midi: [B@12bb4df8 5500000\n"
            + "Adding note to Midi: [B@4cc77c2e 6500000\n"
            + "Adding note to Midi: [B@7a7b0070 3500000\n"
            + "Adding note to Midi: [B@39a054a5 4500000\n"
            + "Adding note to Midi: [B@71bc1ae4 4000000\n"
            + "Adding note to Midi: [B@6ed3ef1 5000000\n"
            + "Adding note to Midi: [B@2437c6dc 4500000\n"
            + "Adding note to Midi: [B@1f89ab83 5500000\n"
            + "Adding note to Midi: [B@e73f9ac 5000000\n"
            + "Adding note to Midi: [B@61064425 6000000\n"
            + "Adding note to Midi: [B@7b1d7fff 5500000\n"
            + "Adding note to Midi: [B@299a06ac 6500000\n" + "Closed\n", receiver.toString());
    }

    @Test
    public void testMaryFile() {
        Synthesizer synthesizer = new MockSynthesizer();
        Receiver receiver;
        try {
            receiver = synthesizer.getReceiver();
        }
        catch (MidiUnavailableException e) {
            receiver = new MockReceiver();
            System.out.print("Error getting receiver during tests.");
        }
        IMusicView midi = getMidiViewMary(receiver, synthesizer);
        midi.viewMusic();
        assertEquals("Adding note to Midi: [B@3caeaf62 1000000\n"
            + "Adding note to Midi: [B@e6ea0c6 1400000\n"
            + "Adding note to Midi: [B@6a38e57f 1000000\n"
            + "Adding note to Midi: [B@5577140b 2400000\n"
            + "Adding note to Midi: [B@1c6b6478 1400000\n"
            + "Adding note to Midi: [B@67f89fa3 1800000\n"
            + "Adding note to Midi: [B@4ac68d3e 1800000\n"
            + "Adding note to Midi: [B@277c0f21 2200000\n"
            + "Adding note to Midi: [B@6073f712 2200000\n"
            + "Adding note to Midi: [B@43556938 2600000\n"
            + "Adding note to Midi: [B@3d04a311 2600000\n"
            + "Adding note to Midi: [B@7a46a697 4000000\n"
            + "Adding note to Midi: [B@5f205aa 2600000\n"
            + "Adding note to Midi: [B@6d86b085 3000000\n"
            + "Adding note to Midi: [B@75828a0f 3000000\n"
            + "Adding note to Midi: [B@3abfe836 3400000\n"
            + "Adding note to Midi: [B@2ff5659e 3400000\n"
            + "Adding note to Midi: [B@77afea7d 4000000\n"
            + "Adding note to Midi: [B@161cd475 4200000\n"
            + "Adding note to Midi: [B@532760d8 5800000\n"
            + "Adding note to Midi: [B@57fa26b7 4200000\n"
            + "Adding note to Midi: [B@5f8ed237 4600000\n"
            + "Adding note to Midi: [B@2f410acf 4600000\n"
            + "Adding note to Midi: [B@47089e5f 5000000\n"
            + "Adding note to Midi: [B@4141d797 5000000\n"
            + "Adding note to Midi: [B@68f7aae2 5800000\n"
            + "Adding note to Midi: [B@4f47d241 5800000\n"
            + "Adding note to Midi: [B@4c3e4790 6200000\n"
            + "Adding note to Midi: [B@38cccef 5800000\n"
            + "Adding note to Midi: [B@5679c6c6 6200000\n"
            + "Adding note to Midi: [B@27ddd392 6200000\n"
            + "Adding note to Midi: [B@19e1023e 6600000\n"
            + "Adding note to Midi: [B@7cef4e59 6600000\n"
            + "Adding note to Midi: [B@64b8f8f4 7400000\n"
            + "Adding note to Midi: [B@2db0f6b2 7400000\n"
            + "Adding note to Midi: [B@3cd1f1c8 9000000\n"
            + "Adding note to Midi: [B@3a4afd8d 7400000\n"
            + "Adding note to Midi: [B@1996cd68 7800000\n"
            + "Adding note to Midi: [B@3339ad8e 7800000\n"
            + "Adding note to Midi: [B@555590 8200000\n"
            + "Adding note to Midi: [B@6d1e7682 8200000\n"
            + "Adding note to Midi: [B@424c0bc4 8600000\n"
            + "Adding note to Midi: [B@3c679bde 8600000\n"
            + "Adding note to Midi: [B@16b4a017 9000000\n"
            + "Adding note to Midi: [B@8807e25 9000000\n"
            + "Adding note to Midi: [B@2a3046da 10600000\n"
            + "Adding note to Midi: [B@2a098129 9000000\n"
            + "Adding note to Midi: [B@198e2867 9400000\n"
            + "Adding note to Midi: [B@12f40c25 9400000\n"
            + "Adding note to Midi: [B@3ada9e37 9800000\n"
            + "Adding note to Midi: [B@5cbc508c 9800000\n"
            + "Adding note to Midi: [B@3419866c 10200000\n"
            + "Adding note to Midi: [B@63e31ee 10200000\n"
            + "Adding note to Midi: [B@68fb2c38 10600000\n"
            + "Adding note to Midi: [B@567d299b 10600000\n"
            + "Adding note to Midi: [B@2eafffde 12200000\n"
            + "Adding note to Midi: [B@59690aa4 10600000\n"
            + "Adding note to Midi: [B@6842775d 11000000\n"
            + "Adding note to Midi: [B@574caa3f 11000000\n"
            + "Adding note to Midi: [B@64cee07 11400000\n"
            + "Adding note to Midi: [B@1761e840 11400000\n"
            + "Adding note to Midi: [B@6c629d6e 11800000\n"
            + "Adding note to Midi: [B@5ecddf8f 11800000\n"
            + "Adding note to Midi: [B@3f102e87 12200000\n"
            + "Adding note to Midi: [B@27abe2cd 12200000\n"
            + "Adding note to Midi: [B@5f5a92bb 13800000\n"
            + "Adding note to Midi: [B@6fdb1f78 12200000\n"
            + "Adding note to Midi: [B@51016012 13800000\n" + "Closed\n", receiver.toString());
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
        }
        catch (FileNotFoundException e) {
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
}

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

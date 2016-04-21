package cs3500.music.util;

import cs3500.music.model.*;

/**
 * Allows for a composition to be created using a few functions offered by the model.
 * This makes it possible to read in text files to make songs.
 * <p>
 * Created by Sam Letcher on 3/23/2016.
 */
public class CompositionBuilder implements ICompositionBuilder<IPiece> {
    private final Piece piece;

    public CompositionBuilder() {
        this.piece = new Piece();
    }

    /**
     * Build the piece.
     *
     * @return piece
     */
    @Override
    public IPiece build() {
        return piece;
    }

    /**
     * Set the tempo.
     *
     * @param tempo The speed, in microseconds per beat
     * @return a builder
     */
    @Override
    public ICompositionBuilder<IPiece> setTempo(int tempo) {
        piece.setTempo(tempo);
        return this;
    }

    /**
     * Allow for the build of a composition by adding a note.
     *
     * @param start      The start time of the note, in beats
     * @param end        The end time of the note, in beats
     * @param instrument The instrument number (to be interpreted by MIDI)
     * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C)
     * @param volume     The volume (in the range [0, 127])
     * @return
     */
    public ICompositionBuilder<IPiece> addNote(int start, int end, int instrument, int pitch,
        int volume) {
        pitch = pitch - 12;
        Octave octave = new Octave(pitch / 12);
        Pitch charPitch = Pitch.values()[pitch % 12];
        int duration = end - start;
        if (duration < 1) {
            duration = 1;
            //support files that accidentally put an invalid duration.
        }

        piece.addNote(new Note(charPitch, octave, start, duration, instrument, volume));
        return this;
    }
}

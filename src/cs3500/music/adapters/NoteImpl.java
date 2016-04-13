package cs3500.music.adapters;

import cs3500.music.model.INote;
import cs3500.music.model.NoteComparator;
import cs3500.music.model.Octave;

/**
 * Implement the note adaption interface to allow for the correct use of the views.
 *
 * Created by Ben on 4/13/16.
 */
public class NoteImpl implements Note {
    private INote note;

    /**
     * Widely used constructor.
     *
     * @param octave to use
     * @param pitch to play
     * @param start beat
     * @param end beat
     */
    public NoteImpl(int octave, Pitch pitch, int start, int end) {
        int whichPitch = pitch.ordinal();
        cs3500.music.model.Pitch equalPitch = cs3500.music.model.Pitch.get(whichPitch);
        Octave octaveToUse = new Octave(octave);
        this.note = new cs3500.music.model.Note(equalPitch, octaveToUse, start, end + 1 - start);
    }

    public NoteImpl(INote note) {
        this.note = note;
    }

    /**
     * Constructor for all note fields.
     *
     * @param octave to use
     * @param pitch to use
     * @param start position (beat)
     * @param end position (beat)
     * @param instrument to play
     * @param volume to play at
     */
    public NoteImpl(int octave, Pitch pitch, int start, int end, int instrument, int volume) {
        int whichPitch = pitch.ordinal();
        cs3500.music.model.Pitch equalPitch = cs3500.music.model.Pitch.get(whichPitch);
        Octave octaveToUse = new Octave(octave);
        this.note = new cs3500.music.model.Note(equalPitch, octaveToUse, start, end + 1 -
            start, instrument, volume);
    }

    @Override
    public boolean sameSound(Note that) {
        return (getPitch() == that.getPitch() && getOctave() == that.getOctave());
    }

    @Override
    public int compareTo(Note o) {
        NoteComparator comparator = new NoteComparator();
        return comparator.compare(note, o.getNote());
    }

    @Override
    public int getEndBeat() {
        return note.getStart() + note.getDuration() - 1;
    }

    @Override
    public int getInstrument() {
        return note.getInstrument();
    }

    @Override
    public int getOctave() {
        return note.getOctave().getValue();
    }

    @Override
    public int getStartBeat() {
        return note.getStart();
    }

    @Override
    public int getValue() {
        int octave = note.getOctave().getValue();
        int pitchVal = note.getPitch().ordinal();
        return (octave + 1) * 12 + pitchVal;
    }

    @Override
    public int getVolume() {
        return note.getVolume();
    }

    @Override
    public Pitch getPitch() {
        int whichPitch = note.getPitch().ordinal();
        return Pitch.getPitch(whichPitch);
    }

    //Allow to see what note is contained via copy.
    @Override
    public INote getNote() {
        return note.copy();
    }
}

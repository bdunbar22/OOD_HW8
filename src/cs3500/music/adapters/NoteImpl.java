package cs3500.music.adapters;

import cs3500.music.model.INote;
import cs3500.music.model.NoteComparator;
import cs3500.music.model.Octave;

import java.util.Objects;

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

    /**
     * Make an adapted note from a given note.
     * @param note to use
     */
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
     * @throws IllegalStateException if instrument is not valid.
     */
    public NoteImpl(int octave, Pitch pitch, int start, int end, int instrument, int volume)
        throws IllegalStateException {
        int whichPitch = pitch.ordinal();
        cs3500.music.model.Pitch equalPitch = cs3500.music.model.Pitch.get(whichPitch);
        Octave octaveToUse = new Octave(octave);
        if(instrument > 16 || instrument < 0) {
            throw new IllegalStateException("Instrument out of valid range.");
        }
        this.note = new cs3500.music.model.Note(equalPitch, octaveToUse, start, end + 1 -
            start, instrument, volume);
    }

    /**
     * See if two notes have the same tone.
     *
     * @param that another Note
     * @return true if same tone. False otherwise.
     */
    @Override
    public boolean sameSound(Note that) {
        return (getPitch() == that.getPitch() && getOctave() == that.getOctave());
    }

    /**
     * Compare two notes.
     *
     * @param o note to compare.
     * @return integer result of comparison.
     */
    @Override
    public int compareTo(Note o) {
        NoteComparator comparator = new NoteComparator();
        return comparator.compare(note, o.getNote());
    }

    /**
     * Get the end beat of a note.
     *
     * @return integer ending beat.
     */
    @Override
    public int getEndBeat() {
        return note.getStart() + note.getDuration() - 1;
    }

    /**
     * Get the instrument being used.
     * @return instrument.
     */
    @Override
    public int getInstrument() {
        return note.getInstrument();
    }

    /**
     * Get the octave of a note.
     * @return octave of note.
     */
    @Override
    public int getOctave() {
        return note.getOctave().getValue();
    }

    /**
     * Get the start beat of a note.
     * @return start beat.
     */
    @Override
    public int getStartBeat() {
        return note.getStart();
    }

    /**
     * Get the note value.
     *
     * @return integer value
     */
    @Override
    public int getValue() {
        int octave = note.getOctave().getValue();
        int pitchVal = note.getPitch().ordinal();
        return (octave + 1) * 12 + pitchVal;
    }

    /**
     * Get the volume of a note.
     *
     * @return volume.
     */
    @Override
    public int getVolume() {
        return note.getVolume();
    }

    /**
     * Get the pitch of a note.
     *
     * @return pitch
     */
    @Override
    public Pitch getPitch() {
        int whichPitch = note.getPitch().ordinal();
        return Pitch.getPitch(whichPitch);
    }

    /**
     * Allow to see what note is contained via copy.
     * @return note
     */
    @Override
    public INote getNote() {
        return note.copy();
    }

    /**
     * Use the existing equals from the private note member.
     * @param check note
     * @return true of false
     */
    @Override
    public boolean equals(Object check) {
        if (!(check instanceof Note)) {
            throw new IllegalArgumentException(
                "Incompatible object being compared to a note.");
        }
        Note checkNote = (Note) check;
        return this.note.equals(checkNote.getNote());
    }

    /**
     * Use hash code from existing note member, which is also used for the equals override.
     * @return int hash code
     */
    @Override public int hashCode() {
        return this.note.hashCode();
    }
}

package cs3500.music.adapters;

import cs3500.music.model.INote;
import cs3500.music.model.NoteComparator;

/**
 * Implement the note adaption interface to allow for the correct use of the views.
 *
 * Created by Ben on 4/13/16.
 */
public class NoteImpl implements Note {
    private INote note;

    //TODO: finish implementing
    public NoteImpl(int octave, Pitch pitch, int start, int end) {
        //TODO: finish implementing
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

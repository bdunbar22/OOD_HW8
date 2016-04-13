package cs3500.music.model;

import java.util.Comparator;

/**
 * This comparators will allow for notes to be sorted by starting beat and then by the pitch and
 * octave that the note belongs to. This will help in keeping the piece of cs3500.music organized
 * for output.
 * <p>
 * Sorting priority: Start beat, Octave, Pitch. (Duration is not considered.)
 * <p>
 * Created by Ben on 3/4/16.
 */
public final class NoteComparator implements Comparator<INote> {
    public NoteComparator() {
        //Don't need to instantiate anything.
    }

    @Override
    public int compare(INote note1, INote note2) {
        //compare start beat first
        if (note1.getStart() < note2.getStart()) {
            return -1;
        } else if (note1.getStart() > note2.getStart()) {
            return 1;
        }
        //compare octave next
        if (note1.getOctave().getValue() < note2.getOctave().getValue()) {
            return -1;
        } else if (note1.getOctave().getValue() > note2.getOctave().getValue()) {
            return 1;
        }
        //compare pitch if same start beat and octave.
        final int pitchComp = note1.getPitch().compareTo(note2.getPitch());
        if (pitchComp > 0) {
            return 1;
        } else if (pitchComp < 0) {
            return -1;
        } else
            return 0;
    }
}

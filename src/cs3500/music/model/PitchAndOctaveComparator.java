package cs3500.music.model;

import java.util.Comparator;

/**
 * This comparator will allow for notes to be sorted by pitch and octave regardless of starting
 * beat or durations. It will be useful in determining the tone range of a set of cs3500.music.
 * <p>
 * Created by Ben on 3/4/16.
 */
public class PitchAndOctaveComparator implements Comparator<INote> {
    public PitchAndOctaveComparator() {
        //Don't need to instantiate anything.
    }

    @Override public int compare(INote note1, INote note2) {
        //compare octave
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

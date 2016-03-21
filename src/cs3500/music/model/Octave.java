package cs3500.music.model;

/**
 * Allow for a finite number of Octaves to be used for notes.
 * Humans can hear ten octaves of notes on average.
 * Octaves -9 through 99 will be allowed so that there is a max of 2 characters for the octave
 * when displaying the note.
 *
 * Octaves should be displayed using arabic numerals.
 *
 * Created by Ben on 3/2/16.
 */
public final class Octave {
    private Integer octave;

    private final static int low = -9;
    private final static int high = 99;

    Octave(final Integer octave) {
        if(octave < low || octave > high) {
            throw new IllegalArgumentException("This octave is out of bounds.");
        }
        this.octave = octave;
    }

    /**
     * Display the octave as a string.
     *
     * @return string to display
     */
    @Override
    public String toString() {
        return this.octave.toString();
    }

    /**
     * Get the next octave. Wrap at extreme.
     */
    public void nextOctave() {
        if(this.octave == high) {
            this.octave = low;
        }
        else {
            this.octave++;
        }
    }

    /**
     * return the int value of the octave
     */
    public int getValue() {
        return octave.intValue();
    }
}

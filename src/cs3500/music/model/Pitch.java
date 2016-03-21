package cs3500.music.model;

/**
 * Represent the 12 distinct pitches used in the Western System of cs3500.music.
 *
 * <i>Natural</i> notes are displayed as the capital letter for the given tone.
 * <i>Accidental</i> notes are supported as sharp notes. Both C# and D♭ represent the same tone,
 * but will be represented as C# in this application.
 *
 * Created by Ben on 3/2/16.
 */
public enum Pitch {
    C("C"),
    CSHARP("C#"),
    D("D"),
    DSHARP("D#"),
    E("E"),
    F("F"),
    FSHARP("F#"),
    G("G"),
    GSHARP("G#"),
    A("A"),
    ASHARP("A#"),
    B("B");

    private String displayString;

    Pitch(final String displayString) {
        this.displayString = displayString;
    }

    /**
     * Provides the display for a pitch.
     *
     * @return string display
     */
    @Override
    public String toString() {
        return this.displayString;
    }

    private final static Pitch[] pitches = Pitch.values();

    /**
     * Provide the next pitch in the list of pitches.
     * The Pitch.values() is set up in the correct order that pitches should be
     * displayed for cs3500.music.
     *
     * @return the next pitch in the scale
     */
    public Pitch nextPitch() {
        return pitches[(this.ordinal() + 1) % pitches.length];
    }
}

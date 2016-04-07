package cs3500.music.model;

/**
 * Give the interface for a note of cs3500.music.
 * Should have equals, hashcode and toString overridden.
 * At a minimum any note class implementing this interface should have a start, duration,
 * pitch and octave that can be get and set.
 * <p>
 * Created by Ben on 3/21/16.
 */
public interface INote {
    /**
     * Shows the display of a note.
     * <p>
     * Notes are displayed as a five-character string with the pitch and octave centered.
     * The pitch and octaves go from 2 characters (ex. C1) to 4 characters (ex. A#10).
     *
     * @return display string
     * @throws IllegalStateException if the tone is invalid.
     */
    @Override String toString();


    /**
     * Allow for a field to be incremented.
     * This can be used to make interesting changes to a piece by making changes to each note.
     *
     * @param field to increment
     */
    void increment(NoteField field);


    /**
     * Return a new note with equivalent fields.
     *
     * @return a copy of this note.
     */
    INote copy();

    /**
     * If the given note has the same pitch, octave and starting beat return true.
     * This is used to determine if a note is starting to play during the output creation.
     *
     * @param checkNote to compare against.
     * @return true if the tone is the same and the note is starting to play.
     */
    Boolean isStarting(INote checkNote);

    /**
     * If this note has the same pitch, octave and will be playing (but not starting) at
     * the start beat of checkNote then return true.
     * This is used to determine if a note is persisting to play during the output creation.
     *
     * @param checkNote to compare against.
     * @return true if the tone is the same and this note is persisting to play during start beat
     * of check note.
     */
    Boolean isPersisting(INote checkNote);

    int getStart();

    void setStart(final int start);

    int getDuration();

    void setDuration(final int duration);

    Pitch getPitch();

    void setPitch(final Pitch pitch);

    Octave getOctave();

    void setOctave(final Octave octave);

    int getInstrument();

    void setInstrument(final int instrument);

    int getVolume();

    void setVolume(final int volume);
}

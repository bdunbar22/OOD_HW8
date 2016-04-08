package cs3500.music.model;

import java.util.Objects;

/**
 * Represent a musical note
 * A note has a pitch in a certain octave. A note also has a beat it will start on and a length
 * of time it is played for.
 * <p>
 * The start must be an integer greater than or equal to 0.
 * the duration must be an integer greater than 0.
 * <p>
 * Created by Ben on 3/2/16.
 */
public final class Note implements INote {
    private Pitch pitch;
    private Octave octave;
    private int start;
    private int duration;
    private int instrument;
    private int volume;

    public Note(final Pitch pitch, final Octave octave, final int start,
        final int duration, final int instrument, final int volume) {
        if (start < 0) {
            throw new IllegalArgumentException(
                "Error. Note must start at beat 0 or later.");
        }
        if (duration < 1) {
            throw new IllegalArgumentException(
                "Error. Durations must be 1 beat or greater.");
        }
        this.pitch = pitch;
        this.octave = octave;
        this.start = start;
        this.duration = duration;
        this.instrument = instrument;
        this.volume = volume;
    }

    /**
     * Allow for the creation of a note. Must provide parameters, there should not be such a thing
     * as a blank note. Rests are simply the lack of any notes.
     *
     * @param pitch    the pitch of the note
     * @param octave   the octave the note is in
     * @param start    the start beat of the note
     * @param duration the length the note is audible for
     */
    public Note(final Pitch pitch, final Octave octave, final int start,
        final int duration) {
        if (start < 0) {
            throw new IllegalArgumentException(
                "Error. Note must start at beat 0 or later.");
        }
        if (duration < 1) {
            throw new IllegalArgumentException(
                "Error. Durations must be 1 beat or greater.");
        }
        this.pitch = pitch;
        this.octave = octave;
        this.start = start;
        this.duration = duration;
        this.instrument = 1;
        this.volume = 64;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(final int start) {
        this.start = start;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public Pitch getPitch() {
        return this.pitch;
    }

    public void setPitch(final Pitch pitch) {
        this.pitch = pitch;
    }

    public Octave getOctave() {
        return new Octave(this.octave.getValue());
    }

    public void setOctave(final Octave octave) {
        this.octave = octave;
    }

    public int getInstrument() {
        return this.instrument;
    }

    public void setInstrument(final int instrument) {
        this.instrument = instrument;
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(final int volume) {
        this.volume = volume;
    }

    /**
     * Shows the display of a note.
     * <p>
     * Notes are displayed as a five-character string with the pitch and octave centered.
     * The pitch and octaves go from 2 characters (ex. C1) to 4 characters (ex. A#10).
     *
     * @return display string
     * @throws IllegalStateException if the tone is invalid.
     */
    @Override public String toString() {
        String tone = this.pitch.toString() + this.octave.toString();
        switch (tone.length()) {
            case 2:
                return "  " + tone + " ";
            case 3:
                return " " + tone + " ";
            case 4:
                return " " + tone;
            default:
                throw new IllegalStateException("Invalid note tone found.");
        }
    }

    /**
     * Allow for a field to be incremented.
     * This can be used to make interesting changes to a piece by making changes to each note.
     *
     * @param field to increment
     */
    public void increment(NoteField field) {
        switch (field) {
            case PITCH:
                this.pitch = pitch.nextPitch();
                break;
            case OCTAVE:
                this.octave.nextOctave();
                break;
            case START:
                this.start += 1;
                break;
            case DURATION:
                this.duration += 1;
                break;
        }
    }

    @Override public boolean equals(Object check) {
        if (!(check instanceof Note)) {
            throw new IllegalArgumentException(
                "Incompatible object being compared to a note.");
        }
        Note checkNote = (Note) check;
        return (this == check) || (this.checkTone(checkNote) &&
            this.start == checkNote.start && this.duration == checkNote.duration);
    }

    @Override public int hashCode() {
        return Objects.hash(this.pitch.hashCode(), this.octave.getValue(), this.start,
            this.duration);
    }

    /**
     * Return a new note with equivalent fields.
     *
     * @return a copy of this note.
     */
    public INote copy() {
        return new Note(this.pitch, new Octave(this.octave.getValue()), this.start,
            this.duration, this.instrument, this.volume);
    }

    /**
     * If the given note has the same pitch, octave and starting beat return true.
     * This is used to determine if a note is starting to play during the output creation.
     *
     * @param checkNote to compare against.
     * @return true if the tone is the same and the note is starting to play.
     */
    public Boolean isStarting(INote checkNote) {
        return this.checkTone(checkNote) && this.start == checkNote.getStart();
    }

    /**
     * If this note has the same pitch, octave and will be playing (but not starting) at
     * the start beat of checkNote then return true.
     * This is used to determine if a note is persisting to play during the output creation.
     *
     * @param checkNote to compare against.
     * @return true if the tone is the same and this note is persisting to play during start beat
     * of check note.
     */
    public Boolean isPersisting(INote checkNote) {
        if (!this.checkTone(checkNote)) {
            return false;
        }
        int end = this.start + this.duration;
        return (this.start < checkNote.getStart() && end > checkNote.getStart());
    }

    /*
     * Returns true if the notes are the same tone.
     */
    private Boolean checkTone(INote checkNote) {
        return (this.pitch.hashCode() == checkNote.getPitch().hashCode()
            && this.octave.getValue() == checkNote.getOctave().getValue());
    }
}

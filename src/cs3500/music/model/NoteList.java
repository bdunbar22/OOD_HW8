package cs3500.music.model;

import javafx.util.Pair;

import java.util.*;

/**
 * Implements INoteList
 *
 * Contains a list of notes that can be played and edited.
 *
 * Created by Ben on 3/2/16.
 */
public class NoteList implements INoteList {
    private List<INote> notes;

    public NoteList() {
        notes = new ArrayList<>();
    }

    @Override
    public void addNote(final INote note) {
        _addNote(note);
    }

    @Override
    public void addNotes(final INote ... notes) {
        _addNotes(notes);
    }

    @Override
    public void addNotes(final List<INote> notes) { _addNotes(notes);}

    @Override
    public void changeNote(final INote old, final INote update) {
        _changeNote(old, update);
    }

    @Override
    public void removeNote(final INote old) {
        _removeNote(old);
    }

    @Override
    public Boolean member(final INote check) {
        return _member(check);
    }

    @Override
    public String musicOutput() {
        return _musicOutput();
    }

    @Override
    public List<INote> getNotes() {
        return _getNotes();
    }

    @Override
    public List<INote> getNotesInBeat(final int beat) { return _getNotesInBeat(beat); }

    @Override
    public int getLastBeat() {
        return _getLastBeat();
    }

    @Override
    public List<Pair<Octave, Pitch>> getToneRange() {
        return _getToneRange();
    }

    @Override
    public Map<Integer, List<INote>> getConsolidationMap() {
        return _getConsolidationMap();
    }

    private void _addNote(final INote note) {
        if (this.notes.contains(note)) {
            return;
        }
        this.notes.add(note);
    }

    private void _addNotes(final INote... notes) {
        for (INote note : notes) {
            this.addNote(note);
        }
    }

    private void _addNotes(final List<INote> notes) {
        for (INote note : notes) {
            this.addNote(note);
        }
    }

    private void _changeNote(final INote old, final INote update) {
        this.addNote(update);
        this.removeNote(old);
    }

    private void _removeNote(final INote old) {
        if (!this.notes.contains(old)) {
            throw new IllegalArgumentException("Note was not found");
        }
        this.notes.remove(old);
    }

    private Boolean _member(final INote check) {
        return notes.contains(check);
    }

    private String _musicOutput() {
        if (this.notes.size() == 0) {
            return "There are no musical notes to display.";
        }
        final int songLength = this._getLastBeat();
        List<Pair<Octave, Pitch>> toneRange = this.getToneRange();
        String output = "";

        //Get Header Row
        for (int i = 0; i < String.valueOf(songLength).length(); i++) {
            output += " ";
        }
        output += this.printNoteRange(toneRange) + "\n";

        //Get each subsequent row
        List<INote> notesInBeat = new ArrayList<>();
        for (int i = 0; i <= songLength; i++) {
            output += this.getRowNumber(i, songLength);

            notesInBeat = this.getNotesInBeat(i);

            for (Pair<Octave, Pitch> tone : toneRange) {
                output +=
                    displayForNote(notesInBeat, new Note(tone.getValue(), tone.getKey(), i, 1));
            }
            output += "\n";
        }
        return output;
    }

    private List<INote> _getNotes() {
        List<INote> listOfNotes = new ArrayList<>();
        for (INote note : this.notes) {
            listOfNotes.add(note.copy());
        }
        return listOfNotes;
    }

    private List<INote> _getNotesInBeat(final int beat) {
        List<INote> notesInBeat = new ArrayList<>();
        for (INote note : this.notes) {
            if (beat >= note.getStart() && beat < note.getStart() + note.getDuration()) {
                notesInBeat.add(note.copy());
            }
        }
        return notesInBeat;
    }

    private Map<Integer, List<INote>> _getConsolidationMap() {
        Map<Integer, List<INote>> data = new HashMap<>();
        for (int i = 0; i <= this.getLastBeat(); i++) {
            data.put(i, this.getNotesInBeat(i));
        }
        return data;
    }

    //For empty song return 0
    private int _getLastBeat() {
        int lastBeat = 0;
        if (notes.size() < 1) {
            return lastBeat;
        }
        for(INote note : notes) {
            if(note.getStart() + note.getDuration() - 1 > lastBeat) {
                lastBeat = note.getStart() + note.getDuration() - 1;
            }
        }
        return lastBeat;
    }

    /*
     * Return the formatted string to display the row number. The width of this string should be
     * equal to the width of the largest number beat in the piece of cs3500.music.
     */
    private String getRowNumber(int row, int songLength) {
        int spacesNeeded = String.valueOf(songLength).length() - String.valueOf(row).length();
        String output = "";
        for (int i = 0; i < spacesNeeded; i++) {
            output += " ";
        }
        return output + String.valueOf(row);
    }

    /*
     * Return a sorted list of all of the tones in the piece. Ex C0 - B10.
     * This list should contain all of the tones between the extremes.
     */
    private List<Pair<Octave, Pitch>> _getToneRange() {
        if (notes.size() == 0) {
            return Arrays.asList(new Pair<>(new Octave(4), Pitch.C));
        }
        Collections.sort(this.notes, new PitchAndOctaveComparator());
        //Identify the range.
        Pitch lowPitch = this.notes.get(0).getPitch();
        Octave lowOctave = this.notes.get(0).getOctave();
        Pitch highPitch = this.notes.get(this.notes.size() - 1).getPitch();
        Octave highOctave = this.notes.get(this.notes.size() - 1).getOctave();

        if (lowOctave.getValue() == highOctave.getValue()) {
            return this.getSingleOctaveRange();
        }

        List<Pair<Octave, Pitch>> range = new ArrayList<>();
        //Add (possibly partial) first octave
        for (Pitch i = lowPitch;
             i.compareTo(Pitch.values()[Pitch.values().length - 1]) < 0; i = i.nextPitch()) {
            range.add(new Pair<>(new Octave(lowOctave.getValue()), i));
        }
        //Add last pitch (next pitch would wrap so the highest should be added outside of loop)
        range.add(new Pair<>(new Octave(lowOctave.getValue()),
            Pitch.values()[Pitch.values().length - 1]));

        //Add full middle octaves
        for (int i = lowOctave.getValue() + 1; i < highOctave.getValue(); i++) {
            for (Pitch pitch : Pitch.values()) {
                range.add(new Pair<>(new Octave(i), pitch));
            }
        }

        //Add (possibly partial) last octave
        for (Pitch i = Pitch.values()[0]; i.compareTo(highPitch) < 0; i = i.nextPitch()) {
            range.add(new Pair<>(new Octave(highOctave.getValue()), i));
        }
        //Add high pitch (next pitch would wrap so the highest should be added outside of loop)
        range.add(new Pair<>(new Octave(highOctave.getValue()), highPitch));
        return range;
    }

    /*
     * Return a sorted list of all of the tones in a single octave piece.
     */
    private List<Pair<Octave, Pitch>> getSingleOctaveRange() {
        Collections.sort(this.notes, new PitchAndOctaveComparator());
        //Identify the range.
        Pitch lowPitch = this.notes.get(0).getPitch();
        Octave lowOctave = this.notes.get(0).getOctave();
        Pitch highPitch = this.notes.get(this.notes.size() - 1).getPitch();
        Octave highOctave = this.notes.get(this.notes.size() - 1).getOctave();

        //Note, this should never happen. If it does the code has been changed in a way that
        //is not valid.
        if (lowOctave.getValue() != highOctave.getValue()) {
            throw new IllegalArgumentException("Should not call getSingleOctaveRange for a piece "
                + "with more than one octave.");
        }

        //Add the pitches from the high pitch to the low pitch.
        List<Pair<Octave, Pitch>> range = new ArrayList<>();
        for (Pitch i = lowPitch; i.compareTo(highPitch) < 0; i = i.nextPitch()) {
            range.add(new Pair<>(new Octave(lowOctave.getValue()), i));
        }
        //Add high pitch (next pitch would wrap so the highest should be added outside of loop)
        range.add(new Pair<>(new Octave(highOctave.getValue()), highPitch));
        return range;
    }



    /*
     * For the range of octaves and pitches in the piece print out the header display for each.
     * Note: the tone range must be sorted for the display to be correct.
     */
    private String printNoteRange(List<Pair<Octave, Pitch>> toneRange) {
        String header = "";
        for (Pair<Octave, Pitch> tone : toneRange) {
            header += new Note(tone.getValue(), tone.getKey(), 0, 1).toString();
        }
        return header;
    }

    /*
     * Given a noteToCheck with a specific start beat, pitch and octave.
     * If a note is not present return "     "
     * If a note is starting to be played return "  X  "
     * If a note is persisting return "  |  "
     * Playing takes precedence over continuing (persisting)
     */
    private String displayForNote(List<INote> notesInBeat, INote noteToCheck) {
        for (INote note : notesInBeat) {
            if (note.isStarting(noteToCheck)) {
                return Output.START.toString();
            } else if (note.isPersisting(noteToCheck)) {
                return Output.PLAYING.toString();
            }
        }
        return Output.REST.toString();
    }
}

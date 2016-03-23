package cs3500.music.view;

import cs3500.music.model.INote;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * The Model View to connect the Model and the views without giving the views too much control over
 * the model.
 *
 * Created by Ben on 3/23/16.
 */
public interface IPieceView {
    /**
     * Return a copied list of the notes from this note list. The notes should be copied so that
     * this note list will not be affected.
     *
     * @return a new list of notes.
     */
    List<INote> getNotes();

    /**
     * Get a copied list of all of the notes that will be making sound at a given beat.
     *
     */
    List<INote> getNotesInBeat(final int beat);

    /**
     * Return the last beat that will contain audible music in a song.
     * Note a note at beat 0 with a duration of 1 will play on beat 0 only. So that is the last beat.
     * A note on beat 4 with a duration of 4 will play on beats 4, 5, 6, 7, so 7 is the last beat.
     * Note: a duration can not be 0.
     * Empty song has a last beat of 0 because nothing was played and the start beat of a song is
     * always 0 for the purposes of this project.
     *
     * @return the length of the song.
     */
    int getLastBeat();

    /**
     * This function will return a Map representation of the data that will be great for working
     * with when it comes to accessing beats fast. Each key will be a beat of the song and the List
     * of notes will contain all notes that will be audible at that beat. It will be a copied form
     * of the data. Note that notes with a duration of x will appear x times in the map, once for
     * beat they will be audible during. Keep in mind that editing one will not necessarily affect
     * the others because implementations may use copies for each time the note is added to the map
     * rather than a reference.
     *
     * @return consolidation map
     */
    Map<Integer, List<INote>> getConsolidationMap();

    /**
     * Get Tone Range for the piece
     */
    List<Pair<Octave, Pitch>> getToneRange();

    /**
     * Returns a vertical text representation of this musical piece.
     * A column of numbers representing the beats, printed right-justified and padded with
     * leading spaces, that is exactly as wide as necessary. (So if a piece is 999 beats long, it
     * gives three columns of characters; if it’s 1000 beats long, it uses four.)
     * A sequence of columns, each five characters wide, representing each pitch.
     * The first line prints out the names of the pitches, more-or-less centered within the
     * five-character column. I.e., "  F2 " and " G#3 " and " D#10".
     * Shows only columns from its lowest to its highest note. Each note-head is rendered
     * as an "  X  ", and each note-sustain is rendered as "  |  ". When a note is not played,
     * five spaces are rendered.
     * As a consequence: every line should be exactly the same length.
     * Every item, including the last one, ends in a newline.
     *
     * @return string to display
     */
    String musicOutput();
}

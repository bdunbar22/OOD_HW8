package cs3500.music.view;

import cs3500.music.model.INote;
import cs3500.music.model.IPiece;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * Implement the IViewPiece to provide a complete view model to be used by any view classes.
 * <p>
 * Created by Ben on 3/23/16.
 */
public class ViewPiece implements IViewPiece {
    private IPiece piece;
    private Map<Integer, List<INote>> map;

    /**
     * Constructor
     * @param piece to represent
     */
    public ViewPiece(IPiece piece) {
        this.piece = piece;
        this.map = piece.getConsolidationMap();
    }

    /**
     * Get the notes in the piece.
     * @return notes
     */
    @Override
    public List<INote> getNotes() {
        return piece.getNotes();
    }

    /**
     * Get the notes in a given beat.
     *
     * @param beat to play
     * @return notes
     */
    @Override
    public List<INote> getNotesInBeat(final int beat) {
        return map.get(beat);
    }

    /**
     * Get the last beat of a song.
     * @return int last beat
     */
    @Override
    public int
    getLastBeat() {
        return piece.getLastBeat();
    }

    /**
     * Get a consolidated form of data
     * @return data
     */
    @Override
    public Map<Integer, List<INote>> getConsolidationMap() {
        return map;
    }

    /**
     * The tone rage of the song.
     * @return tone range
     */
    @Override
    public List<Pair<Octave, Pitch>> getToneRange() {
        return piece.getToneRange();
    }

    /**
     * Get the music output text
     * @return text
     */
    @Override
    public String musicOutput() {
        return piece.musicOutput();
    }

    /**
     * Get the measure size for the song.
     * @return int measure size
     */
    @Override
    public int getMeasure() {
        return piece.getMeasure();
    }

    /**
     * Get the tempo of the song.
     * @return tempo
     */
    @Override
    public int getTempo() {
        return piece.getTempo();
    }
}

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

    public ViewPiece(IPiece piece) {
        this.piece = piece;
        this.map = piece.getConsolidationMap();
    }

    @Override public List<INote> getNotes() {
        return piece.getNotes();
    }

    @Override public List<INote> getNotesInBeat(final int beat) {
        return map.get(beat);
    }

    @Override public int getLastBeat() {
        return piece.getLastBeat();
    }

    @Override public Map<Integer, List<INote>> getConsolidationMap() {
        return map;
    }

    @Override public List<Pair<Octave, Pitch>> getToneRange() {
        return piece.getToneRange();
    }

    @Override public String musicOutput() {
        return piece.musicOutput();
    }

    @Override public int getMeasure() {
        return piece.getMeasure();
    }

    @Override public int getTempo() {
        return piece.getTempo();
    }
}

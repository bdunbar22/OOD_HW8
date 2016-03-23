package cs3500.music.view;

import cs3500.music.model.*;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * Created by Ben on 3/23/16.
 */
public class ViewPiece implements IViewPiece {
    private IPiece piece;

    public ViewPiece(IPiece piece) {
        this.piece = piece;
    }

    @Override
    public List<INote> getNotes() {
        return piece.getNotes();
    }

    @Override
    public List<INote> getNotesInBeat(final int beat) {
        return piece.getNotesInBeat(beat);
    }

    @Override
    public int getLastBeat() {
        return piece.getLastBeat();
    }

    @Override
    public Map<Integer, List<INote>> getConsolidationMap() {
        return piece.getConsolidationMap();
    }

    @Override
    public List<Pair<Octave, Pitch>> getToneRange() {
        return piece.getToneRange();
    }

    @Override
    public String musicOutput() {
        return piece.musicOutput();
    }

    @Override
    public int getMeasure() { return piece.getMeasure(); }

    @Override
    public int getBeat() { return piece.getBeat(); }
}

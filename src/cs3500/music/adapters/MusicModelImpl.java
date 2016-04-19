package cs3500.music.adapters;

import cs3500.music.model.*;
import cs3500.music.model.Pitch;
import cs3500.music.view.IViewPiece;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement the model interface necesarry to use the provided view classes from the other group.
 *
 * Created by Ben on 4/13/16.
 */
public class MusicModelImpl implements MusicModel {
    IPiece piece = new Piece();

    /**
     * Allow to create a new music model.
     */
    public MusicModelImpl() {
        this.piece = new Piece();
    }

    /**
     * Allow to create a music model from our existing model.
     * @param piece to use
     */
    public MusicModelImpl(IPiece piece) {
        this.piece = piece;
    }

    /**
     * Allow to create music model from our view model.
     * @param viewPiece to use.
     */
    public MusicModelImpl(IViewPiece viewPiece) {
        this.piece = new Piece();
        this.piece.addNotes(viewPiece.getNotes());
    }

    /**
     * Add notes to the model.
     *
     * @param note the Note to add
     */
    public void addNote(Note note) {
        piece.addNote(note.getNote());
    }

    /**
     * Get the notes at a beat.
     *
     * @param beat the beat at which to query
     * @return the notes.
     */
    public List<Note> getNotesAtBeat(int beat) {
        List<Note> result = new ArrayList<>();
        for (INote note : piece.getNotesInBeat(beat)) {
            result.add(new NoteImpl(note));
        }

        return result;
    }

    public MusicModel addMusic(MusicModel otherMusic, int startingBeat) {
        MusicModelImpl otherMusicImpl = (MusicModelImpl) otherMusic;
        IPiece otherPiece = otherMusicImpl.getPiece();
        otherPiece = otherPiece.changeField(NoteField.START, startingBeat);
        piece.parallelMerge(otherPiece);
        return this;
    }

    public IPiece getPiece() {return this.piece.copy(); }

    public MusicModel concatenateMusic(MusicModel otherMusic) {
        MusicModelImpl otherMusicImpl = (MusicModelImpl) otherMusic;
        IPiece otherPiece = otherMusicImpl.getPiece();
        piece.serialMerge(otherPiece);
        return this;
    }

    public MusicModel overlayMusic(MusicModel otherMusic) {
        return this.addMusic(otherMusic, 0);
    }

    public void removeNote(Note n) {
        piece.removeNote(n.getNote());
    }

    public int getNumberOfBeats() {
        //TODO: Make sure this is the last beat,
        return piece.getLastBeat() + 1;
    }

    public void setNumberOfBeats(int numBeats) {
        //Unnecessary for this implementation.
    }

    public int getTempo() {
        return piece.getTempo()/10;
    }

    public void setTempo(int tempo) {
        piece.setTempo(tempo);
    }

    public int getHighestNote() {
        List<Pair<Octave, Pitch>> toneList = piece.getToneRange();
        Pair<Octave, Pitch> note = toneList.get(toneList.size()-1);
        return note.getValue().ordinal() + (note.getKey().getValue() * 12) + 12;
    }

    public int getLowestNote() {
        List<Pair<Octave, Pitch>> toneList = piece.getToneRange();
        Pair<Octave, Pitch> note = toneList.get(0);
        return note.getValue().ordinal() + (note.getKey().getValue() * 12) + 12;
    }
}

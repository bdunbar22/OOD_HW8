package cs3500.music.adapters;

import cs3500.music.model.*;
import cs3500.music.model.Pitch;
import cs3500.music.view.IViewPiece;
import cs3500.music.view.ViewPiece;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement the model interface necessary to use the provided view classes from the other group.
 * Adapting the existing model to the provided model interface so that the functionality of the
 * existing model is used to allow the provided views to work with music.
 *
 * Created by Ben on 4/13/16.
 */
public class MusicModelImpl implements MusicModel {
    IPiece piece = new Piece();
    IViewPiece viewPiece;
    /**
     * Allow to create a new music model.
     */
    public MusicModelImpl() {
        this.piece = new Piece();
        this.viewPiece = new ViewPiece(piece);
    }

    /**
     * Allow to create a music model from our existing model.
     * @param piece to use
     */
    public MusicModelImpl(IPiece piece) {
        this.piece = piece;
        this.viewPiece = new ViewPiece(piece);
    }

    /**
     * Allow to create music model from our view model.
     * @param viewPiece to use.
     */
    public MusicModelImpl(IViewPiece viewPiece) {
        this.piece = new Piece();
        this.piece.addNotes(viewPiece.getNotes());
        this.viewPiece = viewPiece;
    }

    /**
     * Add notes to the model.
     *
     * @param note the Note to add
     */
    @Override
    public void addNote(Note note) {
        piece.addNote(note.getNote());
    }

    /**
     * Get the notes at a beat.
     *
     * @param beat the beat at which to query
     * @return the notes.
     */
    @Override
    public List<Note> getNotesAtBeat(int beat) {
        List<Note> result = new ArrayList<>();
        for (INote note : viewPiece.getNotesInBeat(beat)) {
            result.add(new NoteImpl(note));
        }

        return result;
    }

    /**
     * Add music to a music model.
     *
     * @param otherMusic   the other piece of music to add
     * @param startingBeat the beat at which to insert the other piece of music
     * @return resulting music model
     */
    @Override
    public MusicModel addMusic(MusicModel otherMusic, int startingBeat) {
        MusicModelImpl otherMusicImpl = (MusicModelImpl) otherMusic;
        IPiece otherPiece = otherMusicImpl.getPiece();
        otherPiece = otherPiece.changeField(NoteField.START, startingBeat);
        piece.parallelMerge(otherPiece);
        return this;
    }

    /**
     * Get the piece that is being used by the adapter as a copy.
     * @return piece of music.
     */
    public IPiece getPiece() {return this.piece.copy(); }

    /**
     * Concatenate two pieces of music.
     *
     * @param otherMusic another piece of music
     * @return resulting music model.
     */
    @Override
    public MusicModel concatenateMusic(MusicModel otherMusic) {
        MusicModelImpl otherMusicImpl = (MusicModelImpl) otherMusic;
        IPiece otherPiece = otherMusicImpl.getPiece();
        piece.serialMerge(otherPiece);
        return this;
    }

    /**
     * Combine music via parallel addition of music.
     *
     * @param otherMusic another piece of music
     * @return the model
     */
    @Override
    public MusicModel overlayMusic(MusicModel otherMusic) {
        return this.addMusic(otherMusic, 0);
    }

    /**
     * Remove a note from a song.
     *
     * @param n the note to remove
     */
    @Override
    public void removeNote(Note n) {
        piece.removeNote(n.getNote());
    }

    /**
     * Get the number of beats in a song.
     *
     * @return int number of beats.
     */
    @Override
    public int getNumberOfBeats() {
        //TODO: Make sure this is the last beat,
        return piece.getLastBeat() + 1;
    }

    /**
     * Set the number of beats in the song.
     *
     * @param numBeats the new number of beats in the piece of music
     */
    @Override
    public void setNumberOfBeats(int numBeats) {
        //Unnecessary for this implementation. This is never used...
    }

    /**
     * Get the tempo in the appropriate magnitude.
     *
     * @return int tempo
     */
    @Override
    public int getTempo() {
        return piece.getTempo()/10;
    }

    /**
     * Set the tempo
     *
     * @param tempo the new tempo
     */
    @Override
    public void setTempo(int tempo) {
        piece.setTempo(tempo);
    }

    /**
     * Get the highest note in the piece. This means as a tone (pitch & octave) and represented
     * as an integer, as expected from the adapted model.
     *
     * @return highest note as an integer representation.
     */
    @Override
    public int getHighestNote() {
        List<Pair<Octave, Pitch>> toneList = piece.getToneRange();
        Pair<Octave, Pitch> note = toneList.get(toneList.size()-1);
        return note.getValue().ordinal() + (note.getKey().getValue() * 12) + 12;
    }

    /**
     * Get the lowest note in the piece. This means as a tone (pitch & octave) and represented
     * as an integer, as expected from the adapted model.
     *
     * @return lowest note as an integer representation.
     */
    @Override
    public int getLowestNote() {
        List<Pair<Octave, Pitch>> toneList = piece.getToneRange();
        Pair<Octave, Pitch> note = toneList.get(0);
        return note.getValue().ordinal() + (note.getKey().getValue() * 12) + 12;
    }
}

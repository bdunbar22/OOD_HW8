package cs3500.music.adapters;

import cs3500.music.model.*;
import cs3500.music.model.Pitch;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 4/13/16.
 */
public class MusicModelImpl implements MusicModel {
    IPiece piece = new Piece();

    public void addNote(Note note) {
        piece.addNote(note.getNote());
    }

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
        return piece.getLastBeat();
    }

    public void setNumberOfBeats(int numBeats) {
        //TODO: this.
    }

    public int getTempo() {
        return piece.getTempo();
    }

    public void setTempo(int tempo) {
        piece.setTempo(tempo);
    }

    public int getHighestNote() {
        List<Pair<Octave, Pitch>> toneList = piece.getToneRange();
        Pair<Octave, Pitch> note = toneList.get(toneList.size());
        return note.getValue().ordinal() + (note.getKey().getValue() * 12) + 12;
    }

    public int getLowestNote() {
        List<Pair<Octave, Pitch>> toneList = piece.getToneRange();
        Pair<Octave, Pitch> note = toneList.get(0);
        return note.getValue().ordinal() + (note.getKey().getValue() * 12) + 12;
    }
}

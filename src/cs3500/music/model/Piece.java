package cs3500.music.model;

import java.util.List;

/**
 * Allow for a piece of cs3500.music to be realized. A piece of music has all of the abilities of
 * a set of notes. It can also perform macro operations on these notes to create new pieces of
 * music. A piece of music has a measure length and a current beat.
 *
 *
 * Created by Ben on 3/3/16.
 */
public final class Piece extends NoteList implements IPiece {
    private int measure;
    private int currentBeat;
    private int tempo;

    public Piece() {
        super();
        this.measure = 4;
        this.currentBeat = 0;
        this.tempo = 500000;
    }

    public void setMeasure(int measure) {
        this.measure = measure;
    }

    public int getMeasure() {
        return measure;
    }

    public void setBeat(int beat) {
        this.currentBeat = beat;
    }

    public int getBeat() {
        return currentBeat;
    }

    public void setTempo(final int tempo) {this.tempo = tempo; }

    public int getTempo() { return this.tempo; }

    @Override
    public IPiece serialMerge(IPiece piece) {
        IPiece builder = this.copy();
        final int lastNote = builder.getLastBeat();

        IPiece pieceToAdd = piece.changeField(NoteField.START, lastNote);
        List<INote> notesToAdd = pieceToAdd.getNotes();

        builder.addNotes(notesToAdd);
        return builder;
    }

    @Override
    public IPiece parallelMerge(IPiece piece) {
        IPiece builder = this.copy();
        List<INote> notesToAdd = piece.getNotes();
        builder.addNotes(notesToAdd);
        return builder;
    }

    @Override
    public IPiece changeField(NoteField field) {
        IPiece piece = this.copy();
        List<INote> notes = piece.getNotes();
        for(INote note : notes) {
            INote edit = note.copy();
            edit.increment(field);
            piece.changeNote(note, edit);
        }
        return piece;
    }

    @Override
    public IPiece changeField(NoteField field, final int num) {
        //get piece after one change
        IPiece piece = this.changeField(field);
        //change that piece the rest of the times needed
        for(int i = 1; i < num; i++) {
            piece = piece.changeField(field);
        }
        return piece;
    }

    @Override
    public IPiece reversePiece() {
        IPiece reversed = this.copy();
        final int lastBeat = reversed.getLastBeat();
        //get notes returns a deep copy of the list. But the changeNote function will use the
        //Notes equals functionality to find what to change. This will result in a correct change.
        for(INote note : reversed.getNotes()) {
            INote edit = note.copy();
            edit.setStart(lastBeat - note.getStart() - note.getDuration() + 1);
            reversed.changeNote(note, edit);
        }
        return reversed;
    }

    @Override
    public IPiece copy() {
        IPiece copy = new Piece();
        List<INote> notes = this.getNotes();
        copy.addNotes(notes);
        copy.setMeasure(this.getMeasure());
        copy.setTempo(this.getTempo());
        return copy;
    }
}

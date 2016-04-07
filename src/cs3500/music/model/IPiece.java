package cs3500.music.model;

/**
 * The interface for a piece of cs3500.music.
 * A piece of cs3500.music is a collection of notes that have a certain tone, start time and
 * duration.
 * <p>
 * A piece of cs3500.music is editable so that notes can be added and removed. It also supports
 * merges of pieces. Pieces can be combined in parallel (both start at the same time) or in
 * serial (one piece
 * is appended to the other). When pieces are combined the originals will remain and a new piece
 * will be returned.
 * <p>
 * Basic functionality is derived from INoteList, while complex functionality is also provided in
 * this interface.
 * <p>
 * Created by Ben on 3/2/16.
 */
public interface IPiece extends INoteList {
    /**
     * Allow for the serial merge of two pieces of cs3500.music.
     * pieceToAppend is added to the end of a copy of this piece with 1 beat of overlap.
     * A.k.a. the last beat of this piece and the start of piece to append are connected in the new
     * copy. Originals are not affected.
     *
     * @param pieceToAppend piece to append.
     * @return the merged piece
     */
    IPiece serialMerge(final IPiece pieceToAppend);

    /**
     * Allow for the parallel merge of two pieces of cs3500.music. Copy of all notes in
     * pieceToCombine is
     * added to a copy of all of the notes in this piece and the result is returned.
     *
     * @param pieceToCombine will be merged so the new piece plays both at once.
     * @return the merged piece
     */
    IPiece parallelMerge(final IPiece pieceToCombine);

    /**
     * Returns a new piece with a macro run on the specified Note field. See NoteField enum for
     * options. The field will be incremented on every note in the piece.
     *
     * @param field to change
     * @return the new and improved piece.
     */
    IPiece changeField(NoteField field);

    /**
     * Allows for a more powerful change field operation by giving the number of times to
     * increment the field.
     *
     * @param field the field to change
     * @param num   the number of times to increment the field.
     * @return the new and improved piece.
     */
    IPiece changeField(NoteField field, final int num);

    /**
     * Allow for a song to be played backwards.
     * This should be implemented so that the piece of cs3500.music can be played exactly in
     * reverse. The last beat of the song should become beat 0, and going backwards the start
     * beats of the
     * notes should now be where they ended previously.
     *
     * @return the new and improved piece.
     */
    IPiece reversePiece();


    /**
     * Allow for a song to be copied.
     * This should be implemented so that the piece of cs3500.music returned is a deep copy of the
     * original all of the notes will be equal but the notes and list of notes will not be
     * references of
     * each other. When copying an empty piece a new empty piece will be returned.
     *
     * @return the copy.
     */
    IPiece copy();

    /**
     * Get the measure length of a piece of music
     */
    int getMeasure();

    /**
     * Set the measure length of a piece of music. Default is 4.
     *
     * @param measure to set
     */
    void setMeasure(int measure);

    /**
     * get the tempo of the song.
     *
     * @return tempo
     */
    int getTempo();

    /**
     * Every song should have a tempo.
     *
     * @param tempo to set
     */
    void setTempo(final int tempo);
}

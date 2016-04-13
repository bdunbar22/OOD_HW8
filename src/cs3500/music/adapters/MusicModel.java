package cs3500.music.adapters;

import java.util.List;

/**
 * Interface for the model adapter. Allows for the translation between the model needed by the
 * view classes and the existing model, IPiece, in the model package.
 */
public interface MusicModel {
  /**
   * Adds a copy of the specified Note to this piece of music.
   *
   * @param note the Note to add
   * @throws NullPointerException if note is null
   */
  void addNote(Note note);

  /**
   * Get a list containing a copy of all of the notes that exist at a specified beat.
   *
   * @param beat the beat at which to query
   * @return a list containing a copy of all the notes that exist at the beat
   * @throws IllegalArgumentException if beat is invalid
   */
  List<Note> getNotesAtBeat(int beat);

  /**
   * Add another piece of music to this piece of music, inserting the other piece of music starting
   * at the specified beat in this piece of music. Return the new, resulting piece of music.
   *
   * @param otherMusic   the other piece of music to add
   * @param startingBeat the beat at which to insert the other piece of music
   * @return a new MusicModel, the result of merging this piece of music and the other music
   * @throws NullPointerException     if the other music is null
   * @throws IllegalArgumentException if the starting beat is invalid
   */
  MusicModel addMusic(MusicModel otherMusic, int startingBeat);

  /**
   * Add another piece of music to the end of this piece of music. Return the new, resulting piece
   * of music.
   *
   * @param otherMusic another piece of music
   * @return a new MusicModel, the result of concatenating this piece of music and the other music
   * @throws NullPointerException if the other music is null
   */
  MusicModel concatenateMusic(MusicModel otherMusic);

  /**
   * Overlay another piece of music onto this piece of music. Return the new, resulting piece of
   * music.
   *
   * @param otherMusic another piece of music
   * @return a new MusicModel, the result of overlaying this piece of music and the other music
   * @throws NullPointerException if the other music is null
   */
  MusicModel overlayMusic(MusicModel otherMusic);

  /**
   * Remove the specified note from this piece of music. If the same note exists multiple times in
   * the same piece of music, only one will be removed per call to this method.
   *
   * @param n the note to remove
   * @throws NullPointerException     if note is null
   * @throws IllegalArgumentException if note does not exist in music
   */
  void removeNote(Note n);

  /**
   * Get the length of this piece of music in beats
   *
   * @return the total number of beats in this piece of music
   */
  int getNumberOfBeats();

  /**
   * Change the length of this piece of music to the specified number of beats, extending or
   * removing beats as necessary.
   *
   * @param numBeats the new number of beats in the piece of music
   * @throws IllegalArgumentException if there are notes that would be modified/removed by changing
   *                                  the total number of beats.
   * @throws IllegalArgumentException if numBeats is < 1
   */
  void setNumberOfBeats(int numBeats);

  /**
   * Get the tempo of this MusicModel
   *
   * @return the tempo of this MusicModel
   */
  int getTempo();

  /**
   * Set the tempo of this MusicModel
   *
   * @param tempo the new tempo
   * @throws IllegalArgumentException if tempo is invalid
   */
  void setTempo(int tempo);

  /**
   * Return the integer value of the highest note in the piece. If this piece has no notes, return
   * the value of C1 (24).
   *
   * @return int value of highest note
   */
  int getHighestNote();

  /**
   * Return the integer value of the lowest note in the piece. If this piece has no notes, return
   * the value of C1 (24).
   *
   * @return int value of lowest note
   */
  int getLowestNote();
}

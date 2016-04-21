package cs3500.music.adapters;

import cs3500.music.model.INote;

/**
 * Interface to represent Notes in a MusicModel
 */
public interface Note extends Comparable<Note> {
  /** Get the numeric value of this Note. Numeric value is calculated using the following
   * formula: (octave + 1)*12 + numeric value of pitch. C4 therefore has value 60.
   *
   * @return the numeric value of this note
   */
  int getValue();

  /**
   * Get this notes octave.
   *
   * @return this notes octave
   */
  int getOctave();

  /**
   * Get this notes Pitch
   *
   * @return this notes Pitch
   */
  Pitch getPitch();

  /**
   * Get the starting beat of this note
   *
   * @return the starting beat of this note
   */
  int getStartBeat();

  /**
   * Get the ending beat of this note
   *
   * @return the ending beat of this note
   */
  int getEndBeat();

  /**
   * Get the instrument of this note
   *
   * @return the instrument of this note
   */
  int getInstrument();

  /**
   * Get the volume of this note
   *
   * @return the volume of this note
   */
  int getVolume();

  /**
   * Determine whether that Note and this Note have the same sound. Two Notes have the same sound
   * if they have the same pitch and the same octave.
   *
   * @param that another Note
   * @return whether or not this and that Note have the same sound
   * @throws NullPointerException if that is null
   */
  boolean sameSound(Note that);

  /**
   * Allow for the return of the stored INote.
   * @return note.
   */
  INote getNote();
}

package cs3500.music.adapters;


/**
 * Factory class for creation of models and notes
 */
public class ModelFactory {

  /**
   * Create an empty MusicModel
   *
   * @return a new MusicModel
   */
  public static MusicModel createModel() {
    return new MusicModelImpl();
  }

  /**
   * Create a new Note from the specified parameters
   *
   * @param octave    octave value
   * @param pitch     pitch value
   * @param startBeat starting beat
   * @param endBeat   ending beat
   * @return a new Note
   */
  public static Note createNote(int octave, Pitch pitch, int startBeat, int endBeat) {
    return new NoteImpl(octave, pitch, startBeat, endBeat);
  }


  /**
   * Create a new Note from the specified parameters
   *
   * @param octave     octave value
   * @param pitch      pitch value
   * @param startBeat  starting beat
   * @param endBeat    ending beat
   * @param instrument instrument number
   * @param volume     volume value
   * @return a new Note
   */
  public static Note createNote(int octave, Pitch pitch, int startBeat, int endBeat, int
      instrument, int volume) {
    return new NoteImpl(octave, pitch, startBeat, endBeat, instrument, volume);
  }

  /**
   * Create a new Note from the specified parameters
   *
   * @param value     the numeric value of the new note
   * @param startBeat starting beat
   * @param endBeat   ending beat
   * @return a new Note
   */
  public static Note createNote(int value, int startBeat, int endBeat) {
    return createNote((value / 12) - 1, Pitch.getPitch(value % 12), startBeat, endBeat);
  }

  /**
   * Create a new Note from the specified parameters
   *
   * @param value      the numeric value of the new note
   * @param startBeat  starting beat
   * @param endBeat    ending beat
   * @param instrument instrument number
   * @param volume     volume value
   * @return a new Note
   */
  public static Note createNote(int value, int startBeat, int endBeat, int instrument, int
      volume) {
    return createNote((value / 12) - 1, Pitch.getPitch(value % 12), startBeat, endBeat, instrument,
        volume);
  }

}

package cs3500.music.adapters;

import cs3500.music.util.ICompositionBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Builder class for MusicModel
 */
public class MusicModelBuilder implements ICompositionBuilder<MusicModel> {

  private int tempo = 1;
  private List<Note> notes = new ArrayList<>();

  /**
   * Constructs an actual composition, given the notes that have been added
   *
   * @return The new composition
   * @throws IllegalArgumentException if tempo is invalid
   */
  @Override
  public MusicModel build() {
    MusicModel model = ModelFactory.createModel();
    model.setTempo(tempo);
    for (Note n : notes) {
      model.addNote(n);
    }
    return model;
  }

  /**
   * Sets the tempo of the piece
   *
   * @param tempo The speed, in microseconds per beat
   * @return This builder
   */
  @Override
  public ICompositionBuilder<MusicModel> setTempo(int tempo) {
    this.tempo = tempo;
    return this;
  }

  /**
   * Adds a new note to the piece
   *
   * @param start      The start time of the note, in beats
   * @param end        The end time of the note, in beats
   * @param instrument The instrument number (to be interpreted by MIDI)
   * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C on a
   *                   piano)
   * @param volume     The volume (in the range [0, 127])
   */
  @Override
  public ICompositionBuilder<MusicModel> addNote(int start, int end, int instrument, int pitch,
                                                int volume) {
    try {
      Note noteToAdd = new NoteImpl(pitch / 12 - 1, Pitch.getPitch(pitch % 12), start, end - 1,
          instrument, volume);
      notes.add(noteToAdd);
    } catch (IllegalArgumentException e) {
      // do nothing and continue
    }
    return this;
  }

}

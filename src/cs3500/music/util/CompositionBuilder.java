package cs3500.music.util;

import cs3500.music.model.*;

/**
 * Created by Sam Letcher on 3/23/2016.
 */
public class CompositionBuilder implements ICompositionBuilder<IPiece> {
  Piece piece;

  public CompositionBuilder() {
    this.piece = new Piece();
  }

  public IPiece build() {
    return piece;
  }

  public ICompositionBuilder<IPiece> setTempo(int tempo) {
    piece.setTempo(tempo);
    return this;
  }
//TODO: TEST THIS
  public ICompositionBuilder<IPiece> addNote(int start, int end, int instrument, int pitch, int
    volume) {
    pitch = pitch - 12;
    Octave octave = new Octave(pitch/12);
    Pitch charPitch = Pitch.values()[pitch % 12];
    int duration = end - start;

    piece.addNote(new Note(charPitch, octave, start, duration, instrument, volume));
    return this;
  }
}

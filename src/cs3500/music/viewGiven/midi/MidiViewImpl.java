package cs3500.music.viewGiven.midi;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cs3500.music.adapters.MusicModel;
import cs3500.music.adapters.Note;
import cs3500.music.viewGiven.PlayableMusicView;

/**
 * A playable view using MIDI
 */
public class MidiViewImpl implements PlayableMusicView {

  private final Sequencer sequencer;
  private final Sequence sequence;
  private final Map<Integer, Integer> instrumentChannels;
  private final MusicModel model;
  private Track track;

  /**
   * Constructs a midi view from the specified model and sequencer
   *
   * @param model     a MusicModel to construct a view for
   * @param sequencer a Sequencer to construct a view for
   * @throws NullPointerException if model or sequence are null
   */
  public MidiViewImpl(MusicModel model, Sequencer sequencer) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(sequencer);
    this.sequencer = sequencer;
    this.instrumentChannels = new HashMap<>();
    this.model = model;

    Sequence tempSeq;
    try {
      tempSeq = new Sequence(Sequence.PPQ, 1);
    } catch (InvalidMidiDataException | NullPointerException e) {
      throw new IllegalStateException(e);
    }
    this.sequence = tempSeq;
    this.track = tempSeq.createTrack();
    try {
      this.sequencer.open();
      sequencer.setSequence(sequence);
    } catch (InvalidMidiDataException | MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.rebuildSequencer();
  }

  /**
   * Constructs a MidiViewImpl from the specified model, using the default sequencer.
   *
   * @param model a MusicModel to construct view for
   * @throws MidiUnavailableException if default sequencer cannot be accessed
   * @throws NullPointerException     if model is null
   */
  public MidiViewImpl(MusicModel model)
      throws MidiUnavailableException {
    this(model, MidiSystem.getSequencer());
  }

  /**
   * Rebuild the sequencer from the model's data.
   */
  private synchronized void rebuildSequencer() {

    try {

      // build map from instrument number to midi channel
      buildInstrumentMap();

      // open sequencer
      sequence.deleteTrack(track);
      track = sequence.createTrack();

      // set tempo of sequencer to models specified sequencer
      sequencer.setTempoInMPQ(model.getTempo());

      // set midi channels to play specified instrument from base bank (0)
      for (Map.Entry<Integer, Integer> entry : instrumentChannels.entrySet()) {
        track.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, entry.getValue(),
            entry.getKey() - 1, 0), 0));
      }

      // add notes from model to track
      for (int i = 0; i < model.getNumberOfBeats(); i++) {
        for (Note n : model.getNotesAtBeat(i)) {
          if (i == n.getStartBeat()) {
            ShortMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrumentChannels.get(n
                .getInstrument()), n.getValue(), n.getVolume());
            ShortMessage end = new ShortMessage(ShortMessage.NOTE_OFF, instrumentChannels.get(n
                .getInstrument()), n.getValue(), n.getVolume());
            track.add(new MidiEvent(start, n.getStartBeat()));
            track.add(new MidiEvent(end, n.getEndBeat() + 1));
          }
        }
      }

      // set the sequence of the sequencer
      sequencer.setSequence(sequence);

    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  /**
   * Render the given MusicModel by playing the music.
   *
   * @throws IllegalStateException number of instruments > 16
   */
  @Override
  public synchronized void render() {
    this.rebuildSequencer();
    this.setBeat(0);
    this.play();
  }

  /**
   * Refresh/redraw this MusicView to correspond to new state of model.
   *
   * @throws IllegalStateException if view cannot be refreshed
   */
  @Override
  public synchronized void refresh() {
    this.rebuildSequencer();
  }


  /**
   * Build the map from MIDI instrument number to channel number for this view.
   *
   * @throws IllegalStateException if more than 16 channels are necessary
   */
  synchronized void buildInstrumentMap() {

    instrumentChannels.clear();

    int channelCounter = 0;

    for (int i = 0; i < model.getNumberOfBeats(); i++) {
      for (Note n : model.getNotesAtBeat(i)) {
        if (!instrumentChannels.containsKey(n.getInstrument())) {
          if (channelCounter > 15) {
            throw new IllegalStateException("Exceeded number of instruments (16)");
          }
          instrumentChannels.put(n.getInstrument(), channelCounter);
          channelCounter++;
        }
      }
    }
  }

  /**
   * Pause playback and set the playback beat. Automatically set to max or min values if input
   * value is too high or too low.
   *
   * @param beat a beat in the piece
   */
  @Override
  public void setBeat(double beat) {
    this.sequencer.stop();
    if (sequencer.getTickLength() < beat) {
      beat = sequencer.getTickLength();
    }
    if (beat < 0) {
      beat = 0;
    }
    this.sequencer.setTickPosition((long) beat);
  }

  /**
   * Begin playback from this view, from the current beat.
   */
  @Override
  public synchronized void play() {
    setBeat(currentBeat());
    this.rebuildSequencer();
    this.sequencer.start();
  }

  /**
   * Pause playback.
   */
  @Override
  public void pause() {
    this.sequencer.stop();
  }

  /**
   * Determine whether this view is currently playing music
   *
   * @return whehter this view is currently playing
   */
  @Override
  public boolean isPlaying() {
    return sequencer.isRunning();
  }

  /**
   * Get the current position in playback from this view.
   *
   * @return the current position in playback from this view.
   */
  @Override
  public double currentBeat() {
    return this.sequencer.getTickPosition();
  }


  /**
   * Determine whether the input model matches the model this view is drawing.
   *
   * @param model a MusicModel
   * @return whether or not the input model and the model being viewed are the same object
   * @throws NullPointerException if model is null
   */
  @Override
  public boolean correspondsTo(MusicModel model) {
    return Objects.requireNonNull(model, "Model must not be null") == this.model;
  }
}

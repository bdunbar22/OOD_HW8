package cs3500.music.view;

import cs3500.music.model.INote;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;

import javax.sound.midi.*;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IMusicView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private IViewPiece viewPiece;
  private int currentBeat;

  public MidiViewImpl(IViewPiece viewPiece) {
    this.viewPiece = viewPiece;
    this.currentBeat = 0;
    Synthesizer trySynth;
    Receiver tryReceive;
    try {
      trySynth = MidiSystem.getSynthesizer();
      tryReceive = trySynth.getReceiver();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      trySynth = null;
      tryReceive = null;
    }
    this.synth = trySynth;
    this.receiver = tryReceive;
    try {
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  //Allow for mock classes to be sent for testing
  public MidiViewImpl(Synthesizer synth, Receiver receiver, IViewPiece viewPiece) {
    this.synth = synth;
    this.receiver = receiver;
    this.viewPiece = viewPiece;
    try {
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update the view piece being used by the midi player
   */
  @Override
  public void updateViewPiece(IViewPiece viewPiece) {
    this.viewPiece = viewPiece;
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */

  public void playNote(INote note) throws InvalidMidiDataException {
    //Standard Midi tempo is 50000. We will just use the tempo from the piece
    int tempoModifier = viewPiece.getTempo();
    long startTime = (note.getStart() * tempoModifier) + this.synth.getMicrosecondPosition();
    long endTime = (startTime + (note.getDuration() * tempoModifier));
    int pitch = this.getMidiPitch(note);
    int instrument = note.getInstrument();
    int volume = note.getVolume();

    //Midi supports pitch values 0 - 127
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument, pitch, volume);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument, pitch, volume);
    this.receiver.send(start, startTime);
    this.receiver.send(stop, endTime);
  }

  /**
   * An audible representation of music is given in this view.
   */
  @Override
  public void viewMusic() {
    for (INote note : viewPiece.getNotes()) {
      try {
        playNote(note);
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
    }
    try {
      Thread.sleep((viewPiece.getLastBeat()+1) * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.receiver.close();
  }

  public void viewMusicPerBeat() {
    for (INote note : viewPiece.getNotesInBeat(currentBeat)) {
      if (note.getStart() == currentBeat){
        try {
          playNote(note);
        }
        catch (InvalidMidiDataException e) {
          e.printStackTrace();
        }
      }
    }
    if (currentBeat >= viewPiece.getLastBeat()) {
      this.receiver.close();
    }
  }

  /**
   * This function converts the note's pitch and duration to a Midi pitch and duration.
   * Midi will accept ranges 0 - 127.
   * If midi pitch out of range then give the closest valid pitch so the song still plays.
   * @return the number Midi will be able to interpret
   */
  private int getMidiPitch(INote note) {
    Pitch pitch = note.getPitch();
    Octave octave = note.getOctave();

    int midiPitch = pitch.ordinal() + (octave.getValue() * 12) + 12;
    if(midiPitch < 0) {
      midiPitch = 0;
    }
    if(midiPitch > 127) {
      midiPitch = 127;
    }
    return midiPitch;
  }
}

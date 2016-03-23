package cs3500.music.view;

import cs3500.music.model.INote;
import cs3500.music.model.IPiece;

import javax.sound.midi.*;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements IMusicView {
  private final Synthesizer synth;
  private final Receiver receiver;
  private IViewPiece viewPiece;

  public MidiViewImpl(IViewPiece viewPiece) {
    this.viewPiece = viewPiece;
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
    int pitch = note.getMidiPitch();
    int instrument = note.getInstrument();
    int volume = note.getVolume();

    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument, pitch, volume);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument, pitch, volume);
    this.receiver.send(start, startTime);
    this.receiver.send(stop, endTime);
  }

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
    System.out.print("DONE");
    this.receiver.close();
  }
}

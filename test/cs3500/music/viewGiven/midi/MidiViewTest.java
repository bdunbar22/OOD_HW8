package cs3500.music.viewGiven.midi;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import cs3500.music.model.ModelFactory;
import cs3500.music.model.MusicModel;
import cs3500.music.model.impl.NoteImpl;
import cs3500.music.model.Pitch;
import cs3500.music.model.impl.MusicModelBuilder;
import cs3500.music.model.impl.MusicModelImpl;
import cs3500.music.util.MusicReader;
import cs3500.music.viewGiven.MusicView;
import cs3500.music.viewGiven.PlayableMusicView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing MIDI view of MusicModel
 */
public class MidiViewTest {

  private static final double DELTA = 10e-6;
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private static List<String> initializationOperations() {
    List<String> ops = new ArrayList<>();
    ops.add("OPENED");
    ops.add("SEQUENCE SET");
    ops.add("SET MPQ TEMPO");
    ops.add("SEQUENCE SET");
    return ops;
  }

  private static MusicModel mll()
      throws FileNotFoundException {
    return MusicReader.parseFile(new FileReader(new File("res/mary-little-lamb.txt")),
        new MusicModelBuilder());
  }

  private static MusicModel model() {
    MusicModel model = ModelFactory.createModel();
    model.addNote(new NoteImpl(1, Pitch.C, 0, 1, 3, 64));
    model.addNote(new NoteImpl(1, Pitch.CSHARP, 1, 2, 2, 100));
    model.addNote(new NoteImpl(1, Pitch.D, 4, 7, 10, 24));
    return model;
  }

  private static void assertStringListEquals(List<String> expected, List<String> actual) {
    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), actual.get(i));
    }
  }

  @Test
  public void multipleInstrumentsTest() {
    // instrument 7 is channel 1
    // instrument 4 is channel 2
    // instrument 5 is channel 3
    String modelText =
        "tempo 70000\n" +
            "note 0 1 7 60 127\n" +
            "note 1 2 4 61 127\n" +
            "note 2 3 7 60 127\n" +
            "note 3 4 5 62 127";
    MusicModel model = MusicReader.parseFile(new StringReader(modelText), new MusicModelBuilder());
    List<String> operations = new ArrayList<>();
    Sequencer sequencer = new MockSequencer(operations);
    MusicView view = new MidiViewImpl(model, sequencer);
    view.render();

    // make sure instrument channels are set correctly
    assertEquals(1, linesWithString(operations, "BEAT 0 PROGRAM CHANGE: ch=1 prog=6"));
    assertEquals(1, linesWithString(operations, "BEAT 0 PROGRAM CHANGE: ch=2 prog=3"));
    assertEquals(1, linesWithString(operations, "BEAT 0 PROGRAM CHANGE: ch=3 prog=4"));

    // make sure notes of an instrument correspond to the correct channel
    assertEquals(4, linesWithString(operations, ": ch=1 note=60 vol=127"));
    assertEquals(2, linesWithString(operations, ": ch=2 note=61 vol=127"));
    assertEquals(2, linesWithString(operations, ": ch=3 note=62 vol=127"));
  }

  @Test
  public void mllTest()
      throws FileNotFoundException {
    MusicModel model = mll();
    List<String> operations = new ArrayList<>();
    Sequencer sequencer = new MockSequencer(operations);
    MusicView view = new MidiViewImpl(model, sequencer);
    view.render();

    // check to make sure that for every note on there is a corresponding note off
    List<String> ons = new ArrayList<>(); // hold on to the channel, pitch, volume of ONs
    int offsWithoutOns = 0; // count the number of OFFs that do not have an ON
    for (String line : operations) {
      if (line.contains("NOTE ")) {
        if (line.contains(" ON:")) {
          ons.add(line.substring(line.indexOf("ch=")));
        } else {
          if (!ons.remove(line.substring(line.indexOf("ch=")))) {
            offsWithoutOns++;
          }
        }
      }
    }
    assertEquals(0, ons.size()); // should be no ONs without an OFF
    assertEquals(0, offsWithoutOns); // should be no OFFs without an ON

    int[] onsAtBeat = {2, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 0, 2, 0, 1, 0, 1, 0, 0, 0,
        2, 0, 1, 0, 1, 0, 0, 0, 2, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1,
        0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] offsAtBeat = {0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 2, 0, 0, 1, 0, 1, 0, 0, 0,
        2, 0, 2, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1,
        0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2};
    // 52 + index gives note value
    int[] onsAtValue = {1, 0, 0, 7, 0, 0, 0, 0, 3, 0, 10, 0, 11, 0, 0, 2};
    int[] offsAtValue = {1, 0, 0, 7, 0, 0, 0, 0, 3, 0, 10, 0, 11, 0, 0, 2};
    int[] onsOffsAtVolume = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 4, 6, 4, 6, 8, 2, 6, 6, 8, 2,
        2, 2, 0, 4, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    // check the correct number of ONs at each beat
    for (int i = 0; i < onsAtBeat.length; i++) {
      assertEquals(onsAtBeat[i], linesWithString(operations, "BEAT " + i + " NOTE ON: "));
    }

    // check the correct number of OFFs at each beat
    for (int i = 0; i < onsAtBeat.length; i++) {
      assertEquals(offsAtBeat[i], linesWithString(operations, "BEAT " + i + " NOTE OFF: "));
    }

    // check the correct number of ONs for each note value
    for (int i = 0; i < onsAtValue.length; i++) {
      assertEquals(onsAtValue[i],
          linesWithString(operations, "NOTE ON: ch=1 note=" + (i + 52) + " "));
    }

    // check the correct number of OFFs for each note value
    for (int i = 0; i < onsAtValue.length; i++) {
      assertEquals(offsAtValue[i],
          linesWithString(operations, "NOTE OFF: ch=1 note=" + (i + 52) + " "));
    }

    // check the correct number of ONs/OFFs for each volume
    for (int i = 0; i < onsOffsAtVolume.length; i++) {
      assertEquals(onsOffsAtVolume[i], linesWithString(operations, "vol=" + i + "\t"));
    }
  }

  private int linesWithString(List<String> stringToSearch, String searchTerm) {
    int index = 0;
    int count = 0;
    for (String s : stringToSearch) {
      if (s.contains(searchTerm)) {
        count++;
      }
    }
    return count;
  }


  @Test(expected = IllegalStateException.class)
  public void buildInstrumentMapTooManyInstrumentsTest() {
    MusicModel model = ModelFactory.createModel();
    for (int i = 1; i < 18; i++) {
      model.addNote(new NoteImpl(4, Pitch.C, 0, 0, i, 64));
    }

    MusicView view;

    try {
      view = new MidiViewImpl(model);
    } catch (MidiUnavailableException e) {
      view = null;
    }

    view.render();

  }

  @Test
  public void correspondsToTest()
      throws MidiUnavailableException, FileNotFoundException {
    MusicModel model = model();
    MusicView view = new MidiViewImpl(model);
    assertTrue(view.correspondsTo(model));
    assertFalse(view.correspondsTo(model()));
    assertFalse(view.correspondsTo(mll()));
  }

  @Test
  public void correspondsToNullTest()
      throws MidiUnavailableException {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Model must not be null");
    new MidiViewImpl(model()).correspondsTo(null);
  }

  @Test
  public void currentBeatTest() {
    List<String> operations = new ArrayList<>();
    PlayableMusicView view = new MidiViewImpl(model(), new MockSequencer(operations));

    // initially, beat is 0
    int currentBeatExpected = 0;
    assertEquals(currentBeatExpected, view.currentBeat(), DELTA);

    // next, beat is 6
    view.setBeat(6);
    currentBeatExpected = 6;
    assertEquals(currentBeatExpected, view.currentBeat(), DELTA);
  }

  @Test
  public void isPlayingTest() {
    List<String> operations = new ArrayList<>();
    PlayableMusicView view = new MidiViewImpl(model(), new MockSequencer(operations));

    // initally, not playing
    assertFalse(view.isPlaying());

    // make it play
    view.play();
    assertTrue(view.isPlaying());

    // make it stop
    view.pause();
    assertFalse(view.isPlaying());
  }

  @Test
  public void pauseTest() {
    List<String> operations = new ArrayList<>();
    PlayableMusicView view = new MidiViewImpl(model(), new MockSequencer(operations));
    int expectedOccurrences = 0;

    // no pausing yet
    assertEquals(expectedOccurrences, linesWithString(operations, "STOP"));

    // pause
    view.pause();
    expectedOccurrences = 1;
    assertEquals(expectedOccurrences, linesWithString(operations, "STOP"));

    // pause some more
    view.pause();
    view.pause();
    view.pause();
    expectedOccurrences = 4;
    assertEquals(expectedOccurrences, linesWithString(operations, "STOP"));

    // setting the beat pauses the music as well
    view.setBeat(0);
    expectedOccurrences = 5;
    assertEquals(expectedOccurrences, linesWithString(operations, "STOP"));

    //  playing also pauses the music, before starting again
    view.play();
    expectedOccurrences = 6;
    assertEquals(expectedOccurrences, linesWithString(operations, "STOP"));

    // rendering sets the beat, and plays, so it will call pause twice
    view.render();
    expectedOccurrences = 8;
    assertEquals(expectedOccurrences, linesWithString(operations, "STOP"));

  }

  @Test
  public void playTest() {
    List<String> operations = new ArrayList<>();
    MusicModel model = model();
    MockSequencer sequencer = new MockSequencer(operations);
    PlayableMusicView view = new MidiViewImpl(model, sequencer);
    List<String> expectedOperations = initializationOperations();

    // ensure operations only has initializiation stuff in it
    assertStringListEquals(expectedOperations, operations);

    // ensure operations are correct after calling play
    expectedOperations.add("RETRIEVED TICK POSITION");
    expectedOperations.add("STOP");
    expectedOperations.add("RETRIEVED TICK LENGTH");
    expectedOperations.add("SET TICK POSITION");
    expectedOperations.add("SET MPQ TEMPO");
    expectedOperations.add("SEQUENCE SET");
    expectedOperations.add("STARTED");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=2 prog=1\t");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=1 prog=2\t");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=3 prog=9\t");
    expectedOperations.add("BEAT 0 NOTE ON: ch=1 note=24 vol=64\t");
    expectedOperations.add("BEAT 1 NOTE ON: ch=2 note=25 vol=100\t");
    expectedOperations.add("BEAT 2 NOTE OFF: ch=1 note=24 vol=64\t");
    expectedOperations.add("BEAT 3 NOTE OFF: ch=2 note=25 vol=100\t");
    expectedOperations.add("BEAT 4 NOTE ON: ch=3 note=26 vol=24\t");
    expectedOperations.add("BEAT 8 NOTE OFF: ch=3 note=26 vol=24\t");
    expectedOperations.add("BEAT 8 OTHER MESSAGE (EOT)\t");

    view.play();
    assertStringListEquals(expectedOperations, operations);

  }

  @Test
  public void setBeatTest() {
    List<String> operations = new ArrayList<>();
    MusicModel model = model();
    MockSequencer sequencer = new MockSequencer(operations);
    PlayableMusicView view = new MidiViewImpl(model, sequencer);
    List<String> expectedOperations = initializationOperations();

    // ensure operations only has initializiation stuff in it
    assertStringListEquals(expectedOperations, operations);
    assertEquals(0, sequencer.getTickPosition());
    operations.remove(operations.size() - 1); // get rid of the GET TICK POSITION just added

    // set the beat
    expectedOperations.add("STOP");
    expectedOperations.add("RETRIEVED TICK LENGTH");
    expectedOperations.add("SET TICK POSITION");

    view.setBeat(4);

    assertStringListEquals(expectedOperations, operations);
    assertEquals(4, sequencer.getTickPosition());

    // set the beat to too high
    view.setBeat(100);
    assertEquals(8, sequencer.getTickPosition());

    // set the beat to too low
    view.setBeat(-100);
    assertEquals(0, sequencer.getTickPosition());
  }

  @Test
  public void refreshTest() {
    List<String> operations = new ArrayList<>();
    MusicModel model = model();
    MockSequencer sequencer = new MockSequencer(operations);
    MusicView view = new MidiViewImpl(model, sequencer);
    List<String> expectedOperations = initializationOperations();

    // ensure operations only has initializiation stuff in it
    assertStringListEquals(expectedOperations, operations);

    // now, we refresh
    expectedOperations.add("SET MPQ TEMPO");
    expectedOperations.add("SEQUENCE SET");
    view.refresh();
    assertStringListEquals(expectedOperations, operations);

    // lets refresh again
    expectedOperations.add("SET MPQ TEMPO");
    expectedOperations.add("SEQUENCE SET");
    view.refresh();
    assertStringListEquals(expectedOperations, operations);

    // test to make sure the right notes are here
    expectedOperations.add("STARTED");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=2 prog=1\t");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=1 prog=2\t");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=3 prog=9\t");
    expectedOperations.add("BEAT 0 NOTE ON: ch=1 note=24 vol=64\t");
    expectedOperations.add("BEAT 1 NOTE ON: ch=2 note=25 vol=100\t");
    expectedOperations.add("BEAT 2 NOTE OFF: ch=1 note=24 vol=64\t");
    expectedOperations.add("BEAT 3 NOTE OFF: ch=2 note=25 vol=100\t");
    expectedOperations.add("BEAT 4 NOTE ON: ch=3 note=26 vol=24\t");
    expectedOperations.add("BEAT 8 NOTE OFF: ch=3 note=26 vol=24\t");
    expectedOperations.add("BEAT 8 OTHER MESSAGE (EOT)\t");
    sequencer.start();
    assertStringListEquals(expectedOperations, operations);

    // now, modify model, start sequencer, see that nothing has changed yet
    model.addNote(new NoteImpl(1, Pitch.F, 9, 11, 5, 99));
    expectedOperations.add("STARTED");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=2 prog=1\t");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=1 prog=2\t");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=3 prog=9\t");
    expectedOperations.add("BEAT 0 NOTE ON: ch=1 note=24 vol=64\t");
    expectedOperations.add("BEAT 1 NOTE ON: ch=2 note=25 vol=100\t");
    expectedOperations.add("BEAT 2 NOTE OFF: ch=1 note=24 vol=64\t");
    expectedOperations.add("BEAT 3 NOTE OFF: ch=2 note=25 vol=100\t");
    expectedOperations.add("BEAT 4 NOTE ON: ch=3 note=26 vol=24\t");
    expectedOperations.add("BEAT 8 NOTE OFF: ch=3 note=26 vol=24\t");
    expectedOperations.add("BEAT 8 OTHER MESSAGE (EOT)\t");
    sequencer.start();
    assertStringListEquals(expectedOperations, operations);

    // now refresh and see that things have changed
    expectedOperations.add("SET MPQ TEMPO");
    expectedOperations.add("SEQUENCE SET");
    view.refresh();
    assertStringListEquals(expectedOperations, operations);
    expectedOperations.add("STARTED");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=2 prog=1\t");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=1 prog=2\t");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=4 prog=4\t");
    expectedOperations.add("BEAT 0 PROGRAM CHANGE: ch=3 prog=9\t");
    expectedOperations.add("BEAT 0 NOTE ON: ch=1 note=24 vol=64\t");
    expectedOperations.add("BEAT 1 NOTE ON: ch=2 note=25 vol=100\t");
    expectedOperations.add("BEAT 2 NOTE OFF: ch=1 note=24 vol=64\t");
    expectedOperations.add("BEAT 3 NOTE OFF: ch=2 note=25 vol=100\t");
    expectedOperations.add("BEAT 4 NOTE ON: ch=3 note=26 vol=24\t");
    expectedOperations.add("BEAT 8 NOTE OFF: ch=3 note=26 vol=24\t");
    expectedOperations.add("BEAT 9 NOTE ON: ch=4 note=29 vol=99\t");
    expectedOperations.add("BEAT 12 NOTE OFF: ch=4 note=29 vol=99\t");
    expectedOperations.add("BEAT 12 OTHER MESSAGE (EOT)\t");
    sequencer.start();
    assertStringListEquals(expectedOperations, operations);


  }

  public static class MockSequencer implements Sequencer {

    List<String> operations;
    private Sequence sequence;
    private float tempo;
    private long tickPosition;
    private boolean isPlaying;
    private boolean open;

    public MockSequencer(List<String> operations) {
      this.operations = operations;
      this.isPlaying = false;
      this.open = false;
    }

    @Override
    public void setSequence(Sequence sequence)
        throws InvalidMidiDataException {
      operations.add("SEQUENCE SET");
      this.sequence = sequence;
    }

    @Override
    public Sequence getSequence() {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public void setSequence(InputStream stream)
        throws IOException, InvalidMidiDataException {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public void start() {
      operations.add("STARTED");
      this.isPlaying = true;
      Track t = sequence.getTracks()[0];
      for (int i = 0; i < t.size(); i++) {
        operations.add("BEAT " + t.get(i).getTick() + " " + messageToText(t.get(i).getMessage()) +
            "\t");
      }
    }

    private String messageToText(MidiMessage m) {
      int statusInt = (int) m.getStatus();
      String statusCode = new String(Integer.toString(statusInt, 16).toUpperCase());
      int channel = (int) Integer.valueOf(("" + statusCode.charAt(1)), 16) + 1;

      switch ((int) Integer.valueOf(("" + statusCode.charAt(0)), 16)) {
        case 8: //NOTE OFF
          return "NOTE OFF: ch=" + channel + " note=" + ((int) m.getMessage()[1] & 0xFF) +
              " vol=" +
              ((int) m.getMessage()[2] & 0xFF);
        case 9: // NOTE ON
          return "NOTE ON: ch=" + channel + " note=" + ((int) m.getMessage()[1] & 0xFF) + " vol=" +
              ((int) m.getMessage()[2] & 0xFF);
        case 12: // PROGRAM CHANGE
          return "PROGRAM CHANGE: ch=" + channel + " prog=" + ((int) m.getMessage()[1] & 0xFF);
        default:
          return "OTHER MESSAGE (EOT)";
      }

    }

    /**
     * Stops recording, if active, and playback of the currently loaded sequence, if any.
     *
     * @throws IllegalStateException if the <code>Sequencer</code> is closed.
     * @see #start
     * @see #isRunning
     */
    @Override
    public void stop() {
      this.isPlaying = false;
      operations.add("STOP");
    }

    @Override
    public boolean isRunning() {
      operations.add("CHECKED ISRUNNING");
      return isPlaying;
    }

    @Override
    public void startRecording() {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public void stopRecording() {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public boolean isRecording() {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public void recordEnable(Track track, int channel) {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public void recordDisable(Track track) {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public float getTempoInBPM() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public void setTempoInBPM(float bpm) {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public float getTempoInMPQ() {
      operations.add("RETRIEVED MPQ TEMPO");
      return tempo;
    }

    @Override
    public void setTempoInMPQ(float mpq) {
      operations.add("SET MPQ TEMPO");
      this.tempo = mpq;
    }

    @Override
    public float getTempoFactor() {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public void setTempoFactor(float factor) {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public long getTickLength() {
      operations.add("RETRIEVED TICK LENGTH");
      return sequence.getTickLength();
    }

    @Override
    public long getTickPosition() {
      operations.add("RETRIEVED TICK POSITION");
      return tickPosition;
    }

    @Override
    public void setTickPosition(long tick) {
      operations.add("SET TICK POSITION");
      this.tickPosition = tick;
    }


    @Override
    public long getMicrosecondLength() {
      throw new UnsupportedOperationException("Mock object");
    }


    @Override
    public Info getDeviceInfo() {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public void open()
        throws MidiUnavailableException {
      operations.add("OPENED");
      this.open = true;
    }

    @Override
    public void close() {
      operations.add("CLOSED");
      this.open = false;
    }

    @Override
    public boolean isOpen() {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public long getMicrosecondPosition() {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public void setMicrosecondPosition(long microseconds) {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public int getMaxReceivers() {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public int getMaxTransmitters() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public Receiver getReceiver()
        throws MidiUnavailableException {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public List<Receiver> getReceivers() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public Transmitter getTransmitter()
        throws MidiUnavailableException {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public List<Transmitter> getTransmitters() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public SyncMode getMasterSyncMode() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public void setMasterSyncMode(SyncMode sync) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public SyncMode[] getMasterSyncModes() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public SyncMode getSlaveSyncMode() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public void setSlaveSyncMode(SyncMode sync) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public SyncMode[] getSlaveSyncModes() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public void setTrackMute(int track, boolean mute) {
      throw new UnsupportedOperationException("Mock object");
    }

    @Override
    public boolean getTrackMute(int track) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public void setTrackSolo(int track, boolean solo) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public boolean getTrackSolo(int track) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public boolean addMetaEventListener(MetaEventListener listener) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public void removeMetaEventListener(MetaEventListener listener) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public int[] removeControllerEventListener(ControllerEventListener listener,
                                               int[] controllers) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public long getLoopStartPoint() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public void setLoopStartPoint(long tick) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public long getLoopEndPoint() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public void setLoopEndPoint(long tick) {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public int getLoopCount() {
      throw new UnsupportedOperationException("Mock object");

    }

    @Override
    public void setLoopCount(int count) {
      throw new UnsupportedOperationException("Mock object");

    }
  }
}

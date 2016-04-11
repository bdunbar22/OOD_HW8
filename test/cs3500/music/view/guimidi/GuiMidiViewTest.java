package cs3500.music.view.guimidi;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import cs3500.music.model.ModelFactory;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Pitch;
import cs3500.music.model.impl.NoteImpl;
import cs3500.music.view.GuiMusicView;
import cs3500.music.view.gui.GuiViewFrame;
import cs3500.music.view.gui.MockConcreteGuiViewPanel;
import cs3500.music.view.gui.NonDisplayableGuiViewFrame;
import cs3500.music.view.midi.MidiViewImpl;
import cs3500.music.view.midi.MidiViewTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for combined GUI/MIDI view
 */
public class GuiMidiViewTest {

  private static double DELTA = 10e-6;
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  public static MusicModel model() {
    MusicModel model = ModelFactory.createModel();
    model.addNote(new NoteImpl(4, Pitch.C, 0, 1));
    model.addNote(new NoteImpl(4, Pitch.CSHARP, 2, 3));
    model.addNote(new NoteImpl(4, Pitch.D, 4, 5));
    model.addNote(new NoteImpl(4, Pitch.DSHARP, 6, 7));
    return model;
  }

  public static GuiMidiView view(MusicModel model, List<String> midiOps, List<String> guiOps) {
    MidiViewImpl midi = new MidiViewImpl(model, new MidiViewTest.MockSequencer(midiOps));
    GuiMusicView gui = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model, guiOps));
    return new GuiMidiView(model, midi, gui);
  }

  private static void assertStringListEquals(List<String> expected, List<String> actual) {
    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), actual.get(i));
    }
  }

  @Test
  public void constructorExceptionTest() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Model must not be null");
    new GuiMidiView(null);
  }

  @Test
  public void drawFromTest() {
    MusicModel model = model();
    List<String> midiOps = new ArrayList<>();
    List<String> guiOps = new ArrayList<>();
    GuiMidiView guimidi = view(model, midiOps, guiOps);
    List<String> expectedGuiOps = new ArrayList<>();
    GuiViewFrame expectedGuiView = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel
        (model(), expectedGuiOps));

    // assert that the two lists of operations are equal to begin with
    assertStringListEquals(expectedGuiOps, guiOps);

    // redraw both views and see that they perform the same drawing operations
    guimidi.drawFrom(1, 62);
    guimidi.render();
    expectedGuiView.drawFrom(1, 62);
    expectedGuiView.render();
    assertStringListEquals(expectedGuiOps, guiOps);

    // redraw both views again, see that they perform the same drawing operations again
    guimidi.drawFrom(0, 63);
    guimidi.render();
    expectedGuiView.drawFrom(0, 63);
    expectedGuiView.render();
    assertStringListEquals(expectedGuiOps, guiOps);

    // show that the view will perform same operations on gui view no matter what
    for (int i = -2; i < 12; i++) {
      for (int j = 55; j < 67; j++) {
        expectedGuiOps.clear();
        guiOps.clear();
        guimidi.drawFrom(i, j);
        guimidi.render();
        expectedGuiView.drawFrom(i, j);
        expectedGuiView.render();
        assertStringListEquals(expectedGuiOps, guiOps);
      }
    }
  }

  @Test
  public void getStartBeatTest() {
    MusicModel model = model();
    List<String> midiOps = new ArrayList<>();
    List<String> guiOps = new ArrayList<>();
    GuiMidiView guimidi = view(model, midiOps, guiOps);

    for (int beat = -2; beat < 12; beat++) {
      guimidi.drawFrom(beat, 63);
      assertEquals(Math.min(Math.max(0, beat), 7), guimidi.getStartBeat());
    }
  }

  @Test
  public void getStartNoteTest() {
    MusicModel model = model();
    List<String> midiOps = new ArrayList<>();
    List<String> guiOps = new ArrayList<>();
    GuiMidiView guimidi = view(model, midiOps, guiOps);

    for (int note = 68; note > 55; note--) {
      guimidi.drawFrom(0, note);
      assertEquals(Math.min(Math.max(60, note), 63), guimidi.getStartNote());
    }
  }

  @Test
  public void getExistingNoteTest() {
    MusicModel model = model();
    List<String> midiOps = new ArrayList<>();
    List<String> guiOps = new ArrayList<>();
    GuiMidiView guimidi = view(model, midiOps, guiOps);
    List<String> expectedGuiOps = new ArrayList<>();
    GuiViewFrame expectedGuiView = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel
        (model(), expectedGuiOps));

    for (int y = -50; y < 750; y += 5) {
      for (int x = -50; x < 750; x += 5) {
        assertEquals(expectedGuiView.getExistingNote(x, y), guimidi.getExistingNote(x, y));
      }
    }
  }

  @Test
  public void getNoteFromPositionTest() {
    MusicModel model = model();
    List<String> midiOps = new ArrayList<>();
    List<String> guiOps = new ArrayList<>();
    GuiMidiView guimidi = view(model, midiOps, guiOps);
    List<String> expectedGuiOps = new ArrayList<>();
    GuiViewFrame expectedGuiView = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel
        (model(), expectedGuiOps));

    for (int y = -50; y < 750; y += 5) {
      for (int x = -50; x < 750; x += 5) {
        assertEquals(expectedGuiView.getNoteFromPosition(x, y), guimidi.getNoteFromPosition(x, y));
      }
    }
  }

  @Test
  public void setBeatTest() {
    MusicModel model = model();
    List<String> midiOps = new ArrayList<>();
    List<String> guiOps = new ArrayList<>();
    GuiMidiView guimidi = view(model, midiOps, guiOps);

    List<String> expectedMidiOps = new ArrayList<>();
    MidiViewImpl expectedMidi = new MidiViewImpl(model(), new MidiViewTest.MockSequencer
        (expectedMidiOps));

    List<String> expectedGuiOps = new ArrayList<>();
    GuiViewFrame expectedGuiView = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel
        (model(), expectedGuiOps));

    // initially, operations lists are equal
    assertStringListEquals(expectedGuiOps, guiOps);
    assertStringListEquals(expectedMidiOps, midiOps);

    // after one render, they are equal
    expectedGuiOps.clear();
    expectedMidiOps.clear();
    guiOps.clear();
    midiOps.clear();
    guimidi.render();
    expectedGuiView.render();
    assertStringListEquals(expectedGuiOps, guiOps);
    assertStringListEquals(expectedMidiOps, midiOps);
    assertEquals(expectedGuiView.currentBeat(), guimidi.currentBeat(), DELTA);
    assertEquals(expectedMidi.currentBeat(), (int) guimidi.currentBeat(), DELTA);

    // set beat and see that operations and current beats are equal
    expectedGuiOps.clear();
    expectedMidiOps.clear();
    guiOps.clear();
    midiOps.clear();
    guimidi.setBeat(4.2);
    expectedGuiView.setBeat(4.2);
    expectedMidi.setBeat(4.2);
    guimidi.render();
    expectedGuiView.render();
    assertStringListEquals(expectedGuiOps, guiOps);
    assertStringListEquals(expectedMidiOps, midiOps);
    assertEquals(expectedGuiView.currentBeat(), guimidi.currentBeat(), DELTA);
    assertEquals(expectedMidi.currentBeat(), (int) guimidi.currentBeat(), DELTA);

    // no matter where current beat is set, they are equal
    for (double beat = -1.0; beat < 10.0; beat += .3) {
      expectedGuiOps.clear();
      expectedMidiOps.clear();
      guiOps.clear();
      midiOps.clear();
      guimidi.setBeat(beat);
      expectedGuiView.setBeat(beat);
      expectedMidi.setBeat(beat);
      guimidi.render();
      expectedGuiView.render();
      assertStringListEquals(expectedGuiOps, guiOps);
      assertStringListEquals(expectedMidiOps, midiOps);
      assertEquals(expectedGuiView.currentBeat(), guimidi.currentBeat(), DELTA);
      assertEquals(expectedMidi.currentBeat(), (int) guimidi.currentBeat(), DELTA);
    }
  }

  @Test
  public void playPauseIsPlayingTest()
      throws InterruptedException, TimeoutException {
    MusicModel model = model();
    List<String> midiOps = new LinkedList<>();
    List<String> guiOps = new LinkedList<>();
    MidiViewImpl midi = new MidiViewImpl(model, new MidiViewTest.MockSequencer(midiOps));
    GuiViewFrame gui = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model, guiOps));
    GuiMidiView guimidi = new GuiMidiView(model, midi, gui);

    // synced initially
    assertEquals(midi.isPlaying(), guimidi.isPlaying());
    assertEquals(gui.isPlaying(), guimidi.isPlaying());

    // make sure that both midi and gui are set to playing when composite view is also
    guimidi.play();
    assertEquals(midi.isPlaying(), guimidi.isPlaying());
    assertEquals(gui.isPlaying(), guimidi.isPlaying());

    // continue to make sure they sync up
    guimidi.play();
    assertEquals(midi.isPlaying(), guimidi.isPlaying());
    assertEquals(gui.isPlaying(), guimidi.isPlaying());

    guimidi.pause();
    assertEquals(midi.isPlaying(), guimidi.isPlaying());
    assertEquals(gui.isPlaying(), guimidi.isPlaying());

    guimidi.pause();
    assertEquals(midi.isPlaying(), guimidi.isPlaying());
    assertEquals(gui.isPlaying(), guimidi.isPlaying());

    guimidi.play();
    assertEquals(midi.isPlaying(), guimidi.isPlaying());
    assertEquals(gui.isPlaying(), guimidi.isPlaying());

    guimidi.pause();
    assertEquals(midi.isPlaying(), guimidi.isPlaying());
    assertEquals(gui.isPlaying(), guimidi.isPlaying());

    // test that gui view gets the current beat of midi view if midi view is updated
    // initially all are 0
    assertEquals(0, guimidi.currentBeat(), DELTA);
    assertEquals(0, midi.currentBeat(), DELTA);
    assertEquals(0, gui.currentBeat(), DELTA);

    // if paused, gui's will not update current beat if midi does
    guimidi.pause();
    midi.setBeat(4.0);
    assertEquals(0, guimidi.currentBeat(), DELTA);
    assertEquals(4.0, midi.currentBeat(), DELTA);
    assertEquals(0, gui.currentBeat(), DELTA);

    // now if we play, it will update
    midi.setBeat(0.0);
    guimidi.play();
    midi.setBeat(4.0);
    int count = 0;
    while (guimidi.currentBeat() < 4.0) {
      Thread.sleep(1);
      if (count++ > 500) {
        throw new TimeoutException("Test timed out, current beat not updated");
      }
    }
    midi.pause();
    guimidi.pause();
    assertEquals(4.0, guimidi.currentBeat(), DELTA);
    assertEquals(4.0, midi.currentBeat(), DELTA);
    assertEquals(4.0, gui.currentBeat(), DELTA);

    // test that guimidi.isPlaying() is the same as midi.isPlaying()
    midi.pause();
    guimidi.pause();
    gui.pause();
    assertFalse(midi.isPlaying());
    assertFalse(guimidi.isPlaying());
    assertFalse(gui.isPlaying());
    gui.play();
    assertFalse(midi.isPlaying());
    assertFalse(guimidi.isPlaying());
    assertTrue(gui.isPlaying());
    midi.play();
    assertTrue(midi.isPlaying());
    assertTrue(guimidi.isPlaying());
    assertTrue(gui.isPlaying());

  }

  @Test
  public void currentBeatTest() {
    MusicModel model = model();
    List<String> midiOps = new LinkedList<>();
    List<String> guiOps = new LinkedList<>();
    MidiViewImpl midi = new MidiViewImpl(model, new MidiViewTest.MockSequencer(midiOps));
    GuiViewFrame gui = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model, guiOps));
    GuiMidiView guimidi = new GuiMidiView(model, midi, gui);

    assertEquals(0, guimidi.currentBeat(), DELTA);
    assertEquals(0, gui.currentBeat(), DELTA);
    assertEquals(0, midi.currentBeat(), DELTA);

    midi.setBeat(4.2);

    assertEquals(0, guimidi.currentBeat(), DELTA);
    assertEquals(0, gui.currentBeat(), DELTA);
    assertEquals(4.0, midi.currentBeat(), DELTA);

    gui.setBeat(3.2);

    assertEquals(3.2, guimidi.currentBeat(), DELTA);
    assertEquals(3.2, gui.currentBeat(), DELTA);
    assertEquals(4.0, midi.currentBeat(), DELTA);

    guimidi.setBeat(0.5);

    assertEquals(0.5, guimidi.currentBeat(), DELTA);
    assertEquals(0.5, gui.currentBeat(), DELTA);
    assertEquals(0, midi.currentBeat(), DELTA);
  }

  @Test
  public void renderTest() {
    MusicModel model = model();
    List<String> midiOps = new ArrayList<>();
    List<String> guiOps = new ArrayList<>();
    GuiMidiView guimidi = view(model, midiOps, guiOps);

    List<String> expectedMidiOps = new ArrayList<>();
    MidiViewImpl expectedMidi = new MidiViewImpl(model(), new MidiViewTest.MockSequencer
        (expectedMidiOps));

    List<String> expectedGuiOps = new ArrayList<>();
    GuiViewFrame expectedGuiView = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel
        (model(), expectedGuiOps));

    assertStringListEquals(expectedGuiOps, guiOps);
    assertStringListEquals(expectedMidiOps, midiOps);

    guimidi.render();
    expectedGuiView.render();

    assertStringListEquals(expectedGuiOps, guiOps);
    assertStringListEquals(expectedMidiOps, midiOps);

  }

  @Test
  public void refreshTest() {
    MusicModel model = model();
    List<String> midiOps = new ArrayList<>();
    List<String> guiOps = new ArrayList<>();
    GuiMidiView guimidi = view(model, midiOps, guiOps);

    List<String> expectedMidiOps = new ArrayList<>();
    MidiViewImpl expectedMidi = new MidiViewImpl(model(), new MidiViewTest.MockSequencer
        (expectedMidiOps));

    List<String> expectedGuiOps = new ArrayList<>();
    GuiViewFrame expectedGuiView = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel
        (model(), expectedGuiOps));

    assertStringListEquals(expectedGuiOps, guiOps);
    assertStringListEquals(expectedMidiOps, midiOps);

    guimidi.refresh();
    expectedGuiView.refresh();
    expectedMidi.refresh();

    assertStringListEquals(expectedGuiOps, guiOps);
    assertStringListEquals(expectedMidiOps, midiOps);
  }

  @Test
  public void correspondsToTest() {
    MusicModel model = model();
    GuiMidiView view = new GuiMidiView(model);

    assertTrue(view.correspondsTo(model));
    assertFalse(view.correspondsTo(model()));
    assertFalse(view.correspondsTo(ModelFactory.createModel()));
  }

  @Test
  public void correspondsToNullTest() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("Model must not be null");
    new GuiMidiView(model()).correspondsTo(null);
  }
}

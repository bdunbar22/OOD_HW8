package cs3500.music.view.gui;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs3500.music.model.ModelFactory;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.model.impl.MusicModelBuilder;
import cs3500.music.model.impl.NoteImpl;
import cs3500.music.util.MusicReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the GUI view
 */
public class GuiViewTest {

  private static final double DELTA = 10e-6;
  private static final int STARTX = 40; // x coordinate of grid start
  private static final int STARTY = 30; // y coordinate of grid start
  private static final int BEATWIDTH = 20; // width of a single beat in grid
  private static final int BEATHEIGHT = 20; // height of a single beat in grid

  public static MusicModel mll()
      throws FileNotFoundException {
    return MusicReader.parseFile(new FileReader(new File("res/mary-little-lamb.txt")),
        new MusicModelBuilder());
  }

  public static MusicModel model() {
    MusicModel model = ModelFactory.createModel();
    model.addNote(new NoteImpl(4, Pitch.C, 0, 1));
    model.addNote(new NoteImpl(4, Pitch.CSHARP, 2, 3));
    model.addNote(new NoteImpl(4, Pitch.D, 4, 5));
    model.addNote(new NoteImpl(4, Pitch.DSHARP, 6, 7));
    return model;
  }

  private static void assertStringListEquals(List<String> expected, List<String> actual) {
    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), actual.get(i));
    }
  }

  private static List<String> operationsList() {
    List<String> ops = new ArrayList<>();
    ops.add("HEAD - beat: 0 note: 60\t");
    ops.add("SUSTAIN - beat: 1 note: 60\t");
    ops.add("HEAD - beat: 2 note: 61\t");
    ops.add("SUSTAIN - beat: 3 note: 61\t");
    ops.add("HEAD - beat: 4 note: 62\t");
    ops.add("SUSTAIN - beat: 5 note: 62\t");
    ops.add("HEAD - beat: 6 note: 63\t");
    ops.add("SUSTAIN - beat: 7 note: 63\t");
    ops.add("PROGRESS LINE - beat: 0.0\t");
    return ops;
  }

  @Test
  public void mllTest()
      throws FileNotFoundException {
    MusicModel model = mll();
    List<String> operations = new ArrayList<>();
    GuiViewFrame view = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model,
        operations));
    view.render();
    view.dispose();

    int numHeads = 34;
    int numSusts = 85;
    Map<Integer, Integer> notesAtBeat = new HashMap<>();
    notesAtBeat.put(0, 2);
    notesAtBeat.put(27, 1);
    notesAtBeat.put(56, 2);

    Map<Integer, Integer> notesAtValue = new HashMap<>();
    notesAtValue.put(55, 48);
    notesAtValue.put(67, 6);
    notesAtValue.put(60, 12);
    notesAtValue.put(52, 8);

    // check that the correct number of note heads are drawn
    assertEquals(numHeads, linesWithString(operations, "HEAD"));

    // check that the correct number of sustains are drawn
    assertEquals(numSusts, linesWithString(operations, "SUSTAIN"));

    // check that the correct number of notes are drawn at certain beats
    for (Map.Entry<Integer, Integer> entry : notesAtBeat.entrySet()) {
      assertEquals(entry.getValue().intValue(),
          linesWithString(operations, "beat: " + entry.getKey() + " "));
    }

    // check that the correct number of notes are drawn at certain pitches
    for (Map.Entry<Integer, Integer> entry : notesAtValue.entrySet()) {
      assertEquals(entry.getValue().intValue(),
          linesWithString(operations, "note: " + entry.getKey() + "\t"));
    }

    // test progress line drawn at right spot
    assertEquals(1, linesWithString(operations, "PROGRESS LINE - beat: 0.0\t"));


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

  // also tests getStartBeat() and getStartNote()
  @Test
  public void drawFromTest() {
    MusicModel model = model();
    List<String> operations = new ArrayList<>();
    GuiViewFrame view = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model,
        operations));
    int expectedStartBeat = 0;
    int expectedStartNote = 63;

    // correct initially
    assertEquals(expectedStartBeat, view.getStartBeat());
    assertEquals(expectedStartNote, view.getStartNote());

    //set start beat and start note to legal values
    view.drawFrom(1, 62);
    expectedStartBeat = 1;
    expectedStartNote = 62;
    assertEquals(expectedStartBeat, view.getStartBeat());
    assertEquals(expectedStartNote, view.getStartNote());

    // set start beat too low
    view.drawFrom(-100, 63);
    expectedStartBeat = 0;
    expectedStartNote = 63;
    assertEquals(expectedStartBeat, view.getStartBeat());
    assertEquals(expectedStartNote, view.getStartNote());

    // set start beat too high
    view.drawFrom(100, 63);
    expectedStartBeat = 7;
    expectedStartNote = 63;
    assertEquals(expectedStartBeat, view.getStartBeat());
    assertEquals(expectedStartNote, view.getStartNote());

    // set start note too high
    view.drawFrom(0, 100);
    expectedStartBeat = 0;
    expectedStartNote = 63;
    assertEquals(expectedStartBeat, view.getStartBeat());
    assertEquals(expectedStartNote, view.getStartNote());

    // set start note too low
    view.drawFrom(0, -100);
    expectedStartBeat = 0;
    expectedStartNote = 60;
    assertEquals(expectedStartBeat, view.getStartBeat());
    assertEquals(expectedStartNote, view.getStartNote());

    // moving starting beat over to the right will draw only the displayed notes
    List<String> expectedOperations = operationsList();

    for (int i = 0; i < 8; i++) {
      operations.clear();
      view.drawFrom(i, 63);
      view.render();
      assertStringListEquals(expectedOperations, operations);
      expectedOperations.remove(0);
    }

    // moving starting note down will draw only the displayed notes
    expectedOperations = operationsList();

    for (int i = 63; i > 59; i--) {
      operations.clear();
      view.drawFrom(0, i);
      view.render();
      assertStringListEquals(expectedOperations, operations);
      expectedOperations.remove(expectedOperations.size() - 2);
      expectedOperations.remove(expectedOperations.size() - 2);
    }
  }

  @Test
  public void getNoteFromPositionTest() {
    MusicModel model = model();
    List<String> operations = new ArrayList<>();
    GuiViewFrame view = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model,
        operations));

    // test that the correct  note is retrieved
    for (int startBeat = 0; startBeat < 8; startBeat += 3) {
      for (int startNote = 63; startNote > 59; startNote -= 2) {
        view.drawFrom(startBeat, startNote);
        view.render();
        for (int y = 0; y < view.getHeight(); y += 5) {
          for (int x = 0; x < view.getWidth(); x += 5) {
            int beat = startBeat + (x - view.getInsets().left - STARTX) / BEATWIDTH;
            int note = startNote - ((y - view.getInsets().top - STARTY) / BEATHEIGHT);
            NoteImpl expected;
            if (beat < 0 || note < 24) {
              expected = null;
            } else {
              expected = new NoteImpl(note / 12 - 1, Pitch.getPitch(note % 12), beat, beat);
            }
            assertEquals(expected, view.getNoteFromPosition(x, y));
          }
        }
      }
    }
  }

  @Test
  public void getExistingNoteTest() {
    MusicModel model = model();
    List<String> operations = new ArrayList<>();
    GuiViewFrame view = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model,
        operations));
    Set<Note> allNotesInModel = new HashSet<>();
    for (int i = 0; i < model.getNumberOfBeats(); i++) {
      allNotesInModel.addAll(model.getNotesAtBeat(i));
    }
    // test that the correct  note is retrieved
    for (int startBeat = 0; startBeat < 8; startBeat += 3) {
      for (int startNote = 63; startNote > 59; startNote -= 2) {
        view.drawFrom(startBeat, startNote);
        view.render();
        for (int y = 0; y < view.getHeight(); y += 5) {
          for (int x = 0; x < view.getWidth(); x += 5) {
            int beat = startBeat + (x - view.getInsets().left - STARTX) / BEATWIDTH;
            int note = startNote - ((y - view.getInsets().top - STARTY) / BEATHEIGHT);
            Note expected = null;
            if (beat > -1 && note > 23) {
              int octave = note / 12 - 1;
              Pitch pitch = Pitch.getPitch(note % 12);
              for (Note n : allNotesInModel) {
                if (octave == n.getOctave() && pitch == n.getPitch() && beat >= n.getStartBeat()
                    && beat <= n.getEndBeat()) {
                  expected = n;
                }
              }
              assertEquals(expected, view.getExistingNote(x, y));
            }
          }
        }
      }
    }
  }

  @Test
  public void setBeatAndCurrentBeatTest() {
    MusicModel model = model();
    List<String> operations = new ArrayList<>();
    GuiViewFrame view = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model,
        operations));


    // initial progress
    String expectedProgress = "PROGRESS LINE - beat: 0.0\t";
    String progressLabel = "PROGRESS LINE - beat:";
    view.render();
    assertEquals(1, linesWithString(operations, expectedProgress));
    assertEquals(1, linesWithString(operations, progressLabel));
    assertEquals(0, view.currentBeat(), DELTA);

    // set beat to a valid beat
    operations.clear();
    expectedProgress = "PROGRESS LINE - beat: 4.2\t";
    view.setBeat(4.2);
    view.render();
    assertEquals(1, linesWithString(operations, expectedProgress));
    assertEquals(1, linesWithString(operations, progressLabel));
    assertEquals(4.2, view.currentBeat(), DELTA);

    // set beat too low
    operations.clear();
    expectedProgress = "PROGRESS LINE - beat: 0.0\t";
    view.setBeat(-5);
    view.render();
    assertEquals(1, linesWithString(operations, expectedProgress));
    assertEquals(1, linesWithString(operations, progressLabel));
    assertEquals(0, view.currentBeat(), DELTA);

    // set beat too high
    operations.clear();
    expectedProgress = "PROGRESS LINE - beat: 8.0\t";
    view.setBeat(90);
    view.render();
    assertEquals(1, linesWithString(operations, expectedProgress));
    assertEquals(1, linesWithString(operations, progressLabel));
    assertEquals(8, view.currentBeat(), DELTA);
  }

  @Test
  public void pausePlayIsPlayingTest() {
    MusicModel model = model();
    List<String> operations = new ArrayList<>();
    GuiViewFrame view = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model,
        operations));

    assertFalse(view.isPlaying());
    view.play();
    assertTrue(view.isPlaying());
    view.play();
    assertTrue(view.isPlaying());
    view.pause();
    assertFalse(view.isPlaying());
    view.pause();
    assertFalse(view.isPlaying());
    view.play();
    assertTrue(view.isPlaying());

  }

  @Test
  public void correspondsToTest() {
    MusicModel model = model();
    List<String> operations = new ArrayList<>();
    GuiViewFrame view = new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model,
        operations));

    assertTrue(view.correspondsTo(model));
    assertFalse(view.correspondsTo(model()));
    assertFalse(view.correspondsTo(ModelFactory.createModel()));
  }

  @Test(expected = NullPointerException.class)
  public void correspondsToNullTest() {
    new NonDisplayableGuiViewFrame(new MockConcreteGuiViewPanel(model(), new ArrayList<>()))
        .correspondsTo(null);
  }
}

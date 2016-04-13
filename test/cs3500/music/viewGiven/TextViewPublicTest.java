package cs3500.music.viewGiven;

import cs3500.music.adapters.MusicModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.music.adapters.ModelFactory;
import cs3500.music.adapters.MusicModel;
import cs3500.music.adapters.Note;
import cs3500.music.adapters.Pitch;
import cs3500.music.adapters.MusicModelImpl;
import cs3500.music.adapters.NoteImpl;
import cs3500.music.viewGiven.text.TextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing public methods of TextView
 */
public class TextViewPublicTest {

  /**
   * Create music model with a few notes in it for testing
   *
   * @return testing music model
   */
  private static MusicModel model() {

    MusicModel model = ModelFactory.createModel();
    model.addNote(new NoteImpl(4, Pitch.C, 0, 3));
    model.addNote(new NoteImpl(4, Pitch.CSHARP, 0, 1));
    model.addNote(new NoteImpl(3, Pitch.G, 2, 3));
    model.addNote(new NoteImpl(3, Pitch.A, 0, 0));
    model.addNote(new NoteImpl(3, Pitch.A, 10, 11));
    return model;
  }

  /**
   * Create a music model with no notes in it for testing
   *
   * @return testing music model
   */
  private static MusicModel emptyModel() {
    return ModelFactory.createModel();
  }

  /**
   * Count the number of starting notes in the music model specified
   *
   * @param m a music model
   * @return a count of the number of starting notes in the model
   */
  private static int numStartingNotes(MusicModel m) {
    int count = 0;
    for (int i = 0; i < m.getNumberOfBeats(); i++) {
      for (Note n : m.getNotesAtBeat(i)) {
        if (n.getStartBeat() == i) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Count the number of total notes in the music model specified. If a starting note lasts 4
   * beats, it counts as 4 notes for the purpose of this method.
   *
   * @param m a music model
   * @return a count of the number of total notes in the model
   */
  private static int numTotalNotes(MusicModel m) {
    int count = 0;
    for (int i = 0; i < m.getNumberOfBeats(); i++) {
      for (Note n : m.getNotesAtBeat(i)) {
        count++;
      }
    }
    return count;
  }

  @Test
  public void toTextTest() {

    StringBuilder sb = new StringBuilder();
    MusicView testView = new TextView(sb, model());

    // test simple case
    String expectedText =
        "    G3  G#3   A3  A#3   B3   C4  C#4 \n" +
            " 0            X              X    X  \n" +
            " 1                           |    |  \n" +
            " 2  X                        |       \n" +
            " 3  |                        |       \n" +
            " 4                                   \n" +
            " 5                                   \n" +
            " 6                                   \n" +
            " 7                                   \n" +
            " 8                                   \n" +
            " 9                                   \n" +
            "10            X                      \n" +
            "11            |                      \n";
    testView.render();
    assertEquals(expectedText, sb.toString());

    // make sure that beat column is the correct width
    sb = new StringBuilder();
    MusicModel model = emptyModel();
    for (int i = 0; i < 1002; i += 2) {
      model.addNote(new NoteImpl(1, Pitch.getPitch(i / 2 % 12), i, i + 2));
    }

    testView = new TextView(sb, model);
    testView.render();
    String modelText = sb.toString();

    for (int i = 0; i < 1003; i++) {
      assertTrue(modelText.contains(String.format("%4s", i)));
      assertFalse(modelText.contains(String.format("%5s", i)));
    }

    // make sure that there are a correct number of starting notes and mid notes
    int xCount = 0;
    int barCount = 0;
    for (char c : modelText.toCharArray()) {
      if (c == 'X') {
        xCount++;
      }
      if (c == '|') {
        barCount++;
      }
    }
    assertEquals(501, xCount);
    assertEquals(501, numStartingNotes(model));
    assertEquals(1002, barCount);
    assertEquals(1503, numTotalNotes(model));
  }

  @Test
  public void viewEmptyModelTest() {
    StringBuilder sb = new StringBuilder();
    MusicView view = new TextView(sb, emptyModel());
    view.render();
    assertEquals("   C1 \n0     \n", sb.toString());
  }

  @Test
  public void viewOverlappingNoteTest() {
    MusicModel model = emptyModel();
    model.addNote(new NoteImpl(4, Pitch.C, 0, 2, 5, 64));
    model.addNote(new NoteImpl(4, Pitch.C, 1, 3, 1, 51));
    String expected =
        "   C4 \n" +
            "0  X  \n" +
            "1  X  \n" +
            "2  |  \n" +
            "3  |  \n";
    StringBuilder sb = new StringBuilder();
    MusicView textView = new TextView(sb, model);
    textView.render();
    assertEquals(expected, sb.toString());

    sb = new StringBuilder();
    model = model();
    textView = new TextView(sb, model);
    model.addNote(new NoteImpl(4, Pitch.C, 3, 9, 64, 10));

    // test simple case
    String expectedText =
        "    G3  G#3   A3  A#3   B3   C4  C#4 \n" +
            " 0            X              X    X  \n" +
            " 1                           |    |  \n" +
            " 2  X                        |       \n" +
            " 3  |                        X       \n" +
            " 4                           |       \n" +
            " 5                           |       \n" +
            " 6                           |       \n" +
            " 7                           |       \n" +
            " 8                           |       \n" +
            " 9                           |       \n" +
            "10            X                      \n" +
            "11            |                      \n";
    textView.render();
    assertEquals(expectedText, sb.toString());

  }

  @Test
  public void rerenderTest() {

    MusicModel model = emptyModel();
    String expectedText = "   C1 \n0     \n";
    StringBuilder sb = new StringBuilder();
    MusicView view = new TextView(sb, model);
    view.render();

    // ensure view is correct initially
    assertEquals(expectedText, sb.toString());

    // ensure view is correct after adding notes to model
    sb.delete(0, sb.length());
    model.addNote(new NoteImpl(1, Pitch.C, 0, 1));
    expectedText = "   C1 \n" +
        "0  X  \n" +
        "1  |  \n";
    view.render();
    assertEquals(expectedText, sb.toString());

    // ensure view is correct after adding another note
    sb.delete(0, sb.length());
    model.addNote(new NoteImpl(1, Pitch.CSHARP, 1, 1));
    expectedText =
        "   C1  C#1 \n" +
            "0  X       \n" +
            "1  |    X  \n";
    view.render();
    assertEquals(expectedText, sb.toString());

    // ensure view is correct after removing note
    sb.delete(0, sb.length());
    model.removeNote(new NoteImpl(1, Pitch.C, 0, 1));
    expectedText =
        "  C#1 \n" +
            "0     \n" +
            "1  X  \n";
    view.render();
    assertEquals(expectedText, sb.toString());
  }

  @Test
  public void overlappingModelNotesTest() {

    MusicModel model;
    MusicView view;
    StringBuilder sb;
    String expectedText;

    // test that n copies of the same note are drawn the same way
    model = emptyModel();
    sb = new StringBuilder();
    view = new TextView(sb, model);
    model.addNote(new NoteImpl(2, Pitch.GSHARP, 0, 3));
    expectedText =
        "  G#2 \n" +
            "0  X  \n" +
            "1  |  \n" +
            "2  |  \n" +
            "3  |  \n";
    view.render();
    assertEquals(expectedText, sb.toString());
    for (int i = 0; i < 50; i++) {
      sb.delete(0, sb.length());
      model.addNote(new NoteImpl(2, Pitch.GSHARP, 0, 3));
      view.render();
      assertEquals(expectedText, sb.toString());
    }

    // test that heads are always given priority over sustains
    List<NoteImpl> notesToAdd = new ArrayList<>(5);
    notesToAdd.add(new NoteImpl(2, Pitch.GSHARP, 0, 4, 5, 12));
    notesToAdd.add(new NoteImpl(2, Pitch.GSHARP, 1, 4, 6, 87));
    notesToAdd.add(new NoteImpl(2, Pitch.GSHARP, 2, 4, 12, 19));
    notesToAdd.add(new NoteImpl(2, Pitch.GSHARP, 3, 4, 76, 120));
    notesToAdd.add(new NoteImpl(2, Pitch.GSHARP, 4, 4, 3, 15));
    // shuffle to make sure drawing isn't determined by addition order
    Collections.shuffle(notesToAdd);
    model = emptyModel();
    sb = new StringBuilder();
    view = new TextView(sb, model);
    expectedText =
        "  G#2 \n" +
            "0  X  \n" +
            "1  X  \n" +
            "2  X  \n" +
            "3  X  \n" +
            "4  X  \n";
    for (NoteImpl n : notesToAdd) {
      model.addNote(n);
    }
    view.render();
    assertEquals(expectedText, sb.toString());

  }

  @Test
  public void refreshTest() {

    MusicModel model = emptyModel();
    String expectedText = "   C1 \n0     \n";
    StringBuilder sb = new StringBuilder();
    MusicView view = new TextView(sb, model);
    view.refresh();

    // ensure view is correct initially
    assertEquals(expectedText, sb.toString());

    // ensure view is correct after adding notes to model
    sb.delete(0, sb.length());
    model.addNote(new NoteImpl(1, Pitch.C, 0, 1));
    expectedText = "   C1 \n" +
        "0  X  \n" +
        "1  |  \n";
    view.refresh();
    assertEquals(expectedText, sb.toString());

    // ensure view is correct after adding another note
    sb.delete(0, sb.length());
    model.addNote(new NoteImpl(1, Pitch.CSHARP, 1, 1));
    expectedText =
        "   C1  C#1 \n" +
            "0  X       \n" +
            "1  |    X  \n";
    view.refresh();
    assertEquals(expectedText, sb.toString());

    // ensure view is correct after removing note
    sb.delete(0, sb.length());
    model.removeNote(new NoteImpl(1, Pitch.C, 0, 1));
    expectedText =
        "  C#1 \n" +
            "0     \n" +
            "1  X  \n";
    view.refresh();
    assertEquals(expectedText, sb.toString());
  }

  @Test
  public void correspondsToTest() {
    MusicModel correctModel = model();
    TextView view = new TextView(new StringBuilder(), correctModel);

    assertTrue(view.correspondsTo(correctModel));
    assertFalse(view.correspondsTo(model()));
    assertFalse(view.correspondsTo(emptyModel()));
  }

  @Test(expected = NullPointerException.class)
  public void correspondsToNullTest() {
    new TextView(new StringBuilder(), model()).correspondsTo(null);
  }

}

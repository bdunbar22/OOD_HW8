package cs3500.music.viewGiven.text;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import cs3500.music.adapters.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.viewGiven.MusicView;

/**
 * MusicView implementation for printing text view to console.
 */
public class TextView implements MusicView {

  private final MusicModel model;
  private final Appendable appendable;

  /**
   * Construct a TextView of the specified model. Prints a view to the specified appendable when
   * render or refresh is called.
   *
   * @param appendable the output device
   * @param model      a model to view
   */
  public TextView(Appendable appendable, MusicModel model) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(appendable);
    this.model = model;
    this.appendable = appendable;
  }

  /**
   * Convenience method for padding String s to specified size, left justified with spaces
   *
   * @param s    String to pad
   * @param size size to pad to
   * @return the padded String
   */
  protected static String padLeft(String s, int size) {
    return String.format("%" + size + "s", s);
  }

  /**
   * Convenience method for padding String s to specified size, right justified with spaces
   *
   * @param s    String to pad
   * @param size size to pad to
   * @return the padded String
   */
  protected static String padRight(String s, int size) {
    return String.format("%-" + size + "s", s);
  }

  /**
   * Convenience method for padding String s to specified size, center justified with spaces
   *
   * @param s    String to pad
   * @param size size to pad to
   * @return the padded String
   */
  protected static String padCenter(String s, int size) {
    String out;
    int padSize = size - s.length();
    int padStart = s.length() + (padSize / 2);
    out = String.format("%-" + padStart + "s", s);
    return String.format("%" + size + "s", out);
  }


  /**
   * Create the text representation of the model
   *
   * @return text representation of model
   */
  private String toText() {
    StringBuilder sb = new StringBuilder();

    int totalBeats = 0;
    for (int i = 0; i < model.getNumberOfBeats(); i++) {
      totalBeats += model.getNotesAtBeat(i).size();
    }

    // determine necessary width of the beats column
    int beatsColumnWidth = 0;
    if (model.getNumberOfBeats() == 1) {
      beatsColumnWidth = 1;
    } else {
      int temp = model.getNumberOfBeats() - 1;
      while (temp > 0) {
        temp /= 10;
        beatsColumnWidth++;
      }
    }

    // determine minimum and maximum note
    int minNote = model.getLowestNote();
    int maxNote = model.getHighestNote();

    // append the row of labels
    sb.append(padRight("", beatsColumnWidth));
    for (int i = minNote; i < maxNote + 1; i++) {
      sb.append(padCenter(Pitch.getPitch(i % 12).toString() + ((i / 12) - 1), 5));
    }
    sb.append("\n");

    // append notes
    for (int i = 0; i < model.getNumberOfBeats(); i++) {

      // beat number
      sb.append(padLeft("" + i, beatsColumnWidth));
      List<Note> beatNotes = model.getNotesAtBeat(i);

      // if no notes at beat
      if (beatNotes.size() == 0) {
        sb.append(padLeft("", 5 * (maxNote - minNote + 1)));
      } else {

        // if some notes do exist at the beat
        for (int j = minNote; j < maxNote + 1; j++) {
          if (j == beatNotes.get(0).getValue()) {

            String beatChar = "|";
            while (!beatNotes.isEmpty() && beatNotes.get(0).getValue() == j) {
              if (beatNotes.remove(0).getStartBeat() == i) {
                beatChar = "X";
              }
            }
            sb.append(padCenter(beatChar, 5));

            if (beatNotes.isEmpty() && j != maxNote) {
              sb.append(padLeft("", 5 * (maxNote - j)));
              break;
            }

          } else {
            sb.append("     ");
          }
        }
      }

      sb.append("\n");
    }

    return sb.toString();
  }

  /**
   * Render this MusicView
   */
  @Override
  public void render() {
    try {
      this.appendable.append(toText());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Refresh/redraw this MusicView to correspond to new state of model.
   *
   * @throws IllegalStateException if view cannot be refreshed
   */
  @Override
  public void refresh() {
    render();
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

package cs3500.music.viewGiven.gui;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import cs3500.music.adapters.MusicModel;
import cs3500.music.adapters.NoteImpl;
import cs3500.music.adapters.Pitch;
import cs3500.music.adapters.Note;
//TODO: see if allowed to change their import statements

/**
 * A panel containing a visual view of a model
 */
public class ConcreteGuiViewPanel extends JPanel {

  private static final int STARTX = 40; // x coordinate of grid start

  private static final int STARTY = 30; // y coordinate of grid start

  private static final int LABELX = 5; // x coordinate of pitch labels

  private static final int BEATWIDTH = 20; // width of a single beat in grid

  private static final int BEATHEIGHT = 20; // height of a single beat in grid

  private static final int MEASUREWIDTH = 4 * BEATWIDTH; // width of a measure in grid (4 beats)

  private static final int LABELVERTOFFSET = (int) (.75 * BEATHEIGHT);
  // vertical offset of pitch labels

  private static final int MEASURELABELY = STARTY - 5; // y coordinate of measure labels

  private static final int MIDDLECLINEHEIGHT = (BEATHEIGHT / 4) + 1; // height of middle c line

  private static final Color HEADCOLOR = Color.BLACK; // color of note heads

  private static final Color SUSTCOLOR = Color.GREEN; // color of note sustains

  private final MusicModel model;

  private double currentBeat; // beat where progress bar is drawn
  private int startBeat; // leftmost beat column drawn (corresponds to beat in MusicModel)
  private int startNote; // topmost note row drawn (corresponds to note value)

  /**
   * Constructs a panel from the specified model.
   *
   * @param model model to create panel from
   */
  ConcreteGuiViewPanel(MusicModel model) {
    super();
    this.model = model;
    this.currentBeat = 0;
    this.startBeat = 0;
    this.startNote = this.model.getHighestNote();
  }

  /**
   * Draw this panel from the model.
   *
   * @param g a Graphics object
   */
  @Override
  public void paintComponent(Graphics g) {
    // Handle the default painting
    super.paintComponent(g);

    drawNotes(g); // draw all notes first
    drawGrid(g); // then draw the grid on top
    drawProgressLine(g); // finally, draw the progress line on very top

  }

  /**
   * Draw the grid.
   *
   * @param g Graphics object
   */
  void drawGrid(Graphics g) {
    // set color of graphics
    g.setColor(Color.BLACK);

    // figure out where to start drawing
    int maxNote = Math.min(model.getHighestNote(), this.startNote);

    // figure out where to stop drawing
    int minNote = model.getLowestNote();

    // calculate length of first measure
    int firstMeasureWidth = (4 - (startBeat % 4)) * BEATWIDTH;

    // calculate length of last measure
    int lastMeasureWidth = (model.getNumberOfBeats() % 4) * BEATWIDTH;

    // loop over all notes to draw from top to bottom
    for (int currNote = 0; currNote < maxNote - minNote + 1; currNote++) {

      // what is the current note?
      int currentNoteIntVal = (maxNote - currNote);

      // what is its string representation?
      String label =
          Pitch.getPitch(currentNoteIntVal % 12).toString() + (currentNoteIntVal / 12 - 1);

      // draw the label
      g.drawString(label, LABELX, STARTY + currNote * BEATHEIGHT + LABELVERTOFFSET);

      // if this note is a C, draw a thick line below it
      if (currentNoteIntVal % 12 == 0) {
        g.fillRect(STARTX, STARTY + (currNote + 1) * BEATHEIGHT, BEATWIDTH * (model
            .getNumberOfBeats() - this.startBeat), MIDDLECLINEHEIGHT);
      }

      // draw first measure
      g.drawRect(STARTX, STARTY + currNote * BEATHEIGHT, firstMeasureWidth, BEATHEIGHT);

      // loop over all measures from second measure (first measure already drawn)
      for (int currMeas = (startBeat / 4) + 1; currMeas < model.getNumberOfBeats() / 4;
           currMeas++) {
        g.drawRect(STARTX + firstMeasureWidth + (currMeas - (startBeat / 4) - 1) * MEASUREWIDTH,
            STARTY + currNote * BEATHEIGHT, MEASUREWIDTH, BEATHEIGHT);
      }

      // draw last measure if not full measure
      if (model.getNumberOfBeats() - startBeat - 1 % 4 != 0) {
        g.drawRect(STARTX + firstMeasureWidth + ((model.getNumberOfBeats() / 4) - (startBeat / 4) -
                1) * MEASUREWIDTH,
            STARTY + currNote * BEATHEIGHT, lastMeasureWidth, BEATHEIGHT);
      }
    }

    // draw first measure label
    g.drawString("" + startBeat, STARTX, MEASURELABELY);

    // draw rest of measure labels
    for (int currMeas = (startBeat / 4) + 1; currMeas < (model.getNumberOfBeats() / 4);
         currMeas++) {
      g.drawString("" + currMeas * 4,
          STARTX + firstMeasureWidth + (currMeas - (startBeat / 4) - 1) *
              MEASUREWIDTH, MEASURELABELY);
    }

    // draw last measure label if necessary
    if (model.getNumberOfBeats() % 4 != 0) {
      g.drawString("" + (model.getNumberOfBeats() - (model.getNumberOfBeats() % 4)), STARTX +
          firstMeasureWidth + ((model.getNumberOfBeats() / 4) - (startBeat / 4) - 1) *
          MEASUREWIDTH, MEASURELABELY);
    }

  }

  /**
   * Draw the notes of the model onto the panel.
   *
   * @param g a Graphics object
   */
  void drawNotes(Graphics g) {

    // determine highest note to draw
    int maxNote = Math.min(model.getHighestNote(), startNote);

    // for each beat to draw from
    for (int beat = startBeat; beat < model.getNumberOfBeats(); beat++) {

      // get the notes at current beat
      List<Note> notesAtBeat = model.getNotesAtBeat(beat);

      while (!notesAtBeat.isEmpty()) {

        boolean head = false; // whether or not current beat is a head
        Note prevNote = notesAtBeat.get(0); // get the first note

        // check all beats with the same pitch and octave as the prevNote
        while (!notesAtBeat.isEmpty() && notesAtBeat.get(0).sameSound(prevNote)) {

          // if any of the notes with the same sound are heads, set head = true
          if (notesAtBeat.remove(0).getStartBeat() == beat) {
            head = true;
          }
        }


        // only draw the note if it is less than or equal to the starting note
        if (prevNote.getValue() <= this.startNote) {

          // draw correct color
          if (head) {
            drawHead(g, beat - startBeat, maxNote - prevNote.getValue());
          } else {
            drawSustain(g, beat - startBeat, maxNote - prevNote.getValue());
          }

        }
      }
    }

  }

  /**
   * Draw a head of a note at the specified position in the grid.
   *
   * @param g a graphics object
   * @param x the beat at which to draw the note
   * @param y the vertical position in the grid at which to draw the note
   */
  void drawHead(Graphics g, int x, int y) {
    g.setColor(HEADCOLOR);
    g.fillRect(STARTX + x * BEATWIDTH, STARTY + y * BEATHEIGHT, BEATWIDTH, BEATHEIGHT);
  }

  /**
   * Draw a sustain of a note at the specified position in the grid.
   *
   * @param g a graphics object
   * @param x the beat at which to draw the note
   * @param y the vertical position in the grid at which to draw the note
   */
  void drawSustain(Graphics g, int x, int y) {
    g.setColor(SUSTCOLOR);
    g.fillRect(STARTX + x * BEATWIDTH, STARTY + y * BEATHEIGHT, BEATWIDTH, BEATHEIGHT);
  }

  /**
   * Draw a vertical, red progress line at the current beat.
   *
   * @param g a Graphics object.
   */
  void drawProgressLine(Graphics g) {
    g.setColor(Color.RED);
    int x = (int) Math.round(STARTX + (this.currentBeat - this.startBeat) * BEATWIDTH);
    int height = (this.startNote - model.getLowestNote() + 1) * BEATHEIGHT;
    g.drawLine(x, STARTY, x, STARTY + height);
  }

  /**
   * Get the value of the topmost row of notes drawn.
   *
   * @return value of the topmost row of notes drawn
   */
  int getStartNote() {
    return startNote;
  }

  /**
   * Modify the view so that the top row drawn is that corresponding to notes of the input value .
   * Adjust this value if it is higher than the highest note in the model or lower than the lowest
   * note in the model.
   *
   * @param note the value of the desired top row of notes.
   */
  void setStartNote(int note) {
    if (note < model.getLowestNote()) {
      note = model.getLowestNote();
    } else if (note > model.getHighestNote()) {
      note = model.getHighestNote();
    }
    this.startNote = note;
  }

  /**
   * Get the beat value of the leftmost column of notes drawn.
   *
   * @return the beat of the leftmost column
   */
  public int getStartBeat() {
    return startBeat;
  }

  /**
   * Modify the view so that the leftmost column drawn is that corresponding to notes in the beat
   * specified by the input value. Adjust this value if it is higher than the model allows or lower
   * than 0.
   */
  void setStartBeat(int beat) {
    if (beat < 0) {
      beat = 0;
    } else if (beat >= model.getNumberOfBeats()) {
      beat = model.getNumberOfBeats() - 1;
    }
    this.startBeat = beat;
  }

  /**
   * Get a Note at the specified coordinate in the Panel. Return null if no such Note exists in the
   * model.
   *
   * @param x x coord
   * @param y y coord
   * @return the existing Note, or null if nonexistent
   */
  Note getExistingNote(int x, int y) {
    int gridX = (x - STARTX) / BEATWIDTH;
    int gridY = (y - STARTY) / BEATHEIGHT;
    int beat = startBeat + gridX;
    int note = startNote - gridY;

    if (beat < 0 || beat >= model.getNumberOfBeats()) {
      return null;
    }

    List<Note> notesAtBeat = model.getNotesAtBeat(beat);

    for (Note n : notesAtBeat) {
      if (n.getValue() == note) {
        return n;
      }
    }

    return null;
  }

  /**
   * Get a new note that corresponds to the specified coordinate in the Panel. Return null if the
   * coordinate is not within the grid.
   *
   * @param x x coord
   * @param y y coord
   * @return a new Note in the grid of length 1 and default instrument/volume, or null if x and y
   * do not correspond to a point in the grid
   */
  Note getNoteFromPosition(int x, int y) {
    int gridX = (x - STARTX) / BEATWIDTH;
    int gridY = (y - STARTY) / BEATHEIGHT;
    int beat = startBeat + gridX;
    int note = startNote - gridY;

    if (beat < 0 || note < 24) {
      return null;
    }

    return new NoteImpl(note / 12 - 1, Pitch.getPitch(note % 12), beat, beat);

  }

  /**
   * Return the current beat of progress.
   *
   * @return current beat of progress.
   */
  double currentBeat() {
    return this.currentBeat;
  }

  /**
   * Set the progress line value. If too large or too small, automatically adjust. If progress line
   * is outside the bounds of the currently visible panel, then change the starting beat of the
   * view so that the startBeat is as large as possible and still display the progress line.
   *
   * @param beat a beat
   */
  void setProgress(double beat) {
    if (beat < 0) {
      beat = 0;
    } else if (beat > model.getNumberOfBeats()) {
      beat = model.getNumberOfBeats();
    }
    if (startBeat + ((this.getWidth() - STARTX) / BEATWIDTH) < beat || beat < startBeat) {
      this.setStartBeat((int) beat);
    }
    this.currentBeat = beat;

  }

  /**
   * Determine whether the input model is the exact same model this panel is renderring.
   *
   * @param model a model
   */
  boolean correspondsTo(MusicModel model) {
    return model == this.model;
  }
}

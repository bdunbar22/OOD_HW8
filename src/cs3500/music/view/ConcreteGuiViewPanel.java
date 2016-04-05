package cs3500.music.view;

import cs3500.music.model.INote;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A view that draws a graphical representation of a piece of music.
 */
public class ConcreteGuiViewPanel extends JPanel {
  private IViewPiece viewPiece;
  private static final int xGraphStep = 20;
  private static final int yGraphStep = 20;
  private static final int lowX = 40;
  private static final int lowY = 20;

  public ConcreteGuiViewPanel(IViewPiece viewPiece) {
    this.viewPiece = viewPiece;
  }

  /**
   * Displays the piece of music in a graphical format in a jpanel using graphics methods.
   * @param g graphics object
   */
  @Override
  public void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);

    // Set background color
    this.setBackground(Color.decode("#EEEEEE"));

    // Add tone labels
    this.drawTones(g);

    // Add beats
    this.drawBeats(g);

    // Add the background graph
    this.drawGraph(g);

    // Draw the current beat with a red line
    this.drawPlaceholder(g);

    this.setPreferredSize(this.getPreferredSize());
    this.revalidate();
  }

  /**
   * Give the size of the panel based on the space the contents take up.
   *
   * @return dimension for size.
   */
  @Override
  public Dimension getPreferredSize(){
    final int measure = viewPiece.getMeasure();
    final int songLength = viewPiece.getLastBeat();
    //High X depends on whether the song ends at a measure start or not.
    int highX = lowX + (measure*(songLength/measure) + measure) * xGraphStep;
    int highY = 20 + viewPiece.getToneRange().size() * yGraphStep;
    return new Dimension(highX, highY);
  }

  /**
   * Draw the Pitch and Octave for each tone within the range of tones for the song.
   * the tone range that is taken from viewPiece is sorted in order.
   * Should display in increasing order from bottom to top (writes highest y value first)
   *
   * @param g graphic object
     */
  private void drawTones(Graphics g) {
    final List<Pair<Octave, Pitch>> toneRange = viewPiece.getToneRange();
    int count = 1;
    //Print from bottom up.
    for(Pair<Octave, Pitch> tone : toneRange) {
      int y = 35 + ((toneRange.size() - count) * 20);
      g.drawString(tone.getValue().toString() + tone.getKey().toString(), 1, y);
      count++;
    }
  }

  /**
   * Draw the beats of the song.
   * Starting = black
   * Continuing = green (#00FF4F)
   * Starting notes take precedence during display
   *
   * Use same step spacing as the drawGraph function. Which displays a box outline for each measure
   * The x and y location should be the bottom left coordinate of the rectangle.
   *
   * @param g graphics object
     */
  private void drawBeats(Graphics g) {
    final List<Pair<Octave, Pitch>> toneRange = viewPiece.getToneRange();
    final Map<Integer, List<INote>> data = viewPiece.getConsolidationMap();
    List<INote> notesInBeat = new ArrayList<>();

    for(int i = 0; i <= viewPiece.getLastBeat(); i++) {
      notesInBeat = data.get(i);
      for(int j = 0; j < toneRange.size(); j++) {
        Pair<Octave, Pitch> tone = toneRange.get(j);
        int x = lowX + i * xGraphStep;
        int y = lowY + (toneRange.size() - 1 - j) * yGraphStep;

        //Display any notes that are continuing
        for(INote note : notesInBeat) {
          if(note.isPersisting(new Note(tone.getValue(), tone.getKey(), i, 1))) {
            g.setColor(Color.decode("#00FF4F"));
            g.fillRect(x, y, xGraphStep, yGraphStep);
          }
        }

        //Display any notes that are playing. Will overwrite any ones that are continuing
        //because we are more interested if something has just started.
        for(INote note : notesInBeat) {
          if(note.isStarting(new Note(tone.getValue(), tone.getKey(), i, 1))) {
            g.setColor(Color.black);
            g.fillRect(x, y, xGraphStep, yGraphStep);
          }
        }
      }
    }
    //Reset color if last was green
    g.setColor(Color.black);
  }

  /**
   * The graph drawn is a way to make it easier to read music.
   * There is a horizontal line above and below each tone in the range
   * There is a vertical line before and after each measure of the song.
   * Measures are configured against the piece of music.
   * Every fourth measure has a header number for which beat it is.
   *
   * Between octaves there is a thicker line above B and below C.
   *
   * The last full measure is boxed in even if the song ends early.
   *
   * @param g graphics object.
     */
  private void drawGraph(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    final int songLength = viewPiece.getLastBeat();
    final List<Pair<Octave, Pitch>> toneRange = viewPiece.getToneRange();
    final int toneCount = toneRange.size();
    final int measure = viewPiece.getMeasure();

    //High X depends on whether the song ends at a measure start or not.
    int highX = lowX + (measure*(songLength/measure) + measure) * xGraphStep;

    int highY = 20 + toneCount * yGraphStep;

    //Measure headers. Display every 4th measure up till the end of the song
    for(int i = 0; i <= songLength; i += 4 * measure) {
      g2.drawString(String.valueOf(i), lowX + i * xGraphStep, lowY - 2);
    }

    g2.setStroke(new BasicStroke(2));
    //All vertical lines.
    for(int i = 0; i <= songLength; i++) {
      int x = lowX + i * xGraphStep;
      if(i % measure == 0) {
        g2.drawLine(x, lowY, x, highY);
      }
    }
    //Finish last measure
    g2.drawLine(highX, lowY, highX, highY);

    //All horizontal lines
    //Horizontal lines between octaves should be bold
    //The tones are displayed from bottom to top increasing, so the bold line
    //should be above Pitch.B
    for(int i = 0; i < toneCount; i++) {
      int y = lowY + i * yGraphStep;
      if(toneRange.get(toneCount - i - 1).getValue() == Pitch.B) {
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(lowX + 1, y, highX - 2, y);
      } else {
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(lowX, y, highX, y);
      }
    }
    g2.setStroke(new BasicStroke(2));
    g2.drawLine(lowX, highY, highX, highY);
  }

  private void drawPlaceholder(Graphics g) {
    int beat = viewPiece.getBeat();
    int x = lowX + 1 + beat * xGraphStep;
    int highY = 20 + viewPiece.getToneRange().size() * yGraphStep;

    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(2));
    g.setColor(Color.decode("#FF0017"));
    g2.drawLine(x, lowY, x, highY);
  }
}

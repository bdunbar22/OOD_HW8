package cs3500.music.viewGiven.gui;

import java.awt.*;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.adapters.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.viewGiven.GuiMusicView;

/**
 * A Frame to hold GUI view
 */
public class GuiViewFrame extends JFrame implements GuiMusicView {

  protected final ConcreteGuiViewPanel displayPanel;
  private boolean isPlaying;

  /**
   * Creates a gui view from the specified model.
   *
   * @param model the MusicModel for which to view
   * @throws NullPointerException if model is null
   */
  public GuiViewFrame(MusicModel model) {
    this(new ConcreteGuiViewPanel(Objects.requireNonNull(model, "Model cannot be null")));
  }

  /**
   * Testing constructor for GuiViewFrame, allowing ConcreteGuiViewPanel to be specified.
   *
   * @param panel a ConcreteGuiViewPanel
   */
  GuiViewFrame(ConcreteGuiViewPanel panel) {
    this.displayPanel = panel;
    this.isPlaying = false;
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
  }


  /**
   * Make this visible and update.
   */
  @Override
  public void render() {
    this.setVisible(true);
    this.refresh();
  }

  /**
   * Refresh/redraw this MusicView to correspond to new state of model.
   *
   * @throws IllegalStateException if view cannot be refreshed
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1500, 500);
  }

  /**
   * Draw the view starting at the specified beat and note. If beat or note is too small or large,
   * view is drawn at earliest/highest or latest/lowest possible position.
   *
   * @param beat the beat at which to begin rendering
   * @param note the integer value of the note at which to begin rendering
   */
  @Override
  public void drawFrom(int beat, int note) {
    this.displayPanel.setStartBeat(beat);
    this.displayPanel.setStartNote(note);
  }

  /**
   * Get the current starting beat of this GuiMusicView
   *
   * @return current starting beat
   */
  @Override
  public int getStartBeat() {
    return this.displayPanel.getStartBeat();
  }

  /**
   * Get the current starting note of this GuiMusicView
   *
   * @return current starting note
   */
  @Override
  public int getStartNote() {
    return this.displayPanel.getStartNote();
  }

  /**
   * Get the note at pixel (x, y) in the view
   *
   * @param x x coord of pixel
   * @param y y coord of pixel
   */
  @Override
  public Note getExistingNote(int x, int y) {
    Insets in = this.getInsets();
    return this.displayPanel.getExistingNote(x - in.left, y - in.top);
  }

  /**
   * Return a note that has an octave, pitch, and start beat matching the selection at pixel x and
   * pixel y, even if such a note is not being rendered by the view
   *
   * @param x x coord of pixel
   * @param y y coord of pixel
   */
  @Override
  public Note getNoteFromPosition(int x, int y) {
    Insets in = this.getInsets();
    return this.displayPanel.getNoteFromPosition(x - in.left, y - in.top);
  }

  /**
   * Set the position of progress line.
   *
   * @param beat a beat in the piece
   */
  @Override
  public void setBeat(double beat) {
    this.displayPanel.setProgress(beat);
  }

  /**
   * Set state to playing.
   */
  @Override
  public void play() {
    this.isPlaying = true;
  }

  /**
   * Set state to NOT playing.
   */
  @Override
  public void pause() {
    this.isPlaying = false;
  }

  /**
   * Determine current state of playing.
   *
   * @return whether or not this view is set to "playing"
   */
  @Override
  public boolean isPlaying() {
    return isPlaying;
  }

  /**
   * Return the current progress of this view.
   *
   * @return the beat of progress
   */
  @Override
  public double currentBeat() {
    return this.displayPanel.currentBeat();
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
    return displayPanel.correspondsTo(Objects.requireNonNull(model, "Model must not be null"));
  }

}

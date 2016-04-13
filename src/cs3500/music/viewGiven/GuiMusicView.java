package cs3500.music.viewGiven;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.Note;

/**
 * Interface to encapsulate functionality of GUI views
 */
public interface GuiMusicView extends PlayableMusicView {

  /**
   * Draw the view starting at the specified beat and note. If beat or note is too small or large,
   * view is drawn at earliest/highest or latest/lowest possible position.
   *
   * @param beat the beat at which to begin rendering
   * @param note the integer value of the note at which to begin rendering
   */
  void drawFrom(int beat, int note);

  /**
   * Add the specified KeyListener to this view.
   *
   * @param listener a KeyListener
   */
  void addKeyListener(KeyListener listener);

  /**
   * Add the specified MouseListener to this view.
   *
   * @param listener a MouseListener
   */
  void addMouseListener(MouseListener listener);

  /**
   * Get the current starting beat of this GuiMusicView
   *
   * @return current starting beat
   */
  int getStartBeat();

  /**
   * Get the current starting note of this GuiMusicView
   *
   * @return current starting note
   */
  int getStartNote();

  /**
   * Get the note at pixel (x, y) in the view
   *
   * @param x x coord of pixel
   * @param y y coord of pixel
   */
  Note getExistingNote(int x, int y);

  /**
   * Return a note that has an octave, pitch, and start beat matching the selection at pixel x and
   * pixel y, even if such a note is not being rendered by the view
   *
   * @param x x coord of pixel
   * @param y y coord of pixel
   */
  Note getNoteFromPosition(int x, int y);
}

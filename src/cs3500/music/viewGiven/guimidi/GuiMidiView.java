package cs3500.music.viewGiven.guimidi;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Objects;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.viewGiven.GuiMusicView;
import cs3500.music.viewGiven.gui.GuiViewFrame;
import cs3500.music.viewGiven.midi.MidiViewImpl;

/**
 * Composite view for visual and audio playback
 */
public class GuiMidiView implements GuiMusicView {

  private final GuiMusicView gui;
  private final MidiViewImpl midi;
  private final MusicModel model;

  /**
   * Create a view from the specified model.
   *
   * @param model a MusicModel to view
   * @throws NullPointerException if model is null or MidiView cannot be accessed
   */
  public GuiMidiView(MusicModel model) {
    Objects.requireNonNull(model, "Model must not be null");
    MidiViewImpl tempMidi;
    this.gui = new GuiViewFrame(model);
    try {
      tempMidi = new MidiViewImpl(model);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
      tempMidi = null;
    }
    this.midi = Objects.requireNonNull(tempMidi);
    this.model = model;
  }

  /**
   * Testing constructor for this view, allowing midi and gui views to be manually set
   *
   * @param model a model to view
   * @param midi  a midi view
   * @param gui   a visual view
   * @throws IllegalArgumentException if model does not correspond to both views
   */
  GuiMidiView(MusicModel model, MidiViewImpl midi, GuiMusicView gui) {
    if (!midi.correspondsTo(model) || !gui.correspondsTo(model)) {
      throw new IllegalArgumentException("Model must correspond to views");
    }
    this.model = model;
    this.midi = midi;
    this.gui = gui;
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
    this.gui.drawFrom(beat, note);
  }

  /**
   * Add the specified KeyListener to this view.
   *
   * @param listener a KeyListener
   */
  @Override
  public void addKeyListener(KeyListener listener) {
    this.gui.addKeyListener(listener);
  }

  /**
   * Add the specified MouseListener to this view.
   *
   * @param listener a MouseListener
   */
  @Override
  public void addMouseListener(MouseListener listener) {
    this.gui.addMouseListener(listener);
  }

  /**
   * Get the current starting beat of this GuiMusicView
   *
   * @return current starting beat
   */
  @Override
  public int getStartBeat() {
    return this.gui.getStartBeat();
  }

  /**
   * Get the current starting note of this GuiMusicView
   *
   * @return current starting note
   */
  @Override
  public int getStartNote() {
    return this.gui.getStartNote();
  }

  /**
   * Get the note at pixel (x, y) in the view
   *
   * @param x x coord of pixel
   * @param y y coord of pixel
   */
  @Override
  public Note getExistingNote(int x, int y) {
    return this.gui.getExistingNote(x, y);
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
    return this.gui.getNoteFromPosition(x, y);
  }

  /**
   * Set the beat to playback from. Pauses audio playback.
   *
   * @param beat a beat in the piece
   */
  @Override
  public void setBeat(double beat) {
    this.midi.setBeat(beat);
    this.gui.setBeat(beat);
  }

  /**
   * Begin playback from the current beat. Update GUI as long as MIDI remains playing (in a new
   * Thread).
   */
  @Override
  public void play() {
    Thread t = new Thread(() -> {
      this.midi.play();
      this.gui.play();
      while (this.midi.isPlaying()) {
        this.gui.setBeat(this.midi.currentBeat());
        this.gui.refresh();
      }
    });
    t.start();
  }

  /**
   * Pause playback.
   */
  @Override
  public void pause() {
    this.midi.pause();
    this.gui.pause();
  }

  /**
   * Determine whether playback is in progress.
   *
   * @return whether playback is in progress
   */
  @Override
  public boolean isPlaying() {
    return this.midi.isPlaying();
  }

  /**
   * Determine the current beat of playback progress.
   *
   * @return the current beat of playback progress
   */
  @Override
  public double currentBeat() {
    return this.gui.currentBeat();
  }


  /**
   * Render this MusicView in its state as it was last update
   *
   * @throws IllegalStateException if view cannot be rendered
   */
  @Override
  public void render() {
    this.gui.render();
  }

  /**
   * Refresh/redraw this MusicView to correspond to new state of model.
   *
   * @throws IllegalStateException if view cannot be refreshed
   */
  @Override
  public void refresh() {
    this.gui.refresh();
    this.midi.refresh();
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

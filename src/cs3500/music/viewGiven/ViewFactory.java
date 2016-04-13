package cs3500.music.viewGiven;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.adapters.MusicModel;
import cs3500.music.viewGiven.gui.GuiViewFrame;
import cs3500.music.viewGiven.guimidi.GuiMidiView;
import cs3500.music.viewGiven.midi.MidiViewImpl;
import cs3500.music.viewGiven.text.TextView;

/**
 * Factory class for MusicViews
 */
public class ViewFactory {

  /**
   * Create the specified MusicView from the specified MusicModel. "console" creates a TextView to
   * System.out, "midi" creates a MidiViewImpl, "visual" creates a GuiViewFrame, and "visual-midi"
   * creates a GuiMidiView.
   *
   * @param name  a string name of type of view to create
   * @param model the model for which to create a view
   * @return a view of the model
   * @throws IllegalStateException    if MIDI cannot be accessed
   * @throws IllegalArgumentException if invalid name is entered
   * @throws NullPointerException     if model is null
   */
  public static MusicView createMusicView(String name, MusicModel model) {
    switch (name) {
      case "console":
        return new TextView(System.out, model);
      default:
        return createPlayableMusicView(name, model);
    }
  }

  /**
   * Create the specified MusicView from the specified MusicModel. "midi" creates a MidiViewImpl,
   * "visual" creates a GuiViewFrame, and "visual-midi" creates a GuiMidiView.
   *
   * @param name  a string name of type of view to create
   * @param model the model for which to create a view
   * @return a view of the model
   * @throws IllegalStateException    if MIDI cannot be accessed
   * @throws IllegalArgumentException if invalid name is entered
   * @throws NullPointerException     if model is null
   */
  public static PlayableMusicView createPlayableMusicView(String name, MusicModel model) {
    switch (name) {
      case "midi":
        try {
          return new MidiViewImpl(model);
        } catch (MidiUnavailableException e) {
          throw new IllegalStateException("MIDI cannot be accessed");
        }
      default:
        return createGuiMusicView(name, model);
    }
  }

  /**
   * Create the specified GuiMusicView from the specified MusicModel. "visual" creates a
   * GuiViewFrame, and "visual-midi" creates a GuiMidiView.
   *
   * @param name  a string name of type of view to create
   * @param model the model for which to create a view
   * @return a view of the model
   * @throws IllegalArgumentException if invalid name is entered
   * @throws NullPointerException     if model is null
   */
  public static GuiMusicView createGuiMusicView(String name, MusicModel model) {
    switch (name) {
      case "visual":
        return new GuiViewFrame(model);
      case "visual-midi":
        return new GuiMidiView(model);
      default:
        throw new IllegalArgumentException("Cannot create view of type: " + name);
    }
  }
}

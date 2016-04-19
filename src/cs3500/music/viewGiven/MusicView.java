package cs3500.music.viewGiven;

import cs3500.music.adapters.MusicModel;

/**
 * View interface for the MusicModel
 */
public interface MusicView {

  /**
   * Render this MusicView in its state as it was last update
   *
   * @throws IllegalStateException if view cannot be rendered
   */
  void render();

  /**
   * Refresh/redraw this MusicView to correspond to new state of model.
   *
   * @throws IllegalStateException if view cannot be refreshed
   */
  void refresh();

  /**
   * Determine whether the input model matches the model this view is drawing.
   *
   * @param model a MusicModel
   * @return whether or not the input model and the model being viewed are the same object
   * @throws NullPointerException if model is null
   */
  boolean correspondsTo(MusicModel model);
}

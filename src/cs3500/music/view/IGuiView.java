package cs3500.music.view;

import java.awt.*;

/**
 * Interface for use explicitly by the GuiView
 */
public interface IGuiView extends IMusicView {
  /**
   * Sets the view frame for the Gui to visible
   */
  void initialize();

  /**
   * Sets the preferred size for the Gui view frame
   *
   * @return A Dimension containing the preferred gui size
   */
  Dimension getPreferredSize();
}

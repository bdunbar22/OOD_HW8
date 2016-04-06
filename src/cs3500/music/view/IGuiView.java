package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Interface for use by the GuiView.
 * This interface needs to be implemented by a gui view that will allow for key listening ability.
 */
public interface IGuiView extends IMusicView {
  /**
   * Sets the view frame for the Gui to visible
   */
  void initialize();

  /**
   * this is to force the view to have a method to set up the keyboard. The name has been chosen
   * deliberately. This is the same method signature to add a key listener in Java Swing.
   *
   * Thus our Swing-based implementation of this interface will already have such a method.
   */
  void addKeyListener(KeyListener listener);

  /**
   * This is to force the view to have a method to set up mouse events.
   * the name is chosen based on the method signature to add a mouse listener in Java Swing.
   *
   * GuiViewFrame can easily have this if it extends JFrame, but it should add the mouse
   * listener to the panel.
   */
  void addMouseListener(MouseListener listener);
}

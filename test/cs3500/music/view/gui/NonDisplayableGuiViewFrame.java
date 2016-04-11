package cs3500.music.view.gui;

import java.awt.*;

/**
 * Mock test class for gui view
 */
public class NonDisplayableGuiViewFrame extends GuiViewFrame {

  /**
   * Override GuiViewFrames package level constructor
   *
   * @param panel a Panel to construct view for
   */
  public NonDisplayableGuiViewFrame(ConcreteGuiViewPanel panel) {
    super(panel);
  }

  /**
   * Call the mock ConcreteGuiViewPanel's paintComponent method to log note draws without actually
   * having a window pop up. Does everything super.render() does, except set visible to true
   */
  @Override
  public void render() {
    super.displayPanel.paintComponent(null);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500, 500);
  }
}

package cs3500.music.view;

import cs3500.music.model.IPiece;

import javax.swing.*;
import java.awt.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements IMusicView {

  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    this.displayPanel = new ConcreteGuiViewPanel();
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
  }

  //@Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(100, 100);
  }


  @Override
  public void viewMusic(IPiece music) {
    //TODO: this.
  }
}

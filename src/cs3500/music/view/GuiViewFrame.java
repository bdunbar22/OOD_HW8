package cs3500.music.view;

import javax.swing.*;
import java.awt.*;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends JFrame implements IGuiView {

  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

  /**
   * Creates new GuiView
   */
  public GuiViewFrame(IViewPiece viewPiece) {
    this.displayPanel = new ConcreteGuiViewPanel(viewPiece);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    JScrollPane scrollPane = new JScrollPane(displayPanel);
    scrollPane.setPreferredSize(this.getPreferredSize());
    this.getContentPane().add(scrollPane);
    this.pack();
  }

  /**
   * Make the frame visible
   */
  @Override
  public void initialize(){
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize(){
    return new Dimension(1500, 600);
  }

  /**
   * Show the graphical representation of the piece of music by making the frame visible.
   */
  @Override
  public void viewMusic() {
    this.initialize();
  }
}

package cs3500.music.view;

import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;

/**
 * A frame to display a gui for a piece of music. This will be implemented so that the frame
 * contains a scrollable object with a panel inside of it. The panel contains the graphics to
 * display the piece.
 */
public class GuiViewFrame extends JFrame implements IGuiView {
  //Required to use GuiView because JScrollPane must take a Component, which is a class NOT an
  //interface. So while GuiViewPanel is implementing the interface IGuiViewPanel, the class name
  //was required to be the type in order for JScrollPane code to compile.
  private GuiViewPanel displayPanel;
  private JScrollPane scrollPane;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame(IViewPiece viewPiece) {
    this.displayPanel = new GuiViewPanel(viewPiece);
    this.displayPanel.setPreferredSize(this.getPreferredSize());
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.scrollPane = new JScrollPane(displayPanel);
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

  /**
   * Update the view piece being used by the display panel
   */
  @Override
  public void update(IViewPiece viewPiece) {
    this.displayPanel.resetViewPiece(viewPiece);
  }

  @Override
  public void scrollToEnd() {
    Rectangle r = displayPanel.getFullRectangle();
    scrollPane.scrollRectToVisible(r);
  }

  @Override
  public void scrollToStart() {
    Rectangle r = new Rectangle(10,10);
    scrollPane.scrollRectToVisible(r);
    this.getContentPane().add(scrollPane);
    this.pack();
  }
}

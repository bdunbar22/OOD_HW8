package cs3500.music.view;

import javax.swing.*;
import java.awt.*;

/**
 * A dummy view that simply draws a string 
 */
public class ConcreteGuiViewPanel extends JPanel {
  private IPieceView pieceView;

  public ConcreteGuiViewPanel(IPieceView pieceView) {
    this.pieceView = pieceView;
  }

  @Override
  public void paintComponent(Graphics g){
    // Handle the default painting
    super.paintComponent(g);
    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    g.drawString("Hello World", 25, 25);

    //TODO: panel background: #EEE
    //TODO: start beat: #000
    //TODO: continue play color: #00FF4F
    //For future line color: #FF0017
  }
}

package cs3500.music.viewGiven.gui;

import java.awt.*;
import java.util.List;

import cs3500.music.adapters.MusicModel;

/**
 * Mock ConcreteGuiViewPanel for testing GuiViewFrame
 */
public class MockConcreteGuiViewPanel extends ConcreteGuiViewPanel {

  private List<String> operations;
  private MusicModel model;

  /**
   * Constructs a panel from the specified model.
   *
   * @param model model to create panel from
   */
  public MockConcreteGuiViewPanel(MusicModel model, List<String> operations) {
    super(model);
    this.operations = operations;
    this.model = model;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.drawNotes(g);
    this.drawProgressLine(g);
  }

  @Override
  void drawHead(Graphics g, int x, int y) {
    int note = this.getStartNote() - y;
    int beat = this.getStartBeat() + x;
    operations.add("HEAD - beat: " + beat + " note: " + note + "\t");
  }

  @Override
  void drawSustain(Graphics g, int x, int y) {
    int note = this.getStartNote() - y;
    int beat = this.getStartBeat() + x;
    operations.add("SUSTAIN - beat: " + beat + " note: " + note + "\t");
  }

  @Override
  void drawProgressLine(Graphics g) {
    operations.add("PROGRESS LINE - beat: " + this.currentBeat() + "\t");
  }
}

package cs3500.music.viewGiven;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cs3500.music.adapters.ModelFactory;
import cs3500.music.viewGiven.gui.GuiViewFrame;
import cs3500.music.viewGiven.midi.MidiViewImpl;
import cs3500.music.viewGiven.text.TextView;

import static org.junit.Assert.assertTrue;

/**
 * Class for testing view factory
 */
public class ViewFactoryTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void createViewTest() {
    assertTrue(
        ViewFactory.createMusicView("console", ModelFactory.createModel()) instanceof TextView);
    assertTrue(
        ViewFactory.createMusicView("visual", ModelFactory.createModel()) instanceof GuiViewFrame);
    assertTrue(
        ViewFactory.createMusicView("midi", ModelFactory.createModel()) instanceof MidiViewImpl);
  }

  @Test
  public void nullModelTest() {
    thrown.expect(NullPointerException.class);
    ViewFactory.createMusicView("console", null);
  }

  @Test
  public void invalidViewTypeTest() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Cannot create view of type: gui");
    ViewFactory.createMusicView("gui", ModelFactory.createModel());
  }

}

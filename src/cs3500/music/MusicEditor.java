package cs3500.music;

import cs3500.music.model.NoteList;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.GuiViewFrame;
//import cs3500.music.view.MidiViewImpl;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;


public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    /* Sam: Use musicReader to get a noteList() and then use it in each of the
    Views. I think, at least.
     */
    MusicReader musicReader;
    NoteList noteList = new NoteList();
    GuiViewFrame view = new GuiViewFrame();
    //MidiViewImpl midiView = new MidiViewImpl();
    ConsoleView consoleView = new ConsoleView();
    // You probably need to connect these views to your model, too...
  }
}

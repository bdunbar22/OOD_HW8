package cs3500.music;

import cs3500.music.controller.Controller;
import cs3500.music.controller.IController;
import cs3500.music.model.*;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.*;

import javax.sound.midi.InvalidMidiDataException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class MusicEditor {
  /**
   * Arguments to main should be the text file name and the view to use
   *
   * @param args to determine action
   * @throws IOException
   * @throws InvalidMidiDataException
     */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    try {
      String fileName =  "mystery-3.txt";/*args[0];*/
      String desiredView = "visual";/*args[1];*/
      BufferedReader in = new BufferedReader(new FileReader("text/" + fileName));
      IPiece piece = MusicReader.parseFile(in, new CompositionBuilder());
      IViewPiece viewPiece = new ViewPiece(piece);

      IMusicView view = MusicViewCreator.create(desiredView, viewPiece);
      IController controller = new Controller(piece, view);
      view.viewMusic();
    } catch (Exception exception) {
      System.out.print(exception.getMessage());
    }
  }
}

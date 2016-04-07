package cs3500.music.Controller;

import cs3500.music.controller.Controller;
import cs3500.music.controller.IController;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.model.*;
import cs3500.music.view.IMusicView;
import cs3500.music.view.IViewPiece;
import cs3500.music.view.MusicViewCreator;
import cs3500.music.view.ViewPiece;
import org.junit.Test;

import java.awt.event.KeyEvent;

import static org.junit.Assert.assertEquals;
/**
 * Created by Sam Letcher on 4/6/2016.
 */
public class ControllerTest {
  @Test
  public void controllerTest() {
    Controller test = getControllerViewHelper();
    KeyboardHandler mockKeyboard = new KeyboardHandler();
    test.start();
  }

  Controller getControllerViewHelper() {
      IPiece piece = getPieceHelper();
      IViewPiece viewPiece = new ViewPiece(piece);
      IMusicView view = MusicViewCreator.create("composite", viewPiece);
      return new Controller(piece, view);
  }

  IPiece getPieceHelper() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 2, 1, 64));
    piece.addNote(new Note(Pitch.C, new Octave(3), 0, 2, 1, 70));
    piece.addNote(new Note(Pitch.C, new Octave(4), 1, 2, 1, 80));
    piece.addNote(new Note(Pitch.C, new Octave(4), 3, 1, 2, 60));
    piece.addNote(new Note(Pitch.C, new Octave(4), 3, 1, 2, 90));
    return piece;
  }
}

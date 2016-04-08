package cs3500.music.Controller;

import cs3500.music.controller.Controller;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.model.*;
import cs3500.music.view.IMusicView;
import cs3500.music.view.IViewPiece;
import cs3500.music.view.MusicViewCreator;
import cs3500.music.view.ViewPiece;
import org.junit.Test;
/**
 * Created by Sam Letcher on 4/6/2016.
 */
public class ControllerTest {
  @Test
  public void controllerTest() {
  }

  Controller getControllerViewHelper(KeyboardHandler mockKeyboard) {
      IPiece piece = getPieceHelper();
      IViewPiece viewPiece = new ViewPiece(piece);
      IMusicView view = MusicViewCreator.create("composite", viewPiece);
      return new Controller(piece, view);
  }

  IPiece getPieceHelper() {
    IPiece piece = new Piece();
    piece.addNote(new Note(Pitch.A, new Octave(3), 0, 2, 1, 64));
    return piece;
  }
}

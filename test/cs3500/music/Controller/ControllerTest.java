package cs3500.music.Controller;

import cs3500.music.controller.Controller;
import cs3500.music.controller.IController;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.model.*;
import cs3500.music.view.*;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InvalidClassException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing controller functionality.
 *
 * Created by Sam Letcher on 4/6/2016.
 */
public class ControllerTest {
    /**
     * Test that the controller can perform actions when keys are entered and mouse events happen.
     */
      @Test
      public void controllerTest() {
          IPiece piece = getPieceHelper();
          IViewPiece viewPiece = new ViewPiece(piece);
          IMusicView view = MusicViewCreator.create("composite", viewPiece);
          IController testController;
          testController =  new Controller(piece, view);
          CompositeView helperView = (CompositeView) view;
          testController.start();
          KeyListener keyListener = helperView.getKeyListener();
          MouseListener mouseListener = helperView.getMouseListener();
          //Check key actions.
          KeyEvent mockKey = new KeyEvent(new JPanel(), KeyEvent.KEY_PRESSED, 1, 1, 1, ' ');
          mockKey.setKeyCode(KeyEvent.VK_R);
          keyListener.keyPressed(mockKey);
          piece = testController.getPiece();
          assertTrue(
              piece.getNotesInBeat(11).contains(new Note(Pitch.A, new Octave(3), 10, 2, 1, 64))
          );
          assertTrue(
              piece.getNotesInBeat(0).contains(new Note(Pitch.A, new Octave(3), 0, 3, 1, 64))
          );

          //Add a note from beats 1-4 to check mouse actions.
          MouseEvent mouseEvent1 = new MouseEvent(new JPanel(), // which
              MouseEvent.MOUSE_PRESSED, // type of mouse event
              System.currentTimeMillis(), // when
              0, // no modifiers
              65, 30, // where: at (10, 10}
              10, 10,
              1, // only 1 click
              false,
              MouseEvent.BUTTON1);

          MouseEvent mouseEvent2 = new MouseEvent(new JPanel(), // which
              MouseEvent.MOUSE_RELEASED, // type of mouse event
              System.currentTimeMillis(), // when
              0, // no modifiers
              125, 30, // where: at (10, 10}
              10, 10,
              1, // only 1 click
              false,
              MouseEvent.BUTTON1);

          mouseListener.mousePressed(mouseEvent1);
          mouseListener.mouseReleased(mouseEvent2);

          piece = testController.getPiece();
          assertTrue(
              piece.getNotesInBeat(2).contains(new Note(Pitch.A, new Octave(3), 1, 4, 1, 64)));

      }


      IPiece getPieceHelper() {
        IPiece piece = new Piece();
        piece.addNote(new Note(Pitch.A, new Octave(3), 0, 2, 1, 64));
        piece.addNote(new Note(Pitch.A, new Octave(3), 9, 3, 1, 64));
        return piece;
      }
}

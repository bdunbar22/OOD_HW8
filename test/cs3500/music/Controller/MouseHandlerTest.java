package cs3500.music.Controller;

import cs3500.music.controller.Controller;
import cs3500.music.controller.MouseHandler;
import cs3500.music.controller.MouseHandlerHelper;
import cs3500.music.model.INote;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IViewPiece;
import cs3500.music.view.ViewPiece;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static org.junit.Assert.*;

/**
 * Test that the mouse handler works correctly.
 *
 * Created by Ben on 4/7/16.
 */
public class MouseHandlerTest {
    private Controller.Toggle toggle = Controller.Toggle.MOVE;
    private String testString;

    /**
     * Ensure mouse click events are called correctly.
     */
    @Test
    public void testMouseClicked() {
        testString = "";
        MouseListener mockListener = createTestHandler();
        Component jPanel = new JPanel();

        MouseEvent mouseEvent = new MouseEvent(jPanel, // which
            MouseEvent.MOUSE_CLICKED, // type of mouse event
            System.currentTimeMillis(), // when
            0, // no modifiers
            10, 10, // where: at (10, 10}
            10, 10,
            1, // only 1 click
            false,
            MouseEvent.BUTTON3); // not a popup trigger

        jPanel.addMouseListener(mockListener);
        jPanel.dispatchEvent(mouseEvent);

        assertEquals("deleteNote1", testString);
    }

    /**
     * Test for mouse pressed.
     */
    @Test
    public void testMousePressed() {
        testString = "";
        MouseListener mockListener = createTestHandler();
        Component jPanel = new JPanel();
        MouseEvent mouseEvent = new MouseEvent(jPanel, // which
            MouseEvent.MOUSE_PRESSED, // type of mouse event
            System.currentTimeMillis(), // when
            0, // no modifiers
            10, 10, // where: at (10, 10}
            10, 10,
            1, // only 1 click
            false,
            MouseEvent.BUTTON1); // not a popup trigger

        jPanel.addMouseListener(mockListener);
        jPanel.dispatchEvent(mouseEvent);

        assertEquals("getNote", testString);
    }

    /**
     * Test for mouse released.
     */
    @Test
    public void testMouseReleased() {
        testString = "";
        MouseListener mockListener = createTestHandler();
        Component jPanel = new JPanel();
        MouseEvent mouseEvent = new MouseEvent(jPanel, // which
            MouseEvent.MOUSE_RELEASED, // type of mouse event
            System.currentTimeMillis(), // when
            0, // no modifiers
            10, 10, // where: at (10, 10}
            10, 10,
            1, // only 1 click
            false,
            MouseEvent.BUTTON1); // not a popup trigger

        jPanel.addMouseListener(mockListener);
        jPanel.dispatchEvent(mouseEvent);

        assertEquals("moveNote", testString);
    }

    /**
     * Create a handler to test
     */
    private MouseListener createTestHandler() {
        MouseListener mouseHandler = new MouseHandler(new mouseHelper(), true);
        return mouseHandler;
    }

    /**
     * Create a mouse helper.
     */
    class mouseHelper implements MouseHandlerHelper {
        //provide use of controller's deleteNote
        @Override
        public void deleteNoteFromMouse(int x, int y) {
            deleteNote(x, y);
        }

        //provide use of controller's checkForNote
        @Override
        public boolean checkForNoteFromMouse(int x, int y) {
            return checkForNote(x, y);
        }

        //provide use of controller's addNote(x,y,length value)
        @Override
        public void addNoteFromMouse(int x, int y, int dx) {
            addNote(x, y, dx);
        }

        //provide use of controller's add note with all fields.
        @Override
        public void addNoteFromMouse(int x, int y, int length, int instrument,
            int volume) {
            addNote(x, y, length, instrument, volume);
        }

        //provide use of controller's  move note
        @Override
        public void moveNoteFromMouse(INote old, Point point) {
            moveNote(old, point);
        }

        //provide use of controller's get note
        @Override
        public INote getNoteFromMouse(int x, int y) {
            return getNote(x, y);
        }

        ////provide use of controller's toggle status.
        @Override
        public Controller.Toggle getMoveToggleFromMouse() {
            return toggle;
        }

        //provide use of controllers note creation with user location input
        @Override
        public void addNoteLocationNeeded(int dx) {
            addNotePromptLocation(dx);
        }
    }

    private void addNote(int x, int y, int length) {
        testString += "addNote1";
    }

    private void addNotePromptLocation(int length) {
        testString += "addNote2";
    }

    private void addNote(int x, int y, int length, int instrument, int volume) {
        testString += "addNote3";
    }

    private void moveNote(INote note, Point newPos) {
        testString += "moveNote";
    }

    private void deleteNote(final int x, final int y) {
        testString += "deleteNote1";
    }

    private boolean checkForNote(final int x, final int y) {
        return true;
    }

    private INote getNote(final int x, final int y) {
        testString += "getNote";
        return new Note(Pitch.A, new Octave(4), 4, 3);
    }
}

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
import java.awt.event.MouseListener;

import static org.junit.Assert.*;

/**
 * Created by Ben on 4/7/16.
 */
public class MouseHandlerTest {
    private Controller.Toggle toggle;

    @Test public void testMouseClicked() throws Exception {

    }

    @Test public void testMousePressed() throws Exception {

    }

    @Test public void testMouseReleased() throws Exception {

    }

    @Test public void testMouseEntered() throws Exception {

    }

    @Test public void testMouseExited() throws Exception {

    }

    /**
     * Create fake maps and put in a handler
     */
    private MouseListener createTestHandler() {
        MouseListener mouseHandler = new MouseHandler(new mouseHelper());
        return mouseHandler;
    }


    /**
     * Provide the mouse handler with necessary functions from the controller.
     */
    class mouseHelper implements MouseHandlerHelper {
        //provide use of controller's deleteNote
        @Override public void deleteNoteFromMouse(int x, int y) {
            deleteNote(x, y);
        }

        //provide use of controller's checkForNote
        @Override public boolean checkForNoteFromMouse(int x, int y) {
            return checkForNote(x, y);
        }

        //provide use of controller's addNote(x,y,length value)
        @Override public void addNoteFromMouse(int x, int y, int dx) {
            addNote(x, y, dx);
        }

        //provide use of controller's add note with all fields.
        @Override public void addNoteFromMouse(int x, int y, int length, int instrument,
            int volume) {
            addNote(x, y, length, instrument, volume);
        }

        //provide use of controller's  move note
        @Override public void moveNoteFromMouse(INote old, Point point) {
            moveNote(old, point);
        }

        //provide use of controller's get note
        @Override public INote getNoteFromMouse(int x, int y) {
            return getNote(x, y);
        }

        ////provide use of controller's toggle status.
        @Override public Controller.Toggle getMoveToggleFromMouse() {
            return toggle;
        }

        //provide use of controllers note creation with user location input
        @Override public void addNoteLocationNeeded(int dx) {
            addNotePromptLocation(dx);
        }
    }

    /**
     * Allow for a note to be added to the piece of music in
     * question. The note pitch, octave and start beat will be determined based on the location
     * of a mouse click. The volume will be based off of the drag length of the mouse.
     */
    private void addNote(int x, int y, int length) {
        //TODO: something
    }

    /**
     * Allow for the location of the note to be given by the user as input
     * length of note is based on mouse drag distance.
     * <p>
     * Invalid data will be ignored and the app will continue to run.
     */
    private void addNotePromptLocation(int length) {
        //TODO: something
    }

    /**
     * Add a new note by passing params
     *
     * @param x          location
     * @param y          location
     * @param length     duration of the note's sustain
     * @param instrument instrument
     * @param volume     volume
     */
    private void addNote(int x, int y, int length, int instrument, int volume) {
        //TODO: something
    }

    /**
     * Move a note to a different starting beat, pitch and octave.
     *
     * @param note   that is being moved
     * @param newPos to move to on view
     */
    private void moveNote(INote note, Point newPos) {
        //TODO: something
    }

    /**
     * If possible will delete the chosen note from the piece.
     *
     * @param x coordinate in view.
     * @param y coordinate in view.
     */
    private void deleteNote(final int x, final int y) {
        //TODO: something
    }

    /**
     * Delete the given note
     *
     * @param note to delete
     */
    private void deleteNote(INote note) {
        //TODO: something
    }

    /**
     * Determine if getting a note will work.
     *
     * @param x location
     * @param y location
     * @return boolean if note will be retrieved correctly.
     */
    private boolean checkForNote(final int x, final int y) {
        //TODO: something
        return true;
    }

    /**
     * Retrieves the note that was clicked on in the GUI.
     *
     * @param x mouse location
     * @param y mouse location
     * @return note that was found
     */
    private INote getNote(final int x, final int y) {
        //TODO: something
        return new Note(Pitch.A, new Octave(4), 4, 3);
    }
}

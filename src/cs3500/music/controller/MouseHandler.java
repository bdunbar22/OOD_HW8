package cs3500.music.controller;

import cs3500.music.model.INote;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Allow for mouse events to cause edits to the model via controller functions.
 * <p>
 * Created by Ben on 4/6/16.
 */
public class MouseHandler implements MouseListener {
    private Point mousePoint;
    private boolean noteFound;
    private MouseHandlerHelper mouseHandlerHelper;
    private INote currentNote;

    /**
     * Empty default constructor
     */
    public MouseHandler(MouseHandlerHelper mouseHandlerHelper) {
        this.mouseHandlerHelper = mouseHandlerHelper;
    }

    /**
     * Constructor with note found for testing.
     */
    public MouseHandler(MouseHandlerHelper mouseHandlerHelper, boolean noteFound) {
        this.mouseHandlerHelper = mouseHandlerHelper;
        this.noteFound = noteFound;
    }

    /**
     * handle when mouse is clicked. Right button clicks delete notes.
     *
     * @param e mouse event
     */
    @Override public void mouseClicked(MouseEvent e) {
        //Right click will delete note
        //Left click will add a note
        switch (e.getButton()) {
            case MouseEvent.BUTTON3:
                mouseHandlerHelper.deleteNoteFromMouse(e.getX(), e.getY());
                break;
        }
    }

    /**
     * Handle when the mouse is pressed. The location of the press is stored and if possible
     * the note on the gui that was pressed is stored.
     *
     * @param e mouse event
     */
    @Override public void mousePressed(MouseEvent e) {
        try {
            mousePoint = e.getPoint();
            noteFound = mouseHandlerHelper.checkForNoteFromMouse(e.getX(), e.getY());
            if (noteFound) {
                currentNote = mouseHandlerHelper.getNoteFromMouse(e.getX(), e.getY());
            } else {
                currentNote = null;
            }
        } catch (Exception exc) {
            //Do nothing if unusual input doesn't work.
        }
    }

    /**
     * Handle when mouse is released.
     * Depending on the operating mode of the controller a note should either be created, moved
     * or copying into a new
     * location.
     *
     * @param e mouse event
     */
    @Override public void mouseReleased(MouseEvent e) {
        try {
            int dx = 1;
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    switch (mouseHandlerHelper.getMoveToggleFromMouse()) {
                        case ADD:
                            dx = e.getX() - mousePoint.x;
                            mouseHandlerHelper.addNoteFromMouse(mousePoint.x, mousePoint.y, dx);
                            break;
                        case COPY:
                            if (noteFound) {
                                mouseHandlerHelper.addNoteFromMouse(e.getX(), e.getY(),
                                    (currentNote.getDuration() - 1) * 20,
                                    currentNote.getInstrument(), currentNote.getVolume());
                            }
                            break;
                        case MOVE:
                            if (noteFound) {
                                mouseHandlerHelper.moveNoteFromMouse(currentNote, e.getPoint());
                            }
                            break;
                        case LOCATION:
                            dx = e.getX() - mousePoint.x;
                            mouseHandlerHelper.addNoteLocationNeeded(dx);
                    }
                    break;
            }
        } catch (Exception exc) { /*Do nothing.*/ }
    }

    @Override public void mouseEntered(MouseEvent e) {
        // Nothing should be done, this is just mouse entering the part of the screen while
        // hovering.
    }

    @Override public void mouseExited(MouseEvent e) {
        // Nothing should be done, this is just mouse leaving the part of the screen while
        // hovering.
    }
}

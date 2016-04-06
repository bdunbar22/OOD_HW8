package cs3500.music.controller;

import cs3500.music.model.INote;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Allow for mouse events to cause edits to the model via controller functions.
 *
 * Created by Ben on 4/6/16.
 */
public class MouseHandler implements MouseListener {
    private Point mousePoint;
    private boolean noteFound;
    private MouseHandlerHelper mouseHandlerHelper;

    /**
     * Empty default constructor
     */
    public MouseHandler(MouseHandlerHelper mouseHandlerHelper) {
        this.mouseHandlerHelper = mouseHandlerHelper;
    }

    /**
     * handle when mouse is clicked. Left click will add a note and right click will delete
     * a note.
     *
     * @param e mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //Right click will delete note
        //Left click will add a note
        switch (e.getButton()) {
            case MouseEvent.BUTTON3:
                mouseHandlerHelper.deleteNoteFromMouse(e.getX(), e.getY());
                break;
        }
    }

    @Override
    public void mousePressed (MouseEvent e){
        mousePoint = e.getPoint();
        noteFound = mouseHandlerHelper.checkForNoteFromMouse(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    if (!mouseHandlerHelper.getMoveToggleFromMouse()) {
                        int dx = e.getX() - mousePoint.x;
                        mouseHandlerHelper.addNoteFromMouse(mousePoint.x, mousePoint.y, dx);
                    } else {
                        if (noteFound) {
                            INote oldNote = mouseHandlerHelper.getNoteFromMouse(mousePoint.x, mousePoint.y);
                            mouseHandlerHelper.moveNoteFromMouse(oldNote, e.getPoint());
                        }
                    }
                    break;
            }
        }
        catch (Exception exc) { /*Do nothing.*/ }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Nothing should be done, this is just mouse entering the part of the screen while
        // hovering.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Nothing should be done, this is just mouse leaving the part of the screen while
        // hovering.
    }
}

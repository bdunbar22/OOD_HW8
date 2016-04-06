package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Allow for mouse events to cause edits to the model via controller functions.
 *
 * Created by Ben on 4/4/16.
 */
public class MouseHandler implements MouseListener {
    private Map<Integer, Runnable> buttonMap;

    /**
     * Empty default constructor
     */
    public MouseHandler() {
    }

    /**
     * Set the map for key type events. Key typed events in Java Swing are characters
     */
    public void setButtonMap(Map<Integer, Runnable> map) {
        buttonMap = map;
    }

    /**
     * handle when mouse is clicked.
     * @param e mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (buttonMap.containsKey(e.getButton()))
            buttonMap.get(e.getButton()).run();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //TODO: this
        int i = 5;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //TODO: this
        int i = 5;
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

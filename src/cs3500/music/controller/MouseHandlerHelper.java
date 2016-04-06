package cs3500.music.controller;

import cs3500.music.model.INote;

import java.awt.*;

/**
 * Allow for the mouse handler to access functions it does not have. Similar to how the
 * Keyboard Handler has maps of Runnable, except instead of a runnable this is a class with
 * multiple functions with parameters that can be called.
 *
 * Created by Ben on 4/6/16.
 */
public interface MouseHandlerHelper {
    void deleteNoteFromMouse(int x, int y);

    boolean checkForNoteFromMouse(int x, int y);

    void addNoteFromMouse(int x, int y, int dx);

    void moveNoteFromMouse(INote old, Point point);

    INote getNoteFromMouse(int x, int y);

    boolean getMoveToggleFromMouse();
}

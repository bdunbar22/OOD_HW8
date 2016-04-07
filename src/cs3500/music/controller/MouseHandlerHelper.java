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
    /**
     * provide use of controller's deleteNote.
     *
     * @param x location
     * @param y location
     */
    void deleteNoteFromMouse(int x, int y);

    /**
     * provide use of controller's check for note.
     *
     * @param x location
     * @param y location
     * @return if note is present at mouse location
     */
    boolean checkForNoteFromMouse(int x, int y);

    /**
     * provide use of controller's add note limited fields.
     *
     * @param x location
     * @param y location
     * @param dx distance mouse travelled
     */
    void addNoteFromMouse(int x, int y, int dx);


    /**
     * provide use of controller's add note limited fields.
     *
     * @param x location
     * @param y location
     * @param dx distance mouse travelled
     * @param instrument to use
     * @param volume to set
     */
    void addNoteFromMouse(int x, int y, int dx, int instrument, int volume);

    /**
     * Allow for controller to move a note.
     *
     * @param old note being moved
     * @param point to put note at, starts at corresponding location
     */
    void moveNoteFromMouse(INote old, Point point);

    /**
     * Get a note based on the controllers get note function.
     *
     * @param x location
     * @param y location
     * @return note found
     */
    INote getNoteFromMouse(int x, int y);

    /**
     * Determine the operating status of the controller. Adding/Moving/ or Copying mode.
     *
     * @return mode of operation enum
     */
    Controller.Toggle getMoveToggleFromMouse();

    /**
     * Allow for notes to be added by specifying location
     * @param dx length
     */
    void addNoteLocationNeeded(int dx);
}

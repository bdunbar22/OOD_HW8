package cs3500.music.view;

import cs3500.music.model.INote;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Interface for use by the GuiView.
 * This interface needs to be implemented by a gui view that will allow for key listening ability.
 */
public interface IGuiView extends IMusicView {
    /**
     * Sets the view frame for the Gui to visible
     */
    void initialize();

    /**
     * this is to force the view to have a method to set up the keyboard. The name has been chosen
     * deliberately. This is the same method signature to add a key listener in Java Swing.
     * <p>
     * Thus our Swing-based implementation of this interface will already have such a method.
     */
    void addKeyListener(KeyListener listener);

    /**
     * This is to force the view to have a method to set up mouse events.
     * the name is chosen based on the method signature to add a mouse listener in Java Swing.
     * <p>
     * GuiViewFrame can easily have this if it extends JFrame, but it should add the mouse
     * listener to the panel.
     */
    void addMouseListener(MouseListener listener);

    /**
     * Scrolls the viewport to the end of the piece
     */
    void scrollToEnd();

    /**
     * Scrolls the viewport to the start of the piece
     */
    void scrollToStart();

    /**
     * Scroll the viewport up.
     */
    void scrollUp();

    /**
     * Scroll the viewport down.
     */
    void scrollDown();

    /**
     * Scroll the viewport right.
     */
    void scrollRight();

    /**
     * Scroll the viewport left.
     */
    void scrollLeft();

    /**
     * Based on the x and y coordinates provided this function will return the note chosen.
     *
     * @param x location
     * @param y location
     * @return note
     */
    INote getNoteFromLocation(final int x, final int y);

    /**
     * Based on the x and y coordinates provided this function make a note with the correct
     * parameters based on the song being displayed.
     *
     * @param x location
     * @param y location
     * @return note
     */
    INote makeNoteFromLocation(final int x, final int y, final int length);

    /**
     * Display music according to given beat.
     *
     * @param currentBeat to view for
     */
    void playBeat(final int currentBeat);
}


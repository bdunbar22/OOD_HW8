package cs3500.music.view;

import cs3500.music.model.INote;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * A view that implements the IGuiView and allows for the composite display of the Midi and
 * Gui views at the same time.
 * This view will implement the IGuiView so that it offers the add listeners for the keyboard
 * and mouse, which will allow editing to be possible.
 * <p>
 * Created by Ben on 4/4/16.
 */
public class CompositeView implements IGuiView {
    MidiViewImpl midiView;
    IGuiView guiViewFrame;
    IViewPiece viewPiece;
    KeyListener listenerKey;
    MouseListener listenerMouse;
    int startBeat;

    /**
     * Constructor for composite view.
     *
     * @param viewPiece to use for viewing
     */
    public CompositeView(IViewPiece viewPiece) {
        midiView = new MidiViewImpl(viewPiece);
        guiViewFrame = new GuiViewFrame(viewPiece);
        this.viewPiece = viewPiece;
        this.startBeat = 0;
    }

    /**
     * An audible representation of music is given in this view. This should play based on the
     * current beat of the song. The controller can updateViewPiece the song and then view music
     * at the
     * next beat again.
     */
    @Override
    public void viewMusic() {
        guiViewFrame.viewMusic();
        midiView.viewMusicPerBeat(startBeat);
    }

    /**
     * Update the view piece being used by the member views.
     *
     * @param viewPiece new model view to update with.
     */
    @Override
    public void updateViewPiece(IViewPiece viewPiece) {
        guiViewFrame.updateViewPiece(viewPiece);
        midiView.updateViewPiece(viewPiece);
    }

    /**
     * Want the gui part of this view to listen to the keyboard.
     *
     * @param listener for keys
     */
    @Override
    public void addKeyListener(KeyListener listener) {
        guiViewFrame.addKeyListener(listener);
        this.listenerKey = listener;
    }

    //For testing
    public KeyListener getKeyListener() { return listenerKey; }

    //For testing
    public MouseListener getMouseListener() { return listenerMouse; }

    /**
     * Want the gui part of this view to listen to the mouse.
     *
     * @param listener for mouse
     */
    @Override
    public void addMouseListener(MouseListener listener) {
        guiViewFrame.addMouseListener(listener);
        this.listenerMouse = listener;
    }

    /**
     * Offer the gui frames initialize.
     */
    @Override
    public void initialize() {
        guiViewFrame.initialize();
    }

    /**
     * Allow composite view to give the get note from location by using the gui views method.
     *
     * @return note
     */
    @Override
    public INote getNoteFromLocation(int x, int y) {
        return guiViewFrame.getNoteFromLocation(x, y);
    }

    /**
     * Allow composite view to make the note from location by using the gui views method.
     * @return note
     */
    @Override
    public INote makeNoteFromLocation(int x, int y, int length) {
        return guiViewFrame.makeNoteFromLocation(x, y, length);
    }

    /**
     * Scroll to start.
     */
    @Override
    public void scrollToStart() {
        guiViewFrame.scrollToStart();
    }

    /**
     * Scroll to end.
     */
    @Override
    public void scrollToEnd() {
        guiViewFrame.scrollToEnd();
    }

    /**
     * Scroll upwards.
     */
    @Override
    public void scrollUp() {
        guiViewFrame.scrollUp();
    }

    /**
     * Scroll right.
     */
    @Override
    public void scrollRight() {
        guiViewFrame.scrollRight();
    }

    /**
     * Scroll down.
     */
    @Override
    public void scrollDown() {
        guiViewFrame.scrollDown();
    }

    /**
     * Scroll left.
     */
    @Override
    public void scrollLeft() {
        guiViewFrame.scrollLeft();
    }

    /**
     * Play the notes that will be audible at this beat.
     *
     * @param currentBeat to view for
     */
    @Override
    public void playBeat(final int currentBeat) {
        this.guiViewFrame.playBeat(currentBeat);
        this.midiView.viewMusicPerBeat(currentBeat);
    }
}

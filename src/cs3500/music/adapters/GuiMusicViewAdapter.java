package cs3500.music.adapters;

import cs3500.music.model.INote;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IViewPiece;
import cs3500.music.viewGiven.GuiMusicView;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Implement the IGuiView using the provided GuiMusicView.
 *
 * Created by Ben on 4/18/16.
 */
public class GuiMusicViewAdapter implements IGuiView {
    private GuiMusicView guiMusicView;

    /**
     * Create the adapter from an existing gui music view.
     *
     * @param guiMusicView to use.
     */
    public GuiMusicViewAdapter(GuiMusicView guiMusicView) {
        this.guiMusicView = guiMusicView;
    }

    /**
     * Allow for the normal display of music.
     */
    @Override public void viewMusic() {
        this.guiMusicView.render();
    }

    /**
     * Allow for the addition of a key listener.
     *
     * @param listener to add.
     */
    @Override
    public void addKeyListener(KeyListener listener) {
        this.guiMusicView.addKeyListener(listener);
    }

    /**
     * Allow for the addition of a mouse listener.
     */
    @Override
    public void addMouseListener(MouseListener listener) {
        this.guiMusicView.addMouseListener(listener);
    }

    /**
     * Get a note from a specified location.
     *
     * @param x location
     * @param y location
     * @return note
     */
    @Override
    public INote getNoteFromLocation(int x, int y) {
        Note note = this.guiMusicView.getExistingNote(x, y);
        return note.getNote();
    }

    /**
     * make a note from a specified location.
     *
     * @param x location
     * @param y location
     * @param length to make, given in pixels.
     * @return note.
     */
    @Override public INote makeNoteFromLocation(int x, int y, int length) {
        Note note = this.guiMusicView.getNoteFromPosition(x, y);
        INote note1 = note.getNote();
        note1.setDuration(length/20);
        return note1;
    }

    /**
     * Override the initialize function.
     */
    @Override
    public void initialize() {
        //Done automatically with render.
    }

    //TODO: implement
    @Override public void playBeat(int currentBeat) {

    }

    @Override public void scrollToStart() {
        //TODO: for scrolling use draw from, get start beat, and get start note.
        //see GuiViewFrame for details.
    }

    @Override public void scrollUp() {

    }

    @Override public void scrollDown() {

    }

    @Override public void scrollRight() {

    }

    @Override public void scrollLeft() {

    }

    @Override public void scrollToEnd() {

    }

    /**
     * Use the update and refresh functions of the gui music view to provide the update view
     * piece functionality.
     *
     * @param viewPiece new model view to update with.
     */
    @Override
    public void updateViewPiece(IViewPiece viewPiece) {
        this.guiMusicView.updateModel(new MusicModelImpl(viewPiece));
        this.guiMusicView.refresh();
    }
}

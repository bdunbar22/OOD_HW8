package cs3500.music.adapters;

import cs3500.music.model.INote;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IViewPiece;
import cs3500.music.viewGiven.GuiMusicView;
import cs3500.music.viewGiven.gui.GuiViewFrame;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Implement the IGuiView using the provided GuiMusicView.
 * This allows for the provided GuiMusicView to act like the IGuiView originally designed. In
 * this way the gui view provided is adapted to the existing gui view and the controller does
 * not need any modifications.
 *
 * Created by Ben on 4/18/16.
 */
public class GuiMusicViewAdapter implements IGuiView {
    private GuiMusicView guiMusicView;

    private static final int TEMPO_TO_PERIOD = 1000;

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
    @Override
    public void viewMusic() {
        this.guiMusicView.render();
        this.playBeat(0);
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
     *
     * @param listener mouse listener.
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
        note1.setDuration(length/20 + 1);
        return note1;
    }

    /**
     * Override the initialize function.
     */
    @Override
    public void initialize() {
        //Done automatically with render.
    }

    /**
     * Allow for a single beat of a song to be played. Allow for a refresh of the midi view.
     *
     * @param currentBeat to view for
     */
    @Override
    public void playBeat(int currentBeat) {
        if(this.guiMusicView instanceof GuiViewFrame) {
            this.guiMusicView.setBeat(currentBeat);
            this.guiMusicView.refresh();
            return;
        }
        Thread t = new Thread(() -> {
                this.guiMusicView.setBeat(currentBeat);
                this.guiMusicView.play();
                long waitTime = this.guiMusicView.getModel().getTempo()/TEMPO_TO_PERIOD;
                try {
                    Thread.sleep(waitTime);
                }
                catch (InterruptedException e) {
                    //do nothing.
                }
                this.guiMusicView.pause();
        });
        t.start();
    }

    /**
     * Allow for scrolling to the start of a piece.
     */
    @Override
    public void scrollToStart() {
        guiMusicView.drawFrom(0, guiMusicView.getStartNote());
        guiMusicView.refresh();
    }

    /**
     * Allow for scrolling upwards on the gui.
     */
    @Override
    public void scrollUp() {
        guiMusicView.drawFrom(
            guiMusicView.getStartBeat(),
            guiMusicView.getStartNote() + 1);
        guiMusicView.refresh();
    }

    /**
     * Allow for scrolling down on the gui.
     */
    @Override
    public void scrollDown() {
        guiMusicView.drawFrom(
            guiMusicView.getStartBeat(),
            guiMusicView.getStartNote() - 1);
        guiMusicView.refresh();
    }

    /**
     * Allow for scrolling right on the gui.
     */
    @Override
    public void scrollRight() {
        guiMusicView.drawFrom(
            guiMusicView.getStartBeat() + 1,
            guiMusicView.getStartNote());
        guiMusicView.refresh();
    }

    /**
     * Allow for scrolling left on the gui.
     */
    @Override
    public void scrollLeft() {
        guiMusicView.drawFrom(
            guiMusicView.getStartBeat() - 1,
            guiMusicView.getStartNote());
        guiMusicView.refresh();
    }

    /**
     * Allow to scroll to the end of the gui view.
     */
    @Override public void scrollToEnd() {
        guiMusicView.drawFrom(
            guiMusicView.getModel().getNumberOfBeats() - getPreferredSize().width/20 + 10,
            guiMusicView.getStartNote());
        guiMusicView.refresh();
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

    //Get the normal size that is used for the gui.
    private Dimension getPreferredSize() {
        return new Dimension(1500, 500);
    }
}

package cs3500.music.adapters;

import cs3500.music.model.INote;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IViewPiece;
import cs3500.music.viewGiven.GuiMusicView;
import cs3500.music.viewGiven.guimidi.GuiMidiView;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

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
        this.guiMusicView.play();
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

    /**
     * Allow for a single beat of a song to be played.
     *
     * @param currentBeat to view for
     */
    @Override
    public void playBeat(int currentBeat) {
        this.guiMusicView.pause();
        this.guiMusicView.setBeat(currentBeat);
        this.guiMusicView.play();
        this.guiMusicView.pause();

    }

    @Override public void scrollToStart() {
        //TODO: for scrolling use draw from, get start beat, and get start note.
        //see GuiViewFrame for details.
        guiMusicView.drawFrom(0, guiMusicView.getStartNote());
        guiMusicView.refresh();
    }

    @Override public void scrollUp() {
        guiMusicView.drawFrom(
            guiMusicView.getStartBeat(),
            guiMusicView.getStartNote() + 1);
        guiMusicView.refresh();
    }

    @Override public void scrollDown() {
        guiMusicView.drawFrom(
            guiMusicView.getStartBeat(),
            guiMusicView.getStartNote() - 1);
        guiMusicView.refresh();
    }

    @Override public void scrollRight() {
        guiMusicView.drawFrom(
            guiMusicView.getStartBeat() + 1,
            guiMusicView.getStartNote());
        guiMusicView.refresh();
    }

    @Override public void scrollLeft() {
        guiMusicView.drawFrom(
            guiMusicView.getStartBeat() - 1,
            guiMusicView.getStartNote());
        guiMusicView.refresh();
    }

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

    private Dimension getPreferredSize() {
        if (guiMusicView instanceof cs3500.music.viewGiven.gui.GuiViewFrame) {
            cs3500.music.viewGiven.gui.GuiViewFrame temp
                = (cs3500.music.viewGiven.gui.GuiViewFrame) guiMusicView;
            return temp.getPreferredSize();
        }
        else if (guiMusicView instanceof GuiMidiView) {
            GuiMidiView temp = (GuiMidiView) guiMusicView;
            temp
        }
    }
}

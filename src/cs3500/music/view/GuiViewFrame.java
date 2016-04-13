package cs3500.music.view;

import cs3500.music.model.INote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * A frame to display a gui for a piece of music. This will be implemented so that the frame
 * contains a scrollable object with a panel inside of it. The panel contains the graphics to
 * display the piece.
 */
public class GuiViewFrame extends JFrame implements IGuiView {
    //Required to use GuiView because JScrollPane must take a Component, which is a class NOT an
    //interface. So while GuiViewPanel is implementing the interface IGuiViewPanel, the class name
    //was required to be the type in order for JScrollPane code to compile.
    private GuiViewPanel displayPanel;
    private JScrollPane scrollPane;

    /**
     * Creates new GuiView
     */
    public GuiViewFrame(IViewPiece viewPiece) {
        this.displayPanel = new GuiViewPanel(viewPiece);
        this.displayPanel.setPreferredSize(this.getPreferredSize());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.scrollPane = new JScrollPane(displayPanel);
        this.getContentPane().add(scrollPane);
        this.pack();
    }

    public GuiViewFrame(IViewPiece viewPiece, JScrollPane scrollPane) {
        this.displayPanel = new GuiViewPanel(viewPiece);
        this.displayPanel.setPreferredSize(this.getPreferredSize());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.scrollPane = scrollPane;
        this.getContentPane().add(scrollPane);
        this.pack();
    }

    /**
     * Make the frame visible
     */
    @Override public void initialize() {
        this.setVisible(true);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(1500, 600);
    }

    /**
     * Show the graphical representation of the piece of music by making the frame visible.
     */
    @Override public void viewMusic() {
        this.initialize();
    }

    /**
     * Update the view piece being used by the display panel
     */
    @Override public void updateViewPiece(IViewPiece viewPiece) {
        this.displayPanel.resetViewPiece(viewPiece);
    }

    /**
     * Add the mouse listener to the panel.
     *
     * @param listener to add
     */

    @Override public void addMouseListener(MouseListener listener) {
        this.displayPanel.addMouseListener(listener);
    }

    /**
     * Return a note from the given location.
     *
     * @param x location
     * @param y location
     * @return note object if found.
     */
    @Override public INote getNoteFromLocation(int x, int y) {
        return displayPanel.getNoteFromLocation(x, y);
    }


    /**
     * Make a note from the given location.
     *
     * @param x location
     * @param y location
     * @return note object made.
     */
    @Override public INote makeNoteFromLocation(int x, int y, int length) {
        return displayPanel.makeNoteFromLocation(x, y, length);
    }

    @Override public void scrollToEnd() {
        Rectangle r = displayPanel.getFullRectangle();
        displayPanel.scrollRectToVisible(r);
    }

    @Override public void scrollToStart() {
        Rectangle r = new Rectangle(0, 0);
        displayPanel.scrollRectToVisible(r);
    }

    @Override public void playBeat(final int currentBeat) {
        this.displayPanel.updateBeat(currentBeat);
    }

    @Override public void scrollUp() {
        Rectangle r = displayPanel.getVisibleRect();
        r.translate(0, -30);
        displayPanel.scrollRectToVisible(r);
    }

    @Override public void scrollRight() {
        Rectangle r = displayPanel.getVisibleRect();
        r.translate(30, 0);
        displayPanel.scrollRectToVisible(r);
    }

    @Override public void scrollDown() {
        Rectangle r = displayPanel.getVisibleRect();
        r.translate(0, 30);
        displayPanel.scrollRectToVisible(r);
    }

    @Override public void scrollLeft() {
        Rectangle r = displayPanel.getVisibleRect();
        r.translate(-30, 0);
        displayPanel.scrollRectToVisible(r);
    }
}

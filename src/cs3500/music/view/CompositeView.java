package cs3500.music.view;


import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * A view that implements the IGuiView and allows for the composite display of the Midi and
 * Gui views at the same time.
 * This view will implement the IGuiView so that it offers the add listeners for the keyboard
 * and mouse, which will allow editing to be possible.
 *
 * Created by Ben on 4/4/16.
 */
public class CompositeView implements IGuiView {
    IMusicView midiViewImpl;
    IGuiView guiViewFrame;

    public CompositeView(IViewPiece viewPiece) {
        midiViewImpl = new MidiViewImpl(viewPiece);
        guiViewFrame = new GuiViewFrame(viewPiece);
    }

    /**
     * An audible representation of music is given in this view. This should play based on the
     * current beat of the song. The controller can update the song and then view music at the
     * next beat again.
     */
    @Override
    public void viewMusic() {
        guiViewFrame.viewMusic();
        midiViewImpl.viewMusic();
    }

    /**
     * Update the view piece being used by the member views.
     */
    @Override
    public void update(IViewPiece viewPiece) {
        guiViewFrame.update(viewPiece);
        midiViewImpl.update(viewPiece);
    }

    /**
     * Want the gui part of this view to listen to the keyboard.
     * @param listener for keys
     */
    @Override
    public void addKeyListener(KeyListener listener) {
        guiViewFrame.addKeyListener(listener);
    }

    /**
     * Want the gui part of this view to listen to the mouse.
     * @param listener for mouse
     */
    @Override
    public void addMouseListener(MouseListener listener) {
        guiViewFrame.addMouseListener(listener);
    }

    /**
     * Offer the gui frames initialize.
     */
    @Override
    public void initialize(){
        guiViewFrame.initialize();
    }
}

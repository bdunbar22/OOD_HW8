package cs3500.music.view;


/**
 * A view that implements the IMusicView and allows for the composite display of the Midi and
 * Gui views at the same time.
 *
 * Created by Ben on 4/4/16.
 */
public class CompositeView implements IMusicView {
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
}

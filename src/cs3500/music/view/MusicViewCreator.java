package cs3500.music.view;

import cs3500.music.adapters.MusicModel;
import cs3500.music.adapters.MusicModelImpl;
import cs3500.music.adapters.TextViewAdapter;
import cs3500.music.model.IPiece;

/**
 * Allow for the different classes that implement IMusicView to be created at runtime.
 * <p>
 * Created by Ben on 3/23/16.
 */
public class MusicViewCreator {
    public MusicViewCreator() {
        //Empty constructor. Method provided is static
    }

    /**
     * Create the desired class.
     *
     * @param viewType  to choose.
     * @param viewPiece of music to display.
     * @return the IMusicView created.
     */
    public static IMusicView create(String viewType, IViewPiece viewPiece) {
        MusicModel musicModel = new MusicModelImpl(viewPiece);
        switch (viewType) {
            case "console":
                return new ConsoleView(viewPiece);
            case "console2":
                return new TextViewAdapter(musicModel);
            case "visual":
                return new GuiViewFrame(viewPiece);
            case "midi":
                return new MidiViewImpl(viewPiece);
            case "composite":
                return new CompositeView(viewPiece);
            default:
                throw new IllegalArgumentException(
                    "This view type is not supported. Please choose"
                        + "one of the following: \"console\", \"visual\", \"midi\", \"composite\"");
        }
    }
}

package cs3500.music.view;

import cs3500.music.adapters.MusicModel;
import cs3500.music.adapters.MusicModelImpl;
import cs3500.music.adapters.MusicViewAdapter;
import cs3500.music.viewGiven.MusicView;
import cs3500.music.viewGiven.ViewFactory;

/**
 * Allow for the different classes that implement IMusicView to be created at runtime.
 * Choices:
 * console: textual representation
 * visual: gui representation
 * midi: plays music
 * composite: plays music and shows gui
 *
 * console2, visual2, midi2, composite2
 * These choices are the options to use the provided code.
 *
 * Created by Ben on 3/23/16.
 */
public class MusicViewCreator {
    public MusicViewCreator() {
        //Empty constructor. Method provided is static
    }

    /**
     * Create the desired class.
     * Allows for the use of original views: console, visual, midi, composite.
     * Allows for the use of provided view: console2, visual2, midi2, composite2.
     *
     * @param viewType  to choose.
     * @param viewPiece of music to display.
     * @return the IMusicView created.
     */
    public static IMusicView create(String viewType, IViewPiece viewPiece) {
        MusicModel musicModel = new MusicModelImpl(viewPiece);
        MusicView musicView;
        switch (viewType) {
            case "console":
                return new ConsoleView(viewPiece);
            case "console2":
                musicView = ViewFactory.createMusicView("console", musicModel);
                return new MusicViewAdapter(musicView);
            case "visual":
                return new GuiViewFrame(viewPiece);
            case "visual2":
                //TODO: make an adapter from gui music view to IGuiView and then use it
                //instead of the music view adapter here.
                musicView = ViewFactory.createMusicView("visual", musicModel);
                return new MusicViewAdapter(musicView);
            case "midi":
                return new MidiViewImpl(viewPiece);
            case "midi2":
                //TODO: make an adapter from playable music view to a ICompositeView
                // use it instead of the music view adapter here.
                musicView = ViewFactory.createMusicView("midi", musicModel);
                return new MusicViewAdapter(musicView);
            case "composite":
                return new CompositeView(viewPiece);
            case "composite2":
                //TODO: make an adapter from playable music view to a
                // ICompositeView
                // use it instead of the music view adapter here.
                musicView = ViewFactory.createMusicView("visual-midi", musicModel);
                return new MusicViewAdapter(musicView);
            default:
                throw new IllegalArgumentException(
                    "This view type is not supported. Please choose"
                        + "one of the following: \"console\", \"visual\", \"midi\", "
                        + "\"composite\", \"console2\", \"visual2\", \"midi2\", "
                        + "\"composite2\"");
        }
    }
}

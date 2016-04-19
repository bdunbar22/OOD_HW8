package cs3500.music.view;

import cs3500.music.adapters.GuiMusicViewAdapter;
import cs3500.music.adapters.MusicModel;
import cs3500.music.adapters.MusicModelImpl;
import cs3500.music.adapters.MusicViewAdapter;
import cs3500.music.viewGiven.GuiMusicView;
import cs3500.music.viewGiven.MusicView;
import cs3500.music.viewGiven.PlayableMusicView;
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
        GuiMusicView guiMusicView;
        PlayableMusicView playableMusicView;
        switch (viewType) {
            case "console":
                return new ConsoleView(viewPiece);
            case "console2":
                musicView = ViewFactory.createMusicView("console", musicModel);
                return new MusicViewAdapter(musicView);
            case "visual":
                return new GuiViewFrame(viewPiece);
            case "visual2":
                guiMusicView = ViewFactory.createGuiMusicView("visual", musicModel);
                return new GuiMusicViewAdapter(guiMusicView);
            case "midi":
                return new MidiViewImpl(viewPiece);
            case "midi2":
                //TODO: we should make an interface that works like our IGuiView but only
                // offers play beat  (IPlayBeat) instead of all of the other things. Then our
                // IGuiView can extend it and our controller will just check to see if it is of
                // that type when configuring the timer.

                //playableMusicView = ViewFactory.createPlayableMusicView("midi", musicModel);
                //return new PlayableMusicViewAdapter(playableMusicView);
                //PlayableMusicViewAdapter should implement IPlayBeat (which extends IMusicView)

                //TODO: other option given below -> without touching controller at all from here
                //convert playable music view into just an IMusicView and override the play
                // music so that it playes non-stop given a playable music view.
                playableMusicView = ViewFactory.createPlayableMusicView("midi", musicModel);
                return new MusicViewAdapter(playableMusicView);
            case "composite":
                return new CompositeView(viewPiece);
            case "composite2":
                guiMusicView = ViewFactory.createGuiMusicView("visual-midi", musicModel);
                return new GuiMusicViewAdapter(guiMusicView);
            default:
                throw new IllegalArgumentException(
                    "This view type is not supported. Please choose"
                        + "one of the following: \"console\", \"visual\", \"midi\", "
                        + "\"composite\", \"console2\", \"visual2\", \"midi2\", "
                        + "\"composite2\"");
        }
    }
}

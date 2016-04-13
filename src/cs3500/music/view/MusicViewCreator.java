package cs3500.music.view;

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
        switch (viewType) {
            case "console":
                return new ConsoleView(viewPiece);
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

package cs3500.music.view;

/**
 * Created by Ben on 3/23/16.
 */
public class MusicViewCreator {
    public MusicViewCreator() {
        //Empty constructor. Method provided is static
    }

    public static IMusicView create(String viewType, IViewPiece piece) {
        switch (viewType) {
            case "console":
                return new ConsoleView(piece);
            case "visual":
                return new GuiViewFrame(piece);
            case "midi":
                return new MidiViewImpl(piece);
            case "composite":
                return new CompositeView(piece);
            default:
                throw new IllegalArgumentException("This view type is not supported. Please choose"
                    + "one of the following: \"console\", \"visual\", \"midi\", \"composite\"");
        }
    }
}

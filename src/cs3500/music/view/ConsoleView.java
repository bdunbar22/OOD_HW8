package cs3500.music.view;

import cs3500.music.model.IPiece;

/**
 * Allows for the normal display of music to the console as text.
 * Created by Ben on 3/21/16.
 */
public class ConsoleView implements IMusicView {
    public void viewMusic(IPiece piece) {
        // Sam: I'm pretty sure this is all that the ConsoleView has to do...
        piece.musicOutput();
        System.out.print("This should be done as an appendable and then "
          + "tested with a mock."
            + " Move all music to string functionality to this class.");
    }
}

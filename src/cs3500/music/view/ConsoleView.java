package cs3500.music.view;

/**
 * Allows for the normal display of music to the console as text.
 * Created by Ben on 3/21/16.
 */
public class ConsoleView implements IMusicView {
    private IViewPiece viewPiece;

    public ConsoleView(IViewPiece viewPiece) {
        this.viewPiece = viewPiece;
    }

    public void viewMusic() {
        // Sam: I'm pretty sure this is all that the ConsoleView has to do...
        System.out.print(viewPiece.musicOutput());
    }
}

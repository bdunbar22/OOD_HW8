package cs3500.music.view;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Allows for the normal display of music to the console as text.
 * Created by Ben on 3/21/16.
 */
public class ConsoleView implements IMusicView {
    private final IViewPiece viewPiece;
    private final Appendable appendable;

    public ConsoleView(IViewPiece viewPiece) {
        this.viewPiece = viewPiece;
        this.appendable = new PrintStream(System.out);
    }

    public ConsoleView(IViewPiece viewPiece, Appendable appendable) {
        this.viewPiece = viewPiece;
        this.appendable = appendable;
    }

    public void viewMusic() {
        try {
            appendable.append(viewPiece.musicOutput());
        }
        catch (IOException e) {
            System.out.print("Error creating console output.");
        }
    }
}

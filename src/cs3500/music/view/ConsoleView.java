package cs3500.music.view;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Allows for the normal display of music to the console as text.
 * Created by Ben on 3/21/16.
 */
public class ConsoleView implements IMusicView {
    private IViewPiece viewPiece;
    private final Appendable appendable;

    public ConsoleView(IViewPiece viewPiece) {
        this.viewPiece = viewPiece;
        this.appendable = new PrintStream(System.out);
    }

    public ConsoleView(IViewPiece viewPiece, Appendable appendable) {
        this.viewPiece = viewPiece;
        this.appendable = appendable;
    }

    /**
     * Display music via text using an appendable object.
     */
    public void viewMusic() {
        try {
            appendable.append(viewPiece.musicOutput());
        }
        catch (IOException e) {
            System.out.print("Error creating console output.");
        }
    }

    /**
     * Update the view piece being used by the console view.
     */
    @Override
    public void update(IViewPiece viewPiece) {
        this.viewPiece = viewPiece;
    }
}

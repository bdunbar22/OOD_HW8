package cs3500.music.adapters;

import cs3500.music.view.IMusicView;
import cs3500.music.view.IViewPiece;
import cs3500.music.viewGiven.text.TextView;

import java.io.PrintStream;

/**
 * Adapts the provided text view to implement the functions provided by IMusicView so that the
 * current controller can use the provided text view correctly.
 *
 * Created by Ben on 4/18/16.
 */
public class TextViewAdapter implements IMusicView {
    private TextView textView;
    private Appendable appendable;

    public TextViewAdapter(MusicModel model) {
        this.appendable = new PrintStream(System.out);
        this.textView = new TextView(appendable, model);
    }

    /**
     * Use the text views render to implement view music with the appendable system out print
     * stream.
     */
    @Override
    public void viewMusic() {
        this.textView.render();
    }

    /**
     * Use the text views refresh to implement the update view piece function. The provided
     * text view class had a model given to it before so the view piece argument of this
     * function will not be used.
     *
     * @param viewPiece new model view to update with.
     */
    @Override
    public void updateViewPiece(IViewPiece viewPiece) {
        this.textView.refresh();
    }
}

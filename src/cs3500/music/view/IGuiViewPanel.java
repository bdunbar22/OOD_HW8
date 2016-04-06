package cs3500.music.view;

import cs3500.music.model.INote;

import javax.accessibility.Accessible;

/**
 * This interface adds the function to reset the view piece in use by any class implementing
 * IGuiViewPanel.
 *
 * Created by Ben on 4/5/16.
 */
public interface IGuiViewPanel extends Accessible {
    /**
     * Allow for the view piece to be reset. This is the view model that is used to display music.
     * @param viewPiece to place.
     */
    void resetViewPiece(IViewPiece viewPiece);

    /**
     * Based on the x and y coordinates provided this function will return the note chosen.
     */
    INote getNoteFromLocation(final int x, final int y);
}

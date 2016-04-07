package cs3500.music.view;

/**
 * This interface give the basic functionality we want to provide with out views.
 * <p>
 * Created by Ben on 3/21/16.
 */
public interface IMusicView {
    /**
     * Start display of music, this can be text, a gui, audible music, or any other view
     * representation that is valid.
     */
    void viewMusic();

    /**
     * Allows for the view to be updated with a new view piece so that if the model changes the
     * model then the new corresponding view piece can be sent to the view. After update
     * refresh the view accordingly.
     *
     * @param viewPiece new model view to update with.
     */
    void updateViewPiece(IViewPiece viewPiece);
}

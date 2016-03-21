package cs3500.music.view;

import cs3500.music.model.IPiece;

/**
 * This interface give the basic functionality we want to provide with out views.
 *
 * Created by Ben on 3/21/16.
 */
public interface IMusicView {
    /**
     * Start display of music
     */
    void viewMusic(IPiece music);
}

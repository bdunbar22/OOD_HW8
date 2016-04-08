package cs3500.music.controller;

import cs3500.music.model.IPiece;

/**
 * Interface for the controller.
 * <p>
 * Created by Ben on 4/5/16.
 */
public interface IController {
    /**
     * Start the display of the view(s) to start the program.
     */
    void start();

    /**
     * Found that we needed this for a test.
     * returns a copy to stay secure.
     * @return piece.
     */
    IPiece getPiece();
}

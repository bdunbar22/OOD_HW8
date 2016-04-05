package cs3500.music.controller;

/**
 * This should allow for modifications to be made to the model because the controller will have
 * access to call the model functions. When changes are made the view should be re-made.
 *
 *
 * Created by Ben on 3/30/16.
 */
public interface IMusicController {
    /**
     * Display the view according to the model.
     */
    void setView();

    /**
     * Add a note to the model. Length should also be allowed to be variable.
     */
    void addNotes();

    /**
     * This should allow a note to be moved to a different starting beat/pitch/octave.
     */
    void moveNote();

    /**
     * This should allow a note to be moved to a different starting beat/pitch/octave.
     */
    void moveNotes();

    /**
     * This should allow edits to a note field like volume or duration etc.
     */
    void editNote();

    /**
     * This should allow edits to a note field like volume or duration etc.
     */
    void editNotes();

    /**
     * This should allow for a note to be removed from a piece.
     */
    void deleteNote();

    /**
     * Jump to start or end of composition with the home or end keys.
     */
    void viewExtrema();

    /**
     * Scroll ability
     */
    void scroll();
}

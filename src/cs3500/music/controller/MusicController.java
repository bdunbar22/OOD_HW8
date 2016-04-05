package cs3500.music.controller;

import cs3500.music.model.*;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMusicView;

import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implement the IMusicController to allow for edits to be made to a piece of music via the
 * GUI view. Has a model that will be edited and a view that will be updated.
 *
 * Created by Ben on 4/4/16.
 */
public class MusicController implements IMusicController {
    private IPiece piece;
    private IMusicView musicView;

    public MusicController(IPiece piece, IMusicView musicView) {
        this.piece = piece;
        this.musicView = musicView;
        try{
            configureHandlers();
        }
        catch (InvalidClassException e) {
            //Do nothing. Could not add handlers to an IMusicView that was not also an
            //IGuiView
        }
    }

    /**
     * This method will allow for a key board handler and a mouse handler to be added to the view.
     * This will only happen if the view is an instance of the IGuiView interface, meaning it it
     * a view that will accept these handlers.
     */
    private void configureHandlers() throws InvalidClassException {
        if(!(musicView instanceof IGuiView)) {
            throw new InvalidClassException("In order to have handlers, must also be an "
              + "IGuiview");
        }

        MouseHandler mousehandler = configureMouseHandler();
        KeyboardHandler keyboardHandler = configureKeyBoardHandler();

        IGuiView view = (IGuiView)musicView;
        view.addMouseListener(mousehandler);
        view.addKeyListener(keyboardHandler);
        musicView = view;
    }

    /**
     * Creates a key board handler that can be added to the view if the view is an IGuiView
     */
    private KeyboardHandler configureKeyBoardHandler() {
        Map<Character, Runnable> keyTypes = new HashMap<>();
        Map<Integer, Runnable> keyPresses = new HashMap<>();
        Map<Integer, Runnable> keyReleases = new HashMap<>();

        //TODO: see notes

        KeyboardHandler keyboardHandler = new KeyboardHandler();
        keyboardHandler.setKeyHoldMap(keyTypes);
        keyboardHandler.setKeyPressedMap(keyPresses);
        keyboardHandler.setKeyReleasedMap(keyReleases);

        return keyboardHandler;
    }


    /**
     * Creates a mouse handler that implements mouse listener for the view.
     * This handler can be added to the view if the view is of the IGuiView type.
     */
    private MouseHandler configureMouseHandler() {
        MouseHandler mouseHandler = new MouseHandler();

        return mouseHandler;
    }

    @Override
    public void setView() {
        //TODO: create
    }

    @Override
    public void addNote() {
        //TODO: create

        INote note = new Note(Pitch.A, new Octave(2), 0, 1, 4, 2);
        piece.addNote(note);
    }

    @Override
    public void addNotes() {
        //TODO: create

        INote note = new Note(Pitch.A, new Octave(2), 0, 1, 4, 2);
        piece.addNotes(note);
    }

    @Override
    public void moveNote() {
        //TODO: create
    }

    @Override
    public void moveNotes() {
        //TODO: create
    }

    @Override
    public void editNote() {
        //TODO: create
    }

    @Override
    public void editNotes() {
        //TODO: create
    }

    @Override
    public void deleteNote() {
        //TODO: create
    }

    @Override
    public void reversePiece() {
        //TODO: create
    }

    @Override
    public void viewExtrema() {
        //TODO: create
    }

    @Override
    public void scroll() {
        //TODO: create
    }
}

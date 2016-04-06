package cs3500.music.controller;

import cs3500.music.model.*;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMusicView;
import cs3500.music.view.IViewPiece;
import cs3500.music.view.ViewPiece;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

/**
 * Allow for edits to be made to a piece of music via the
 * GUI view. Has a model that will be edited and a view that will be updated.
 *
 * Created by Ben on 4/4/16.
 */
public class Controller implements IController{
    private IPiece piece;
    private IMusicView musicView;

    public Controller(IPiece piece, IMusicView musicView) {
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

    @Override
    public void start() {
        musicView.viewMusic();
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

        keyPresses.put(KeyEvent.VK_R, new ReversePiece());
        keyPresses.put(KeyEvent.VK_END, new viewExtremaEnd());
        keyPresses.put(KeyEvent.VK_HOME, new viewExtremaStart());

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

    /**
     * A runnable class that will allow for a note to be added to the piece of music in
     * question. The note pitch, octave and start beat will be determined based on the location
     * of a mouse click. The volume will be configurable.
     */
    private void addNote(int x, int y) {
        INote note = new Note(Pitch.FSHARP, new Octave(5), 1, 3);
        piece.addNote(note);
        IViewPiece updatedViewPiece = new ViewPiece(piece);
        musicView.update(updatedViewPiece);
        musicView.viewMusic();
    }

    public void addNotes() {
        //TODO: create

        INote note = new Note(Pitch.A, new Octave(2), 0, 1, 4, 2);
        piece.addNotes(note);
    }

    public void moveNote() {
        //TODO: create
    }

    public void moveNotes() {
        //TODO: create
    }

    public void editNote() {
        //TODO: create
    }

    public void editNotes() {
        //TODO: create
    }

    /**
     * If possible will delete the chosen note from the piece.
     * @param x coordinate in view.
     * @param y coordinate in view.
     */
    private void deleteNote(final int x, final int y) {
        IGuiView view = (IGuiView)musicView;
        INote deleteNote = view.getNoteFromLocation(x, y);
        piece.removeNote(deleteNote);
        IViewPiece updatedViewPiece = new ViewPiece(piece);
        musicView.update(updatedViewPiece);
        musicView.viewMusic();
    }

    /**
     * A runnable class that will allow the piece to be reversed when the 'r' key it typed.
     */
    class ReversePiece implements Runnable {
        public void run() {
            piece = piece.reversePiece();
            IViewPiece updatedViewPiece = new ViewPiece(piece);
            musicView.update(updatedViewPiece);
            musicView.viewMusic();
        }
    }

    /**
     * A runnable class which changes the view to the end of the piece
     */
    class viewExtremaEnd implements Runnable {
        public void run() {
            IGuiView view = (IGuiView)musicView;
            view.scrollToEnd();
            musicView = view;
        }
    }

    /**
     * A runnable class which changes the view to the start of the piece
     */
    class viewExtremaStart implements Runnable {
        public void run() {
            IGuiView view = (IGuiView)musicView;
            view.scrollToStart();
            musicView = view;
        }
    }

    /**
     * Allow for mouse events to cause edits to the model via controller functions.
     */
    class MouseHandler implements MouseListener {
        /**
         * Empty default constructor
         */
        public MouseHandler() {
        }

        /**
         * handle when mouse is clicked. Left click will add a note and right click will delete
         * a note.
         *
         * @param e mouse event
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            //Right click will delete note
            //Left click will add a note
            switch (e.getButton()) {
                case MouseEvent.BUTTON3:
                    deleteNote(e.getX(), e.getY());
                case MouseEvent.BUTTON1:
                    addNote(e.getY(),e.getY());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //TODO: this
            int i = 5;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //TODO: this
            int i = 5;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // Nothing should be done, this is just mouse entering the part of the screen while
            // hovering.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Nothing should be done, this is just mouse leaving the part of the screen while
            // hovering.
        }
    }
}

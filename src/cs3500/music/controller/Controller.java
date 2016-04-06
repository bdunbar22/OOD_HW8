package cs3500.music.controller;

import cs3500.music.model.*;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMusicView;
import cs3500.music.view.IViewPiece;
import cs3500.music.view.ViewPiece;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.*;
import java.awt.event.*;
import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

/**
 * Allow for edits to be made to a piece of music via the
 * GUI view. Has a model that will be edited and a view that will be updated.
 *
 * <h1>Functionality:</h1>
 * <p>Right click - delete a note</p>
 * <p>Left drag - create a note, starts at start pressed location, length based on drag length</p>
 * <p>TOGGLE 'm' and Left drag now moves notes.</p>
 * <i>Pressing 'm' enters move note mode until 'm' is pressed again.</i>
 * <p>Press 'r' reverses the song.</p>
 *
 * Created by Ben on 4/4/16.
 */
public class Controller implements IController{
    private IPiece piece;
    private IMusicView musicView;
    private Timer timer;
    private boolean playing;
    private boolean moveToggle = false;

    public Controller(IPiece piece, IMusicView musicView) {
        this.piece = piece;
        this.musicView = musicView;
        this.timer = new Timer();
        this.playing = false;
        //TODO: get music play working.
        //TODO: this music play should only apply during composite views. because we will want to
        //TODO: view each beat at a time based on timer. Other views we only want to view music
        //TODO: once.
        //toggleMusicPLay();
        //piece.setBeat(piece.getBeat() + 1); for each display.
        try{
            configureHandlers();
        }
        catch (InvalidClassException e) {
            //Do nothing. Could not add handlers to an IMusicView that was not also an
            //IGuiView
        }
    }

    /**
     * Start displaying music.
     */
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
        keyPresses.put(KeyEvent.VK_M, new toggleMove());

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
    private void addNote(int x, int y, int length) {
        IGuiView view = (IGuiView)musicView;
        INote addNote = view.makeNoteFromLocation(x, y, length);
        piece.addNote(addNote);
        IViewPiece updatedViewPiece = new ViewPiece(piece);
        musicView.updateViewPiece(updatedViewPiece);
        musicView.viewMusic();
    }

  /**
   * Add a new note by passing params
   * @param x location
   * @param y location
   * @param length duration of the note's sustain
   * @param instrument instrument
   * @param volume volume
   */
    private void addNote(int x, int y, int length, int instrument, int volume) {
        IGuiView view = (IGuiView)musicView;
        INote addNote = view.makeNoteFromLocation(x, y, length);
        addNote.setInstrument(instrument);
        addNote.setVolume(volume);
        piece.addNote(addNote);
        IViewPiece updatedViewPiece = new ViewPiece(piece);
        musicView.updateViewPiece(updatedViewPiece);
        musicView.viewMusic();
    }

    /**
     * Move a note to a different starting beat, pitch and octave.
     * @param note that is being moved
     * @param newPos to move to on view
     */
    private void moveNote(INote note, Point newPos) {
        addNote(
          (int)newPos.getX(),
          (int)newPos.getY(),
          (note.getDuration() - 1) * 20,
          note.getInstrument(),
          note.getVolume());
        deleteNote(note);
    }

    /**
     * If possible will delete the chosen note from the piece.
     * @param x coordinate in view.
     * @param y coordinate in view.
     */
    private void deleteNote(final int x, final int y) {
        if(!checkForNote(x, y)) {
            return;
        }
        INote deleteNote = getNote(x, y);
        piece.removeNote(deleteNote);
        IViewPiece updatedViewPiece = new ViewPiece(piece);
        musicView.updateViewPiece(updatedViewPiece);
        musicView.viewMusic();
    }

    /**
     * Delete the given note
     * @param note to delete
     */
    private void deleteNote(INote note) {
        try {
            piece.removeNote(note);
            IViewPiece updatedViewPiece = new ViewPiece(piece);
            musicView.updateViewPiece(updatedViewPiece);
            musicView.viewMusic();
        }
        catch (IllegalArgumentException e) {
            //Note wasn't present, do nothing.
        }
    }

    /**
     * Determine if getting a note will work.
     * @param x location
     * @param y location
     * @return boolean if note will be retrieved correctly.
     */
    private boolean checkForNote(final int x, final int y) {
        return getNote(x, y) != null;
    }

    /**
     * Retrieves the note that was clicked on in the GUI.
     *
     * @param x mouse location
     * @param y mouse location
     * @return note that was found
     */
    private INote getNote(final int x, final int y) {
        IGuiView view = (IGuiView)musicView;
        return view.getNoteFromLocation(x, y);
    }

    /**
     * A runnable class that will allow the piece to be reversed when the 'r' key it typed.
     */
    class ReversePiece implements Runnable {
        public void run() {
            piece = piece.reversePiece();
            IViewPiece updatedViewPiece = new ViewPiece(piece);
            musicView.updateViewPiece(updatedViewPiece);
            musicView.viewMusic();
        }
    }


    /**
     * Toggles moveToggle on and off when the "M" key is pressed.
     *
     * When false (default) left click will add a note
     * When true left click will move existing notes.
     */
    class toggleMove implements Runnable {
        public void run() {
            moveToggle = !moveToggle;
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
        private Point mousePoint;
        private boolean noteFound;
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
                    break;
            }
        }

        @Override
        public void mousePressed (MouseEvent e){
            mousePoint = e.getPoint();
            noteFound = checkForNote(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            try {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        if (!moveToggle) {
                            int dx = e.getX() - mousePoint.x;
                            addNote(mousePoint.x, mousePoint.y, dx);
                        } else {
                            if (noteFound) {
                                INote oldNote = getNote(mousePoint.x, mousePoint.y);
                                moveNote(oldNote, e.getPoint());
                            }
                        }
                        break;
                }
            }
            catch (Exception exc) { /*Do nothing.*/ }
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

    class timerTask extends TimerTask {
        private Controller controller;
        public timerTask(Controller controller){
            this.controller = controller;
        }
        @Override
        public void run() {
            controller.start();
            if(checkSongEnd()){
                this.cancel();
            }
        }
    }

    /**
     * Determine if the song is over.
     * @return true if song is over.
     */
    private boolean checkSongEnd(){
        return (piece.getBeat() >= piece.getLastBeat());
    }

    /**
     * This should be called when the space bar is hit to start and stop play of music.
     */
    private void toggleMusicPLay(){
        if(playing){
            timer.cancel();
            playing = false;
            timer = new Timer();
        }
        else {
            playing = true;
            int Delay = (piece.getTempo()/1000);
            timer.schedule(new timerTask(this), 0, Delay);
        }
    }
}

package cs3500.music.controller;

import cs3500.music.model.*;
import cs3500.music.view.*;

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
 * <p>TOGGLE 'a', 'm', 'c' for operation modes.</p>
 * <p>A = Add (default)</p>
 * <p>M = Move </p>
 * <p>C = Copy </p>
 * <i>Pressing the key will enter the operation mode. Pressing a different key will switch
 * modes. Starts at Add mode.</i>
 * <p>Press 'r' reverses the song.</p>
 *
 * Created by Ben on 4/4/16.
 */
public class Controller implements IController{
    private IPiece piece;
    private IMusicView musicView;
    private Timer timer;
    private boolean playing;
    private Toggle toggle = Toggle.ADD;
    private int currentBeat;

    private static final int TEMPO_TO_PERIOD = 1000;

    public Controller(IPiece piece, IMusicView musicView) {
        this.piece = piece;
        this.musicView = musicView;
        this.playing = false;
        this.currentBeat = 0;
        try{
            configureHandlers();
        }
        catch (InvalidClassException e) {
            //Do nothing. Could not add handlers to an IMusicView that was not also an
            //IGuiView
        }
        configureTiming();
    }

    /**
     * Start displaying music.
     */
    @Override
    public void start() {
        musicView.viewMusic();
    }

    /**
     * For cases when timing is needed. Update the current beat and then have the views
     * respond so that the music plays and the gui has a moving bar.
     */
    private void updateEachBeat() {
        currentBeat++;
        IGuiView view = (IGuiView)musicView;
        view.playBeat(currentBeat);
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
        keyPresses.put(KeyEvent.VK_BRACERIGHT, new viewExtremaEnd());
        keyPresses.put(KeyEvent.VK_HOME, new viewExtremaStart());
        keyPresses.put(KeyEvent.VK_BRACELEFT, new viewExtremaStart());
        keyPresses.put(KeyEvent.VK_M, new moveToggle());
        keyPresses.put(KeyEvent.VK_C, new copyToggle());
        keyPresses.put(KeyEvent.VK_A, new addToggle());
        keyPresses.put(KeyEvent.VK_SPACE, new StopAndPlay());
        keyPresses.put(KeyEvent.VK_B, new Restart());

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
        MouseHandler mouseHandler = new MouseHandler(new mouseHelper());
        return mouseHandler;
    }

    /**
     * When timing is required make timing available.
     */
    private void configureTiming() {
        try{
            if(!(musicView instanceof CompositeView)) {
                throw new InvalidClassException("Only composite view requires timing to be "
                    + "added");
            }
            this.timer = new Timer();
            playing = true;
            int period = piece.getTempo()/TEMPO_TO_PERIOD;
            timer.schedule(new timerTask(this), 0, period);
        }
        catch (InvalidClassException e) {
            //Do nothing. Only need timing in some cases.
        }
    }

    class Restart implements Runnable {
        @Override public void run() {
            currentBeat = 0;
        }
    }

    /**
     * Pause and play the song when space bar is hit.
     */
    class StopAndPlay implements Runnable {
        public void run() {
            toggleMusicPLay();
        }
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
        }
    }


    /**
     * Sets toggle to ADD - allowing the user to add notes
     */
    class addToggle implements Runnable {
        @Override public void run() {
            toggle = Toggle.ADD;
        }
    }

    /**
     * Sets the toggle to ADD if it is MOVE, and to MOVE otherwise
     */
    class moveToggle implements Runnable {
        public void run() {
            if (toggle == Toggle.MOVE)
                toggle = Toggle.ADD;
            else
                toggle = toggle.MOVE;
        }
    }

    /**
     * Sets the toggle to ADD if it is COPY, and to COPY otherwise
     */
    class copyToggle implements  Runnable {
      @Override public void run() {
          if (toggle == Toggle.COPY)
              toggle = Toggle.ADD;
          else
              toggle = Toggle.COPY;
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
     * Provide the mouse handler with necessary functions from the controller.
     */
    class mouseHelper implements MouseHandlerHelper {
        //provide use of controller's deleteNote
        public void deleteNoteFromMouse(int x, int y) {
            deleteNote(x, y);
        }

        //provide use of controller's checkForNote
        public boolean checkForNoteFromMouse(int x, int y) {
            return checkForNote(x, y);
        }

        //provide use of controller's addNote(x,y,length value)
        public void addNoteFromMouse(int x, int y, int dx) {
            addNote(x, y, dx);
        }

        //provide use of controller's add note with all fields.
        public void addNoteFromMouse(int x, int y, int length, int instrument, int volume) {
            addNote(x, y, length, instrument, volume);
        }

        //provide use of controller's  move note
        public void moveNoteFromMouse(INote old, Point point) {
            moveNote(old, point);
        }

        //provide use of controller's get note
        public INote getNoteFromMouse(int x, int y) {
            return getNote(x, y);
        }

        ////provide use of controller's toggle status.
        public Toggle getMoveToggleFromMouse() {
            return toggle;
        }
    }


    /**
     * Timer for the controller.
     */
    class timerTask extends TimerTask {
        private Controller controller;
        public timerTask(Controller controller){
            this.controller = controller;
        }
        @Override
        public void run() {
            controller.updateEachBeat();
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
        return (currentBeat >= piece.getLastBeat());
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
            int period = piece.getTempo()/TEMPO_TO_PERIOD;
            timer.schedule(new timerTask(this), 0, period);
        }
    }

    /**
     * Enum to determine the operating mode of the controller, whether it is currently adding,
     * moving or copying notes.
     */
    public enum Toggle {
        ADD, COPY, MOVE
    }
}

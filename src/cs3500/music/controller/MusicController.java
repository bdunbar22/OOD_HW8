package cs3500.music.controller;

import cs3500.music.model.IPiece;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMusicView;

import java.awt.event.KeyEvent;
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
    private IGuiView musicView;

    public MusicController(IPiece piece, IGuiView musicView) {
        this.piece = piece;
        this.musicView = musicView;
        configureKeyBoardHandler();
        configureMouseListener();
    }

    /**
     * Creates and sets a keyboard listener for the view. In effect it creates snippets of code as
     * Runnable object, one for each time a key is typed, pressed and released, only for those that
     * the program needs.
     *
     * TODO: update this
     *
     * Last we create our KeyboardHandler object, set all its maps and then give it to the view.
     */
    private void configureKeyBoardHandler() {
        Map<Character, Runnable> keyTypes = new HashMap<>();
        Map<Integer, Runnable> keyPresses = new HashMap<>();
        Map<Integer, Runnable> keyReleases = new HashMap<>();

//        keyPresses.put(KeyEvent.VK_C, new MakeCaps());
//        keyReleases.put(KeyEvent.VK_C, new MakeOriginalCase());
//        // Another possible syntax: instead of defining a new class, just to make a single instance,
//        // you can create an "anonymous class" that implements a particular interface, by writing
//        // "new Interfacename() { all the methods you need to implement }"
//        // Note that "view" is in scope inside this Runnable!  But, also note that within the Runnable,
//        // "this" refers to the Runnable and not to the Controller, so we don't say "this.view".
//        keyTypes.put('r', new Runnable() {
//            public void run() {
//                musicView.toggleColor();
//            }
//        });
//
//        // Another possible syntax:
//        // Instead of an anonymous class, you can (as of Java 8) use "lambda syntax",
//        // as follows: if the interface you want to implement has only one single method,
//        // then write the parenthesized argument list, followed by an arrow, followed by
//        // the body of the method.  Java will infer that you mean to implement the particular
//        // single method of that interface, and translate the code for you to resemble the
//        // anonymous Runnable example above.
//        // Again note all the names that are in scope.
//        keyTypes.put('x', () -> {
//            // exchange the hotkeys C and U:
//            // Take the event handlers from VK_C and VK_U
//            Runnable oldCHandler = keyPresses.get(KeyEvent.VK_C);
//            Runnable oldUHandler = keyPresses.get(KeyEvent.VK_U);
//            // Update the C handler
//            if (oldCHandler != null)
//                keyPresses.put(KeyEvent.VK_U, oldCHandler);
//            else
//                keyPresses.remove(KeyEvent.VK_U);
//            // Update the U handler
//            if (oldUHandler != null)
//                keyPresses.put(KeyEvent.VK_U, oldCHandler);
//            else
//                keyPresses.remove(KeyEvent.VK_U);
//        });

        KeyboardHandler kbd = new KeyboardHandler();
        kbd.setKeyHoldMap(keyTypes);
        kbd.setKeyPressedMap(keyPresses);
        kbd.setKeyReleasedMap(keyReleases);

        musicView.addKeyListener(kbd);
    }


    /**
     * Creates and sets a mouse handler that implements mouse listener for the view.
     * This handler will be added to the view
     */
    private void configureMouseListener() {
        MouseHandler mouse = new MouseHandler();

        musicView.addMouseListener(mouse);
    }

    @Override
    public void setView() {
        //TODO: create
    }

    @Override
    public void addNote() {
        //TODO: create
    }

    @Override
    public void addNotes() {
        //TODO: create
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

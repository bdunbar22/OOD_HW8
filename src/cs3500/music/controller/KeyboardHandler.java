package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Implement KeyListener to provide for key board events to trigger controller methods that will
 * edit the model accordingly.
 * Uses a Map<Integer, Runnable> to get the handlers for specific key strokes.
 * <p>
 * Created by Ben on 4/4/16.
 */
public class KeyboardHandler implements KeyListener {
    private Map<Integer, Runnable> keyPressedMap;
    private Map<Character, Runnable> keyTypedMap;
    private Map<Integer, Runnable> keyReleasedMap;

    /**
     * Empty default constructor
     */
    public KeyboardHandler() {}

    /**
     * Set the map for key type events. Key typed events in Java Swing are characters
     */
    public void setKeyHoldMap(Map<Character, Runnable> map) {
        keyTypedMap = map;
    }

    /**
     * Set the map for key pressed events. Key pressed events in Java Swing are integer codes
     */
    public void setKeyPressedMap(Map<Integer, Runnable> map) {
        keyPressedMap = map;
    }

    /**
     * Set the map for key released events. Key released events in Java Swing are integer codes
     */
    public void setKeyReleasedMap(Map<Integer, Runnable> map) {
        keyReleasedMap = map;
    }


    /**
     * This is called when the view detects that a key has been typed. Find if anything has been
     * mapped to this key character and if so, execute it
     */
    @Override public void keyTyped(KeyEvent e) {
        if (keyTypedMap.containsKey(e.getKeyChar()))
            keyTypedMap.get(e.getKeyChar()).run();
    }

    /**
     * This is called when the view detects that a key has been pressed. Find if anything has been
     * mapped to this key code and if so, execute it
     */
    @Override public void keyPressed(KeyEvent e) {
        if (keyPressedMap.containsKey(e.getKeyCode()))
            keyPressedMap.get(e.getKeyCode()).run();
    }

    /**
     * This is called when the view detects that a key has been released. Find if anything has been
     * mapped to this key code and if so, execute it
     */
    @Override public void keyReleased(KeyEvent e) {
        if (keyReleasedMap.containsKey(e.getKeyCode()))
            keyReleasedMap.get(e.getKeyCode()).run();
    }
}

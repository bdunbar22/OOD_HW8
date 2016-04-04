package cs3500.music.controller;

import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Implement KeyListener to provide for key board events to trigger controller methods that will
 * edit the model accordingly.
 * Uses a Map<Integer, Runnable> to get the handlers for specific key strokes.
 *
 * Created by Ben on 4/4/16.
 */
public class KeyboardHandler implements KeyListener {
    private Map<Integer, Runnable> keyPresses;
    private Map<Integer, Runnable> keyHolds;
    private Map<Integer, Runnable> keyReleases;

}

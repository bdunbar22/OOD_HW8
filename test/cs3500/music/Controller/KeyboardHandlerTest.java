package cs3500.music.Controller;

import cs3500.music.controller.KeyboardHandler;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Give fake maps of operations to do and then test that the right ones are called.
 *
 * Created by Ben on 4/7/16.
 */
public class KeyboardHandlerTest {
    private String testHolder = "";

    /**
     * Key typed is the only method we needed. Testing here.
     */
    @Test
    public void testKeyTyped() {
        KeyListener mockListener = createTestKeyHandler();
        Component jPanel = new JPanel();

        KeyEvent mockKey = new KeyEvent(jPanel, KeyEvent.KEY_PRESSED, 1, 1, 1, ' ');

        int[] eventArray = {
            KeyEvent.VK_R,
            KeyEvent.VK_END,
            KeyEvent.VK_0,
            KeyEvent.VK_HOME,
            KeyEvent.VK_1,
            KeyEvent.VK_M,
            KeyEvent.VK_C,
            KeyEvent.VK_A,
            KeyEvent.VK_L,
            KeyEvent.VK_SPACE,
            KeyEvent.VK_B,
            KeyEvent.VK_UP,
            KeyEvent.VK_DOWN,
            KeyEvent.VK_RIGHT,
            KeyEvent.VK_LEFT,
            KeyEvent.VK_T
        };

        jPanel.addKeyListener(mockListener);

        for (int i : eventArray) {
            mockKey.setKeyCode(i);
            mockListener.keyPressed(mockKey);
            //jPanel.dispatchEvent(mockKey);
        }
        assertEquals("r end zero home one m c a l space b up down right left t ", testHolder);
    }

    /**
     * Create fake maps and put in a handler
     */
    public KeyListener createTestKeyHandler() {
        Map<Character, Runnable> keyTypes = new HashMap<>();
        Map<Integer, Runnable> keyPresses = new HashMap<>();
        Map<Integer, Runnable> keyReleases = new HashMap<>();

        keyPresses.put(KeyEvent.VK_R, new rPress());
        keyPresses.put(KeyEvent.VK_END, new endPress());
        keyPresses.put(KeyEvent.VK_0, new zeroPress());
        keyPresses.put(KeyEvent.VK_HOME, new homePress());
        keyPresses.put(KeyEvent.VK_1, new onePress());
        keyPresses.put(KeyEvent.VK_M, new mPress());
        keyPresses.put(KeyEvent.VK_C, new cPress());
        keyPresses.put(KeyEvent.VK_A, new aPress());
        keyPresses.put(KeyEvent.VK_L, new lPress());
        keyPresses.put(KeyEvent.VK_SPACE, new spacePress());
        keyPresses.put(KeyEvent.VK_B, new bPress());
        keyPresses.put(KeyEvent.VK_UP, new upPress());
        keyPresses.put(KeyEvent.VK_DOWN, new downPress());
        keyPresses.put(KeyEvent.VK_RIGHT, new rightPress());
        keyPresses.put(KeyEvent.VK_LEFT, new leftPress());
        keyPresses.put(KeyEvent.VK_T, new tPress());

        KeyboardHandler keyboardHandler = new KeyboardHandler();
        keyboardHandler.setKeyHoldMap(keyTypes);
        keyboardHandler.setKeyPressedMap(keyPresses);
        keyboardHandler.setKeyReleasedMap(keyReleases);
        return keyboardHandler;
    }

    class rPress implements Runnable {
        @Override
        public void run() {
            testHolder += "r ";
        }
    }

    class endPress implements Runnable {
        @Override
        public void run() {
            testHolder += "end ";
        }
    }

    class zeroPress implements Runnable {
        @Override
        public void run() {
            testHolder += "zero ";
        }
    }

    class homePress implements Runnable {
        @Override
        public void run() {
            testHolder += "home ";
        }
    }

    class onePress implements Runnable {
        @Override
        public void run() {
            testHolder += "one ";
        }
    }

    class mPress implements Runnable {
        @Override
        public void run() {
            testHolder += "m ";
        }
    }

    class cPress implements Runnable {
        @Override
        public void run() {
            testHolder += "c ";
        }
    }

    class aPress implements Runnable {
        @Override
        public void run() {
            testHolder += "a ";
        }
    }

    class lPress implements Runnable {
        @Override
        public void run() {
            testHolder += "l ";
        }
    }

    class spacePress implements Runnable {
        @Override
        public void run() {
            testHolder += "space ";
        }
    }

    class bPress implements Runnable {
        @Override
        public void run() {
            testHolder += "b ";
        }
    }

    class upPress implements Runnable {
        @Override
        public void run() {
            testHolder += "up ";
        }
    }

    class downPress implements Runnable {
        @Override
        public void run() {
            testHolder += "down ";
        }
    }

    class rightPress implements Runnable {
        @Override
        public void run() {
            testHolder += "right ";
        }
    }

    class leftPress implements Runnable {
        @Override
        public void run() {
            testHolder += "left ";
        }
    }

    class tPress implements Runnable {
        @Override
        public void run() {
            testHolder += "t ";
        }
    }
}

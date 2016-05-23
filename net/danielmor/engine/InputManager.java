package net.danielmor.engine;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**The InputManager manages input of key events. Events are mapped to GameActions.*/
public class InputManager implements KeyListener
{
    /**An invisible cursor.*/
    public static final Cursor INVISIBLE_CURSOR =
        Toolkit.getDefaultToolkit().createCustomCursor(
            Toolkit.getDefaultToolkit().getImage(""),
            new Point(0,0),
            "invisible");

    // key codes are defined in java.awt.KeyEvent.
    // most of the codes (except for some rare ones like
    // "alt graph") are less than 600.
    private static final int NUM_KEY_CODES = 600;

    private GameAction[] keyActions = new GameAction[NUM_KEY_CODES];
    private Component comp;

    /**Creates a new InputManager that listens to input from thespecified component.*/
    public InputManager(Component comp) {
        this.comp = comp;

        // register key and mouse listeners
        comp.addKeyListener(this);

        // allow input of the TAB key and other keys normally
        // used for focus traversal
        comp.setFocusTraversalKeysEnabled(false);
    }

    /**Sets the cursor on this InputManager's input component.*/
    public void setCursor(Cursor cursor) {
        comp.setCursor(cursor);
    }

    /**Maps a GameAction to a specific key. The key codes are defined in java.awt.KeyEvent. If the key already has
    a GameAction mapped to it, the new GameAction overwritesit.*/
    public void mapToKey(GameAction gameAction, int keyCode) {
        keyActions[keyCode] = gameAction;
    }

    /**Clears all mapped keys and mouse actions to thisGameAction.*/
    public void clearMap(GameAction gameAction) {
        for (int i=0; i<keyActions.length; i++) {
            if (keyActions[i] == gameAction) {
                keyActions[i] = null;
            }
        }

        gameAction.reset();

    }

    /**Gets a List of names of the keys and mouse actions mappedto this GameAction. Each entry in the List is a String.*/
    public List getMaps(GameAction gameCode) {
        ArrayList<String> list = new ArrayList<String>();

        for (int i=0; i<keyActions.length; i++) {
            if (keyActions[i] == gameCode) {
                list.add(getKeyName(i));
            }
        }
        return list;
    }

    /**Resets all GameActions so they appear like they haven'tbeen pressed.*/
    public void resetAllGameActions() {
        for (int i=0; i<keyActions.length; i++) {
            if (keyActions[i] != null) {
                keyActions[i].reset();
            }
        }
    }

    /**Gets the name of a key code.*/
    public static String getKeyName(int keyCode) {
        return KeyEvent.getKeyText(keyCode);
    }

    private GameAction getKeyAction(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keyActions.length) {
            return keyActions[keyCode];
        }
        else {
            return null;
        }
    }

    // from the KeyListener interface
    public void keyPressed(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.press();
        }
        // make sure the key isn't processed for anything else
        e.consume();
    }

    // from the KeyListener interface
    public void keyReleased(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.release();
        }
        // make sure the key isn't processed for anything else
        e.consume();
    }

    // from the KeyListener interface
    public void keyTyped(KeyEvent e) {
        // make sure the key isn't processed for anything else
        e.consume();
    }
}
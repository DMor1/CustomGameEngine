package net.danielmor.engine;

import java.awt.*;
import java.awt.image.*;

/**Creates image buffer capable of storing data and switching between different screens*/
public abstract class GameScreen 
{
    int WIDTH;
    int HEIGHT;

    public GameWindow window;
    public Game game;
    private static int currentScreenPointer = 0;
    private static int numberOfScreens = 0;
    private BufferedImage screen;

    /**Stores which GameWindow this screen will be in, and creates screen's image buffer with window's width and height. 
     * Also adds to 'numberOfScreens' to keep track of total screens deployed
     */
    public GameScreen(GameWindow window, Game game) {
        this.game = game;
        this.window = window;
        WIDTH = window.windowSize.getWidth();
        HEIGHT = window.windowSize.getHeight();
        screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB );
        numberOfScreens++;
    }

    /**Return what screen is currently being updated and drawn*/
    public static int getCurrentScreenPointer() {
        return currentScreenPointer;
    }

    /**Return total number of screens*/
    public static int getNumberOfScreens() {
        return numberOfScreens;
    }

    /**Returns specified screen's graphics*/
    public Graphics getScreenGraphics() {
        return screen.getGraphics();
    }

    /**returns specified screen object*/
    public BufferedImage getScreen() {
        return screen;
    }

    /**Change to specified screen using array numbers (IE: first screen is zero)*/
    public static void setScreenPointer(int i) {
        currentScreenPointer = i;
    }

    public void setBGColor(Graphics g, Color c) {
        //background of screen
        g.setColor(c);
        g.fillRect(0, 0, window.windowSize.getWidth(), window.windowSize.getHeight());
    }
    
    /**Init the screen*/
    public abstract void init();
    
    /**Updates the screen*/
    public abstract void update(long elapsedTime);

    /**Draws the screen*/
    public abstract void draw();
}
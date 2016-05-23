package net.danielmor.engine;

import javax.swing.*;

/**Holds All the information needed for a GameWindow to be created*/
public class GameWindow
{   
    private int frameRate = 0;
    public Size windowSize;
    public Location windowLocation;

    /**Empty Constructor*/
    public GameWindow() {
    }
    
    /**Creates a GameWindow and automatically centers it with specified width, height and framerate*/
    public GameWindow(int WIDTH, int HEIGHT, int frameRate) {
        windowSize = new Size(WIDTH, HEIGHT);
        this.frameRate = 1000/frameRate; //Converts frames per second to frames per millisecond
    }

    /**Creates a GameWindow with specified x and y location, width and height and framerate*/
    public GameWindow(int x, int y, int WIDTH, int HEIGHT, int frameRate) {
        windowSize = new Size(WIDTH, HEIGHT);
        windowLocation = new Location(x, y);
        this.frameRate = frameRate;
    }
    
    /**Returns GameWindow's FrameRate*/
    public int getFrameRate() {
        return frameRate;
    }

    /**Stores GameWindow width and height*/
    public class Size 
    {
        private int WIDTH = 0;
        private int HEIGHT = 0;

        public Size(int WIDTH, int HEIGHT) {
            this.WIDTH = WIDTH;
            this.HEIGHT = HEIGHT;
        }

        /**Returns width of GameWindow*/
        public int getWidth() {
            return WIDTH;
        }

        /**Returns Height of GameWindow*/
        public int getHeight() {
            return HEIGHT;
        }
    }

    /**Stores GameWindow location*/
    public class Location
    {
        private int x = 0;
        private int y = 0;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**Returns GameWindow's x location*/
        public int getX() {
            return x;
        }

        /**Returns GameWindow's y location*/
        public int getY() {
            return y;
        }
    }
}
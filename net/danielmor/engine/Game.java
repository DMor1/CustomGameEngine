package net.danielmor.engine;

import java.awt.*;
import java.util.*;
import java.applet.*;
import java.io.*;

public class Game extends GameCore
{
    /**Main Screen in Game*/
    public ArrayList<GameScreen> screens;

    /**List of All Sprites in the game*/
    public ArrayList<Player> userSprites;
    public ArrayList<Entity> sprites;
    public ArrayList<Entity> spritesToBeAdded;
    public ArrayList<Sprite> spritesToBeRemoved;

    public ScoreBoard scoreBoard;

    public final static int STARTING_STATE = -1;
    public final static int LOADING_STATE = 0;
    public final static int NORMAL_STATE = 1;
    public final static int PAUSED_STATE = 2;
    private int gameState = -1;    

    private boolean gridEnabled = false;
    private int gridSize = 10; //Creates squares of 10 pixels in a grid

    /**Initiate Game*/
    protected void init() {
        super.init();
        spritesToBeRemoved = new ArrayList<Sprite>();
        spritesToBeAdded = new ArrayList<Entity>();
        userSprites = new ArrayList<Player>(); //list of user sprites (usually controlled by player)
        sprites = new ArrayList<Entity>(); //list of sprites to be updated and drawn
        screens = new ArrayList<GameScreen>(); //list of screens to be deployed in game

        scoreBoard = new ScoreBoard("Mario", "1 - 1", this);

        loadAudio();
        mainThemeAudio.loop();

        //Time to load anything and catch up
        try {Thread.sleep(50);}
        catch(Exception ex) {}
    }

    public AudioClip mainThemeAudio;
    public AudioClip runningOuttaTime;
    public AudioClip coinAudio;
    public AudioClip dying;
    public AudioClip hitBlock;
    public AudioClip jump;
    public AudioClip outOfTime;
    public AudioClip powerUpInBox;
    public AudioClip warpPipe;
    protected void loadAudio() {
        try {
            mainThemeAudio = Applet.newAudioClip(new File("Content//Audio//ThemeSong.wav").toURI().toURL());
            runningOuttaTime = Applet.newAudioClip(new File("Content//Audio//RunningOuttaTime.wav").toURI().toURL());
            coinAudio = Applet.newAudioClip(new File("Content//Audio//Coin.wav").toURI().toURL());
            dying = Applet.newAudioClip(new File("Content//Audio//Dying.wav").toURI().toURL());
            hitBlock = Applet.newAudioClip(new File("Content//Audio//HitBlock.wav").toURI().toURL());
            jump = Applet.newAudioClip(new File("Content//Audio//Jump.wav").toURI().toURL());
            outOfTime = Applet.newAudioClip(new File("Content//Audio//OutOfTime.wav").toURI().toURL());
            powerUpInBox = Applet.newAudioClip(new File("Content//Audio//PowerUpInBox.wav").toURI().toURL());
            warpPipe = Applet.newAudioClip(new File("Content//Audio//WarpPipe.wav").toURI().toURL());
        }       
        catch(Exception ex) {
        }
    }

    /**Update GameCore*/
    protected void update(long elapsedTime) {
        super.update(elapsedTime);

        int screenPointer = GameScreen.getCurrentScreenPointer();  //currect screen
        int screenNum = GameScreen.getNumberOfScreens();   //number of total screens

        //Update current screen buffer
        if(screenPointer >= 0 && screenPointer < screenNum) // prevents nullPointer exception if retrieving null screen object
            screens.get(screenPointer).update(elapsedTime); //update currently available screen
    }

    /**Draw GameCore*/
    protected void draw(Graphics g) {
        int screenPointer = GameScreen.getCurrentScreenPointer();  //currect screen
        int screenNum = GameScreen.getNumberOfScreens();   //number of total screens

        if(screenPointer >=0 && screenPointer < screenNum) { //prevents nullPointer exception if retrieving null screen object
            screens.get(screenPointer).draw(); //Draw to specified screen buffer
            g.drawImage(screens.get(screenPointer).getScreen(), 0, 0, null); //Draw specified screen buffer to physical screen
        }
        super.draw(g); // Draw system needs on top of game needs

        if(gridEnabled == true)
            drawGrid(g);
    }

    //Change the game state
    public void setGameState(int i) {
        if(gameState != i)
            gameState = i;
    }

    //Retrieve the current game state
    public int getGameState() {
        return gameState;
    }

    protected void loadMaps(String directory, MapKey key) {
        int i = 0; //Map Number
        MapReader r = new MapReader(directory); 
        Map[] maps = r.getMaps();
        char[][] map = maps[i].getMap();

        for(int row = 0; row < maps[i].getRowSize(); row++) {
            for(int col = 0; col < maps[i].getColSize(); col++) {
                char c = map[row][col]; 
                Player p = key.selectPlayerSprite(c);
                Entity s = key.selectSprite(c);
                if(s != null) {                    
                    s.setLocation(col*32, row*32);
                    sprites.add(s);
                }
                if(p != null) {
                    p.setLocation(col*32, row*32);
                    userSprites.add(p);
                }
            }
        }
    }

    private void drawGrid(Graphics g) {
        int width = window.windowSize.getWidth();
        int height = window.windowSize.getHeight();

        g.setColor(Color.BLACK);

        //Vertical Lines
        for(int i = 0; i <= width/gridSize; i++) {
            g.drawLine(i*gridSize, 0, i*gridSize, height);
        }

        //Horizontal Lines
        for(int i = 0; i <= height/gridSize; i++) {
            g.drawLine(0, i*gridSize, width, i*gridSize);
        }
    }

    protected void setGridSize(int i) {
        gridSize = i;
    }

    protected void enableGrid() {
        gridEnabled = true;
    }

    protected void disableGrid() {
        gridEnabled = false;
    }
}
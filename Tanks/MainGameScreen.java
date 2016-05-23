import net.danielmor.engine.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.applet.*;

public class MainGameScreen extends GameScreen
{    
    private InputManager gameScreenInput;
    private GameAction moveLeft1;
    private GameAction moveRight1;
    private GameAction fire1;

    private GameAction moveLeft2;
    private GameAction moveRight2;
    private GameAction fire2;

    private GameAction pause;

    Image bgImage;   
    Image bgBuffer;
    
    Tank p1;
    Tank p2;

    private boolean collisionBoxesEnabled = false;

    MapGenerator generator;

    public MainGameScreen(GameWindow window, Game game) {
        super(window, game);
        init();
    }

    public void init() {
        //Retrieve BG Image and store in Image object
        //bgImage = new ImageIcon("Content//Images//Background//Level1//TransparentBG.png").getImage();

        //load GameActions
        loadGameActions();

        //load audio
        loadAudio();       

        generator = new MapGenerator(window.windowSize.getWidth(), window.windowSize.getHeight(), new Range(50, 300));
    }

    public void update(long elapsedTime) {
        //check key strokes
        checkGameInput(elapsedTime); 

        if(game.getGameState() == game.NORMAL_STATE) {            
            //Update all sprites on main list
            for(Sprite s : game.sprites) {
                if(s instanceof Enemy) {
                    if(s.getX() <= window.windowSize.getWidth() + 64)
                        s.update(elapsedTime);
                }
                else s.update(elapsedTime);
            }

            //Update user sprites
            for(Sprite s : game.userSprites) 
                s.update(elapsedTime);

            //Remove destroyed sprites from sprite list    
            for(Sprite s : game.spritesToBeRemoved) 
                game.sprites.remove(s);
            game.spritesToBeRemoved.clear();

            for(Entity e : game.spritesToBeAdded)
                game.sprites.add(0, e);
            game.spritesToBeAdded.clear();
        }     
    }

    public void draw() {
        Graphics g = getScreenGraphics();

        //BG Color
        setBGColor(g, new Color(127,131,231));

        //Draw Randomly generated Map
        generator.drawMap(g);

        //Draw user Sprites
        for(Sprite s : game.userSprites) { 
            int x = Math.round(s.getX());
            int y = Math.round(s.getY());
            g.drawImage(s.getImage(), x, y, null);
        }

        //Draw all sprites on main list that are visible on screen
        for(Sprite s : game.sprites) {
            if(s.getX() >= -100 && s.getX() <= window.windowSize.getWidth()) //Only draw visible in screen. draw 2 blocks before screen
            {
                if(s.getY() >= -64 && s.getY() <= window.windowSize.getHeight())  //Only draw visible in vertical y
                {
                    int x = Math.round(s.getX());
                    int y = Math.round(s.getY());
                    g.drawImage(s.getImage(), x, y, null); 
                }
            }
        }
    }

    ////////////////////////
    ////////////////////////
    //Set GameActions
    private void loadGameActions() {
        pause = new GameAction("Pause Game", GameAction.DETECT_INITIAL_PRESS_ONLY);

        moveLeft1 = new GameAction("Move Left");
        moveRight1 = new GameAction("Move Right");
        fire1 = new GameAction("Fire Weapon", GameAction.DETECT_INITIAL_PRESS_ONLY);

        moveLeft2 = new GameAction("Move Left");
        moveRight2 = new GameAction("Move Right");
        fire2 = new GameAction("Fire Weapon", GameAction.DETECT_INITIAL_PRESS_ONLY);

        gameScreenInput = new InputManager(game);

        //Map game action to keys
        gameScreenInput.mapToKey(pause, KeyEvent.VK_P);

        gameScreenInput.mapToKey(moveLeft1, KeyEvent.VK_LEFT);
        gameScreenInput.mapToKey(moveRight1, KeyEvent.VK_RIGHT);
        gameScreenInput.mapToKey(fire1, KeyEvent.VK_SPACE);

        gameScreenInput.mapToKey(moveLeft2, KeyEvent.VK_A);
        gameScreenInput.mapToKey(moveRight2, KeyEvent.VK_D);
        gameScreenInput.mapToKey(fire2, KeyEvent.VK_F);
    }

    //Check if game action is pressed
    private void checkGameInput(long elapsedTime) {
        int gameState = game.getGameState();

        if(pause.isPressed()) {
            if(gameState == Game.NORMAL_STATE) 
                game.setGameState(Game.PAUSED_STATE);
            else if(gameState == Game.PAUSED_STATE) 
                game.setGameState(Game.NORMAL_STATE);
        }

        //////////////////////////
        //Player 1
        if(moveLeft1.isPressed()) 
            p1.moveLeft();
        else if(moveRight1.isPressed()) 
            p1.moveRight();

        if(fire1.isPressed()) 
            p1.fire();

        ////////////////////////////
        //Player 2
        if(moveLeft2.isPressed()) 
            p2.moveLeft();
        else if(moveRight2.isPressed()) 
            p2.moveRight();

        if(fire2.isPressed()) 
            p2.fire();
    }

    public void enableCollisionBoxes() {
        collisionBoxesEnabled = true;
    }

    protected void loadAudio() {
        try {
        }       
        catch(Exception ex) {
        }
    }
    
    public void setPlayers(Tank p1, Tank p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
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
    private GameAction moveLeft;
    private GameAction moveRight;
    private GameAction jump;
    private GameAction pause;

    Image bgImage;   
    Image bgBuffer;

    public float offSetX = 0;
    public float offSetBG = 0;

    //info regarding character movement
    float walkSpeed = 4f; //horizontal velocity
    float scrollSpeed = 4f; //horizontal movement speed of bg 
    int stopDistance = 350;  //Distance that character stops and bg starts moving

    private boolean collisionBoxesEnabled = false;

    public MainGameScreen(GameWindow window, Game game) {
        super(window, game);
        init();
    }

    public void init() {
        //Retrieve BG Image and store in Image object
        bgImage = new ImageIcon("Content//Images//Background//Level1//TransparentBG.png").getImage();

        //load GameActions
        loadGameActions();

        //load audio
        loadAudio(); 
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

        //BG Image - draw part of bg that is visible
        offSetBG = offSetX%2475; //keep offset within range of 0-2475 for endless bg cycle
        g.drawImage(bgImage, Math.round(offSetBG), 0, null);  //draw bg
        if(offSetBG <= 1650)      //if first bg is 2/3 finished cycle, draw another one right infront
            g.drawImage(bgImage, Math.round(offSetBG)+2475, 0, null); 

        int rectThickness = 5; //thickness of collision boxes (number is the thickness of corners

        //Draw user Sprites
        for(Sprite s : game.userSprites) { 
            int x = Math.round(s.getX());
            int y = Math.round(s.getY());
            g.drawImage(s.getImage(), x, y, null);
            if(collisionBoxesEnabled == true) {
                int w = s.getWidth();
                int h = s.getHeight();
                g.setColor(Color.RED);
                //g.drawRect(x, y, s.getWidth(), s.getHeight()); // Full Rect
                g.drawRect(x+rectThickness, y, w-(2*rectThickness), rectThickness); //Top Rect
                g.drawRect(x+rectThickness, y+h-rectThickness, w-(2*rectThickness), rectThickness); //Bottom Rect
                g.drawRect(x, y+rectThickness, rectThickness, h-(2*rectThickness)); //Left Rect               
                g.drawRect(x+w-rectThickness, y+rectThickness, rectThickness, h-(2*rectThickness)); // Right rect 

                //Corners
                g.drawRect(x, y, rectThickness, rectThickness); // Top Left Corner
                g.drawRect(x+w-rectThickness, y, rectThickness, rectThickness); //Top Right Corner
                g.drawRect(x, y+h-rectThickness, rectThickness, rectThickness); //Bottom Left Corner
                g.drawRect(x+w-rectThickness, y+h-rectThickness, rectThickness, rectThickness); //Bottom right Corner
            }
        }

        //Draw all sprites on main list that are visible on screen
        for(Sprite s : game.sprites) {
            if(s.getX() >= -64 && s.getX() <= window.windowSize.getWidth()) //Only draw visible in screen. draw 2 blocks before screen
            {
                if(s.getY() >= -32 && s.getY() <= window.windowSize.getHeight())  //Only draw visible in vertical y
                {
                    int x = Math.round(s.getX());
                    int y = Math.round(s.getY());
                    g.drawImage(s.getImage(), x, y, null); 

                    if(collisionBoxesEnabled == true) {
                        if(s instanceof Living || s instanceof PowerUp) {
                            int w = s.getWidth();
                            int h = s.getHeight();
                            g.setColor(Color.RED);
                            //g.drawRect(x, y, s.getWidth(), s.getHeight()); // Full Rect
                            g.drawRect(x+rectThickness, y, w-(2*rectThickness), rectThickness); //Top Rect
                            g.drawRect(x+rectThickness, y+h-rectThickness, w-(2*rectThickness), rectThickness); //Bottom Rect
                            g.drawRect(x, y+rectThickness, rectThickness, h-(2*rectThickness)); //Left Rect               
                            g.drawRect(x+w-rectThickness, y+rectThickness, rectThickness, h-(2*rectThickness)); // Right rect 

                            //Corners
                            g.drawRect(x, y, rectThickness, rectThickness); // Top Left Corner
                            g.drawRect(x+w-rectThickness, y, rectThickness, rectThickness); //Top Right Corner
                            g.drawRect(x, y+h-rectThickness, rectThickness, rectThickness); //Bottom Left Corner
                            g.drawRect(x+w-rectThickness, y+h-rectThickness, rectThickness, rectThickness); //Bottom right Corner
                        }
                    }
                }
            }
        }
    }

    ////////////////////////
    ////////////////////////
    //Set GameActions
    private void loadGameActions() {
        //Assign game actions
        moveLeft = new GameAction("Move Left");
        moveRight = new GameAction("Move Right");
        jump = new GameAction("Jump", GameAction.DETECT_INITIAL_PRESS_ONLY);
        pause = new GameAction("Pause", GameAction.DETECT_INITIAL_PRESS_ONLY);

        gameScreenInput = new InputManager(game); //creates input manager for game input, passing instance of jframe

        //gameInput.setCursor(InputManager.INVISIBLE_CURSOR); //make the cursor invisible within game

        //Map game actions to keys
        gameScreenInput.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        gameScreenInput.mapToKey(moveRight, KeyEvent.VK_RIGHT);
        gameScreenInput.mapToKey(jump, KeyEvent.VK_SPACE);
        gameScreenInput.mapToKey(pause, KeyEvent.VK_P);
    }

    //Check if game action is pressed
    private void checkGameInput(long elapsedTime) {
        Player  player = game.userSprites.get(0);
        int gameState = game.getGameState();

        //If 'p' is pressed
        if(pause.isPressed()) 
        {
            pauseAudio.play();
            //Pause if not paused, resume if paused
            if(gameState == Game.NORMAL_STATE)
                game.setGameState(Game.PAUSED_STATE);
            else if(gameState == Game.PAUSED_STATE)
                game.setGameState(Game.NORMAL_STATE);
        }

        //Only acknowledge keys when not paused or loading
        if(gameState == Game.NORMAL_STATE )
        {
            if(player.getState() != player.DYING_STATE) 
            {
                //If neither left or right button press, change direction to STOPPED
                if(moveLeft.isPressed() == false && moveRight.isPressed() == false) {
                    if(player.getDirection() == player.DIRECTION_LEFT)
                        player.setDirection(player.DIRECTION_STOPPED_LEFT);
                    else if(player.getDirection() == player.DIRECTION_RIGHT)
                        player.setDirection(player.DIRECTION_STOPPED_RIGHT);
                }

                //If left button is pressed
                if(moveLeft.isPressed()) 
                {
                    player.setDirection(player.DIRECTION_LEFT); //Change direction to LEFT

                    if(player.collision.getCollisionStateX() != Collision.COLLISION_LEFT) //Move if not collision on left
                    {
                        //If player within left bound of screen, move left
                        if(player.getX() >= 0) 
                            player.setX(player.getX()-walkSpeed);
                    }
                }

                //If right button is pressed
                if(moveRight.isPressed()) 
                {
                    player.setDirection(player.DIRECTION_RIGHT); //Change direction to RIGHT

                    if(player.collision.getCollisionStateX() != Collision.COLLISION_RIGHT) //Move if not collision on right
                    {
                        //If player is farther than stop distance, start scrolling bg
                        if(player.getX() >= stopDistance) {
                            offSetX -= scrollSpeed;     
                            for(Sprite s : game.sprites) //Moves sprites back when moving right
                            {
                                s.setX(s.getX() - scrollSpeed);
                            }
                        }
                        else if(player.getX() < stopDistance)   //if player is before stop distance, physically move him backwards
                            player.setX(player.getX()+walkSpeed);
                    }
                }

                //if spacebar is pressed
                if(jump.isPressed()) {
                    player.jump(); // tell player to jump
                }
            }
        }
    }

    public void enableCollisionBoxes() {
        collisionBoxesEnabled = true;
    }

    public AudioClip pauseAudio;
    protected void loadAudio() {
        try {
            pauseAudio = Applet.newAudioClip(new File("Content//Audio//Pause.wav").toURI().toURL());
        }       
        catch(Exception ex) {
        }
    }
}
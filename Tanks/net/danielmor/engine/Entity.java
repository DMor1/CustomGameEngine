package net.danielmor.engine;

import java.util.*;
import java.awt.*;

/**Any "Thing" that is a sprite and exists in the game world. A basic abstraction of a sprite.*/
public abstract class Entity extends Sprite
{
    public Collision collision;

    private boolean gravityEnabled = false; 
    private float GRAVITY = 0.0007f;  //Default Gravity strength

    public static final int DESTROYED_STATE = 100;
    public static final int LOADING_STATE = -2; //loading sprite
    public static final int STARTING_STATE = -1;
    public static final int NORMAL_STATE = 0;   //loaded and on ground
    public static final int FALLING_STATE = 1;  //velocityY is bringing sprite down
    private int state = -1;

    public static final int DIRECTION_LEFT = -2;  //Moving left
    public static final int DIRECTION_STOPPED_LEFT = -1;  //stopped facing left
    public static final int DIRECTION_STOPPED_RIGHT = 1; //stopped facing right
    public static final int DIRECTION_RIGHT = 2;  //Moving Right
    private int direction = 0;
    
    protected Color c = Color.YELLOW; //default color

    public Entity() {
        setState(LOADING_STATE);
        createFrames();
        collision = new Collision();
        setState(STARTING_STATE);
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
        
        if(getState() == DESTROYED_STATE) 
            game.spritesToBeRemoved.add(this);

        //If gravity is enabled, add Y accerleration
        if(gravityEnabled == true) 
            setVelocityY(getVelocityY()+(GRAVITY*elapsedTime));
    }

    protected abstract void createFrames();

    /////////////////////////////////////////////////////////////
    //GRAVITY
    /**Turn gravity on for this sprite*/
    protected void enableGravity() {
        gravityEnabled = true;
    }

    /**Turn gravity off for this sprite*/
    protected void disableGravity() {
        gravityEnabled = false;
        setVelocityY(0f);
    }

    /**set the strength of the downward Y acceleration*/
    protected void setGravity(float g) {
        GRAVITY = g;
    }

    protected boolean gravityEnabled() {
        return gravityEnabled;
    }

    ////////////////////////////////////////////////////////////
    //SPRITE STATE
    /**Set the current state of the sprite*/
    public void setState(int i) {
        state = i;
    }

    /**Get the current state of the sprite*/
    public int getState() {
        return state;
    }

    //////////////////////////////////////////////////////////////
    //DIRECTION
    /**Set the direction of the sprite*/
    public void setDirection(int i) {
        direction = i;
    }

    /**Get the direction of the sprite*/
    public int getDirection() {
        return direction;
    }

    //////////////////////////////////////////////////////////
    //ANIMATION
    /**Change the animation of this sprite*/
    public void setAnim(Animation a) {
        super.setAnim(a);
    }

    ////
    protected void destroy() {
        state = DESTROYED_STATE;
    }

    //used for blocks
    public void playerCollided() {
    }

    protected void addSprite(Entity e) {
        game.spritesToBeAdded.add(e);
    }
}
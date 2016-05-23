package net.danielmor.engine;

import java.util.*;

/**A "thing" that can be aquired by the player for a specific effect*/
public abstract class PowerUp extends Entity
{
    public static final int CREATING_STATE = 2;
    public static final int CREATED_STATE = 3;
    public static final int HIT_STATE = 4;
    public static final int COLLECTING_STATE = 5;
    public static final int COLLECTED_STATE = 6;

    protected boolean stored = false;
    protected boolean collisionEnabled = false;

    public PowerUp() {
        disableGravity();
        setState(NORMAL_STATE);
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);

        if(stored == true && getState() == CREATING_STATE || getState() == CREATED_STATE) 
            processMovement();

        if(collisionEnabled == true && getState() != CREATING_STATE && getState() != CREATED_STATE) 
            processCollision();

        if(gravityEnabled() == true && getVelocityY() > 0) 
            setState(FALLING_STATE);
    }

    public void processCollision() {
        //Reset collision states to no collision and await retest
        collision.setCollisionStateX(Collision.NO_COLLISION_X);
        collision.setCollisionStateY(Collision.NO_COLLISION_Y);
        collision.setCornerCollisionState(Collision.NO_CORNER_COLLISION);

        int finalCollisionX = Collision.NO_COLLISION_X; //Place holder for collisionX state while cycling through sprites

        //Cycle through all sprites
        for(Entity s : game.sprites) 
        {
            //Test collision if on screen (x axis) - not testing y axis
            if(s.getX() >= -64 && s.getX() <= game.window.windowSize.getWidth()) 
            {
                //Test collision if at least 32 pixels behind and in front
                boolean collided = collision.generalCollision(this, s);  //Test for general collision
                //Test if general Collision occurs
                if(collided)                    
                {    
                    int collisionX = collision.testCollisionX(this, s); //change collision state for X axis 
                    int collisionY = collision.testCollisionY(this, s);  //change collision state for y axis
                    int cornerCollision = collision.testCornerCollision(this, s);  //change collision state for corners

                    //Fix x position if left or right collision
                    if(collisionX == Collision.COLLISION_LEFT || collisionX == Collision.COLLISION_RIGHT) 
                    {
                        finalCollisionX = collisionX;
                        collision.fixPositionX(this, s);
                    }

                    //if bottom collision
                    if(collisionY == Collision.COLLISION_BOTTOM) //Bottom Collision
                    {
                        if(getState() != DESTROYED_STATE)
                        {
                            disableGravity();
                            collision.fixPositionY(this, s);
                            setState(NORMAL_STATE);
                        }
                    }  
                }
                else  //If no collision occurs
                {
                    if(getState() == NORMAL_STATE) {
                        int cornerCollision = collision.testCornerCollision(this, s);  //change collision state for corners
                        if(cornerCollision == Collision.NO_CORNER_COLLISION) 
                        {
                            enableGravity();
                        }
                    }
                }
            }
        }
        collision.setCollisionStateX(finalCollisionX); // set collision state for x axis for whole cycle of sprites
        if(finalCollisionX == Collision.COLLISION_LEFT || finalCollisionX == Collision.COLLISION_RIGHT) 
            collisionEffect();
    }

    float originalY = 0;
    int distance = 50;
    public void processMovement() {
        if(getState() == CREATING_STATE) {
            originalY = getY();
            setVelocityY(-0.25f);
            addScore();
            setState(CREATED_STATE);
        }
        else if(getState() == CREATED_STATE) {
            if(getY() <= originalY - distance) {
                setVelocityY(0f);
                created();
            }
        }
    }

    protected void collisionEffect() {
    }

    protected void addScore() {
    }

    protected void created() {
    }

    public void playerCollided() {
        if(getState() == NORMAL_STATE) {
            setState(HIT_STATE);
        }
    }

    /**Set the current state of the sprite*/
    public void setState(int i) {
        if(getState() != DESTROYED_STATE) {
            super.setState(i);
        }
    }
}
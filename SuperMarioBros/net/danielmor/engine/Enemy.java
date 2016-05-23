package net.danielmor.engine;

import java.util.*;

/**A living enemy who moves and dies, and can hurt the player*/
public abstract class Enemy extends Living
{
    public static final int KOOPA_STUNNED = 3;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private float walkSpeed = 0.075f;

    protected boolean pointsCollected = false;

    public Enemy() {
    }

    long killTime = 2000; //default time till destroyed
    long killTimer = 0;
    public void update(long elapsedTime) {
        super.update(elapsedTime);

        if(getState() == DYING_STATE) {
            killTimer += elapsedTime;

            if(killTimer >= killTime) 
                destroy();
        }

        //Reset collision states to no collision and await retest
        collision.setCollisionStateX(Collision.NO_COLLISION_X);
        collision.setCollisionStateY(Collision.NO_COLLISION_Y);
        collision.setCornerCollisionState(Collision.NO_CORNER_COLLISION);

        processPlayerCollision(game.userSprites);
        processCollision(game.sprites);
    } 

    //////////////////////////////////////////////////////

    public void processCollision(ArrayList<Entity> sprites) {
        //Cycle through all sprites
        for(Entity s : sprites) {
            boolean collided = collision.generalCollision(this, s);  //Test for general collision
            if(collided) //if general collision occurs
            {
                int collisionX = collision.testCollisionX(this, s); //change collision state for X axis 
                int collisionY = collision.testCollisionY(this, s);  //change collision state for y axis

                //Fix x position if left or right collision
                if(collisionX == Collision.COLLISION_LEFT || collisionX == Collision.COLLISION_RIGHT) 
                {
                    if(s instanceof Enemy == false) {
                        collision.fixPositionX(this, s);
                        collisionEffect();
                    }
                }

                if(collisionY == Collision.COLLISION_BOTTOM) //Bottom Collision
                {
                    disableGravity();
                    setState(NORMAL_STATE);
                    collision.fixPositionY(this, s);
                }        
            }
            else  //If no collision occurs
            {
                if(getState() == NORMAL_STATE) {
                    int cornerCollision = collision.testCornerCollision(this, s);  //change collision state for corners
                    if(cornerCollision == Collision.NO_CORNER_COLLISION) {
                        enableGravity();
                    }
                }
            }
        }
    }

    public void processPlayerCollision(ArrayList<Player> players) {
        //Cycle through all sprites
        for(Player p : players) {
            boolean collided = collision.generalCollision(this, p);  //Test for general collision
            if(collided) //if general collision occurs
            {
                int collisionY = collision.testCollisionY(this, p);  //change collision state for y axis
                int cornerCollision = collision.testCornerCollision(this, p);

                if(collisionY == Collision.COLLISION_TOP || cornerCollision == Collision.TOP_CORNERS) {
                    if(p.getState() != DYING_STATE && getState() != DESTROYED_STATE) {
                        if(pointsCollected == false) {
                            game.scoreBoard.addScorePoints(100);
                            pointsCollected = true;
                        }
                        die();
                    }
                }
                else {
                    if(getState() != DYING_STATE && getState() != DESTROYED_STATE) //if enemy isn't dying or dead, kill player
                        p.die();
                }
            }
        }
    }

    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    protected void setWalkSpeed(float f) {
        walkSpeed = f;
    }

    protected float getWalkSpeed() {
        return walkSpeed;
    }

    //If hits left or right wall
    protected void collisionEffect() {
        setVelocityX(-getVelocityX()); //Reverse direction
    }

    protected void setKillTime(long i) {
        killTime = i;
    }
}
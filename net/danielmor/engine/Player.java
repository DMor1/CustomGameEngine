package net.danielmor.engine;

/**A living "thing" who is controlled by a person*/
public abstract class Player extends Living
{
    public Animation standingRight;
    public Animation standingLeft;
    public Animation walkingRight;
    public Animation walkingLeft;
    public Animation jumpingRight;
    public Animation jumpingLeft;
    public Animation dying;

    public Player() {
        setAnim(standingRight);
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);    

        if(getState() != DYING_STATE && getState() != DESTROYED_STATE) 
            processCollision();

        if(getY() >= game.window.windowSize.getHeight()) 
            die();
    }

    /////////////////////////////////////////////////////////////////

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
                if(s.getX() >= getX()- 64 && s.getX() <= getX() + 64) 
                {
                    boolean collided = collision.generalCollision(this, s);  //Test for general collision
                    //Test if general Collision occurs
                    if(collided)                    
                    {    
                        if(s instanceof PowerUp) {
                            s.playerCollided();
                            game.hitBlock.play();
                            continue;
                        }

                        int collisionX = collision.testCollisionX(this, s); //change collision state for X axis 
                        int collisionY = collision.testCollisionY(this, s);  //change collision state for y axis
                        int cornerCollision = collision.testCornerCollision(this, s);  //change collision state for corners

                        if(s instanceof Enemy == false) {
                            //Fix x position if left or right collision
                            if(collisionX == Collision.COLLISION_LEFT || collisionX == Collision.COLLISION_RIGHT) 
                            {
                                finalCollisionX = collisionX;
                                collision.fixPositionX(this, s);
                            }

                            //if bottom collision
                            if(collisionY == Collision.COLLISION_BOTTOM) //Bottom Collision
                            {
                                if(getState() != DYING_STATE && getState() != DESTROYED_STATE)
                                {
                                    disableGravity();
                                    collision.fixPositionY(this, s);
                                    setState(NORMAL_STATE);
                                }
                            }  
                            else if(collisionY == Collision.COLLISION_TOP) //if hit head on something
                            {
                                setVelocityY(-getVelocityY()/2); //Stop jump in place
                                collision.fixPositionY(this, s);
                                if(s instanceof Block) 
                                    s.playerCollided();
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
        }
        collision.setCollisionStateX(finalCollisionX); // set collision state for x axis for whole cycle of sprites
    }

    protected void destroy() {
        //DO NOTHING
    }

    public void die() {
        if(getState() != DYING_STATE && getState() != DESTROYED_STATE) {
            super.die();
            game.mainThemeAudio.stop();
            game.dying.play();
            setGravity(0.00075f);
            setVelocityY(-0.5f);
            enableGravity();
        }
    }
}
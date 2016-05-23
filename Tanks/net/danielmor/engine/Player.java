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
    }

    protected void destroy() {
        //DO NOTHING
    }

    public void die() {
        if(getState() != DYING_STATE && getState() != DESTROYED_STATE) {
            super.die();
            setGravity(0.00075f);
            setVelocityY(-0.5f);
            enableGravity();
        }
    }
}
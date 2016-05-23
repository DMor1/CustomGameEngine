package net.danielmor.engine;

/**Any living "thing" who can live, move and die throughout the game*/
public abstract class Living extends Entity
{
    public static final int DYING_STATE = 99;

    public static final int JUMPING_STATE = 2; //Gives ability for sprites to jump and alert other classes
    private float jumpVelocity = 0.35f; //Strength of jump

    public Living() {
        enableGravity();
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);

        if(gravityEnabled() == true && getVelocityY() > 0) 
            setState(FALLING_STATE);
    }

    ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////

    /**Set the current state of the sprite*/
    public void setState(int i) {
        if(getState() != DESTROYED_STATE) {
            if(getState() != DYING_STATE) 
                super.setState(i);
        }
    }

    /**Jump if currently not jumping. enable gravity, change to JUMPING_STATE, and create negative jumpVelocity*/
    public void jump() {
        if(getState() == NORMAL_STATE) {
            game.jump.play();
            setState(JUMPING_STATE);
            setVelocityY(-jumpVelocity);
            enableGravity();
        }
    }

    /**Get current jump velocity*/
    public float getJumpVelocity() {
        return jumpVelocity;
    }

    /**Set the jump velocity for this sprite*/
    public void setJumpVelocity(float i) {
        jumpVelocity = i;
    }

    ///////////////////////////////////////////////
    //////////////////////////////////////////////
    protected void die() {
        setState(DYING_STATE);
        setVelocityX(0f);
    }
}
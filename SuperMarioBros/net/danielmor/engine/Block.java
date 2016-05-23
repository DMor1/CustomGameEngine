package net.danielmor.engine;

/**A solid object, usually used for scenery*/
public abstract class Block extends Entity
{
    public static final int HIT_STATE = 2;
    public static final int MOVINGUP_STATE = 3;
    public static final int MOVINGDOWN_STATE = 4;
    public static final int COLLECTED_STATE = 5;

    private boolean bounceEffect = false;

    public Block() {
        disableGravity();
        disableBounceEffect();
        setState(NORMAL_STATE);
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);

        if(bounceEffect == true) 
            processBlockMovement(elapsedTime);
    }

    float originalY;
    //Process the movement of a block when hit (up then down)
    protected void processBlockMovement(long elapsedTime) {
        float movementSpeed = 0.15f;
        int distance = 12;
        if(getState() == HIT_STATE) {
            originalY = getY();
            setVelocityY(-movementSpeed);
            setState(MOVINGUP_STATE);
            hitEffect();
        }
        else if(getState() == MOVINGUP_STATE) {
            if(getY() <= originalY - distance) {
                setVelocityY(movementSpeed);
                setState(MOVINGDOWN_STATE);
            }
        }
        else if(getState() == MOVINGDOWN_STATE) {
            if(getY() >= originalY) {
                setVelocityY(0f);
                setState(COLLECTED_STATE);
                setY(originalY);
            }
        }
        else if(getState() == COLLECTED_STATE) {
            processCollection();
        }
    }

    protected void processCollection() {
    }
    
    protected void hitEffect() {
    }

    //Tells Block sprite that a player hit the block from the bottom
    public void playerCollided() {
        if(getState() == NORMAL_STATE)
            setState(HIT_STATE);
    }

    protected void enableBounceEffect() {
        bounceEffect = true;
    }

    protected void disableBounceEffect() {
        bounceEffect = false;
    }
}
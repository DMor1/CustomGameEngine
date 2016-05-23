import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class Mario extends Player
{
    public Mario() {
        setGravity(0.001f);
        setJumpVelocity(0.5f);
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);

        //Change the animation of this sprite based on its state and direction
        processStatesAnimation();
    }

    //Change animations based on state and direction
    private void processStatesAnimation() {
        if(getState() == JUMPING_STATE || getState() == FALLING_STATE) {
            if(getDirection() == DIRECTION_RIGHT)
                setAnim(jumpingRight);
            else if(getDirection() == DIRECTION_LEFT)
                setAnim(jumpingLeft);
        }

        if(getState() == NORMAL_STATE) {
            if(getDirection() == DIRECTION_STOPPED_RIGHT)
                setAnim(standingRight);
            else if(getDirection() == DIRECTION_STOPPED_LEFT)
                setAnim(standingLeft);
            else if(getDirection() == DIRECTION_RIGHT)
                setAnim(walkingRight);
            else if(getDirection() == DIRECTION_LEFT)
                setAnim(walkingLeft);
        }

        if(getState() == DYING_STATE) 
            setAnim(dying);
    }

    ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////
    //Load all the frames and animations 

    protected void createFrames() {
        createStandingRight();
        createStandingLeft();
        createWalkingRight();
        createWalkingLeft();
        createJumpingRight();
        createJumpingLeft();
        createDying();
    }

    private void createStandingRight() {
        Image standing1 = new ImageIcon("Content//Images//Sprites//Players//Mario//Right//Standing.png").getImage();

        standingRight = new Animation();
        standingRight.addFrame(standing1, 500);
    }

    private void createStandingLeft() {
        Image standing1 = new ImageIcon("Content//Images//Sprites//Players//Mario//Left//Standing.png").getImage();

        standingLeft = new Animation();
        standingLeft.addFrame(standing1, 500);
    }

    private void createWalkingRight() {
        Image standing =  new ImageIcon("Content//Images//Sprites//Players//Mario//Right//standing.png").getImage();
        Image walking1 = new ImageIcon("Content//Images//Sprites//Players//Mario//Right//walking//walking1.png").getImage();
        Image walking2 = new ImageIcon("Content//Images//Sprites//Players//Mario//Right//walking//walking2.png").getImage();
        Image walking3 = new ImageIcon("Content//Images//Sprites//Players//Mario//Right//walking//walking3.png").getImage();

        walkingRight = new Animation();
        walkingRight.addFrame(standing, 120);
        walkingRight.addFrame(walking1, 140);
        walkingRight.addFrame(walking2, 60);
        walkingRight.addFrame(walking3, 100);
    }

    private void createWalkingLeft() {
        Image standing =  new ImageIcon("Content//Images//Sprites//Players//Mario//Left//standing.png").getImage();
        Image walking1 = new ImageIcon("Content//Images//Sprites//Players//Mario//Left//walking//walking1.png").getImage();
        Image walking2 = new ImageIcon("Content//Images//Sprites//Players//Mario//Left//walking//walking2.png").getImage();
        Image walking3 = new ImageIcon("Content//Images//Sprites//Players//Mario//Left//walking//walking3.png").getImage();

        walkingLeft = new Animation();
        walkingLeft.addFrame(standing, 120);
        walkingLeft.addFrame(walking1, 140);
        walkingLeft.addFrame(walking2, 60);
        walkingLeft.addFrame(walking3, 100);
    }

    private void createJumpingRight() {
        Image jumping =  new ImageIcon("Content//Images//Sprites//Players//Mario//Right//Jumping.png").getImage();

        jumpingRight = new Animation();
        jumpingRight.addFrame(jumping, 500);
    }

    private void createJumpingLeft() {
        Image jumping =  new ImageIcon("Content//Images//Sprites//Players//Mario//Left//Jumping.png").getImage();

        jumpingLeft = new Animation();
        jumpingLeft.addFrame(jumping, 500);
    }

    private void createDying() {
        Image dyingIMG =  new ImageIcon("Content//Images//Sprites//Players//Mario//Dying.png").getImage();

        dying = new Animation();
        dying.addFrame(dyingIMG, 500);
    }
}
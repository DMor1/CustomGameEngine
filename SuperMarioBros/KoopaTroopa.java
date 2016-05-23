import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class KoopaTroopa extends Enemy
{
    Animation walkingRight;
    Animation walkingLeft;
    Animation koopaHit;
    Animation koopaShelled;

    boolean shelled = false;
    int hitCounter = 0;

    public KoopaTroopa(int direction) {
        if(direction == LEFT) 
            setVelocityX(-getWalkSpeed());
        else if(direction == RIGHT) 
            setVelocityX(getWalkSpeed());
        setState(NORMAL_STATE);
    }

    long timer = 0;
    public void update(long elapsedTime) {
        super.update(elapsedTime);

        if(shelled == true) {
            timer += elapsedTime;

            if(timer >= 2000) {
                shelled = false;
                setVelocityX(getWalkSpeed());
                hitCounter = 0;
                timer = 0;
            }
        }

        if(hitCounter == 0) {
            if(getVelocityX() < 0)
                setAnim(walkingLeft);
            else if(getVelocityX() > 0)
                setAnim(walkingRight);
        }
        else if(hitCounter == 1) {
            setAnim(koopaHit);
        }
        else if(hitCounter == 2) {
            setAnim(koopaShelled);
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
                        setState(KOOPA_STUNNED);
                        setVelocityX(0f);
                        hitCounter++;
                        shelled = true;
                        if(pointsCollected == false) {
                            game.scoreBoard.addScorePoints(100);
                            pointsCollected = true;
                        }
                    }
                }
                else {
                    if(getState() != DYING_STATE && getState() != DESTROYED_STATE && getState() != KOOPA_STUNNED && shelled == false && hitCounter == 0) //if enemy isn't dying or dead, kill player
                        p.die();
                }
            }
        }
    }

    public void createFrames() {
        createWalkingRight();
        createWalkingLeft();
        createKoopaHit();
        setAnim(walkingRight);
    }

    private void createWalkingRight() {
        Image walking1 = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Right//Koopa Troopa1.png").getImage();
        Image walking2 = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Right//Koopa Troopa2.png").getImage();
        Image walking3 = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Right//Koopa Troopa3.png").getImage();
        Image walking4 = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Right//Koopa Troopa4.png").getImage();

        walkingRight = new Animation();
        walkingRight.addFrame(walking1, 100);
        walkingRight.addFrame(walking2, 100);
        walkingRight.addFrame(walking3, 100);
        walkingRight.addFrame(walking4, 100);
    }

    private void createWalkingLeft() {
        Image walking1 = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Left//Koopa Troopa1.png").getImage();
        Image walking2 = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Left//Koopa Troopa2.png").getImage();
        Image walking3 = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Left//Koopa Troopa3.png").getImage();
        Image walking4 = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Left//Koopa Troopa4.png").getImage();

        walkingLeft = new Animation();
        walkingLeft.addFrame(walking1, 100);
        walkingLeft.addFrame(walking2, 100);
        walkingLeft.addFrame(walking3, 100);
        walkingLeft.addFrame(walking4, 100);
    }

    private void createKoopaHit() {
        Image hit = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Koopa Hit.png").getImage();
        Image shelled = new ImageIcon("Content//Images//Sprites//Enemies//Koopa Troopa//Koopa Shelled.png").getImage();

        koopaHit = new Animation();
        koopaHit.addFrame(hit, 500);

        koopaShelled = new Animation();
        koopaShelled.addFrame(shelled, 500);
    }
}
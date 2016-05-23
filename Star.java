import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class Star extends PowerUp
{
    Animation star;

    float jumpPower = 0.6f;

    public Star() {
        setGravity(0.002f);
    }

    public Star(boolean stored) {
        this.stored = stored;
        setState(CREATING_STATE);
        collisionEnabled = true;
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    public void playerCollided() {
        if(getState() != DESTROYED_STATE) {
            game.scoreBoard.addScorePoints(1000);
            destroy();
        }
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
                            setVelocityY(-jumpPower);
                            collision.fixPositionY(this, s);
                        }
                    }  
                    else if(collisionY == Collision.COLLISION_TOP) {
                        setVelocityY(jumpPower);
                    }
                }
            }
        }
        collision.setCollisionStateX(finalCollisionX); // set collision state for x axis for whole cycle of sprites
        if(finalCollisionX == Collision.COLLISION_LEFT || finalCollisionX == Collision.COLLISION_RIGHT) 
            collisionEffect();
    }

    //If hits left or right wall
    protected void collisionEffect() {
        setVelocityX(-getVelocityX()); //Reverse direction
    }

    protected void created() {
        setVelocityY(-.3f);
        setVelocityX(0.095f);
        setState(NORMAL_STATE);
        enableGravity();
    }

    public void createFrames() {
        Image star1 = new ImageIcon("Content//Images//Sprites//PowerUp//Star//Star1.png").getImage();
        Image star2 = new ImageIcon("Content//Images//Sprites//PowerUp//Star//Star2.png").getImage();
        Image star3 = new ImageIcon("Content//Images//Sprites//PowerUp//Star//Star3.png").getImage();
        Image star4 = new ImageIcon("Content//Images//Sprites//PowerUp//Star//Star4.png").getImage();

        star = new Animation();
        star.addFrame(star1, 150);    
        star.addFrame(star2, 150);
        star.addFrame(star3, 150);
        star.addFrame(star4, 150);
        setAnim(star);
    }
}
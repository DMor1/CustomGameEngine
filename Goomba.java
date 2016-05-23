import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class Goomba extends Enemy
{
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    Animation walking;
    Animation dead;
    
    public Goomba(int direction) {
        if(direction == LEFT) 
            setVelocityX(-getWalkSpeed());
        else if(direction == RIGHT) 
            setVelocityX(getWalkSpeed());
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);       

        if(getState() == DYING_STATE) {
            setAnim(dead);
        }
    }

    public void createFrames() {
        Image goomba1 = new ImageIcon("Content//Images//Sprites//Enemies//Goomba//Goomba1.png").getImage();
        Image goomba2 = new ImageIcon("Content//Images//Sprites//Enemies//Goomba//Goomba2.png").getImage();
        Image goombaDead = new ImageIcon("Content//Images//Sprites//Enemies//Goomba//Goomba Dead.png").getImage();

        walking = new Animation();
        walking.addFrame(goomba1, 250);
        walking.addFrame(goomba2, 250);

        dead = new Animation();
        dead.addFrame(goombaDead, 500);

        setAnim(walking);
    }      
}
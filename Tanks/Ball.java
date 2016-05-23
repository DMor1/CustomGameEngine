import net.danielmor.engine.Entity;
import net.danielmor.engine.Sprite;
import net.danielmor.engine.Animation; 

import java.awt.*;
import java.awt.image.*;

public class Ball extends Entity
{
    Color c = Color.RED.darker();
    int size = 25;
    float speed = .2f;

    public Ball(int x, int y, float xSpeed, float ySpeed) {
        this.speed = speed;
        setLocation(x,y);
        setVelocityX(xSpeed);
        setVelocityY(ySpeed);
        createFrames();
    }

    boolean hitX = false;
    boolean hitY = false;
    public void update(long elapsedTime) {
        super.update(elapsedTime);

        if(getX() >= (game.getWidth()-size) && hitX == false) {
            setVelocityX(-getVelocityX());
            hitX = true;
        }
        else if(getX() <= 0 && hitX == false) {
            setVelocityX(-getVelocityX());
            hitX = true;
        }
        else 
            hitX = false;

        if(getY() >= (game.getHeight()-size) && hitY == false) {
            setVelocityY(-getVelocityY());
            hitY = true;
        }
        else if(getY() <= 0 && hitY == false) {
            setVelocityY(-getVelocityY());
            hitY = true;
        }
        else
            hitY = false;

    }

    protected void createFrames() {    
        int imgSize = 25;
        BufferedImage img = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(c);
        g.fillOval(0,0,imgSize,imgSize);

        anim = new Animation();
        anim.addFrame(img, 500);
        setAnim(anim);
    }
}
import net.danielmor.engine.Entity;
import net.danielmor.engine.Sprite;
import net.danielmor.engine.Animation; 
import net.danielmor.engine.Util;

import java.awt.*;
import java.awt.image.*;

public class Cloud extends Entity
{
    public Cloud() {
        float speed = Util.getRandFloat(0.01f, 0.07f, 4);
        float locationX = Util.getRandFloat(0, 500, 4);
        float locationY = Util.getRandFloat(0, 75, 4);
        
        setVelocityX(speed);
        setLocation(locationX, locationY);
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
        
        if(getX() >= 800) 
            setLocation(-120, getY());
    }

    protected void createFrames() {   
        int w = 120;
        int h = w/2;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        Polygon cloud = generateRandomCloud();
        g.setColor(Color.WHITE);
        g.fillPolygon(cloud);
        
        g.setColor(Color.GRAY.darker());
        g.drawPolygon(cloud);

        anim = new Animation();
        anim.addFrame(img, 500);
        setAnim(anim);
    }

    private Polygon generateRandomCloud() {
        int offSetX = Util.getRandInt(-20, 40);
        int offSetY = Util.getRandInt(-20, 25);
        
        Polygon cloud = new Polygon();
        cloud.addPoint(10+offSetX, 20+offSetY);
        cloud.addPoint(35+offSetX, 10+offSetY);
        cloud.addPoint(60+offSetX, 4+offSetY);
        cloud.addPoint(75+offSetX, 10+offSetY);
        cloud.addPoint(100+offSetX, 20+offSetY);
        cloud.addPoint(120+offSetX, 40+offSetY);
        cloud.addPoint(60+offSetX, 60+offSetY);
        cloud.addPoint(35+offSetX, 70+offSetY);
        cloud.addPoint(20+offSetX, 60+offSetY);
        cloud.addPoint(0+offSetX, 42+offSetY);
        
        return cloud;
    }
}
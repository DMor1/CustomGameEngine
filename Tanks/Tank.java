import net.danielmor.engine.Player;
import net.danielmor.engine.Sprite;
import net.danielmor.engine.Animation; 
import net.danielmor.engine.Util;

import java.awt.*;
import java.awt.image.*;

public class Tank extends Player
{
    enum Direction {LEFT, RIGHT}

    Direction tankDirection;

    public static MapGenerator generator;
    public static MapGenerator.MapLine[] map;

    float speed = 0.03f;

    public Tank(Color c, String direction) {
        super.c = c;
        tankDirection = direction.equalsIgnoreCase("L") ? Direction.LEFT : Direction.RIGHT;
        createFrames();        

        setGravity(0.0008f);
        enableGravity();
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);

        if(collided() == true) {
            disableGravity();
        }
        else 
            enableGravity();
    }

    protected void createFrames() {    
        int size = 40;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        drawTank(g, size);

        if(tankDirection == Direction.LEFT)
            img = Util.horizontalFlip(img);

        anim = new Animation();
        anim.addFrame(img, 500);
        setAnim(anim);
    }

    private void drawTank(Graphics g, int size) {
        //g.setColor(Color.WHITE);
        //g.fillRect(0,0, size, size ); //BG - TEMPORARY

        //Tank Appearance
        ///////////////////////////////////////////
        //Tank Body
        g.setColor(c.darker());
        g.fill3DRect(6, size-23, 25, 13, true); 

        //Tank Inner Body
        int offSet = 3;
        g.setColor(c.darker());
        g.fill3DRect(6+offSet, size-23+offSet, 25-(2*offSet), 13-(2*offSet), true);

        //Tank Treads/Wheels
        g.setColor(Color.gray.darker());
        g.fillRect(5, size-10, 30, 10); //Wheel Rect
        g.fillOval(0, size-10, 10, 10); //Round left
        g.fillOval(size-10, size-10, 10, 10); //Round right

        //Tank Treads/Wheels Outline
        g.setColor(Color.BLACK.darker());
        g.drawRect(5, size-10, 30, 10); //Wheel Rect
        g.drawOval(0, size-10, 10, 10); //Round left
        g.drawOval(size-10-1, size-10, 10, 10); //Round right

        //Tread Gray Cicles fill
        g.setColor(Color.GRAY);
        g.fillOval(0, size-10, 10, 10); //Round left
        g.fillOval(size-10-1, size-10, 10, 10); //Round right

        //Tread Moving Parts - plus
        g.setColor(Color.BLACK);
        g.drawLine(1, size-(9/2), 10, size-(9/2)); //horizontal left wheel
        g.drawLine(5, size-10, 5, size); //vertical left wheel

        g.drawLine(size-10-1, size-(9/2), size, size-(9/2)); //horizontal right wheel
        g.drawLine(size-10-1+5, size-10, size-10-1+5, size); //vertical right wheel        

        //Tank Canon        
        int startX = 6;
        int startY = 17;
        int canonLength = 20;
        int canonElevation = 6;
        int canonThickness = 10;

        Polygon canon = new Polygon(); 
        canon.addPoint(startX, startY);
        canon.addPoint(startX+canonThickness, startY);
        canon.addPoint(startX+canonThickness+canonLength, startY-canonElevation-2);
        canon.addPoint(startX+(canonThickness/2)+canonLength, startY-canonElevation-(canonThickness/2));

        //Filled
        g.setColor(c);
        g.fillPolygon(canon);

        //Outline
        g.setColor(c.darker());
        g.drawPolygon(canon);
    }

    public static void setMapGenerator(MapGenerator g) {
        generator = g;
        map = g.getMap();
    }

    private boolean collided() {
        boolean collision = false;

        int x = Math.round(getX())+20; //size is 40, so midpoint is +20
        int y = Math.round(getY())+40; //Moves point to bottom of tank

        for(int i = 0; i < generator.mapWidth; i++) {
            int floor = map[i].floor;

            if(x != i)
                continue;
            else {
                //If is on same x coordinate
                //Check if is on same y coordinate

                if(y >= floor) { 
                    collision = true;

                    //Correct y position
                    setY(floor-40);
                }
            }

        }

        return collision;
    }

    public void moveRight() {
        setVelocityX(speed);
    }

    public void moveLeft() {
        setVelocityX(-speed);
    }
    
    public void fire() {
        System.out.println("fire");
    }
}
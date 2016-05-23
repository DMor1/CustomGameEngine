package net.danielmor.engine;

import java.awt.*;

public class Collision 
{
    //Collision not yet tested
    public static final int COLLISION_INVALID = -1;

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    //X Axis collision States
    public static final int NO_COLLISION_X = 0;
    public static final int COLLISION_X = 1;
    public static final int COLLISION_LEFT = 2;
    public static final int COLLISION_RIGHT = 3;

    //Y Axis collision States
    public static final int NO_COLLISION_Y = 0;
    public static final int COLLISION_Y = 1;
    public static final int COLLISION_TOP = 2;
    public static final int COLLISION_BOTTOM = 3;

    ///////////////////////////////////////////////
    //////////////////////////////////////////////
    //Corner Collision States
    public static final int NO_CORNER_COLLISION = 0;
    public static final int TOPLEFT_CORNER_COLLISION = 1;
    public static final int TOPRIGHT_CORNER_COLLISION = 2;
    public static final int BOTTOMLEFT_CORNER_COLLISION = 3;
    public static final int BOTTOMRIGHT_CORNER_COLLISION = 4;

    //Multiple Corners Collision
    public static final int TOP_CORNERS = 5;
    public static final int BOTTOM_CORNERS = 6;
    public static final int LEFT_CORNERS = 7;
    public static final int RIGHT_CORNERS = 8;

    //////////////////////////////////////////////////
    //////////////////////////////////////////////////

    //Collision state variables
    private int collisionStateX;
    private int collisionStateY;
    private int cornerCollisionState;

    private Rectangle r1; //Collision rect1
    private Rectangle r2; //Collision rect2

    private int rectThickness; //Thickness of bounding collision rectangles

    public Collision() {
        r1 = new Rectangle();
        r2 = new Rectangle();
        rectThickness = 5;
        collisionStateX = COLLISION_INVALID;
        collisionStateY = COLLISION_INVALID;
        cornerCollisionState = COLLISION_INVALID;
    }

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    /**Tests if 2 sprite's bounds collide - outer bound*/
    public boolean generalCollision(Sprite s1, Sprite s2) {
        boolean collision = false;

        r1.setBounds(Math.round(s1.getX()), Math.round(s1.getY()), s1.getWidth(), s1.getHeight());
        r2.setBounds(Math.round(s2.getX()), Math.round(s2.getY()), s2.getWidth(), s2.getHeight());       

        if(r1.intersects(r2))
            collision = true;

        return collision;
    }

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    //return if collision comes from right, left both or neither
    public int testCollisionX(Sprite s1, Sprite s2) {
        boolean collisionLeft = leftCollision(s1, s2);
        boolean collisionRight = rightCollision(s1, s2);

        int stateX = -1;

        if(collisionLeft == true && collisionRight == true) //If left and right collision 
            stateX = COLLISION_X;  //set to general X collision
        else if(collisionLeft == true)    //if only left collision
            stateX = COLLISION_LEFT;   //set to left
        else if(collisionRight == true)      //if only right collision
            stateX = COLLISION_RIGHT;  //set to right
        else 
            stateX = NO_COLLISION_X;   //if neither, set to false collision

        return stateX;
    }

    //return if collision comes from top, bottom, both or neither
    public int testCollisionY(Sprite s1, Sprite s2) {
        boolean collisionTop = topCollision(s1, s2);
        boolean collisionBottom = bottomCollision(s1, s2); 

        int stateY = -1;

        if(collisionTop == true && collisionBottom == true) //If top and bottom collision 
            stateY = COLLISION_Y;  //set to general Y collision
        else if(collisionTop == true)    //if only top collision
            stateY = COLLISION_TOP;   //set to top
        else if(collisionBottom == true)      //if only bottom collision
            stateY = COLLISION_BOTTOM;  //set to bottom
        else 
            stateY = NO_COLLISION_Y;   //if neither, set to false collision

        setCollisionStateY(stateY);
        return stateY;
    }

    //return if corner collision occurs
    public int testCornerCollision(Sprite s1, Sprite s2) {
        boolean topLeft = topLeftCornerCollision(s1, s2);
        boolean topRight = topRightCornerCollision(s1, s2);
        boolean bottomLeft = bottomLeftCornerCollision(s1, s2);
        boolean bottomRight = bottomRightCornerCollision(s1,s2);

        if(topLeft == true && topRight == true) //if both top corners true
            cornerCollisionState = TOP_CORNERS;
        else if(bottomLeft == true && bottomRight == true) //if both bottom corners true
            cornerCollisionState = BOTTOM_CORNERS;
        else if(topLeft == true && bottomLeft == true) //if both left corners true
            cornerCollisionState = LEFT_CORNERS;
        else if(topRight == true && bottomRight == true) //if both right corners true
            cornerCollisionState = RIGHT_CORNERS;

        return cornerCollisionState;
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    //Test if sprite 1's bottom collides with sprite 2
    public boolean bottomCollision(Sprite s1, Sprite s2) {
        boolean collisionTest = false;

        int x1 = Math.round(s1.getX());
        int y1 = Math.round(s1.getY());
        int w1 = s1.getWidth();
        int h1 = s1.getHeight(); 

        r1.setBounds(x1+rectThickness, y1+h1-rectThickness, w1-(2*rectThickness), rectThickness); //Small Rectangle on bottom of sprite 1
        r2.setBounds(Math.round(s2.getX()), Math.round(s2.getY()), s2.getWidth(), s2.getHeight()); //full square on sprite 2

        if(r1.intersects(r2))
            collisionTest = true;

        return collisionTest;
    }

    //Test if sprite 1's top collides with sprite 2
    public boolean topCollision(Sprite s1, Sprite s2) {
        boolean collisionTest = false;

        int x1 = Math.round(s1.getX());
        int y1 = Math.round(s1.getY());
        int w1 = s1.getWidth();
        int h1 = s1.getHeight(); 

        r1.setBounds(x1+rectThickness, y1, w1-(2*rectThickness), rectThickness); //Small Rectangle on top of sprite 1
        r2.setBounds(Math.round(s2.getX()), Math.round(s2.getY()), s2.getWidth(), s2.getHeight()); //full square on sprite 2

        if(r1.intersects(r2))
            collisionTest = true;

        return collisionTest;
    }

    //Test if sprite 1's left collides with sprite 2
    public boolean leftCollision(Sprite s1, Sprite s2) {
        boolean collisionTest = false;

        int x1 = Math.round(s1.getX());
        int y1 = Math.round(s1.getY());
        int w1 = s1.getWidth();
        int h1 = s1.getHeight(); 

        //r1.setBounds(x1, y1, rectThickness, h1); //full left rect side
        r1.setBounds(x1, y1+rectThickness, rectThickness, h1-(2*rectThickness)); //ONLY center of left side (not corners)
        r2.setBounds(Math.round(s2.getX()), Math.round(s2.getY()), s2.getWidth(), s2.getHeight()); //full square on sprite 2

        if(r1.intersects(r2))
            collisionTest = true;

        return collisionTest;
    }

    //Test if sprite 1's right collides with sprite 2
    public boolean rightCollision(Sprite s1, Sprite s2) {
        boolean collisionTest = false;

        int x1 = Math.round(s1.getX());
        int y1 = Math.round(s1.getY());
        int w1 = s1.getWidth();
        int h1 = s1.getHeight(); 

        //r1.setBounds(x1+w1-rectThickness, y1, rectThickness, h1); //full right rect side
        r1.setBounds(x1+w1-rectThickness, y1+rectThickness, rectThickness, h1-(2*rectThickness)); //ONLY center of right side(not corners);
        r2.setBounds(Math.round(s2.getX()), Math.round(s2.getY()), s2.getWidth(), s2.getHeight()); //full square on sprite 2

        if(r1.intersects(r2))
            collisionTest = true;

        return collisionTest;
    }

    //////////////////////////////////////
    //set collision states
    public void setCollisionStateX(int i) {
        collisionStateX = i;
    }

    public void setCollisionStateY(int i) {
        collisionStateY = i;
    }

    public void setCornerCollisionState(int i) {
        cornerCollisionState = i;
    }

    //get collision states
    //State of X axis collision
    public int getCollisionStateX() {
        return collisionStateX;
    }

    //State of Y axis collision
    public int getCollisionStateY() {
        return collisionStateY;
    }

    public int getCornerCollisionState() {
        return cornerCollisionState;
    }

    /////////////////////////////////////
    /////////////////////////////////////
    public void fixPositionX(Sprite s1, Sprite s2) {
        float x1 = s1.getX();
        float y1 = s1.getY();
        float w1 = s1.getWidth();
        float h1 = s1.getHeight();

        float x2 = s2.getX();
        float y2 = s2.getY();
        float w2 = s2.getWidth();
        float h2 = s2.getHeight();

        if(collisionStateX == COLLISION_LEFT) {
            float diffX = (x2+w2) - x1;
            s1.setX(x1+diffX);
        }
        else if(collisionStateX == COLLISION_RIGHT) {
            float diffX = (x1+w1) - x2;
            s1.setX(x1-diffX+1);
        }
    }

    public void fixPositionY(Sprite s1, Sprite s2) {
        float x1 = s1.getX();
        float y1 = s1.getY();
        float w1 = s1.getWidth();
        float h1 = s1.getHeight();

        float x2 = s2.getX();
        float y2 = s2.getY();
        float w2 = s2.getWidth();
        float h2 = s2.getHeight();

        if(collisionStateY == COLLISION_TOP) {
            float diffY = (y2+h2) - y1;
            s1.setY(y1+diffY);
        }
        else if(collisionStateY == COLLISION_BOTTOM) {
            float diffY = (y1+h1) - y2;
            s1.setY(y1-diffY+0.5f);
        }
    }

    //////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //CORNERS COLLISION

    //tests if sprite 1's top left corner hits sprite 2
    public boolean topLeftCornerCollision(Sprite s1, Sprite s2) {
        boolean collisionTest = false;

        int x1 = Math.round(s1.getX());
        int y1 = Math.round(s1.getY());
        int w1 = s1.getWidth();
        int h1 = s1.getHeight(); 

        r1.setBounds(x1, y1, rectThickness, rectThickness); //top left corner
        r2.setBounds(Math.round(s2.getX()), Math.round(s2.getY()), s2.getWidth(), s2.getHeight()); //full square on sprite 2

        if(r1.intersects(r2))
            collisionTest = true;

        return collisionTest;
    }

    //tests if sprite 1's top right corner hits sprite 2
    public boolean topRightCornerCollision(Sprite s1, Sprite s2) {
        boolean collisionTest = false;

        int x1 = Math.round(s1.getX());
        int y1 = Math.round(s1.getY());
        int w1 = s1.getWidth();
        int h1 = s1.getHeight(); 

        r1.setBounds(x1+w1-rectThickness, y1, rectThickness, rectThickness); //top right corner
        r2.setBounds(Math.round(s2.getX()), Math.round(s2.getY()), s2.getWidth(), s2.getHeight()); //full square on sprite 2

        if(r1.intersects(r2))
            collisionTest = true;

        return collisionTest;
    }

    //tests if sprite 1's bottom left corner hits sprite 2
    public boolean bottomLeftCornerCollision(Sprite s1, Sprite s2) {
        boolean collisionTest = false;

        int x1 = Math.round(s1.getX());
        int y1 = Math.round(s1.getY());
        int w1 = s1.getWidth();
        int h1 = s1.getHeight(); 

        r1.setBounds(x1, y1+h1-rectThickness, rectThickness, rectThickness); //bottom left corner
        r2.setBounds(Math.round(s2.getX()), Math.round(s2.getY()), s2.getWidth(), s2.getHeight()); //full square on sprite 2

        if(r1.intersects(r2))
            collisionTest = true;

        return collisionTest;
    }

    //tests if sprite 1's bottom right corner hits sprite 2
    public boolean bottomRightCornerCollision(Sprite s1, Sprite s2) {
        boolean collisionTest = false;

        int x1 = Math.round(s1.getX());
        int y1 = Math.round(s1.getY());
        int w1 = s1.getWidth();
        int h1 = s1.getHeight(); 

        r1.setBounds(x1+w1-rectThickness, y1+h1-rectThickness, rectThickness, rectThickness); //bottom right corner
        r2.setBounds(Math.round(s2.getX()), Math.round(s2.getY()), s2.getWidth(), s2.getHeight()); //full square on sprite 2

        if(r1.intersects(r2))
            collisionTest = true;

        return collisionTest;
    }
}
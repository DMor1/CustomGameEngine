package net.danielmor.engine;

import java.awt.*;
import java.util.*;

public class Sprite 
{
    protected Animation anim; //pointer animation object. anim is animated for the sprite. Assign animation objects to = anim.
    private float x; //x location of sprite
    private float y; //y location of sprite
    private float dx; //x velocity of sprite
    private float dy; //y velocity of sprite

    protected static Game game;
    
    /**Empty Constructor*/
    public Sprite() {
    }

    public Sprite(Game g) {
        game = g;
    }

    /**change animation of sprite*/
    protected void setAnim(Animation anim) {
        this.anim = anim;
    }

    /**update position of sprite based on velocities and update animation image*/
    public void update(long elapsedTime) {
        x += (dx * elapsedTime);
        y += (dy * elapsedTime);
        anim.update(elapsedTime);
    }

    /**set location of sprite using floats*/
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**set location of sprite using integers*/
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**set x location of sprite*/
    public void setX(float x) {
        this.x = x;
    }

    /**set y location of sprite*/
    public void setY(float y) {
        this.y = y;
    }

    /**get x location of sprite*/
    public float getX() {
        return x;
    }

    /**get y location of sprite*/
    public float getY() {
        return y;
    }

    /**set x velocity*/
    public void setVelocityX(float dx) {
        this.dx = dx;
    }

    /**set y velocity*/
    public void setVelocityY(float dy) {
        this.dy = dy;
    }

    /**Get x velocity*/
    public float getVelocityX() {
        return dx;
    }

    /**get y velocity*/
    public float getVelocityY() {
        return dy;
    }

    /**return current animation image*/
    public Image getImage() {
        return anim.getImage();
    }

    /**Return current frame width*/
    public int getWidth() {
        return anim.getImage().getWidth(null);
    }

    /**Return current frame height*/
    public int getHeight() {
        return anim.getImage().getHeight(null);
    }
}
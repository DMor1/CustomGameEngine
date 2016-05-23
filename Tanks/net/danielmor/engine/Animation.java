package net.danielmor.engine;

import java.awt.Image;
import java.util.*;

public class Animation
{
    private ArrayList<AnimFrame> frames; //stores each image in an animation
    private int currFrameIndex; //keeps track of current image
    private long animTime; 
    private long totalDuration; //how long an animation object lasts

    //Constructor initializing variables
    public Animation() {
        frames = new ArrayList<AnimFrame>();
        totalDuration = 0;
        start();
    }

    //Add an image for a specific duration to an animation
    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    public synchronized void start() {
        animTime = 0;
        currFrameIndex = 0;
    }

    //updates the animation frame based on the duration set for each image to appear
    public synchronized void update(long elapsedTime) {
        if(frames.size() > 1) {
            animTime += elapsedTime;

            if(animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currFrameIndex = 0;
            }

            while(animTime > getFrame(currFrameIndex).endTime) 
                currFrameIndex++;
        }
    }

    //returns current image for the animation
    public synchronized Image getImage() {
        if(frames.size() == 0) 
            return null;
        else
            return getFrame(currFrameIndex).image;
    }

    private AnimFrame getFrame(int i) {
        return (AnimFrame)frames.get(i);
    }

    //Stores information for each frame in a class
    private class AnimFrame {
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
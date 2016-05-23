package net.danielmor.engine;

import java.awt.*;
import java.awt.image.*;

/**Initiates main game loop*/
public class GameCore extends Core
{
    /**Initiates Game*/
    protected void execute(int WIDTH, int HEIGHT, int frameRate) {
        initWindow(WIDTH, HEIGHT, frameRate);
        init();
        gameLoop();
    }

    /**
     * Main GameLoop
     */
    protected void gameLoop() {
        this.createBufferStrategy(2); //double buffers game

        //Time system
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        while(true) {
            //elapsedTime is the amount of milliseconds between cycles
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            BufferStrategy strategy = this.getBufferStrategy();
            Graphics g = strategy.getDrawGraphics();
            draw(g); // draw
            strategy.show();
            update(elapsedTime);
            sleep();
        }
    }

    /**sleep thread to create specified framerate*/
    private void sleep() {
        try {
            Thread.sleep(window.getFrameRate());
        }
        catch(Exception ex) {
        }
    }
}
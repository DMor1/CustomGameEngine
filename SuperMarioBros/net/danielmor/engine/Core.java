package net.danielmor.engine;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Core extends JFrame
{
    /**Allows program to retrieve window information*/
    public GameWindow window; 
    
    InputManager systemInput;
    GameAction exit;
    
    /**Executes program*/
    protected void initWindow(int WIDTH, int HEIGHT, int frameRate) {
        window = new GameWindow(WIDTH, HEIGHT, frameRate);
        this.setSize(window.windowSize.getWidth(), window.windowSize.getHeight());
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.setVisible(true);
    }

    /**Initiate Core Elements*/
    protected void init() {
        createSystemInput();
    }

    /**Update Core Elements*/
    protected void update(long elapsedTime) {
        checkSystemInput(elapsedTime);
    }

    /**Draw Core Elements (Window Borders)*/
    protected void draw(Graphics g) {
        //Draw a black border for the jframe       
        int WIDTH = window.windowSize.getWidth();
        int HEIGHT = window.windowSize.getHeight();
        
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, WIDTH, 0); // Top line
        g.drawLine(0, 0, 0, HEIGHT); //Left Line
        g.drawLine(0, HEIGHT-1, WIDTH, HEIGHT-1); // Bottom Line
        g.drawLine(WIDTH-1, 0, WIDTH-1, HEIGHT); // Right Line
    }

    private void createSystemInput() {
        exit = new GameAction("Exit Game", GameAction.DETECT_INITIAL_PRESS_ONLY);
        
        systemInput = new InputManager(this);
        
        systemInput.mapToKey(exit, KeyEvent.VK_ESCAPE);
    }
    
    private void checkSystemInput(long elapsedTime) {
        if(exit.isPressed())
            System.exit(0);
    }
}
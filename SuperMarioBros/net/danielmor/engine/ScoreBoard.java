package net.danielmor.engine;

import java.awt.*;
import javax.swing.*;
import java.text.*;

public class ScoreBoard
{
    public Game game;

    DecimalFormat scoreFormat;
    DecimalFormat coinFormat;

    private String character;
    private String world;
    private int score;
    private int coins;
    private int time;

    Font scoreFont;
    int fontSize = 28;

    Image coinImage;
    public ScoreBoard(String character, String world, Game g) {
        game = g;
        this.character = character;
        score = 0;
        coins = 0;
        this.world = world;
        time = 200;

        scoreFont = new Font("Times New Roman", Font.BOLD, fontSize);
        coinImage = new ImageIcon("Content//Images//Sprites//PowerUp//Coin//Coin1.png").getImage();

        scoreFormat = new DecimalFormat("000000");
        coinFormat = new DecimalFormat("00");
    }

    int timer = 0;
    public void update(long elapsedTime) {
        timer += elapsedTime;
        if(timer >= 1000) {
            if(time > 0) {
                if(time == 100) {
                    game.outOfTime.play();
                    game.mainThemeAudio.stop();
                }
                else if(time == 95) {
                    game.runningOuttaTime.loop();
                }
                time -= 1;
                timer = 0;
            }
            else if(time <= 0) {
                game.userSprites.get(0).die();
            }
        }
    }

    public void draw(Graphics g) {  
        int yOffSet = 38; //distance from y = 0;

        g.setFont(scoreFont);
        g.setColor(Color.WHITE);

        //Draw Character Name
        g.drawString(character, 85, yOffSet);

        //Draw score
        g.drawString(scoreFormat.format(score), 85, yOffSet+fontSize);

        //Draw Coins Marker
        g.drawString("x " + coinFormat.format(coins), 290, yOffSet+fontSize);
        //Draw image of coin
        g.drawImage(coinImage, 260, yOffSet+fontSize-28, null);

        //Draw world LOGO
        g.drawString("WORLD", 450, yOffSet);
        //Draw literal World
        g.drawString(world, 480, yOffSet+fontSize);

        //Draw Time logo
        g.drawString("TIME", 650, yOffSet);
        //Draw Time
        if(time <= 100) 
            g.setColor(Color.RED.brighter());
        g.drawString(Integer.toString(time), 650, yOffSet+fontSize);
    }

    public void addScorePoints(int points) {
        score += points;
    }

    public void coinCollected() {
        coins++;
    }
}
import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.applet.*;

public class Coin extends PowerUp
{
    Animation coin;

    public Coin() {
    }

    public Coin(boolean stored) {
        this.stored = stored;
        setState(CREATING_STATE);
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);

        if(getState() == HIT_STATE && stored == false) {
            game.scoreBoard.coinCollected();
            game.scoreBoard.addScorePoints(200);
            game.coinAudio.play();
            destroy();
        }
    }

    protected void addScore() {
        game.scoreBoard.coinCollected();
        game.scoreBoard.addScorePoints(200);
        game.coinAudio.play();
    }

    protected void created() {
        destroy();
    }

    public void createFrames() {
        Image coin1 = new ImageIcon("Content//Images//Sprites//PowerUp//Coin//Coin1.png").getImage();
        Image coin2 = new ImageIcon("Content//Images//Sprites//PowerUp//Coin//Coin2.png").getImage();
        Image coin3 = new ImageIcon("Content//Images//Sprites//PowerUp//Coin//Coin3.png").getImage();
        Image coin4 = new ImageIcon("Content//Images//Sprites//PowerUp//Coin//Coin4.png").getImage();

        coin = new Animation();
        coin.addFrame(coin1, 250);
        coin.addFrame(coin2, 20);
        coin.addFrame(coin3, 50);
        coin.addFrame(coin4, 75);
        setAnim(coin);
    }
}
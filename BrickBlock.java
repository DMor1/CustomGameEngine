import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class BrickBlock extends Block
{
    Animation brick; 

    boolean coinLoaded = false;
    boolean coinsCollected = false;
    int numOfCoins = 9;

    public BrickBlock() {
        enableBounceEffect();
    }

    public BrickBlock(boolean coinLoaded) {
        enableBounceEffect();
        this.coinLoaded = coinLoaded;
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    int counter = 0;
    protected void processCollection() {
        if(coinLoaded == true) 
        {
            if(coinsCollected == false) 
            {
                Coin c = new Coin(true);
                c.setLocation(getX(), getY());
                addSprite(c);
                
                counter++;

                //if all coins collected
                if(counter >= numOfCoins) 
                    coinsCollected = true;
            }
        }
        setState(NORMAL_STATE);
    }

    public void createFrames() {
        Image brick1 = new ImageIcon("Content//Images//Sprites//Blocks//Brick Block.png").getImage();

        brick = new Animation();
        brick.addFrame(brick1, 500);
        setAnim(brick);
    }
}
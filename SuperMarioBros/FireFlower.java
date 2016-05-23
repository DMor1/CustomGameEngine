import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class FireFlower extends PowerUp
{
    Animation fireFlower;

    public FireFlower() {
    }

    public FireFlower(boolean stored) {
        this.stored = stored;
        setState(CREATING_STATE);
        collisionEnabled = true;
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    public void playerCollided() {
        if(getState() != DESTROYED_STATE) {
            game.scoreBoard.addScorePoints(1000);
            destroy();
        }
    }

    protected void created() {
        setState(NORMAL_STATE);
    }

    public void createFrames() {
        Image fireFlower1 = new ImageIcon("Content//Images//Sprites//PowerUp//Fire Flower//Fire Flower1.png").getImage();
        Image fireFlower2 = new ImageIcon("Content//Images//Sprites//PowerUp//Fire Flower//Fire Flower2.png").getImage();
        Image fireFlower3 = new ImageIcon("Content//Images//Sprites//PowerUp//Fire Flower//Fire Flower3.png").getImage();
        Image fireFlower4 = new ImageIcon("Content//Images//Sprites//PowerUp//Fire Flower//Fire Flower4.png").getImage();
        Image fireFlower5 = new ImageIcon("Content//Images//Sprites//PowerUp//Fire Flower//Fire Flower5.png").getImage();
        Image fireFlower6 = new ImageIcon("Content//Images//Sprites//PowerUp//Fire Flower//Fire Flower6.png").getImage();
        Image fireFlower7 = new ImageIcon("Content//Images//Sprites//PowerUp//Fire Flower//Fire Flower7.png").getImage();
        Image fireFlower8 = new ImageIcon("Content//Images//Sprites//PowerUp//Fire Flower//Fire Flower8.png").getImage();

        fireFlower = new Animation();
        fireFlower.addFrame(fireFlower1, 60);
        fireFlower.addFrame(fireFlower2, 60);
        fireFlower.addFrame(fireFlower3, 60);
        fireFlower.addFrame(fireFlower4, 60);
        fireFlower.addFrame(fireFlower5, 60);
        fireFlower.addFrame(fireFlower6, 60);
        fireFlower.addFrame(fireFlower7, 60);
        fireFlower.addFrame(fireFlower8, 60);
        setAnim(fireFlower);
    }
}
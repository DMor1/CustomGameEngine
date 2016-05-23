import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class MysteryBox extends Block
{
    Animation mysteryBox;
    PowerUp storedPowerUp;

    public MysteryBox() {
        enableBounceEffect();
        storedPowerUp = new Coin(true);
    }

    public MysteryBox(PowerUp p) {
        enableBounceEffect();
        storedPowerUp = p;
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    protected void hitEffect() {
        storedPowerUp.setLocation(getX(), getY());
        addSprite(storedPowerUp);
    }
    
    //Called when Collecting_state is active
    protected void processCollection() {        
        SolidBlock b = new SolidBlock();
        b.setLocation(getX(), getY());
        addSprite(b);

        destroy();
    }

    public void createFrames() {
        Image box1 = new ImageIcon("Content//Images//Sprites//Blocks//Mystery Box//Mystery1.png").getImage();
        Image box2 = new ImageIcon("Content//Images//Sprites//Blocks//Mystery Box//Mystery2.png").getImage();
        Image box3 = new ImageIcon("Content//Images//Sprites//Blocks//Mystery Box//Mystery3.png").getImage();

        mysteryBox = new Animation();
        mysteryBox.addFrame(box1, 500);
        mysteryBox.addFrame(box2, 150);
        mysteryBox.addFrame(box3, 150);
        setAnim(mysteryBox);
    }
}
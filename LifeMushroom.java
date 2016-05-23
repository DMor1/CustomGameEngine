import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class LifeMushroom extends PowerUp
{
    Animation mushroom;
    
    public LifeMushroom() {
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    public void createFrames() {
        Image mushroom1 = new ImageIcon("Content//Images//Sprites//PowerUp//Mushroom//1UP Mushroom.png").getImage();

        mushroom = new Animation();
        mushroom.addFrame(mushroom1, 500);    
        setAnim(mushroom);
    }
}
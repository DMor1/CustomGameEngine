import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class MagicMushroom extends PowerUp
{
    Animation mushroom;

    public MagicMushroom() {
    }

    public MagicMushroom(boolean stored) {
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
        setVelocityX(0.1f);
        setState(NORMAL_STATE);
    }

    //If hits left or right wall
    protected void collisionEffect() {
        setVelocityX(-getVelocityX()); //Reverse direction
    }

    public void createFrames() {
        Image mushroom1 = new ImageIcon("Content//Images//Sprites//PowerUp//Mushroom//Magic Mushroom.png").getImage();

        mushroom = new Animation();
        mushroom.addFrame(mushroom1, 500);    
        setAnim(mushroom);
    }
}
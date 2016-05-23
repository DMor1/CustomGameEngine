import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class BigSolidBlock extends Block
{
    Animation block;

    public BigSolidBlock() {
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    public void createFrames() {
        Image block1 = new ImageIcon("Content//Images//Sprites//Blocks//Big Solid Block.png").getImage();

        block = new Animation();
        block.addFrame(block1, 500);
        setAnim(block);
    }
}
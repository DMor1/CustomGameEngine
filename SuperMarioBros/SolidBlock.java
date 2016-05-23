import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class SolidBlock extends Block
{
    Animation block;

    public SolidBlock() {
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    public void createFrames() {
        Image block1 = new ImageIcon("Content//Images//Sprites//Blocks//Solid Block.png").getImage();

        block = new Animation();
        block.addFrame(block1, 500);
        setAnim(block);
    }
}
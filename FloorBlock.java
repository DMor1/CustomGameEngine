import net.danielmor.engine.*;

import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class FloorBlock extends Block
{
    Animation floorBlock;
    
    public FloorBlock() {
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    public void createFrames() {
        Image block1 = new ImageIcon("Content//Images//Sprites//Blocks//Floor Block.png").getImage();

        floorBlock = new Animation();
        floorBlock.addFrame(block1, 500);
        setAnim(floorBlock);
    }
}
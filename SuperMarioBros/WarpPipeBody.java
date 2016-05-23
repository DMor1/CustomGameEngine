import net.danielmor.engine.*;

import java.awt.*;
import javax.swing.*;

public class WarpPipeBody extends Block
{
    Animation warpPipe;

    public WarpPipeBody() {
    }

    public void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    public void createFrames() {
        Image warpPipeIMG = new ImageIcon("Content//Images//Sprites//Blocks//Warp Pipe//WarpPipe Body.png").getImage();

        warpPipe = new Animation();
        warpPipe.addFrame(warpPipeIMG, 500);
        setAnim(warpPipe);
    }
}
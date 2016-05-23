import net.danielmor.engine.*;
import java.awt.*;

public class StartScreen extends GameScreen
{
    public StartScreen(GameWindow window, Game game) {
        super(window, game);
    }
    
    public void init() {
    }
    
    public void update(long elapsedTime) {
    }
    
    public void draw() {
        Graphics g = getScreenGraphics();
        int width = window.windowSize.getWidth();
        int height = window.windowSize.getHeight();
        
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);
    }
}
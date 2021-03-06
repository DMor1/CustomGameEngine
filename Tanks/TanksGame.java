import net.danielmor.engine.Game;
import net.danielmor.engine.Sprite;
import net.danielmor.engine.GameScreen;
import net.danielmor.engine.Util;

import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.event.*;

public class TanksGame extends Game {
    static int width = 800;
    static int height = 475;
    public MainGameScreen mainGameScreen;

    MapGenerator generator;

    public Tank player1;
    public Tank player2;

    public static TanksGame g;

    public static void main(String[] args) {
        g = new TanksGame();
        g.execute(width, height, 100);
    }

    protected void init() {
        super.init();

        loadCursors();
        this.addMouseListener(new MouseList());
        this.addMouseMotionListener(new MouseList());

        new Sprite(g); //Pass Game object to sprite class

        //Create Main Screen
        mainGameScreen = new MainGameScreen(window, this); //Create one game screen
        screens.add(mainGameScreen);  // Add screen to screen list
        generator = mainGameScreen.generator;
        Tank.setMapGenerator(generator);

        //Set Screen
        GameScreen.setScreenPointer(0); //set screen pointer to main game screen

        setGameState(NORMAL_STATE); //Set state of game to normal, allowing animation to start

        loadGameSprites();

        mainGameScreen.setPlayers(player1, player2);
    }

    protected void update(long elapsedTime) {
        super.update(elapsedTime);
    }

    protected void draw(Graphics g) {
        super.draw(g);
    }

    private void loadGameSprites() {
        player1 = new Tank(Color.RED, "R"); //Create ball and add to sprite list
        player1.setLocation(100, 40);
        userSprites.add(player1);

        player2 = new Tank(Color.YELLOW, "L");
        player2.setLocation(600, 40);
        userSprites.add(player2);

        for(int i = 0; i < 4; i++)
            sprites.add(new Cloud());
    }

    Cursor crosshair;
    Cursor crosshairShot;
    private void loadCursors() {
        Image p1 = new ImageIcon("Content//Images//Crosshair.png").getImage();
        Image p2 = new ImageIcon("Content//Images//CrosshairShot.png").getImage();
        Toolkit toolKit = Toolkit.getDefaultToolkit();
        Point hotSpot = new Point(15, 15);

        crosshair = toolKit.createCustomCursor(p1, hotSpot, "Crosshair");
        crosshairShot = toolKit.createCustomCursor(p2, hotSpot, "CrosshairShot");
    }

    private class MouseList implements MouseListener, MouseMotionListener
    {
        boolean shot = false;

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            setCursor(crosshair);
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            if(!shot) {
                shot = true;
                setCursor(crosshairShot);

                try {
                    Thread.sleep(150);
                }
                catch(Exception ex) {
                }
                setCursor(crosshair);
                shot = false;
            }
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) { 
        }
    }
}

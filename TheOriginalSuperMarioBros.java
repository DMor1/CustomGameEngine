import net.danielmor.engine.Game;
import net.danielmor.engine.GameScreen;
import net.danielmor.engine.Sprite;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;

public class TheOriginalSuperMarioBros extends Game
{
    public StartScreen startScreen;
    public MainGameScreen mainGameScreen;

    public static void Main(String[] args) {
        TheOriginalSuperMarioBros game = new TheOriginalSuperMarioBros();
        game.execute(825,432,100);
    }

    protected void init() {
        super.init();

        //Change game state to loading
        setGameState(LOADING_STATE);

        //Load Starting Screen
        startScreen = new StartScreen(window, this);
        screens.add(startScreen);

        //Load Game screen
        mainGameScreen = new MainGameScreen(window, this);
        screens.add(mainGameScreen);

        //Set screen
        GameScreen.setScreenPointer(1);

        //Load maps and draw to buffer
        loadMaps("Content//Maps", new SpriteKey());    

        new Sprite(this);

        ///////////////////////////////////////////////
        //Enable grid for 32x32 size
        //mainGameScreen.enableCollisionBoxes();
        //setGridSize(32);
        //enableGrid();

        //Change game state to normal as gameloop starts after initiating
        setGameState(NORMAL_STATE);
    }

    protected void update(long elapsedTime) {
        super.update(elapsedTime);

        if(getGameState() == Game.NORMAL_STATE) {
            //Update scoreboard
            scoreBoard.update(elapsedTime);
        }
    }

    protected void draw(Graphics g) {
        super.draw(g);

        //Draw Scoreboard
        scoreBoard.draw(g);
    }
}
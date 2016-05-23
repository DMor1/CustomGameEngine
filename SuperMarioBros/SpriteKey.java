import net.danielmor.engine.MapKey;
import net.danielmor.engine.Entity;
import net.danielmor.engine.Player;
import net.danielmor.engine.Enemy;

public class SpriteKey extends MapKey
{
    protected Entity selectSprite(char c) {
        switch(c) {            
            //Enemies
            case 'G':
                return new Goomba(Enemy.RIGHT);
            case 'g': 
                return new Goomba(Enemy.LEFT);
            case 'K': 
                return new KoopaTroopa(Enemy.RIGHT);
            case 'k':
                return new KoopaTroopa(Enemy.LEFT);

            //PowerUps
            case '!':
                return new Coin();
            case '%':
                return new MysteryBox(new FireFlower(true));
            case '*':
                return new MysteryBox(new Star(true));
            case '&':
                return new MysteryBox(new MagicMushroom(true));
            case '^':
                return new LifeMushroom();

            //Blocks
            case 'b':
                return new SolidBlock();
            case 'B': 
                return new BigSolidBlock(); 
            case 'r':
                return new BrickBlock();
            case 'R':
                return new BrickBlock(true);
            case '?':
                return new MysteryBox();
            case 'x':
                return new FloorBlock();
            case 'v':
                return new WarpPipeTop();
            case 'z':
                return new WarpPipeBody();

            //Empty Space
            default:
                return null;
        }
    }

    protected Player selectPlayerSprite(char c) {
        switch(c) {
            case 'M':
                return new Mario();
            default:
                return null;
        }
    }
}
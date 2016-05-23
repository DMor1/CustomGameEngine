import net.danielmor.engine.Util;

import java.awt.*;

public class MapGenerator 
{
    public MapLine[] map;

    int mapWidth;
    int mapHeight;

    int minHeight;
    int maxHeight;

    enum SlopeDirection {
        DOWN, UP
    } //direction for slope/hill

    public MapGenerator(int mapWidth, int mapHeight, Range mapHeightRange) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.minHeight = mapHeightRange.getMin();
        this.maxHeight = mapHeightRange.getMax();

        map = generateMap();
    }

    public MapLine[] generateMap() {
        MapLine[] map = new MapLine[mapWidth];

        boolean firstEntrance = true; //program hasn't entered yet
        SlopeDirection direction = null; //enum holder

        boolean sloping = false; //In process of creating a slope or hill
        int slopeStart = 0;
        int slopeWidth = 0; //total size of slope or hill
        
        int minSlopeWidth = 5;
        int maxSlopeWidth = 25;

        int startHeight = Util.getRandInt(minHeight, maxHeight);
        int lineHeight = 0;

        for(int i = 0; i < mapWidth; i++) {
            if(firstEntrance == true) {
                lineHeight = startHeight;
                firstEntrance = false;
            }
            else if (sloping == true){
                //If the start of the slope hasn't reached the end of the slope, continue
                if(i < slopeStart+slopeWidth) {
                    if(direction == SlopeDirection.UP) {
                        //If sloping upward
                        //Calculate Chance of slopeincrease
                        int chance = 650;
                        int outOf = 1000;
                        int num = Util.getRandInt(0, outOf);

                        if(num <= chance && lineHeight < maxHeight)
                            lineHeight += 2;
                    }
                    else if(direction == SlopeDirection.DOWN) {
                        //If sloping downward
                        //calculate chance to slope down
                        int chance = 430;
                        int outOf = 1000;
                        int num = Util.getRandInt(0, outOf);

                        if(num <= chance && lineHeight > minHeight)
                            lineHeight -= 2;                        
                    }
                    else 
                        throw new NullPointerException("MapGenerator Class - generateMap() Method - null enum type, not up or down direction");
                }
                else
                    sloping = false;
            }
            else {
                boolean decision = Util.getRandBoolean();

                if(decision == true) {
                    sloping = true;
                    direction = SlopeDirection.UP;
                    slopeStart = i;
                    //slopeWidth = Util.getRandInt(minSlopeWidth, maxSlopeWidth);
                    slopeWidth = maxSlopeWidth;
                }
                else if(decision == false) {
                    sloping = true;
                    direction = SlopeDirection.DOWN;
                    slopeStart = i;
                    //slopeWidth = Util.getRandInt(minSlopeWidth, maxSlopeWidth);
                    slopeWidth = maxSlopeWidth;
                }
            }

            map[i] = new MapLine(i, mapHeight, i, mapHeight-lineHeight);
        }

        return map;
    }

    public void drawMap(Graphics g) {
        for(int i = 0; i < mapWidth; i++) {
            MapLine line = map[i];

            g.setColor(Color.GREEN);
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
            
            //Draw green top
            int size = 7;
            g.setColor(Color.GREEN.darker());
            g.drawLine(line.x2, line.y2+size, line.x2, line.y2);
        }
    }
    
    public MapLine[] getMap() {
        return map;
    }

    public class MapLine 
    {
        public int x1; //floor
        public int y1; //floor

        public int x2; //elevation
        public int y2; //elevation
        
        public int floor; //y

        public MapLine(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            
            floor = y2;
        }   
    }
}

class Range
{
    private int min;
    private int max;

    public Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
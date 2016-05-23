package net.danielmor.engine;

import java.io.*;

/**Map class holds 2 dimensional String array (The map data) as well as its file location*/
public class Map 
{
    private File mapFile;
    private char[][] map;
    private int rowSize;
    private int colSize;

    public Map(File mapFile, char[][] map) {
        this.mapFile = mapFile;
        this.map = map;

        rowSize = map.length; //Vertical - num of lines

        for(int i = 0; i < rowSize; i++) {
            if(map[i].length > colSize)
                colSize = map[i].length;
        }
    }

    public File getMapFile() {
        return mapFile;
    }

    public char[][] getMap() {
        return map;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }
}
package net.danielmor.engine;

import java.io.*;

public class MapReader
{
    File[] mapFiles;
    Map[] maps;

    public MapReader(String mapDirectory) {
        mapFiles = new File(mapDirectory).listFiles();
        maps = new Map[mapFiles.length];
        createMaps();
    }

    private void createMaps() {
        for(int i = 0; i < mapFiles.length; i++) {
            File file = mapFiles[i];
            char[][] map = getScannedMap(file);

            maps[i] = new Map(file, map);
        }
    }

    private char[][] getScannedMap(File mapFile) {      
        int row = 0;
        int colSize = 0;
        //Retrieves numOfLines and largest column size
        try {
            FileReader fr = new FileReader(mapFile);
            BufferedReader buffer = new BufferedReader(fr);

            String line = buffer.readLine();

            while(line != null) {
                if(line.length() > colSize)
                    colSize = line.length();

                row++;
                line = buffer.readLine();
            }

            buffer.close();
            fr.close();
        }
        catch(Exception ex) {
            System.err.println("Error - MapReader Class: Sizes");
        }

        //Stores data in array
        char[][] map = new char[row][colSize];
        try {
            FileReader fr = new FileReader(mapFile);
            BufferedReader buffer = new BufferedReader(fr);

            String line = buffer.readLine();
            row = 0;

            while(line != null) {
                for(int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    map[row][col] = c;
                }
                row++;
                line = buffer.readLine();
            }

            buffer.close();
            fr.close();
        }
        catch(Exception ex) {
            System.err.println("Error - MapReader Class: Sizes");
        }
        return map;
    }

    public Map[] getMaps() {
        return maps;
    }

    public int getNumOfMaps() {
        return maps.length;
    }
}
package net.danielmor.engine;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Util
{
    /**returns random integer in range [min, max]*/
    public static int getRandInt(int min, int max) {
        return (int)(Math.random() * (max-min+1) + min);
    }

    /**returns random double in range [min, max] with the number of decimal places*/
    public static double getRandDouble(double min, double max, int decPlaces) {
        double randFloat = (double)(Math.random()*(max-min) + min);        
        double precision = Math.pow(10, decPlaces);
        double finalDouble = Math.floor(randFloat * precision + .5)/precision;
        return finalDouble;
    }

    public static int[] getDimensions(File f) {
        int[] dimensions = new int[2]; // [0] is num of lines, [1] is biggest column
        try {
            FileReader file_to_read = new FileReader(f);
            BufferedReader fileBuffer = new BufferedReader(file_to_read);

            int lineNum = 0;
            String line = "";
            int greatestColSize = 0;

            do {
                line = fileBuffer.readLine();

                if(line != null) {
                    lineNum++;
                    int length = line.length();
                    if(length > greatestColSize)
                        greatestColSize = length;
                }
            }  
            while(line != null); 

            fileBuffer.close();
            file_to_read.close();
            
            dimensions[0] = lineNum;
            dimensions[1] = greatestColSize;
        }
        catch(Exception ex) {
            System.err.println("Error Scanning Map - Getting number of lines in file (Util Class)");
        }
        return dimensions;
    }

    //NOT WORKING -- DO NOT USE
    //Attempting to rotate image, but is still flawed.
    public static Image rotate(Image img, int degrees) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);

        AffineTransform afn = new AffineTransform();
        afn.rotate(degrees * Math.PI / 180.0, w, h);

        BufferedImage rotated = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)rotated.getGraphics();

        g2.drawImage(img, afn, null);

        return rotated;
    }
}

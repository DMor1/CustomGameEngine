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

    /**returns random double in range [min, max] with the number of decimal places*/
    public static float getRandFloat(float min, float max, int decPlaces) {
        float randFloat = (float)(Math.random()*(max-min) + min);        
        float precision = (float)Math.pow((float)10, decPlaces);
        float finalDouble = (float)Math.floor(randFloat * precision + .5)/(float)precision;
        return finalDouble;
    }

    public static boolean getRandBoolean() {
        int bool = getRandInt(0, 1);

        if(bool == 0)
            return false;
        else if(bool == 1)
            return true;
        else
            throw new NullPointerException("Util Class - getRandBoolean Method - Improper boolean generated (random int isn't ranged properly");
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

    public static BufferedImage verticalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());

        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();

        return dimg;
    }

    /**
     * This method flips the image horizontally
     * @param img --> BufferedImage Object to be flipped horizontally
     * @return
     */
    public static BufferedImage horizontalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        /*
         * img - the specified image to be drawn. This method does nothing if
         * img is null. dx1 - the x coordinate of the first corner of the
         * destination rectangle. dy1 - the y coordinate of the first corner of
         * the destination rectangle. dx2 - the x coordinate of the second
         * corner of the destination rectangle. dy2 - the y coordinate of the
         * second corner of the destination rectangle. sx1 - the x coordinate of
         * the first corner of the source rectangle. sy1 - the y coordinate of
         * the first corner of the source rectangle. sx2 - the x coordinate of
         * the second corner of the source rectangle. sy2 - the y coordinate of
         * the second corner of the source rectangle. observer - object to be
         * notified as more of the image is scaled and converted.
         *
         */
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }

}

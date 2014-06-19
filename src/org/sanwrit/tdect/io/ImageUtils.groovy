/**
 * ImageUtils.groovy
 *
 * Version 1.0
 *
 * June 19, 2014
 *
 * Copyright Sanwrit Software Laboratories, Inc. 2014
 */
package org.sanwrit.tdect.io

import javax.imageio.ImageIO
import java.awt.Color
import java.awt.image.BufferedImage
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Mostly IO utilities for image files.
 *
 */
class ImageUtils {

    private static final String EXTENSION_PATTERN = "([\\s\\S]+(\\.(?i)" +
            "(jpg|png|gif|bmp))\$)"

    /**
     * Put the image in the memory.
     * @param pathToFile
     * @return BufferedImage
     */
    static def readImage(String pathToFile) {
        File file = null
        //check if extension is supported
        if (validateExtension(pathToFile)) {
            file = new File(pathToFile)
        }

        return (pathToFile != null && !file.isDirectory()) ? ImageIO.read(file)
                : null
    }

    /**
     * Put the image in the storage.
     * @param image
     * @param format
     * @param file
     * @return true if image was written
     */
    static def writeImage(def image, String format, File file) {
        ImageIO.write(image, format, file)
    }

    /**
     * Checks if the file has the correct extension of an image.
     * @param pathToFile
     * @return true if it matches, false otherwise
     */
    static def validateExtension(String pathToFile) {
        Pattern pattern = Pattern.compile(EXTENSION_PATTERN)
        Matcher matcher = pattern.matcher(pathToFile)
        return matcher.matches()
    }

    /**
     * Calculates the average of the red, green, and blue colors of a
     * picture element.
     *
     * @param image
     * @return BufferedImage
     */
    static def grayScaleAverage(def image) {
        def rgbBuff = image.getRGB(0, 0, image.getWidth(),
                image.getHeight(), null, 0, image.getWidth())
        def tempBuff = new int[image.getHeight() * image.getWidth()]

        rgbBuff.eachWithIndex { it, i ->
            def pixel = new Color(it.next())
            def alpha = pixel.getAlpha()
            def red = pixel.getRed()
            def green = pixel.getGreen()
            def blue = pixel.getBlue()
            def average = ((red + green + blue) / 3) as int //the average for the pixel
            def color = new Color(average, average, average, alpha)
            tempBuff[i] = color.getRGB()
        }
        return toImage(tempBuff, image.getWidth(), image.getHeight())
    }

    private def static int2byte(int[] src) {
        byte[] dst = new byte[src.length * 4]
        int j = 0
        for (i in 0..src.length - 1) {
            int a = src[i]
            dst[j++] = (byte) ((a >> 24) & 0xFF)
            dst[j++] = (byte) ((a >> 16) & 0xFF)
            dst[j++] = (byte) ((a >> 8) & 0xFF)
            dst[j++] = (byte) (a & 0xFF)
        }
        return dst
    }

    /**
     * Converts a byte array to BufferedImage
     * @param data
     * @return
     */
    static def toImage(int[] data, int width, int height) {
        def newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB)
        newImage.setRGB(0, 0, width, height, data, 0, width)
        return newImage
    }


}



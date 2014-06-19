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
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Mostly IO utilities for image files.
 *
 */
class ImageUtils {

    private static final String EXTENSION_PATTERN = "([^\\s]+(\\.(?i)" +
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
        def tempBuff = []

        rgbBuff.each { i ->
            Color pixel = new Color(it[i])
            def red = pixel.getRed()
            def green = pixel.getGreen()
            def blue = pixel.getBlue()
            tempBuff[i] = (red + green + blue) / 3 //the average for the pixel
        }
        return toImage(int2byte(tempBuff))
    }

    private def static int2byte(int[]src) {
        int srcLength = src.length
        byte[]dst = new byte[srcLength << 2]

        for (int i=0; i<srcLength; i++) {
            int x = src[i]
            int j = i << 2
            dst[j++] = (byte) ((x >>> 0) & 0xff)
            dst[j++] = (byte) ((x >>> 8) & 0xff)
            dst[j++] = (byte) ((x >>> 16) & 0xff)
            dst[j++] = (byte) ((x >>> 24) & 0xff)
        }
        return dst
    }

    /**
     * Converts a byte array to BufferedImage
     * @param data
     * @return
     */
   static def toImage(byte[] data) {
        BufferedImage bi = null
        ByteArrayInputStream bais = new ByteArrayInputStream(data)
        bi = ImageIO.read(bais)
        return bi
   }


}



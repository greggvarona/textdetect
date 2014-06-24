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

    /**
     * Limit {@code i} from 0 to 255.
     * @param i the integer to clamp (e.g. one R, G, and B values).
     * @return an integer whose value is between 0 and 255, inclusively.
     */
    static def clampRGBValue(int i) {
        int retVal = i
        if (i < 0) {
            retVal = 0
        } else if (i > 255) {
            retVal = 255
        }
        return retVal
    }


}



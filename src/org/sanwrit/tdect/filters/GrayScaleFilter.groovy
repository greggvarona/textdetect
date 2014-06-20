/**
 * Created by greggvarona on 6/20/14.
 */

package org.sanwrit.tdect.filters

import java.awt.Color

/**
 * Applies gray scaling algorithms to integer pixels.
 */
class GrayScaleFilter {

    int[] img

    /**
     * Loads raw int pixel data to the filter.
     * @param img
     * @param width
     * @param height
     */
    GrayScaleFilter(int[] img) {
        this.img = img
    }


    /**
     * <p>Calculates the average of the red, green, and blue colors of a
     * picture element within the bounds of {@code width} and {@code height}.
     * </p>
     *
     * <p>The original raw data is not changed.</p>
     *
     * @param image
     * @return int array
     */
    def average() {
        def tempBuff = new int[this.img.length]

        this.img.eachWithIndex { it, i ->
            def pixel = new Color(it.next())
            def alpha = pixel.getAlpha()
            def red = pixel.getRed()
            def green = pixel.getGreen()
            def blue = pixel.getBlue()

            //the average for the pixel
            def average = ((red + green + blue) / 3) as int

            def color = new Color(average, average, average, alpha)
            tempBuff[i] = color.getRGB()
        }

        return tempBuff
    }

}

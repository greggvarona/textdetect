/**
 * Created by greggvarona on 6/20/14.
 */

package org.sanwrit.tdect.filters

import java.awt.Color

/**
 * Applies gray scaling algorithms to integer pixels.
 */
class GrayScaleFilter extends Filter {

    /**
     * Loads raw int pixel data to the filter.
     * @param img
     * @param width
     * @param height
     */
    GrayScaleFilter(int[] img) {
        super(img)
    }


    /**
     * <p>Calculates the average of the red, green, and blue colors of a
     * picture element within the bounds of {@code width} and {@code height}.
     * </p>
     *
     * <p>The {@code img} will not be changed after this operation.</p>
     *
     * @param image
     * @return int[] output
     */
    def average() {
        int[] output = new int[this.img.length]
        for (i in 0..this.img.length - 1) {
            def pixel = new Color(this.img[i])
            def alpha = pixel.getAlpha()
            def red = pixel.getRed()
            def green = pixel.getGreen()
            def blue = pixel.getBlue()

            //the average for the pixel
            def average = ((red + green + blue) / 3) as int

            output[i] = new Color(average, average, average, alpha)
                    .getRGB()
        }
    }

    def imageIsGrayScale() {
        def flag = true
        for (intPixel in this.img) {
            def pixel = new Color(intPixel)
            def red = pixel.getRed()
            def green = pixel.getGreen()
            def blue = pixel.getBlue()

            if (red != green || red != blue) {
                flag = false
                break
            }
        }
        return flag
    }

}

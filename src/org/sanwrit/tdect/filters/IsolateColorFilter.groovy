/**
 * Created by greggvarona on 6/20/14.
 */
package org.sanwrit.tdect.filters

import java.awt.Color

/**
 * <p>Simple filter for isolating a particular color.</p>
 *
 * <p>This can be helpful in removing noise after applying edge sharpening
 * filters.</p>
 *
 */
class IsolateColorFilter extends Filter {

    /**
     * Default color to replace the other colors.
     */
    static final int DEFAULT_BACKGROUND = 0xFFFFFF

    /**
     * Default color to isolate
     */
    static final int DEFAULT_COLOR = 0x0

    /**
     * Default threshold amount (used together with gray scaling).
     */
    static final int DEFAULT_THRESHOLD = 0

    /**
     * Loads raw data to img
     * @param img
     */
    IsolateColorFilter(int[] img) {
        super(img)
    }

    /**
     * <p>Changes all other colors to background if the color is not equal to
     * the specified color.</p>
     *
     * @param background
     * @param color
     * @param threshold - mostly used for gray scaled images. Adjust this to
     * a suitable amount if you need thresholding.
     * @return
     */
    def apply(def background = DEFAULT_BACKGROUND, def color = DEFAULT_COLOR,
              def threshold = DEFAULT_THRESHOLD) {

        int[] tempBuff = new int[this.getImg().length]
        Color b = new Color(background)
        Color c = new Color(color)

        img.eachWithIndex { it, i ->
            Color pixel = new Color(it.next())
            tempBuff[i] = pixel.getRGB()

            // These calculations are used for comparing with the threshold
            // value.
            def pixelAvg = (pixel.getRed() + pixel.getBlue()
                    + pixel.getGreen()) / 3
            def cAvg = (c.getRed() + c.getBlue() + c.getRed()) / 3

            // Not the color that we need.
            if (Math.abs(pixelAvg - cAvg) >= threshold) {
                tempBuff[i] = b.getRGB()
            }

        }

        return tempBuff
    }


}

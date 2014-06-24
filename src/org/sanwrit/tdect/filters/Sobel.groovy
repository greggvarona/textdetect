/**
 * Created by greggvarona on 6/24/14.
 */
package org.sanwrit.tdect.filters

import org.sanwrit.tdect.filters.util.Convolution
import org.sanwrit.tdect.io.ImageUtils

/**
 * Sobel operator
 */
class Sobel extends Filter {

    int width

    int height

    /**
     * Sobel operator kernel for horizontal derivative approximations
     */
    static final int[] KGX = [-1, 0, +1, -2, 0, +2, -1, 0, +1]

    static final int KGX_WIDTH = 3

    /**
     * Sobel operator kernel for vertical derivative approximations
     */
    static final int[] KGY = [+1, +2, +1, 0, 0, 0, -1, -2, -1]

    static final int KGY_WIDTH = 3

    /**
     * Gx - horizontal derivative approximations
     */
    int[] gx

    /**
     * Gy - vertical derivative approximations
     */
    int[] gy

    /**
     * Gradiant magnitude
     */
    int[] g

    /**
     * Gradient direction
     */
    int[] Θ

    Sobel(int[] img, int width, int height) {
        super(img)
        this.width = width
        this.height = height
    }

    void calculateGx() {
        gx = Convolution.convolve(img, width, height, KGX, KGX_WIDTH, KGX_WIDTH)
    }

    void calculateGy() {
        gy = Convolution.convolve(img, width, height, KGY, KGY_WIDTH, KGY_WIDTH)
    }

    int[] calculateGradiantMagnitude() {
        calculateGx()
        calculateGy()

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

            }
        }

    }
}

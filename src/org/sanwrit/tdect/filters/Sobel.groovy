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
    double[] gx

    /**
     * Gy - vertical derivative approximations
     */
    double[] gy

    /**
     * Gradient magnitude
     */
    double[] g

    /**
     * Gradient direction
     */
    double[] Θ

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

    void calculateGradiantMagnitude() {
        calculateGx()
        calculateGy()

        this.g = new int[ this.width * this.height ]
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                def index = y * this.width + x

                this.g[index] = Math.hypot(gx[index], gy[index])
            }
        }
    }

    void calculateGradientDirection() {
        calculateGx()
        calculateGy()

        this.Θ = new int[ this.width * this.height ]
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                def index = y * this.width + x

                this.Θ[index] = Math.atan2(gy[index], gx[index])
            }
        }
    }




}

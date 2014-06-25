/**
 * Created by greggvarona on 6/23/14.
 */
package org.sanwrit.tdect.filters

import org.sanwrit.tdect.filters.util.Convolution
import org.sanwrit.tdect.io.ImageUtils

/**
 * Implements a simple Gaussian blur.
 *
 * @see <a href="http://www.songho.ca/dsp/convolution/convolution2d_example.html">
 *     http://www.songho.ca/dsp/convolution/convolution2d_example.html</a>
 * @see <a href="http://en.wikipedia.org/wiki/Gaussian_blur#Mechanics">
 *     http://en.wikipedia.org/wiki/Gaussian_blur#Mechanics</a>
 * @see <a href="http://http.developer.nvidia.com/GPUGems3/gpugems3_ch40.html">
 *     http://http.developer.nvidia.com/GPUGems3/gpugems3_ch40.html</a>
 */
class GaussianBlur extends Filter {

    /**
     * Length starting from the origin towards a point at the edge of the
     * matrix. Most likely be sigma/3.
     */
    int radius

    /**
     * Width of {@code img}
     */
    int width

    /**
     * Height of {@code img}
     */
    int height

    /**
     * The amount of blur which is also radius * 3.
     * Standard deviation of the Gaussian distribution.
     */
    double sigma

    /**
     * Also known as kernel in image processing.
     */
    double[] matrix

    /**
     * Default value of radius. Sigma will be 1.
     */
    static final int DEFAULT_RADIUS = 1

    /**
     *
     * @param img input pixels
     * @param width width of img
     * @param height height of img
     */
    GaussianBlur(int[] img, int width, int height) {
        this(img, width, height, DEFAULT_RADIUS)
    }

    /**
     *
     * @param img input pixels
     * @param width width of img
     * @param height height of img
     * @param radius radius of the blur
     */
    GaussianBlur(int[] img, int width, int height, int radius) {
        this(img, width, height, radius, radius / 3)
    }

    /**
     *
     * @param img input pixels
     * @param width width of img
     * @param height height of img
     * @param radius radius of the blur
     * @param sigma standard deviation of the Gaussian blur
     */
    GaussianBlur(int[] img, int width, int height, int radius, double sigma) {
        super(img)
        this.width = width
        this.height = height
        this.radius = radius
        this.sigma = sigma
        buildMatrix()
    }

    /**
     * Builds a kernel using the Gaussian function
     *
     * @see <a href="http://en.wikipedia.org/wiki/Gaussian_blur#Mechanics">
     *     http://en.wikipedia.org/wiki/Gaussian_blur#Mechanics</a>
     * @see <a href="http://http.developer.nvidia.com/GPUGems3/gpugems3_ch40.html">
     *     http://http.developer.nvidia.com/GPUGems3/gpugems3_ch40.html</a>
     */
    void buildMatrix() {
        def width = this.radius * 2 + 1
        def sigma2_2 =  sigma * sigma * 2
        def piSigma2_2 = sigma2_2 * Math.PI
        double accumulator = 0

        this.matrix = new int[ width * width ] //area of a square array.

        for (int row = 0; row < width; row++) {
            for (int col= 0; col < width; col++) {
                int idx = row * width + col
                int w2 = width / 2
                int x = col - ((int)(width / 2)) // distance from the origin (x-axis)
                int y = ((int)(width / 2)) - row // distance from the origin (y-axis)

                //Gaussian Blur function G(x,y)
                double exp = (((x * x) + (y * y)) / sigma2_2)
                this.matrix[idx] = (1 / piSigma2_2) * Math.exp(-1 * exp)

                accumulator += this.matrix[idx]
            }
        }

        //normalization
        for (i in 0..this.matrix.length - 1) {
            this.matrix[i] /= accumulator
        }
    }

    /**
     * <p>Blurs the image by multiply each kernel(matrix) value to the
     * corresponding input pixels and assign the sum of the products to the
     * output pixel. With this, the output pixel becomes the average of its
     * surrounding pixels.</p>
     *
     * <p>This does not overwrite the original input ({@code img}).
     *
     * @see <a href="http://en.wikipedia.org/wiki/Kernel_%28image_processing%29#Convolution">
     *     http://en.wikipedia.org/wiki/Kernel_%28image_processing%29#Convolution</a>
     * @return
     */
    def blur() {
        def mw = this.radius * 2 + 1 //width of the kernel/matrix
        Convolution.convolve(this.img, width, height, this.matrix, mw, mw)
    }

}

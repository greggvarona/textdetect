/**
 * Created by greggvarona on 6/23/14.
 */
package org.sanwrit.tdect.filters

class GaussianBlur extends Filter {

    /**
     * Length starting from the origin towards a point at the edge of the
     * matrix. Most likely be sigma/3.
     */
    int radius

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

    GaussianBlur(int[] img) {
        this(img, DEFAULT_RADIUS)
    }

    GaussianBlur(int[] img, int radius) {
        this(img, radius, radius / 3)
    }

    GaussianBlur(int[] img, int radius, double sigma) {
        super(img)
        this.radius = radius
        this.sigma = sigma
        buildMatrix()
    }


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
            this.matrix[i] /= accumulator;
        }
    }

}

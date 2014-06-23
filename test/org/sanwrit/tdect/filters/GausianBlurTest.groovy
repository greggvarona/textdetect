/**
 * Created by greggvarona on 6/23/14.
 */
package org.sanwrit.tdect.filters

class GausianBlurTest extends GroovyTestCase {

    void testBuildMatrix() {
        int[] img // dummy input
        GaussianBlur filter = new GaussianBlur(img, 3, 0.84089642)
        filter.buildMatrix()
        double[] matrix = filter.getMatrix()
        int width = filter.getRadius() * 2 + 1

        assertEquals(matrix.length, 49)

        //dump matrix
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < width; x++) {
                print matrix[y * width + x] + " ";
            }
            println()
        }

    }
}

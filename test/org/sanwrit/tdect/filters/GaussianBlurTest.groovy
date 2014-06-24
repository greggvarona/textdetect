/**
 * Created by greggvarona on 6/23/14.
 */
package org.sanwrit.tdect.filters

import org.sanwrit.tdect.io.ImageUtils

class GaussianBlurTest extends GroovyTestCase {

    void testBuildMatrix() {
        // dummy input
        int[] img
        int imgWidth
        int imgHeight

        GaussianBlur filter = new GaussianBlur(img, imgWidth, imgHeight, 3,
                0.84089642)

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

        int midXY = ((int) Math.floor(matrix.length / 2))
        assertEquals(matrix[midXY], 0.22508351751007893)
    }

    void testConvolve() {
        String path = URLDecoder.decode(this.getClass()
                .getResource("/org/sanwrit/tdect/io/in/gbf/Captcha.jpg")
                .getPath() + File.separator, "UTF-8")
        String outPath = URLDecoder.decode(this.getClass()
                .getResource("/org/sanwrit/tdect/io/out")
                .getPath() + File.separator, "UTF-8")

        File file = new File(path)

        assertFalse file.isDirectory()

        def images = []
        images.add(ImageUtils.readImage(file.getAbsoluteFile()
                .getAbsolutePath()))
        def image = images.first()

        assertTrue image != null

        int[] input = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
                null, 0, image.getWidth())
        int imgWidth = image.getWidth()
        int imgHeight = image.getHeight()

        //apply Gaussian blur
        GaussianBlur filter = new GaussianBlur(input, imgWidth, imgHeight, 3,
                0.84089642)
        filter.buildMatrix()
        int[] output = filter.convolve()

        assertTrue output.length != null

        //convert to BufferedImage
        def img = ImageUtils.toImage(output, imgWidth, imgHeight)

        //store
        File outputDir = new File(outPath)
        File newFile = new File(outputDir.getAbsolutePath()
                + "/gbf${ new Date().getTime() }.png".toString())
        assertTrue ImageUtils.writeImage(img, "PNG", newFile)
    }
}

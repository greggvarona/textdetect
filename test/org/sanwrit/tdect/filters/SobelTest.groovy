package org.sanwrit.tdect.filters

import org.sanwrit.tdect.io.ImageUtils

/**
 * Created by greggvarona on 6/25/14.
 */
class SobelTest extends GroovyTestCase {

    void testMagnitude() {
        String path = URLDecoder.decode(this.getClass()
                .getResource("/org/sanwrit/tdect/io/in/Valve_original.PNG")
                .getPath() + File.separator, "UTF-8")
        String outPath = URLDecoder.decode(this.getClass()
                .getResource("/org/sanwrit/tdect/io/out")
                .getPath() + File.separator, "UTF-8")

        File file = new File(path)
        def img = ImageUtils.readImage(file.getAbsoluteFile()
                .getAbsolutePath())
        int[] input = img.getRGB(0,0, img.getWidth(), img.getHeight(), null, 0,
                img.getWidth())
        int[] gradientMagnitude

        GrayScaleFilter grayscale = new GrayScaleFilter(input)
        input = grayscale.luminosity()

        GaussianBlur gaussian = new GaussianBlur(input, img.getWidth(),
                img.getHeight(), 2, 1.4)
        input = gaussian.blur()

        Sobel filter = new Sobel(input, img.getWidth(), img.getHeight())
        filter.calculateGradiantMagnitude()
        gradientMagnitude = filter.getG()
        def gradientX = filter.getGx()
        def gradientY = filter.getGy()

        println "["
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int index = y * img.getWidth() + x
                print gradientMagnitude[index] + ", "
            }
            println()
        }
        println "]"

        //convert to BufferedImage
        def outImg = ImageUtils.toImage(gradientMagnitude, img.getWidth(),
                img.getHeight())

        //store
        File outputDir = new File(outPath)
        File newFile = new File(outputDir.getAbsolutePath()
                + "/so${ new Date().getTime() }.png".toString())
        assertTrue ImageUtils.writeImage(outImg, "PNG", newFile)

        //convert to BufferedImage
        outImg = ImageUtils.toImage(gradientX, img.getWidth(),
                img.getHeight())

        //store
        newFile = new File(outputDir.getAbsolutePath()
                + "/sogx${ new Date().getTime() }.png".toString())
        assertTrue ImageUtils.writeImage(outImg, "PNG", newFile)

        //convert to BufferedImage
        outImg = ImageUtils.toImage(gradientY, img.getWidth(),
                img.getHeight())

        //store
        newFile = new File(outputDir.getAbsolutePath()
                + "/sogy${ new Date().getTime() }.png".toString())
        assertTrue ImageUtils.writeImage(outImg, "PNG", newFile)

    }

    /*void testDirection() {
        String path = URLDecoder.decode(this.getClass()
                .getResource("/org/sanwrit/tdect/io/in/OZuBo.png")
                .getPath() + File.separator, "UTF-8")
        String outPath = URLDecoder.decode(this.getClass()
                .getResource("/org/sanwrit/tdect/io/out")
                .getPath() + File.separator, "UTF-8")

        File file = new File(path)
        def img = ImageUtils.readImage(file.getAbsoluteFile()
                .getAbsolutePath())
        int[] input = img.getRGB(0,0, img.getWidth(), img.getHeight(), null, 0,
                img.getWidth())
        int[] gradientDirection

        GrayScaleFilter grayscale = new GrayScaleFilter(input)
        input = grayscale.average()
        GrayScaleFilter grayscale2 = new GrayScaleFilter(input)

        assertTrue grayscale2.imageIsGrayScale()

        GaussianBlur gaussian = new GaussianBlur(input, img.getWidth(),
                img.getHeight(), 2, 1.4)
        input = gaussian.blur()

        Sobel filter = new Sobel(input, img.getWidth(), img.getHeight())
        filter.calculateGradientDirection()
        gradientDirection = filter.getΘ()
        def gradientX = filter.getGx()
        def gradientY = filter.getGy()

        println "["
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int index = y * img.getWidth() + x
                print gradientDirection[index] + ", "
            }
            println()
        }
        println "]"

        //convert to BufferedImage
        def outImg = ImageUtils.toImage(gradientDirection, img.getWidth(),
                img.getHeight())

        //store
        File outputDir = new File(outPath)
        File newFile = new File(outputDir.getAbsolutePath()
                + "/so${ new Date().getTime() }.png".toString())
        assertTrue ImageUtils.writeImage(outImg, "PNG", newFile)

        //convert to BufferedImage
        outImg = ImageUtils.toImage(gradientX, img.getWidth(),
                img.getHeight())

        //store
        newFile = new File(outputDir.getAbsolutePath()
                + "/sogx${ new Date().getTime() }.png".toString())
        assertTrue ImageUtils.writeImage(outImg, "PNG", newFile)

        //convert to BufferedImage
        outImg = ImageUtils.toImage(gradientY, img.getWidth(),
                img.getHeight())

        //store
        newFile = new File(outputDir.getAbsolutePath()
                + "/sogy${ new Date().getTime() }.png".toString())
        assertTrue ImageUtils.writeImage(outImg, "PNG", newFile)

    }*/



}

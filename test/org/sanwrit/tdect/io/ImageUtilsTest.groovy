/**
 * ImageUtilsTest.groovy
 *
 * Version 1.0
 *
 * June 19, 2014
 *
 * Copyright Sanwrit Software Laboratories, Inc. 2014
 */
package org.sanwrit.tdect.io
/**
 * Tests for org.sanwrit.tdect.io.ImageUtils
 *
 */
class ImageUtilsTest extends GroovyTestCase {
    
    void testGetImage() {

        File inputDir = new File(this.getClass().getResource("in").getPath() + File.separator)

        assertTrue inputDir.isDirectory()

        def images = []
        for (file in inputDir.listFiles()) {
            images.add(ImageUtils.readImage(file.getAbsoluteFile()
                    .getAbsolutePath()))
        }
        
        assertTrue images.size() > 0
    }

    void testGrayScaleAverage() {
        File inputDir = new File(this.getClass().getResource("in").getPath()
                + File.separator)

        assertTrue inputDir.isDirectory()

        def images = []
        for (file in inputDir.listFiles()) {
            images.add(ImageUtils.readImage(file.getAbsoluteFile()
                    .getAbsolutePath()))
        }

        assertTrue images.size() > 0

        images.each { it ->
            def img = ImageUtils.grayScaleAverage(it)
            File newFile = new File(inputDir.getAbsolutePath() + "/temp.png")
            assertTrue ImageUtils.writeImage(it, "PNG", newFile)
        }
    }
}



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
        String path = URLDecoder.decode(this.getClass().getResource("in")
                .getPath() + File.separator, "UTF-8")
        File inputDir = new File(path)
        assertTrue inputDir.isDirectory()

        def images = []
        for (file in inputDir.listFiles()) {
            images.add(ImageUtils.readImage(file.getAbsoluteFile()
                    .getAbsolutePath()))
        }
        
        assertTrue images.size() > 0
    }

}



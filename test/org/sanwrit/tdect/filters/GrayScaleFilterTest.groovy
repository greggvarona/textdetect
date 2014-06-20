package org.sanwrit.tdect.filters

import org.sanwrit.tdect.io.ImageUtils

/**
 * Created by greggvarona on 6/20/14.
 */
class GrayScaleFilterTest extends GroovyTestCase {

    void testGrayScaleAverage() {
        String path = URLDecoder.decode(this.getClass()
                .getResource("/org/sanwrit/tdect/io/in").getPath()
                + File.separator, "UTF-8")
        File inputDir = new File(path)

        assertTrue inputDir.isDirectory()

        def images = []
        for (file in inputDir.listFiles()) {
            images.add(ImageUtils.readImage(file.getAbsoluteFile()
                    .getAbsolutePath()))
        }

        assertTrue images.size() > 0

        images.each { it ->
            def data = it.getRGB(0, 0, it.getWidth(),
                    it.getHeight(), null, 0, it.getWidth())

            //apply filter
            GrayScaleFilter filter = new GrayScaleFilter(data)
            data = filter.average()

            //convert to BufferedImage
            def img = ImageUtils.toImage(data, it.getWidth(), it.getHeight())

            //store
            File newFile = new File(inputDir.getAbsolutePath() + "/temp.png")
            assertTrue ImageUtils.writeImage(img, "PNG", newFile)
        }
    }
}

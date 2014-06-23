/**
 * Created by greggvarona on 6/23/14.
 */
package org.sanwrit.tdect.filters

import org.sanwrit.tdect.io.ImageUtils


class IsolateColorFilterTest extends GroovyTestCase {

    void testIsolation() {
        String path = URLDecoder.decode(this.getClass()
                .getResource("/org/sanwrit/tdect/io/in/icf").getPath()
                + File.separator, "UTF-8")

        String outPath = URLDecoder.decode(this.getClass()
                .getResource("/org/sanwrit/tdect/io/out").getPath()
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
            IsolateColorFilter filter = new IsolateColorFilter(data)
            filter.apply(90)
            data = filter.getImg()

            //convert to BufferedImage
            def img = ImageUtils.toImage(data, it.getWidth(), it.getHeight())

            //store
            File outputDir = new File(outPath)
            File newFile = new File(outputDir.getAbsolutePath()
                    + "/icf${ new Date().getTime() }.png".toString())
            assertTrue ImageUtils.writeImage(img, "PNG", newFile)
        }
    }
}

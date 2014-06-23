/**
 * Created by greggvarona on 6/20/14.
 */
package org.sanwrit.tdect.filters

/**
 * Base class for filters.
 */
class Filter {

    // Although "private" never really works much in some Groovy versions.
    private int[] img

    Filter(int[] img) {
        this.img = img
    }

    def getImg() {
        return img
    }

}

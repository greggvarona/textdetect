/**
 * Created by greggvarona on 6/24/14.
 */
package org.sanwrit.tdect.filters.util

import org.sanwrit.tdect.io.ImageUtils

/**
 * Utility class used for convolution.
 */
class Convolution {


    static int[] convolve(int[] input, int w, int h,
                          int[] matrix, int mw, int mh) {
        int[] output = new int[w * h]

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                //index relative to the matrix's origin
                int origin = row * w + col
                //set argb to 0 every pass
                double a = 0 //alpha
                double r = 0 //red
                double g = 0 //green
                double b = 0 //blue

                for (int my = 0; my < mh; my++) {
                    for (int mx = 0; mx < mw; mx++) {
                        // a lot of coercions here bec. of groovy
                        int y = (int)(row + my - (int)(mw/2))
                        int x = (int)(col + mx - (int)(mw/2))

                        if ((x >= 0 && y >= 0)) {
                            if ((x < w && y < h)) {
                                int index = y * w + x
                                int mIndex = my * mw + mx
                                int rgb = input[index];
                                double f = matrix[mIndex]

                                a += f * ((rgb >> 24) & 0xff);
                                r += f * ((rgb >> 16) & 0xff);
                                g += f * ((rgb >> 8) & 0xff);
                                b += f * (rgb & 0xff);
                            }
                        }
                    }
                }

                output[origin] = (ImageUtils.clampRGBValue((int)(a+0.5))<<24) |
                        (ImageUtils.clampRGBValue((int)(r+0.5)) << 16) |
                        (ImageUtils.clampRGBValue((int)(g+0.5)) << 8) |
                        ImageUtils.clampRGBValue((int)(b+0.5));
            }
        }

        return output
    }

    static int[] convolve(int[] input, int w, int h,
                          double[] matrix, int mw, int mh) {
        int[] output = new int[w * h]

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                //index relative to the matrix's origin
                int origin = row * w + col
                //set argb to 0 every pass
                double a = 0 //alpha
                double r = 0 //red
                double g = 0 //green
                double b = 0 //blue

                for (int my = 0; my < mh; my++) {
                    for (int mx = 0; mx < mw; mx++) {
                        // a lot of coercions here bec. of groovy
                        int y = (int)(row + my - (int)(mw/2))
                        int x = (int)(col + mx - (int)(mw/2))

                        if ((x >= 0 && y >= 0)) {
                            if ((x < w && y < h)) {
                                int index = y * w + x
                                int mIndex = my * mw + mx
                                int rgb = input[index];
                                double f = matrix[mIndex]

                                a += f * ((rgb >> 24) & 0xff);
                                r += f * ((rgb >> 16) & 0xff);
                                g += f * ((rgb >> 8) & 0xff);
                                b += f * (rgb & 0xff);
                            }
                        }
                    }
                }

                output[origin] = (ImageUtils.clampRGBValue((int)(a+0.5))<<24) |
                        (ImageUtils.clampRGBValue((int)(r+0.5)) << 16) |
                        (ImageUtils.clampRGBValue((int)(g+0.5)) << 8) |
                        ImageUtils.clampRGBValue((int)(b+0.5));
            }
        }

        return output
    }


}

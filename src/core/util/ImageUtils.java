/*
 * This file is part of mayrio.
 *
 * mayrio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mayrio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mayrio.  If not, see <https://www.gnu.org/licenses/>.
 */

package core.util;

import mayflower.MayflowerImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {
    /**
     * Convert an Image to a BufferedImage. Used during sprite scaling.
     *
     * @param source Image to convert
     * @return Converted Image
     */
    public static BufferedImage imageToBufferedImage(Image source) {
        if (source instanceof BufferedImage) {
            return (BufferedImage) source;
        }

        BufferedImage ret = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = ret.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();

        return ret;
    }

    /**
     * Converts a BufferedImage to a MayflowerImage.
     * The implementation for this is abhorrent, but it's a necessary workaround
     * since MayflowerImage doesn't provide a constructor that accepts a BufferedImage.
     * It works without any performance problems (theoretically) at smaller image sizes, though.
     *
     * @param image The BufferedImage to convert
     * @return The converted MayflowerImage
     */
    public static MayflowerImage bufferedImageToMayflowerImage(BufferedImage image) {
        MayflowerImage ret = new MayflowerImage(image.getWidth(), image.getHeight());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                java.awt.Color color = new java.awt.Color(image.getRGB(x, y), true);
                mayflower.Color convertedColor = new mayflower.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
                ret.setColorAt(x, y, convertedColor);
            }
        }
        return ret;
    }

    /**
     * Mirror an image horizontally. Unlike MayflowerImage.mirrorHorizontally(), this method doesn't mutate the original image.
     *
     * @param image Image to mirror
     * @return Mirrored image
     */
    public static MayflowerImage mirrorImageHorizontal(MayflowerImage image) {
        MayflowerImage ret = new MayflowerImage(image);
        ret.mirrorHorizontally();
        return ret;
    }

    /**
     * Mirror an image horizontally. Unlike MayflowerImage.mirrorHorizontally(), this method doesn't mutate the original image.
     *
     * @param image Image to mirror
     * @return Mirrored image
     */
    public static MayflowerImage mirrorImageVertical(MayflowerImage image) {
        MayflowerImage ret = new MayflowerImage(image);
        ret.mirrorVertically();
        return ret;
    }
}

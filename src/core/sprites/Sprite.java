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

package core.sprites;

import mayflower.MayflowerImage;

import java.awt.image.BufferedImage;

public class Sprite {
    private BufferedImage original;
    private MayflowerImage sprite;
    private SpriteSheet parent;

    Sprite(BufferedImage image, SpriteSheet sheet) {
        this.original = image;
        this.sprite = bufferedImageToMayflowerImage(image);
        this.parent = sheet;
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
    private MayflowerImage bufferedImageToMayflowerImage(BufferedImage image) {
        MayflowerImage ret = new MayflowerImage(image.getWidth(), image.getHeight());
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                mayflower.Color currentPixel = new mayflower.Color(new java.awt.Color(image.getRGB(x,y)));
                ret.setColorAt(x, y, currentPixel);
            }
        }
        return ret;
    }

    /**
     * Returns the original BufferedImage for this Sprite
     * @return BufferedImage version of this Sprite
     */
    public BufferedImage getOriginal() {
        return this.original;
    }

    /**
     * Returns the MayflowerImage sprite to be used in-game
     * @return MayflowerImage version of this Sprite
     */
    public MayflowerImage getSprite() {
        return this.sprite;
    }

    /**
     * Returns the SpriteSheet this Sprite belongs to
     * @return Parent SpriteSheet
     */
    public SpriteSheet getSheet() {
        return this.parent;
    }
}

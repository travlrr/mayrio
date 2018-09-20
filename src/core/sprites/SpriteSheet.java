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

import core.util.ImageUtils;
import core.util.log.LogLevel;
import core.util.log.MayrioLogger;
import mayflower.MayflowerImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The SpriteSheet class represents a full sprite sheet image, and provides functionality for fetching sprites from the sheet.
 * <p>
 * Sprite sheet images provided to this class should contain sprites of uniform size. All the other work is done for you.
 * The slice tool in Adobe Photoshop
 */
public class SpriteSheet {
    private static final MayrioLogger logger;
    private static int scaleFactor;

    static {
        logger = new MayrioLogger(SpriteSheet.class);
        scaleFactor = 1;
    }

    private Dimension spriteSize;
    private String path;
    private BufferedImage sheet;
    private ArrayList<MayflowerImage> sprites;

    /**
     * Constructs a new SpriteSheet, splitting the given sheet image into usable chunks by row-major order.
     * Sprite scale defaults to 1:1.
     *
     * @param spriteSize Size of each sprite (width x height)
     * @param sheetPath  The full path relative to the project root for the sprite sheet image.
     */
    public SpriteSheet(Dimension spriteSize, String sheetPath) {
        this.path = sheetPath;
        this.spriteSize = spriteSize;
        this.sprites = new ArrayList<>();

        // Fetch the BufferedImage at the given path
        this.sheet = getImage(sheetPath);

        // Make sure the spriteSize is valid, if not then throw an Exception
        if (sheet.getWidth() % spriteSize.getWidth() != 0 | sheet.getHeight() % spriteSize.getHeight() != 0) {
            throw new InvalidSpriteSizeException("Invalid tile size when constructing SpriteSheet for " + sheetPath);
        }

        // Get the number of sprites in the sheet
        int nColumns = sheet.getWidth() / spriteSize.getWidth();
        int nRows = sheet.getHeight() / spriteSize.getHeight();

        // Populate sprites
        int cellIndex = 0;
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nColumns; c++) {
                BufferedImage spriteImage = sheet.getSubimage(c * spriteSize.getWidth(), r * spriteSize.getHeight(), spriteSize.getWidth(), spriteSize.getHeight());
                if (scaleFactor != 1) {
                    spriteImage = ImageUtils.imageToBufferedImage(spriteImage.getScaledInstance(spriteImage.getWidth() * SpriteSheet.scaleFactor, spriteImage.getHeight() * SpriteSheet.scaleFactor, Image.SCALE_FAST));
                }
                sprites.add(cellIndex, ImageUtils.bufferedImageToMayflowerImage(spriteImage));
                cellIndex++;
            }
        }

        logger.logf(LogLevel.DEBUG, "SpriteSheet from source \"%s\" constructed, %dx%d, %d sprites added in total", sheetPath, sprites.size(), spriteSize.getWidth(), spriteSize.getHeight());
    }

    /**
     * Sets the scaling factor of all new spritesheets. This must be a power of 2!
     *
     * @param scale Scale multiplier (power of 2)
     */
    public static void setScale(int scale) {
        if (!isPowerOf2(scale)) {
            throw new IllegalArgumentException("Scale passed to SpriteSheet.setScale is not a power of 2!");
        }
        SpriteSheet.scaleFactor = scale;
    }

    /**
     * Get a sprite from the sprite sheet
     */
    public MayflowerImage getSprite(int index) {
        return sprites.get(index);
    }

    /**
     * Get multiple sprites from the sprite sheet
     */
    public MayflowerImage[] getSprites(int... indexes) {
        MayflowerImage[] ret = new MayflowerImage[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            ret[i] = sprites.get(i);
        }
        return ret;
    }

    /**
     * Return the global sprite scale factor
     */
    public static int getScaleFactor() {
        return SpriteSheet.scaleFactor;
    }

    /**
     * Check if a number is a power of 2.
     * Used to make sure scale factor doesn't make the sprites fit on the screen unevenly.
     */
    private static boolean isPowerOf2(int n) {
        if (n == 2) {
            return true;
        }

        while (n % 2 == 0) {
            n /= 2;
        }

        return n == 1;
    }

    /**
     * Get a BufferedImage by file path.
     *
     * @param path Path to fetch image from
     * @return BufferedImage from path
     */
    private BufferedImage getImage(String path) {
        BufferedImage ret = null;
        try {
            ret = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.log(LogLevel.ERROR, "A SpriteSheet was passed an invalid path!");
        }
        return ret;
    }
}
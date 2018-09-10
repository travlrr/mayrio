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

import core.util.log.LogLevel;
import core.util.log.MayrioLogger;
import mayflower.MayflowerImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * The SpriteSheet class represents a full sprite sheet image, and provides functionality for fetching sprites from the sheet.
 * <p>
 * Sprite sheet images provided to this class should contain sprites of uniform size. All the other work is done for you.
 * The slice tool in Adobe Photoshop
 */
public class SpriteSheet {
    private static final MayrioLogger logger = new MayrioLogger(SpriteSheet.class);
    private final boolean usesIntegerKeys;
    private Dimension spriteSize;
    private String path;
    private BufferedImage sheet;
    private HashMap<Object, MayflowerImage> sprites;

    /**
     * Constructs a new SpriteSheet, splitting the given sheet image into usable chunks by row-major order.
     * The SpriteSheet uses integer indexes when names are not provided.
     *
     * @param spriteSize Size of each sprite (width x height)
     * @param sheetPath  The full path relative to the project root for the sprite sheet image.
     */
    public SpriteSheet(Dimension spriteSize, String sheetPath) {
        this.path = sheetPath;
        this.spriteSize = spriteSize;
        this.usesIntegerKeys = true;
        this.sprites = new HashMap<>();

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
        int cellIndex = 1;
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nColumns; c++) {
                BufferedImage spriteImage = sheet.getSubimage(c * spriteSize.getWidth(), r * spriteSize.getHeight(), spriteSize.getWidth(), spriteSize.getHeight());
                sprites.put(cellIndex, bufferedImageToMayflowerImage(spriteImage));
            }
        }
    }

    /**
     * Constructs a new SpriteSheet, splitting the given sheet image into usable chunks by row-major order.
     *
     * @param spriteSize Size of each sprite (width x height)
     * @param sheetPath  The full path relative to the project root for the sprite sheet image.
     * @param names      Names for each sprite.
     */
    public SpriteSheet(Dimension spriteSize, String sheetPath, String[] names) {
        this.path = sheetPath;
        this.spriteSize = spriteSize;
        this.usesIntegerKeys = false;
        this.sprites = new HashMap<>();

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
        int cellIndex = 1;
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nColumns; c++) {
                BufferedImage spriteImage = sheet.getSubimage(c * spriteSize.getWidth(), r * spriteSize.getHeight(), spriteSize.getWidth(), spriteSize.getHeight());
                sprites.put(names[cellIndex], bufferedImageToMayflowerImage(spriteImage));
            }
        }
    }

    /**
     * Returns whether or not this sheet has a sprite paired with the given key
     *
     * @param key Key to check
     * @return Boolean result
     */
    public boolean hasSprite(Object key) {
        return sprites.containsKey(key) && sprites.get(key) != null;
    }

    /**
     * Returns the sprite paired with the given key
     *
     * @param key Key to get sprite from
     * @return Sprite at the given key
     */
    public MayflowerImage getSprite(Object key) {
        return sprites.get(key);
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

    public boolean usesIntegerKeys() {
        return this.usesIntegerKeys;
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
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                mayflower.Color currentPixel = new mayflower.Color(new java.awt.Color(image.getRGB(x, y)));
                ret.setColorAt(x, y, currentPixel);
            }
        }
        return ret;
    }
}

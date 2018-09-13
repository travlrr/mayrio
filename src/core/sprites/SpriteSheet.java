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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The SpriteSheet class represents a full sprite sheet image, and provides functionality for fetching sprites from the sheet.
 * <p>
 * Sprite sheet images provided to this class should contain sprites of uniform size. All the other work is done for you.
 * The slice tool in Adobe Photoshop
 */
public class SpriteSheet {
    private static final MayrioLogger logger;
    private Dimension spriteSize;
    private String path;
    private BufferedImage sheet;
    private ArrayList<MayflowerImage> sprites;
    private int scaleFactor;

    // TODO: Implement sprite scaling

    static {
        logger = new MayrioLogger(SpriteSheet.class);
    }

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
        this.scaleFactor = 1;

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
                sprites.add(cellIndex, bufferedImageToMayflowerImage(spriteImage));
                cellIndex++;
            }
        }

        logger.logf(LogLevel.DEBUG, "SpriteSheet from source \"%s\" constructed, %dx%d, %d sprites added in total", sheetPath, sprites.size(), spriteSize.getWidth(), spriteSize.getHeight());
    }

    /**
     * Constructs a new SpriteSheet, splitting the given sheet image into usable chunks by row-major order.
     * Providing a scale argument
     *
     * @param spriteSize Size of each sprite (width x height)
     * @param sheetPath  The full path relative to the project root for the sprite sheet image.
     */
    public SpriteSheet(Dimension spriteSize, String sheetPath, int scale) {
        this.path = sheetPath;
        this.spriteSize = spriteSize;
        this.sprites = new ArrayList<>();
        this.scaleFactor = scale;

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
                BufferedImage scaled = imageToBufferedImage(spriteImage.getScaledInstance(spriteImage.getWidth() * scaleFactor, spriteImage.getHeight() * scaleFactor, Image.SCALE_FAST));
                sprites.add(cellIndex, bufferedImageToMayflowerImage(scaled));
                cellIndex++;
            }
        }

        logger.logf(LogLevel.DEBUG, "SpriteSheet from source \"%s\" constructed, %dx%d, %d sprites added in total", sheetPath, sprites.size(), sprites.get(0).getWidth(), sprites.get(0).getHeight());
    }

    /**
     * Get a sprite from the sprite sheet
     *
     * @param index Index to get sprite from
     * @return Sprite at the given index
     */
    public MayflowerImage getSprite(int index) {
        return sprites.get(index);
    }

    /**
     * Get multiple sprites from the sprite sheet
     *
     * @param indexes Indexes to get sprites from
     * @return Sprites at the given indexes
     */
    public MayflowerImage[] getSprites(int... indexes) {
        MayflowerImage[] ret = new MayflowerImage[indexes.length];
        for(int i = 0; i < indexes.length; i++) {
            ret[i] = sprites.get(i);
        }
        return ret;
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

    /**
     * Convert an Image to a BufferedImage. Used during sprite scaling.
     *
     * @param source Image to convert
     * @return Converted Image
     */
    private BufferedImage imageToBufferedImage(Image source) {
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
    private MayflowerImage bufferedImageToMayflowerImage(BufferedImage image) {
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
}

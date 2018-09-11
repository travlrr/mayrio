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

/**
 * A Dimension represents a measure of 2D space on the screen or in an image.
 */
public class Dimension {
    private int width;
    private int height;

    /**
     * Constructs a new Dimension with the given width and height.
     *
     * @param width  Width of the Dimension
     * @param height Height of the Dimension
     */
    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Checks whether this Dimension is equal to another Dimension.
     *
     * @param other Other dimension to check against
     * @return Boolean result of comparison
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Dimension dimension = (Dimension) other;
        return width == dimension.width && height == dimension.height;
    }

    /**
     * Returns the width of this Dimension.
     *
     * @return Width of this Dimension
     */
    int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of this Dimension.
     *
     * @return Height of this Dimension
     */
    int getHeight() {
        return this.height;
    }
}

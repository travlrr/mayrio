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

package actors.core;

import java.util.Objects;

/**
 * The Coordinate class is used to represent a single point in 2D space.
 */
public class Coordinate {
    private double x;
    private double y;

    /**
     * Construct a Coordinate with the given values
     *
     * @param x X value of the Coordinate
     * @param y Y value of the Coordinate
     */
    public Coordinate(int x, int y) {
        this.x = (double) x;
        this.y = (double) y;
    }

    /**
     * Construct a Coordinate with the given values
     *
     * @param x X value of the Coordinate
     * @param y Y value of the Coordinate
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return (int) x;
    }

    public int y() {
        return (int) y;
    }

    public double dx() {
        return x;
    }

    public double dy() {
        return y;
    }

    /**
     * Subtract a Coordinate from this one
     */
    public Coordinate sub(Coordinate other) {
        return new Coordinate(this.x - other.x, this.y - other.y);
    }

    /**
     * Add a Coordinate to this one
     */
    public Coordinate add(Coordinate other) {
        return new Coordinate(this.x + other.x, this.y + other.y);
    }

    /**
     * Checks if this Coordinate is equal to another
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Coordinate) {
            Coordinate casted = (Coordinate) other;
            return (this.x() == casted.x()) && (this.y() == casted.y());
        }
        return false;
    }

    /**
     * Returns the hashcode for this Coordinate
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

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

public enum GroundType {
    CORNER_TOPLEFT(0),
    FLAT_TOP(1),
    CORNER_TOPRIGHT(2),
    FLAT_LEFT(3),
    MIDDLE(4),
    FLAT_RIGHT(5),
    CORNER_BOTTOMLEFT(6),
    FLAT_BOTTOM(7),
    CORNER_BOTTOMRIGHT(8);

    private int type;

    GroundType(int type) {
        this.type = type;
    }

    public int getValue() {
        return type;
    }
}

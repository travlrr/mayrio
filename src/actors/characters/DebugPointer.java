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

package actors.characters;

import actors.core.Coordinate;
import actors.core.StaticActor;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;

/**
 * A small circle used to visualize coordinates for debugging
 */
public class DebugPointer extends StaticActor {
    private static SpriteSheet sheet;

    static {
        sheet = new SpriteSheet(new Dimension(16, 16), "/sprites/debugpointers.png");
    }

    public DebugPointer(int size) {
        super(sheet.getSprite(size), false);
    }

    public void setPosition(double x, double y) {
        this.setLocation(x - (this.getImage().getWidth() / 2.0), y - (this.getImage().getHeight() / 2.0));
    }

    public void setPosition(Coordinate pos) {
        this.setLocation(pos.dx() - (this.getImage().getWidth() / 2.0), pos.dy() - (this.getImage().getHeight() / 2.0));
    }
}

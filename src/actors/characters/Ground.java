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

import actors.core.GroundType;
import actors.core.StaticActor;
import core.sprites.Dimension;
import core.sprites.SpriteSheet;

/**
 * Ground blocks. Self-explanatory.
 */
public class Ground extends StaticActor {
    private static SpriteSheet sheet;

    static {
        sheet = new SpriteSheet(new Dimension(16, 16), "/sprites/ground.png");
    }

    public Ground(GroundType type) {
        super(sheet.getSprite(type.getValue()), true);
    }
}
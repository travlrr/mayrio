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

package worlds;

import actors.core.TextActor;
import mayflower.Keyboard;
import mayflower.Mayflower;
import worlds.core.MayrioWorld;

import java.awt.Color;

public class Menu extends MayrioWorld {
    @Override
    public void init() {
        super.initNoUi();
        TextActor title = new TextActor("Mayrio", 64, Color.white);
        TextActor hint = new TextActor("Press space to start", 24, Color.white);
        this.addObject(title, getWidth() / 2 - (title.getImage().getWidth() / 2), 128);
        this.addObject(hint, getWidth() / 2 - (hint.getImage().getWidth() / 2), 312);

        this.setBackground(8);
    }

    @Override
    public void act() {
        super.act();
        if (Mayflower.isKeyDown(Keyboard.KEY_SPACE)) {
            MayrioWorld world = new Stage1();
            world.init();
            Mayflower.setWorld(world);
        }
    }
}

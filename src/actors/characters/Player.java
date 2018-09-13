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

package actors.characters;

import actors.core.AnimatedActor;
import actors.core.AnimationSet;
import mayflower.Keyboard;
import mayflower.Mayflower;


// TODO: Add basic game behavior to Player
public class Player extends AnimatedActor {
    private AnimationSet animations;

    public Player() {
        //SpriteSheet small = new SpriteSheet();

        //Animation walk_sm =
    }

    @Override
    public void act() {
        super.act();
        if(Mayflower.isKeyPressed(Keyboard.KEY_RIGHT)){
            this.setAnimation("walkRight");
            this.move(5);
        }
        if(Mayflower.isKeyPressed(Keyboard.KEY_LEFT)){
            this.setAnimation("walkLeft");
            this.move(-5);
        }
        if(Mayflower.isKeyPressed(Keyboard.KEY_UP)){
            this.setAnimation("jump");
            super.jump();
        }

    }

}
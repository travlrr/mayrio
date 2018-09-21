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

import worlds.core.Frame;
import worlds.core.FramedWorld;
import worlds.frames.stage1.Stage1f1;
import worlds.frames.stage1.Stage1f2;

public class Stage1 extends FramedWorld {

    public Stage1() {
        super();

        Frame f1 = new Stage1f1(this);
        Frame f2 = new Stage1f2(this);
        registerFrames(f1, f2);
    }

    @Override
    public void act() {
        super.act();
    }
}
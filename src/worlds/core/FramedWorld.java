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

package worlds.core;

import actors.characters.Player;
import actors.core.Direction;
import mayflower.Actor;
import mayflower.Mayflower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FramedWorld extends MayrioWorld {
    private static ArrayList<Frame> frames;
    private int currentFrame;
    private Player player;

    protected FramedWorld(Player player) {
        this.player = player;
        frames = new ArrayList<>();
        currentFrame = 0;
    }

    @Override
    public void act() {
        if (player != null && getPlyEdge() != null) {
            if (getPlyEdge().equals(Direction.RIGHT) && currentFrame + 1 < frames.size()) {
                nextFrame();
            } else if (getPlyEdge().equals(Direction.LEFT) && currentFrame - 1 >= 0) {
                previousFrame();
            }
        }
    }

    @Override
    public void init() {
        for (Frame frame : frames) {
            frame.init();
        }
    }

    private Direction getPlyEdge() {
        if (player.getEdge(Direction.RIGHT).x() >= this.getWidth()) {
            return Direction.RIGHT;
        } else if (player.getEdge(Direction.LEFT).x() <= 0) {
            return Direction.LEFT;
        }
        assert false;
        return null;
    }

    private void nextFrame() {
        currentFrame++;
        setFrame(frames.get(currentFrame));
    }

    private void previousFrame() {
        currentFrame--;
        setFrame(frames.get(currentFrame));
    }

    private void setFrame(Frame frame) {
        Direction edge = getPlyEdge();

        this.clear();
        frame.init();

        this.addObject(player, 0, player.getY());
        double x;
        assert edge != null;
        if (edge.equals(Direction.RIGHT)) {
            x = 1;
        } else {
            x = Mayflower.getWidth() - player.getImage().getWidth();
        }
        player.setLocation(x, player.getY());
    }

    private void clear() {
        Iterator<Actor> iterator = this.getObjects().iterator();

        while (iterator.hasNext()) {
            Actor a = iterator.next();
            iterator.remove();
        }
    }

    protected void registerFrames(Frame... frameArray) {
        frames.addAll(Arrays.asList(frameArray));
    }
}

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
import actors.core.Coordinate;
import actors.core.Direction;
import core.Grid;
import core.Main;
import mayflower.Actor;
import mayflower.Mayflower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FramedWorld extends MayrioWorld {
    private ArrayList<Frame> frames;
    private int currentFrame;
    private Player player;

    protected FramedWorld() {
        frames = new ArrayList<>();
        this.player = Main.getPlayer();
    }

    @Override
    public void init() {
        currentFrame = 0;
        super.init();
        Player player = Main.getPlayer();
        Coordinate spawnPos = Grid.toScreen(4, 9);
        addObject(player, spawnPos.x(), spawnPos.y());
        frames.get(0).init();
    }

    @Override
    public void act() {
        super.act();
        if (player != null && getPlyEdge() != null) {
            if (getPlyEdge().equals(Direction.RIGHT) && currentFrame + 1 < frames.size()) {
                nextFrame();
            } else if (getPlyEdge().equals(Direction.LEFT) && currentFrame - 1 >= 0) {
                previousFrame();
            }
        }
    }

    /**
     * Get the edge of the screen the player is on, assuming they're on an edge
     */
    private Direction getPlyEdge() {
        if (player.getEdge(Direction.RIGHT).x() >= this.getWidth()) {
            return Direction.RIGHT;
        } else if (player.getEdge(Direction.LEFT).x() <= 0) {
            return Direction.LEFT;
        }
        return null;
    }

    /**
     * Switch to the next frame
     */
    private void nextFrame() {
        currentFrame++;
        setFrame(frames.get(currentFrame));
    }

    /**
     * Switch to the previous frame
     */
    private void previousFrame() {
        currentFrame--;
        setFrame(frames.get(currentFrame));
    }

    /**
     * Set the frame to the given frame
     */
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

    /**
     * Clear the World
     */
    private void clear() {
        Iterator<Actor> iterator = this.getObjects().iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        super.addUi();
    }

    /**
     * Register frames to this FramedWorld.
     *
     * @param frameArray Frames to add, in order
     */
    protected void registerFrames(Frame... frameArray) {
        frames.addAll(Arrays.asList(frameArray));
    }
}

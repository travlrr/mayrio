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

package core;

import mayflower.World;

import java.util.HashMap;

public class WorldManager {
    private static WorldManager instance;
    private static HashMap<String, World> worlds;

    private WorldManager() {
        worlds = new HashMap<>();
    }

    /**
     * A static method called at the beginning of all Worlds in Mayrio to make them accessible through the WorldManager.
     * @param name What to name the World
     * @param world Class of the World to add
     */
    public static void register(String name, World world) {
        worlds.put(name, world);
    }

    /**
     * Get a World from the current list
     * @param name Name of World to get
     * @return The World, if it exists
     */
    public static World get(String name) {
        return worlds.get(name);
    }

    public WorldManager getInstance() {
        if (instance == null) {
            instance = new WorldManager();
        }
        return instance;
    }
}
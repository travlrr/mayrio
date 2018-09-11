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

import core.util.log.LogLevel;
import core.util.log.MayrioLogger;

import java.util.HashMap;

/**
 * The Grid simplifies the game window's coordinate system by reducing it to a 2D array of larger cells.
 */
public class Grid {
    private static final MayrioLogger logger;
    private static int width;
    private static int height;
    private static int cellSize;
    private static HashMap<int[], int[]> positions;
    private static Grid instance;

    static {
        positions = new HashMap<>();
        logger = new MayrioLogger(Grid.class);
    }

    private Grid(int width, int height, int cellSize) {
        Grid.width = width;
        Grid.height = height;
        Grid.cellSize = cellSize;


        if (width % cellSize != 0 | height % cellSize != 0) {
            logger.logf(LogLevel.FATAL, "Grid failed to initialize: width%cellSize = %d, height%cellSize = %d", width % cellSize, height % cellSize);
            throw new IllegalArgumentException("Grid failed to initialize; cellSize is invalid");
        }

        int cellCountX = width / cellSize;
        int cellCountY = height / cellSize;

        // TODO: Finish implementing Grid
    }

    public static Grid getGrid(int width, int height, int cellSize) {
        if (instance == null) {
            instance = new Grid(width, height, cellSize);
        }

        return instance;
    }

    public int[][] screenToGrid() {
        // TODO: Finish implementing Grid.screenToGrid()
        return new int[0][0];
    }

    public int[][] gridToScreen() {
        // TODO: Finish implementing Grid.gridToScreen()
        return new int[0][0];
    }
}

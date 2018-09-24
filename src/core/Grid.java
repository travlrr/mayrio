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

import actors.core.Coordinate;
import core.util.log.LogLevel;
import core.util.log.MayrioLogger;

import java.util.HashMap;

/**
 * The Grid simplifies the game window's coordinate system by reducing it to a 2D array of larger cells.
 */
public class Grid {
    private static final MayrioLogger logger;
    private static Grid instance;
    private static int width;
    private static int height;
    private static int cellSize;
    private static int cellCountX;
    private static int cellCountY;
    private static HashMap<Coordinate, Coordinate> gridToScreen;

    static {
        logger = new MayrioLogger(Grid.class);
    }

    private Grid(int width, int height, int cellSize) {
        Grid.width = width;
        Grid.height = height;
        Grid.cellSize = cellSize;
        Grid.cellCountX = Grid.width / cellSize;
        Grid.cellCountY = Grid.height / cellSize;
        Grid.gridToScreen = new HashMap<>();

        if (width % cellSize != 0 | height % cellSize != 0) {
            logger.logf(LogLevel.FATAL, "Grid failed to initialize: width%cellSize = %d, height%cellSize = %d", width % cellSize, height % cellSize);
            throw new IllegalArgumentException("Grid failed to initialize; cellSize is invalid");
        }


        for (int y = 0; y < cellCountY; y++) {
            for (int x = 0; x < cellCountX; x++) {
                Coordinate gridCoords = new Coordinate(x, y);
                Coordinate screenCoords = new Coordinate(x * cellSize, y * cellSize);

                gridToScreen.put(gridCoords, screenCoords);
            }
        }
    }

    public static Grid set(int width, int height, int cellSize) {
        if (instance == null) {
            instance = new Grid(width, height, cellSize);
        }

        return instance;
    }

    public static Coordinate toScreen(int x, int y) {
        Coordinate coord = new Coordinate(x, y);
        return gridToScreen.get(coord);
    }

    public static int getCellsX() {
        return cellCountX;
    }

    public static int getCellsY() {
        return cellCountY;
    }
}
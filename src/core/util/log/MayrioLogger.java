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

package core.util.log;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Mayrio's logging framework. Provides several log levels to allow filtering of log output.
 * The current log level is shared across all instances of MayrioLogger, always defaulting to LogLevel.ERROR.
 * MayrioLogger instances SHOULD NOT be shared between classes.
 */
public class MayrioLogger {
    private static LogLevel level = LogLevel.ERROR;
    private Object owner;
    private PrintStream output;

    /**
     * Constructs a new MayrioLogger.
     * The output stream defaults to System.out.
     *
     * @param owner The owner of this logger. This should be the class that constructed the logger.
     */
    public MayrioLogger(Object owner) {
        this.owner = owner;
        this.output = System.out;
    }

    /**
     * Constructs a new MayrioLogger, outputting to the given stream.
     *
     * @param owner  The owner of this logger. This should be the class that constructed the logger.
     * @param output The stream that this logger should output to.
     */
    public MayrioLogger(Object owner, PrintStream output) {
        this.owner = owner;
        this.output = output;
    }

    /**
     * Returns the current log level of this logger.
     *
     * @return Current log level of this logger
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * Sets the log level of this logger.
     *
     * @param level Log level to change to
     */
    public static void setLevel(LogLevel level) {
        MayrioLogger.level = level;
    }

    /**
     * Log an event.
     *
     * @param level The log level to log the message at. This CANNOT be LogLevel.ALL or LogLevel.NONE!
     * @param o     The object to log. This should usually be a string.
     */
    public void log(LogLevel level, Object o) {

        // Check validity of LogLevel
        if (level.equals(LogLevel.ALL) | level.equals(LogLevel.NONE)) {
            throw new IllegalArgumentException(String.format("An instance of %s tried to use an invalid log level!", owner.getClass().getName()));
        }

        // Make sure the message should be logged given the current LogLevel, otherwise ignore it
        if (!level.isAllowedAtLevel(MayrioLogger.level)) {
            return;
        }

        // Get current LocalDateTime and set up date formatters
        LocalDateTime time = LocalDateTime.now();

        /*
         * Log the event!
         * Output is formatted as "[DATE, TIMESTAMP] LOGLEVEL: CALLER MESSAGE".
         */
        output.println(
                String.format("[%s, %s] %s: %s %s",
                        time.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                        time.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        level.name(),
                        owner.getClass().getName(),
                        o.toString()
                )
        );
    }
}

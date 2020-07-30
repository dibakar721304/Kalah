package com.dibakar.kalah.configuration;

/**
 * Utility class which holds basic game parameters.
 *
 * @author divakar721304@gmail.com
 */
public class GameSettings {

    public static final int INITIAL_STONES_QUANTITY = 6;

    public static final int FIRST_PIT_INDEX = 1;

    public static final int LAST_PIT_INDEX = 14;

    private GameSettings() {
        throw new AssertionError("Current class can not be instantiated");
    }
}

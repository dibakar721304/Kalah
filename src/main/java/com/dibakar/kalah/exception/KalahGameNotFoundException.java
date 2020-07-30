package com.dibakar.kalah.exception;

/**
 * Thrown if the game is not existing in database.
 * @author divakar721304@gmail.com
 */
public class KalahGameNotFoundException extends RuntimeException {

    public KalahGameNotFoundException(String message) {
        super(message);
    }
}

package com.dibakar.kalah.exception;

/**
 * Thrown if pit number is invalid.
 *
 * @author divakar721304@gmail.com
 */
public class IllegalPitNumberException extends RuntimeException {

    public IllegalPitNumberException(String message) {
        super(message);
    }
}

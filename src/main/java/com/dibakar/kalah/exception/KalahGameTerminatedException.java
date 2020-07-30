package com.dibakar.kalah.exception;

import com.dibakar.kalah.domain.enums.KalahStatus;

/**
 * Thrown if the game is already terminated
 *
 * @author divakar721304@gmail.com
 */
public class KalahGameTerminatedException extends RuntimeException {

    private final KalahStatus status;

    public KalahGameTerminatedException(String message, KalahStatus status) {
        super(message);
        this.status = status;
    }

    public KalahStatus getStatus() {
        return status;
    }
}

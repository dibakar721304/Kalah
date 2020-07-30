package com.dibakar.kalah.controller;

import com.dibakar.kalah.dto.KalahExceptionDTO;
import com.dibakar.kalah.exception.KalahGameNotFoundException;
import com.dibakar.kalah.exception.KalahGameTerminatedException;
import com.dibakar.kalah.exception.IllegalPitNumberException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Converts exceptions to Http statuses.
 * {@link HttpStatus}
 * {@link ExceptionDTO} represents error.
 *
 * @author divakar721304@gmail.com
 */

@RestControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(KalahGameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public KalahExceptionDTO handleNotFound(Exception ex) {
        return new KalahExceptionDTO(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalPitNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public KalahExceptionDTO handleBadRequest(Exception ex) {
        return new KalahExceptionDTO(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(KalahGameTerminatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public KalahExceptionDTO handleConflict(KalahGameTerminatedException ex) {
        return new KalahExceptionDTO(ex.getMessage(), HttpStatus.CONFLICT, ex.getStatus());
    }
}

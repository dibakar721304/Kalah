package com.dibakar.kalah.dto;

import org.springframework.http.HttpStatus;

import com.dibakar.kalah.domain.enums.KalahStatus;

/**
 * @author divakar721304@gmail.com
 */
public class KalahExceptionDTO {

    private String message;

    private HttpStatus httpStatus;

    private KalahStatus gameStatus;

    public KalahExceptionDTO() { }

    public KalahExceptionDTO(String message, HttpStatus status) {
        this(message, status, null);
    }

    public KalahExceptionDTO(String message, HttpStatus httpStatus, KalahStatus gameStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.gameStatus = gameStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public KalahStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(KalahStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}

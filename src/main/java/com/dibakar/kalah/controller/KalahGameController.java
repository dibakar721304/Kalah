package com.dibakar.kalah.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dibakar.kalah.dto.KalahGameDTO;
import com.dibakar.kalah.dto.NewKalahGameDTO;
import com.dibakar.kalah.service.GameService;

/**
 * The controller class which directs the requests to service layers.
 * @author divakar721304@gmail.com
 *
 */



@RestController
@RequestMapping("/games")
public class KalahGameController {

    private final GameService service;

    public KalahGameController(GameService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewKalahGameDTO createNewGame() {
        return service.createNewGame();
    }

    @PutMapping("/{gameId}/pits/{pitId}")
    public KalahGameDTO makeMove(@PathVariable int gameId,
                            @PathVariable int pitId) {

        return service.makeMove(gameId, pitId);
    }
}

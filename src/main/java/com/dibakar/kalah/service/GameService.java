package com.dibakar.kalah.service;

import com.dibakar.kalah.dto.KalahGameDTO;
import com.dibakar.kalah.dto.NewKalahGameDTO;

/**
 * Represents business logic
 * for API endpoints.
 *
 * @author divakar721304@gmail.com
 */
public interface GameService {

    /**
     * Creates new Kalah game.
     *
     * @return new game
     */
	NewKalahGameDTO createNewGame();

    /**
     * Makes a move of current associated player.
     *
     * @param gameId unique identifier of a game
     * @param pitId  id of the pit selected to make a move
     * @return modified game
     * @throws com.dibakar.kalah.exception.GameTerminatedException
     *         if the game has been already terminated.
     *
     * @throws com.dibakar.kalah.exception.GameNotFoundException
     *         if provided game identifier is not associated with
     *         any game in database.
     *
     */
	KalahGameDTO makeMove(int gameId, int pitId);
}

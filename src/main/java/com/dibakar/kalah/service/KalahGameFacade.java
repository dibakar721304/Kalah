package com.dibakar.kalah.service;

import com.dibakar.kalah.domain.KalahGame;
import com.dibakar.kalah.domain.enums.KalahPlayer;
import com.dibakar.kalah.domain.enums.KalahStatus;
import com.dibakar.kalah.exception.IllegalPitNumberException;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

import static com.dibakar.kalah.configuration.GameSettings.*;
import static com.dibakar.kalah.domain.enums.KalahPlayer.FIRST_PLAYER;
import static com.dibakar.kalah.domain.enums.KalahPlayer.SECOND_PLAYER;

/**
 * Business logic starts in this class
 *
 * @author divakar721304@gmail.com
 */

@Service
public class KalahGameFacade {

    /**
     * Makes a move of current associated player.
     * Aggregates all necessary operations.
     *
     * @param game
     * @param pitId id of the pit selected to make a move
     */
    public void makeMove(KalahGame game, int pitId) {
        validatePitNumber(pitId, game);

        Map<Integer, Integer> board = game.getBoard();
        int amount = board.get(pitId);
        int lastIndex = pitId + amount;
        clearPit(pitId, game.getBoard());

        int lastPit = lastIndex;
        for (int currentIndex = pitId + 1; currentIndex <= lastIndex; currentIndex++) {
            int currentPit = currentIndex;
            if (currentIndex == LAST_PIT_INDEX && lastIndex != LAST_PIT_INDEX) {
                lastIndex = lastIndex - currentIndex;
                currentIndex = 0;
            }
            if (game.getPlayer().getOppositePlayer().getKalahId() != currentPit) {
                addStonesToPit(currentPit, board, 1);
            } else {
                if (currentIndex != LAST_PIT_INDEX) {
                    lastIndex++;
                }else {
                    lastIndex = FIRST_PIT_INDEX;
                    currentIndex = 0;
                }
            }
        }
        lastPit = lastPit > LAST_PIT_INDEX ? lastIndex : lastPit;
        checkLastPit(lastPit, game);

        if (!playerHasAnotherTurn(lastPit, game.getPlayer())) {
            game.setPlayer(game.getPlayer().getOppositePlayer());
        }
        if (gameIsTerminated(game)) {
        	KalahStatus winner = findTheWinner(game);
            game.setStatus(winner);
        }
    }

    /**
     * Checks pit where the last stone landed. If the pit is own empty pit,
     * captures this stone and all stones in the opposite pit and puts them
     * in own Kalah.
     *
     * @param lastPit
     * @param game
     */
    private void checkLastPit(int lastPit, KalahGame game) {
        if (lastPitWasOwnEmptyPit(lastPit, game)) {
            int oppositePit = getOppositePit(lastPit);
            int oppositePitAmount = game.getBoard().get(oppositePit);
            if (oppositePitAmount != 0) {
                clearPit(oppositePit, game.getBoard());
                clearPit(lastPit, game.getBoard());
                addStonesToPit(game.getPlayer().getKalahId(), game.getBoard(), oppositePitAmount + 1);
            }
        }
    }

    /**
     * If the game is terminated adds all remained stones
     * to Kalah of each player.
     *
     * @param game
     * @return true if the game is terminated.
     */
    private boolean gameIsTerminated(KalahGame game) {
    	KalahPlayer player = game.getPlayer();
        List<Integer> pits = player.getPits();
        Map<Integer, Integer> board = game.getBoard();

        boolean playerPitsAreEmpty = pits.stream()
                .map(board::get)
                .allMatch(stoneNumbers -> stoneNumbers == 0);

        boolean oppositePlayerPitsAreEmpty = player.getOppositePlayer().getPits().stream()
                .map(board::get)
                .allMatch(stoneNumbers -> stoneNumbers == 0);

        if (playerPitsAreEmpty || oppositePlayerPitsAreEmpty) {
            addAllRemainedStonesToKalah(player, board);
            addAllRemainedStonesToKalah(player.getOppositePlayer(), board);
            return true;
        }
        return false;
    }

    private void addAllRemainedStonesToKalah(KalahPlayer player, Map<Integer, Integer> board ) {
        player.getPits().forEach(pit -> {
            int amount = board.get(pit);
            if (amount != 0) {
                int kalahId = player.getKalahId();
                board.replace(kalahId, board.get(kalahId) + amount);
                clearPit(pit, board);
            }
        });
    }

    private KalahStatus findTheWinner(KalahGame game) {
        Map<Integer, Integer> board = game.getBoard();
        int firstPlayerStones = board.get(FIRST_PLAYER.getKalahId());
        int secondPlayerStones = board.get(SECOND_PLAYER.getKalahId());
        if (firstPlayerStones > secondPlayerStones) {
            return KalahStatus.FIRST_PLAYER_WIN;
        }else if (firstPlayerStones < secondPlayerStones) {
            return KalahStatus.SECOND_PLAYER_WIN;
        }else {
            return KalahStatus.DRAW;
        }
    }

    private void validatePitNumber(int pitId, KalahGame game) {
    	KalahPlayer player = game.getPlayer();
        if (pitId == player.getKalahId() || pitId == player.getOppositePlayer().getKalahId()) {
            throw new IllegalPitNumberException("You can not select Kalah!");
        }

        if (pitId < FIRST_PIT_INDEX || pitId > LAST_PIT_INDEX) {
            throw new IllegalPitNumberException("Provided pitId is out of bounds...");
        }

        if (!isUserPit(pitId, player)) {
            throw new IllegalPitNumberException("It is not your turn!");
        }
        if (game.getBoard().get(pitId) == 0) {
            throw new IllegalPitNumberException("You can not select empty pit!");
        }
    }

    private boolean lastPitWasOwnEmptyPit(int lastPitId, KalahGame game) {
        Map<Integer, Integer> board = game.getBoard();
        return board.get(lastPitId) == 1 && isUserPit(lastPitId, game.getPlayer());
    }

    private boolean isUserPit(int pitId, KalahPlayer player) {
        return player.getPits().contains(pitId);
    }

    private int getOppositePit(int pitId) {
        return LAST_PIT_INDEX - pitId;
    }

    private boolean playerHasAnotherTurn(int lastPitId, KalahPlayer player) {
        return player.getKalahId() == lastPitId;
    }

    private void addStonesToPit(int pitId, Map<Integer, Integer> board, int amount) {
        board.replace(pitId, board.get(pitId) + amount);
    }

    private void clearPit(int pitId, Map<Integer, Integer> board) {
        board.replace(pitId, 0);
    }
}
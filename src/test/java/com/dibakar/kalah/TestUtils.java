package com.dibakar.kalah;

import static com.dibakar.kalah.configuration.GameSettings.FIRST_PIT_INDEX;
import static com.dibakar.kalah.configuration.GameSettings.LAST_PIT_INDEX;

import java.util.Map;
import java.util.stream.IntStream;

import com.dibakar.kalah.domain.enums.KalahPlayer;

/**
 * @author divakar721304@gmail.com
 */
public class TestUtils {

    private TestUtils() { }

    public static void fillBoard(int firstKalahStones, int secondKalahStones, int pitStones, Map<Integer, Integer> board) {
        IntStream.range(FIRST_PIT_INDEX, LAST_PIT_INDEX + 1)
                .forEach(pit -> {
                    int amount;
                    if (pit == KalahPlayer.FIRST_PLAYER.getKalahId()) {
                        amount = firstKalahStones;
                    } else if (pit == KalahPlayer.SECOND_PLAYER.getKalahId()) {
                        amount = secondKalahStones;

                    } else amount = pitStones;
                    board.put(pit, amount);
                });
    }

    public static void prepareBoardForResult(int firstKalahStones, int secondKalahStones, Map<Integer, Integer> board) {
        fillBoard(firstKalahStones, secondKalahStones, 0, board);
    }
}

package com.dibakar.kalah.domain.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enum to store player properties
 * @author divakar721304@gmail.com
 *
 */
public enum KalahPlayer {

    FIRST_PLAYER(7, Arrays.asList(1, 2, 3, 4, 5, 6)),

    SECOND_PLAYER(14, Arrays.asList(8, 9, 10, 11, 12, 13));

    private final int kalahId;

    private final List<Integer> pits;

    KalahPlayer(int kalahId, List<Integer> pits) {
        this.kalahId = kalahId;
        this.pits = Collections.unmodifiableList(pits);
    }

    public int getKalahId() {
        return kalahId;
    }

    public List<Integer> getPits() {
        return pits;
    }

    public KalahPlayer getOppositePlayer() {
        return this == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
    }
}

package com.dibakar.kalah.domain;

import javax.persistence.*;

import com.dibakar.kalah.domain.enums.KalahPlayer;
import com.dibakar.kalah.domain.enums.KalahStatus;

import static com.dibakar.kalah.configuration.GameSettings.*;

import java.util.HashMap;
import java.util.Map;

/** The game wizard class
 * @author divakar721304@gmail.com
 */

@Entity
@Table(name = "games")
public class KalahGame {

    @Id
    @GeneratedValue
    private int id;

    @ElementCollection
    @MapKeyColumn(name="pitId")
    @Column(name="value")
    private Map<Integer, Integer> board;

    @Enumerated(value = EnumType.STRING)
    private KalahStatus status;

    @Enumerated(value = EnumType.STRING)
    private KalahPlayer player;

    public KalahGame() {
        initializeBoard();
        status = KalahStatus.IN_PROGRESS;
        player = KalahPlayer.FIRST_PLAYER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getBoard() {
        return board;
    }

    public void setBoard(Map<Integer, Integer> board) {
        this.board = board;
    }

    public KalahStatus getStatus() {
        return status;
    }

    public void setStatus(KalahStatus status) {
        this.status = status;
    }

    public KalahPlayer getPlayer() {
        return player;
    }

    public void setPlayer(KalahPlayer player) {
        this.player = player;
    }

    private void initializeBoard() {
        board = new HashMap<>();
        for (int i = FIRST_PIT_INDEX; i <= LAST_PIT_INDEX; i++) {
            int firstKhalIndex = KalahPlayer.FIRST_PLAYER.getKalahId();
            int secondKhalIndex = KalahPlayer.SECOND_PLAYER.getKalahId();
            int value = (i != firstKhalIndex && i != secondKhalIndex) ? INITIAL_STONES_QUANTITY : 0;
            board.put(i, value);
        }
    }
}

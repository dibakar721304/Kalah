package com.dibakar.kalah.service;

import static com.dibakar.kalah.TestUtils.fillBoard;
import static com.dibakar.kalah.TestUtils.prepareBoardForResult;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.dibakar.kalah.domain.KalahGame;
import com.dibakar.kalah.domain.enums.KalahPlayer;
import com.dibakar.kalah.domain.enums.KalahStatus;

/**
 * @author divakar721304@gmail.com
 */
public class KalahGameFacadeTest {

    private final KalahGameFacade facade = new KalahGameFacade();
    private Class<? extends KalahGameFacade> clazz = facade.getClass();

    private KalahGame game;
    private Map<Integer, Integer> board;

    @Before
    public void init() {
        game = new KalahGame();
        board = game.getBoard();
    }

    @Test
    public void testClearPit() throws Exception {
        Method clearPit = clazz.getDeclaredMethod("clearPit", int.class, Map.class);
        openMethodForTest(clearPit);
        clearPit.invoke(facade, 3, board);
        assertEquals(0, board.get(3).intValue());

    }

    @Test
    public void testAddStonesToPit() throws Exception {
        Method addStonesToPit = clazz.getDeclaredMethod("addStonesToPit", int.class, Map.class, int.class);
        openMethodForTest(addStonesToPit);
        addStonesToPit.invoke(facade, 1, board, 8);
        assertEquals(14, board.get(1).intValue());
    }

    @Test
    public void testPlayerHasAnotherTurn() throws Exception {
        Method playerHasAnotherTurn = clazz.getDeclaredMethod("playerHasAnotherTurn", int.class, KalahPlayer.class);
        openMethodForTest(playerHasAnotherTurn);
        boolean first = (boolean) playerHasAnotherTurn.invoke(facade, 7, KalahPlayer.FIRST_PLAYER);
        boolean second = (boolean) playerHasAnotherTurn.invoke(facade, 14, KalahPlayer.SECOND_PLAYER);
        boolean invalid = (boolean) playerHasAnotherTurn.invoke(facade, 12, KalahPlayer.SECOND_PLAYER);
        assertTrue(first);
        assertTrue(second);
        assertFalse(invalid);
    }

    @Test
    public void testGetOppositePit() throws Exception {
        Method getOppositePit = clazz.getDeclaredMethod("getOppositePit", int.class);
        openMethodForTest(getOppositePit);
        int opposite = (int) getOppositePit.invoke(facade, 8);
        assertEquals(6, opposite);
    }

    @Test
    public void testIsUserPit() throws Exception {
        Method isUserPit = clazz.getDeclaredMethod("isUserPit", int.class, KalahPlayer.class);
        openMethodForTest(isUserPit);
        for (Integer pit : KalahPlayer.FIRST_PLAYER.getPits()) {
            boolean result = (boolean) isUserPit.invoke(facade, pit, KalahPlayer.FIRST_PLAYER);
            assertTrue(result);
        }

        for (Integer pit : KalahPlayer.SECOND_PLAYER.getPits()) {
            boolean result = (boolean) isUserPit.invoke(facade, pit, KalahPlayer.SECOND_PLAYER);
            assertTrue(result);
        }
        boolean result = (boolean) isUserPit.invoke(facade, 3, KalahPlayer.SECOND_PLAYER);
        assertFalse(result);
    }

    @Test
    public void testLastPitWasOwnEmptyPit() throws Exception {
        Method lastPitWasOwnEmptyPit = clazz.getDeclaredMethod("lastPitWasOwnEmptyPit", int.class, KalahGame.class);
        openMethodForTest(lastPitWasOwnEmptyPit);
        board.replace(3, 1);
        boolean trueResult = (boolean) lastPitWasOwnEmptyPit.invoke(facade, 3, game);
        assertTrue(trueResult);

        board.replace(8, 1);
        boolean falseResult = (boolean) lastPitWasOwnEmptyPit.invoke(facade, 8, game);
        assertFalse(falseResult);
    }

    @Test
    public void testFindTheWinner() throws Exception {
        Method findTheWinner = clazz.getDeclaredMethod("findTheWinner", KalahGame.class);
        openMethodForTest(findTheWinner);

        prepareBoardForResult(23, 49, board);
        KalahStatus firstWinner = (KalahStatus) findTheWinner.invoke(facade, game);
        assertEquals(KalahStatus.SECOND_PLAYER_WIN, firstWinner);

        prepareBoardForResult(49, 23, board);
        KalahStatus secondWinner = (KalahStatus) findTheWinner.invoke(facade, game);
        assertEquals(KalahStatus.FIRST_PLAYER_WIN, secondWinner);

        prepareBoardForResult(36, 36, board);
        KalahStatus draw = (KalahStatus) findTheWinner.invoke(facade, game);
        assertEquals(KalahStatus.DRAW, draw);
    }

    @Test
    public void testAddAllRemainedStonesToKalah() throws Exception {
        Method addAllRemainedStonesToKalah = clazz.getDeclaredMethod("addAllRemainedStonesToKalah", KalahPlayer.class, Map.class);
        openMethodForTest(addAllRemainedStonesToKalah);

        fillBoard(0, 0, 3, board);
        addAllRemainedStonesToKalah.invoke(facade, KalahPlayer.FIRST_PLAYER, board);
        Integer amount = board.get(KalahPlayer.FIRST_PLAYER.getKalahId());
        assertEquals(18, amount.intValue());
    }

    @Test
    public void testGameIsTerminated() throws Exception {
        Method gameIsTerminated = clazz.getDeclaredMethod("gameIsTerminated", KalahGame.class);
        openMethodForTest(gameIsTerminated);

        boolean falseResult = (boolean) gameIsTerminated.invoke(facade, game);
        assertFalse(falseResult);

        prepareBoardForResult(36, 36, board);
        boolean trueResult = (boolean) gameIsTerminated.invoke(facade, game);
        assertTrue(trueResult);
    }

    @Test
    public void testCheckLastPit() throws Exception {
        Method checkLastPit = clazz.getDeclaredMethod("checkLastPit", int.class, KalahGame.class);
        openMethodForTest(checkLastPit);

        board.replace(3, 1);
        checkLastPit.invoke(facade, 3, game);

        int pit = board.get(3);
        int oppositePit = board.get(11);
        int kalahAmount = board.get(KalahPlayer.FIRST_PLAYER.getKalahId());

        assertEquals(0, pit);
        assertEquals(0, oppositePit);
        assertEquals(7, kalahAmount);
    }

    @Test
    public void testMakeMove() {
        int pitId = 3;
        int pitAmount = board.get(pitId);
        Map<Integer, Integer> beforeMove = new HashMap<>(board);
        facade.makeMove(game, pitId);

        IntStream.range(pitId + 1, pitAmount + 1).forEach(pit -> {
            int amount = board.get(pit);
            assertEquals(amount, beforeMove.get(pit) + 1);
        });
    }

    private void openMethodForTest(Method method) {
        ReflectionUtils.makeAccessible(method);
    }
}

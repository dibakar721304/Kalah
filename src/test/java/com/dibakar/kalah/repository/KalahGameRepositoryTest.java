package com.dibakar.kalah.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dibakar.kalah.domain.KalahGame;
import com.dibakar.kalah.domain.enums.KalahPlayer;
import com.dibakar.kalah.domain.enums.KalahStatus;

/**
 *@author divakar721304@gmail.com
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class KalahGameRepositoryTest {

    @Autowired
    private KalahGameRepository repository;

    @Test
    public void testSaveGame() {
    	KalahGame game = new KalahGame();
    	KalahGame created = repository.save(game);

        assertNotNull(created);
        assertEquals(1, created.getId());
        assertEquals(KalahStatus.IN_PROGRESS, created.getStatus());
        assertEquals(KalahPlayer.FIRST_PLAYER, created.getPlayer());
    }
}

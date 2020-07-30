package com.dibakar.kalah.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.dibakar.kalah.domain.KalahGame;
import com.dibakar.kalah.dto.KalahGameDTO;
import com.dibakar.kalah.dto.NewKalahGameDTO;
import com.dibakar.kalah.mapper.KalahGameMapper;
import com.dibakar.kalah.repository.KalahGameRepository;

/**
 * @author divakar721304@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class KalahGameServiceTest {

    private static final String URL = "http://host:port/games/1";

    @Mock
    private KalahGameRepository repository;

    @Mock
    private KalahGameFacade facade;

    @Mock
    private KalahGameMapper mapper;

    private GameService service;

    @Before
    public void init() {
        service = new GameServiceImpl(repository, mapper, facade);
    }

    @Test
    public void testCreateGame() {
        KalahGame created = new KalahGame();
        created.setId(1);
        when(repository.save(any(KalahGame.class))).thenReturn(created);
        when(mapper.toNewDTO(created)).thenReturn(new NewKalahGameDTO(created.getId(), URL));

        NewKalahGameDTO newGame = service.createNewGame();
        assertEquals(1, newGame.getId());
        assertEquals(URL, newGame.getUri());
    }

    @Test
    public void testMakeMove() {
        KalahGame game = new KalahGame();
        game.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(game));
        when(repository.save(game)).thenReturn(game);
        when(mapper.toDTO(game)).thenReturn(new KalahGameDTO(game.getId(), URL, game.getBoard()));

        KalahGameDTO gameDTO = service.makeMove(1, 3);

        verify(facade).makeMove(game, 3);
        assertEquals(1, gameDTO.getId());
        assertEquals(URL, gameDTO.getUrl());
        MatcherAssert.assertThat(gameDTO.getStatus(), CoreMatchers.is(game.getBoard()));
    }
}

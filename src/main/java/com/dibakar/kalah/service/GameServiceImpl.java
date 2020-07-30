package com.dibakar.kalah.service; 


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dibakar.kalah.domain.KalahGame;
import com.dibakar.kalah.domain.enums.KalahStatus;
import com.dibakar.kalah.dto.KalahGameDTO;
import com.dibakar.kalah.dto.NewKalahGameDTO;
import com.dibakar.kalah.exception.KalahGameNotFoundException;
import com.dibakar.kalah.exception.KalahGameTerminatedException;
import com.dibakar.kalah.mapper.KalahGameMapper;
import com.dibakar.kalah.repository.KalahGameRepository;




@Service
public class GameServiceImpl implements GameService { 

    private final KalahGameRepository repository;
    private final KalahGameMapper mapper;
    private final KalahGameFacade facade;

    public GameServiceImpl(KalahGameRepository repository, KalahGameMapper mapper, KalahGameFacade facade) {
        this.repository = repository;
        this.mapper = mapper;
        this.facade = facade;
    }

    @Override
    @Transactional
    public NewKalahGameDTO createNewGame() {
    	KalahGame created = repository.save(new KalahGame());
        return mapper.toNewDTO(created);
    }

    @Override
    @Transactional
    public KalahGameDTO makeMove(int gameId, int pitId) {
    	KalahGame game = repository.findById(gameId).orElseThrow(
                () -> new KalahGameNotFoundException("Game with id: " + gameId + " not found."));
        checkGameStatus(game);
        facade.makeMove(game, pitId);
        KalahGame afterMove = repository.save(game);
        return mapper.toDTO(afterMove);
    }

    private void checkGameStatus(KalahGame game) { 
        KalahStatus status = game.getStatus();
        if (status != KalahStatus.IN_PROGRESS) {
            throw new KalahGameTerminatedException("Game has been already terminated ", status);
        }
    }
}

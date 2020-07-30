package com.dibakar.kalah.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dibakar.kalah.domain.KalahGame;

/**
 * Data access layer.
 *
 *
 */
@Repository
public interface KalahGameRepository extends CrudRepository<KalahGame, Integer> { }

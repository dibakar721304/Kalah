package com.dibakar.kalah.mapper;

import com.dibakar.kalah.domain.KalahGame;
import com.dibakar.kalah.dto.KalahGameDTO;
import com.dibakar.kalah.dto.NewKalahGameDTO;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;
import java.util.Map;

/**
 * Converts Entity to data transfer object.
 *
 * @author divakar721304@gmail.com
 */

@Component
public class KalahGameMapper {

    private String gameUrl;

    private final Environment environment;

    public KalahGameMapper(Environment environment) {
        this.environment = environment;
        this.gameUrl = environment.getActiveProfiles()[0].equals("prod")
                ? environment.getProperty("prod.game.url") : environment.getProperty("dev.game.url");
    }

    public KalahGameDTO toDTO(KalahGame game) {
        int id = game.getId();
        Map<Integer, Integer> status = game.getBoard();
        String url = generateGameUrl(id);
        return new KalahGameDTO(id, url, status);
    }

    public NewKalahGameDTO toNewDTO(KalahGame game) {
        int id = game.getId();
        String url = generateGameUrl(id);
        return new NewKalahGameDTO(id, url);
    }

    private String generateGameUrl(int gameId) {
        return new UriTemplate(gameUrl).expand(gameId).toString();
    }
}

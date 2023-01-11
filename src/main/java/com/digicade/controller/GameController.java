package com.digicade.controller;

import com.digicade.service.GameService;
import com.digicade.service.dto.GameDTO;
import com.digicade.web.rest.GameResource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/all-games")
public class GameController {

    private final Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<GameDTO>> findAllGames() {
        List<GameDTO> allGames = gameService.findAllGames();

        log.debug("REST request to all Games {}", allGames);

        return new ResponseEntity<>(allGames, HttpStatus.OK);
    }
}

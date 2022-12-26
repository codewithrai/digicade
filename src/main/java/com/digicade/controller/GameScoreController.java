package com.digicade.controller;

import com.digicade.service.GameScoreService;
import com.digicade.service.dto.GameScoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GameScoreController {

    @Autowired
    private GameScoreService gameScoreService;

    @PostMapping("/save-game-scores")
    public ResponseEntity<GameScoreDTO> saveGameScores(@RequestBody GameScoreDTO gameScoreDTO) {
        GameScoreDTO save = gameScoreService.save(gameScoreDTO);

        return new ResponseEntity<>(save, HttpStatus.OK);
    }
}
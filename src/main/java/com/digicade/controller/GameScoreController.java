package com.digicade.controller;

import com.digicade.domain.GameScore;
import com.digicade.domain.HighScore;
import com.digicade.repository.HighScoreRepository;
import com.digicade.service.GameScoreService;
import com.digicade.service.HighScoreService;
import com.digicade.service.dto.GameScoreDTO;
import com.digicade.service.dto.UserGameHighScoresDTO;
import com.digicade.service.dto.UserGameStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameScoreController {

    @Autowired
    private HighScoreService highScoreService;
    @Autowired
    private GameScoreService gameScoreService;

    @GetMapping("/user-highest-scores/{playerId}")
    public ResponseEntity<List<UserGameHighScoresDTO>> getUserGameHighestScoresByPlayerId(@PathVariable Long playerId) {
        List<UserGameHighScoresDTO> all = highScoreService.getUserGameHighestScoresByPlayerId(playerId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/user-game-stats/{playerId}")
    public ResponseEntity<List<UserGameStatsDTO>> getUserGameStatsByPlayerId(@PathVariable Long playerId) {
        List<UserGameStatsDTO> statsByPlayerId = gameScoreService.getUserGameStatsByPlayerId(playerId);

        return new ResponseEntity<>(statsByPlayerId, HttpStatus.OK);
    }
}

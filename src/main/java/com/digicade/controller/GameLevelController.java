package com.digicade.controller;

import com.digicade.service.GameLevelService;
import com.digicade.service.dto.GameLevelDTO;
import com.digicade.service.dto.LeaderBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameLevelController {
    @Autowired
    private GameLevelService gameLevelService;

    @GetMapping("/leader-board/{id}/{filter}")
    public ResponseEntity<LeaderBoard> getLeaderBoardByGameId(@PathVariable Long id, @PathVariable String filter) {
        LeaderBoard leaderBoard = gameLevelService.getLeaderBoardByGameId(id, filter);

        return new ResponseEntity<>(leaderBoard, HttpStatus.OK);
    }
}

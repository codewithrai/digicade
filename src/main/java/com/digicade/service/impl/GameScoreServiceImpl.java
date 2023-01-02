package com.digicade.service.impl;

import com.digicade.domain.GameScore;
import com.digicade.domain.HighScore;
import com.digicade.repository.GameScoreRepository;
import com.digicade.repository.HighScoreRepository;
import com.digicade.service.GameLevelService;
import com.digicade.service.GameScoreService;
import com.digicade.service.dto.GameLevelDTO;
import com.digicade.service.dto.GameScoreDTO;
import com.digicade.service.dto.UserGameStatsDTO;
import com.digicade.service.mapper.GameScoreMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GameScore}.
 */
@Service
@Transactional
public class GameScoreServiceImpl implements GameScoreService {

    private final Logger log = LoggerFactory.getLogger(GameScoreServiceImpl.class);

    private final GameScoreRepository gameScoreRepository;

    private final GameScoreMapper gameScoreMapper;
    @Autowired
    private HighScoreRepository highScoreRepository;
    @Autowired
    private GameLevelService gameLevelService;

    public GameScoreServiceImpl(GameScoreRepository gameScoreRepository, GameScoreMapper gameScoreMapper) {
        this.gameScoreRepository = gameScoreRepository;
        this.gameScoreMapper = gameScoreMapper;
    }

    @Override
    public GameScoreDTO save(GameScoreDTO gameScoreDTO) {
        log.debug("Request to save GameScore : {}", gameScoreDTO);
        GameScore gameScore = gameScoreMapper.toEntity(gameScoreDTO);

        Optional<HighScore> highScoreOptional = highScoreRepository.
            findByGameIdAndPlayerId(gameScoreDTO.getGame().getId(), gameScoreDTO.getPlayer().getId());


        // save high score
        if (highScoreOptional.isPresent()) {
            HighScore highScore = highScoreOptional.get();
            if (gameScoreDTO.getScore() > highScore.getHighestScore()) {
                highScore.setHighestScore(gameScoreDTO.getScore());
                HighScore save = highScoreRepository.save(highScore);
            }
        } else {
            HighScore highScore = new HighScore();
            highScore.setGame(gameScore.getGame());
            highScore.setPlayer(gameScore.getPlayer());
            highScore.setHighestScore(gameScore.getScore());
            HighScore save = highScoreRepository.save(highScore);
        }

        // save game level
        GameLevelDTO gameLevelDTO = new GameLevelDTO();
        gameLevelDTO.setGame(gameScoreDTO.getGame());
        gameLevelDTO.setPlayer(gameScoreDTO.getPlayer());
        gameLevelDTO.setScore(gameScore.getScore());
        gameLevelService.save(gameLevelDTO);


        gameScore.setDate(LocalDate.now());

        gameScore = gameScoreRepository.save(gameScore);
        return gameScoreMapper.toDto(gameScore);
    }

    @Override
    public GameScoreDTO update(GameScoreDTO gameScoreDTO) {
        log.debug("Request to update GameScore : {}", gameScoreDTO);
        GameScore gameScore = gameScoreMapper.toEntity(gameScoreDTO);
        gameScore = gameScoreRepository.save(gameScore);
        return gameScoreMapper.toDto(gameScore);
    }

    @Override
    public Optional<GameScoreDTO> partialUpdate(GameScoreDTO gameScoreDTO) {
        log.debug("Request to partially update GameScore : {}", gameScoreDTO);

        return gameScoreRepository
            .findById(gameScoreDTO.getId())
            .map(existingGameScore -> {
                gameScoreMapper.partialUpdate(existingGameScore, gameScoreDTO);

                return existingGameScore;
            })
            .map(gameScoreRepository::save)
            .map(gameScoreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameScoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GameScores");
        return gameScoreRepository.findAll(pageable).map(gameScoreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GameScoreDTO> findOne(Long id) {
        log.debug("Request to get GameScore : {}", id);
        return gameScoreRepository.findById(id).map(gameScoreMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameScore : {}", id);
        gameScoreRepository.deleteById(id);
    }

    @Override
    public List<UserGameStatsDTO> getUserGameStatsByPlayerId(Long playerId) {
        List<UserGameStatsDTO> gameStatsDTOS = gameScoreRepository.findGameScoreByPlayerIdOrderByScoreDesc(playerId);
        return gameStatsDTOS;
    }
}

package com.digicade.service.impl;

import com.digicade.domain.GameLevel;
import com.digicade.domain.Player;
import com.digicade.repository.GameLevelRepository;
import com.digicade.repository.PlayerRepository;
import com.digicade.service.GameLevelService;
import com.digicade.service.dto.GameLevelDTO;
import com.digicade.service.dto.LeaderBoard;
import com.digicade.service.dto.LeaderBoardDTO;
import com.digicade.service.mapper.GameLevelMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Service Implementation for managing {@link GameLevel}.
 */
@Service
@Transactional
public class GameLevelServiceImpl implements GameLevelService {

    private final Logger log = LoggerFactory.getLogger(GameLevelServiceImpl.class);

    private final GameLevelRepository gameLevelRepository;

    private final GameLevelMapper gameLevelMapper;

    @Autowired
    private PlayerRepository playerRepository;

    public GameLevelServiceImpl(GameLevelRepository gameLevelRepository, GameLevelMapper gameLevelMapper) {
        this.gameLevelRepository = gameLevelRepository;
        this.gameLevelMapper = gameLevelMapper;
    }

    @Override
    public GameLevelDTO save(GameLevelDTO gameLevelDTO) {
        log.debug("Request to save GameLevel : {}", gameLevelDTO);

        Optional<GameLevel> optionalGameLevel = gameLevelRepository.
            findByGameIdAndPlayerId(gameLevelDTO.getGame().getId(), gameLevelDTO.getPlayer().getId());

        if (optionalGameLevel.isPresent()) {
            GameLevel gameLevel = optionalGameLevel.get();
            if (gameLevelDTO.getScore() > gameLevel.getScore()) {
                log.debug("Request to save GameLevel GameScore is Higher");
                gameLevelDTO.setId(gameLevel.getId());
            } else {
                return null;
            }
        }

        GameLevel gameLevel = gameLevelMapper.toEntity(gameLevelDTO);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        gameLevel.setCreatedAt(timestamp);
        gameLevel = gameLevelRepository.save(gameLevel);
        return gameLevelMapper.toDto(gameLevel);
    }

    @Override
    public GameLevelDTO update(GameLevelDTO gameLevelDTO) {
        log.debug("Request to update GameLevel : {}", gameLevelDTO);
        GameLevel gameLevel = gameLevelMapper.toEntity(gameLevelDTO);
        gameLevel = gameLevelRepository.save(gameLevel);
        return gameLevelMapper.toDto(gameLevel);
    }

    @Override
    public Optional<GameLevelDTO> partialUpdate(GameLevelDTO gameLevelDTO) {
        log.debug("Request to partially update GameLevel : {}", gameLevelDTO);

        return gameLevelRepository
            .findById(gameLevelDTO.getId())
            .map(existingGameLevel -> {
                gameLevelMapper.partialUpdate(existingGameLevel, gameLevelDTO);

                return existingGameLevel;
            })
            .map(gameLevelRepository::save)
            .map(gameLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GameLevels");
        return gameLevelRepository.findAll(pageable).map(gameLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GameLevelDTO> findOne(Long id) {
        log.debug("Request to get GameLevel : {}", id);
        return gameLevelRepository.findById(id).map(gameLevelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameLevel : {}", id);
        gameLevelRepository.deleteById(id);
    }

    @Override
    public LeaderBoard getLeaderBoardByGameId(Long id, String filter) {
        Optional<List<GameLevelDTO>> optionalList = null;

        if (filter.toUpperCase().equals("TODAY")) {
            Instant instant = Instant.now().minus(1, ChronoUnit.DAYS);
            Timestamp start = Timestamp.from(instant);
            Timestamp end = new Timestamp(System.currentTimeMillis());
            optionalList = gameLevelRepository
                .findByGameIdAndCreatedAtBetweenOrderByScoreDesc(id, start, end)
                .map(gameLevelMapper::toDto);
            log.debug("Date Start: {}", start);
            log.debug("Date End: {}", end);
        } else if (filter.toUpperCase().equals("WEEK")) {
            Instant instant = Instant.now().minus(7, ChronoUnit.DAYS);
            Timestamp start = Timestamp.from(instant);
            Timestamp end = new Timestamp(System.currentTimeMillis());
            optionalList = gameLevelRepository
                .findByGameIdAndCreatedAtBetweenOrderByScoreDesc(id, start, end)
                .map(gameLevelMapper::toDto);
        } else if (filter.toUpperCase().equals("MONTH")) {
            Instant instant = Instant.now().minus(30, ChronoUnit.DAYS);
            Timestamp start = Timestamp.from(instant);
            Timestamp end = new Timestamp(System.currentTimeMillis());
            optionalList = gameLevelRepository
                .findByGameIdAndCreatedAtBetweenOrderByScoreDesc(id, start, end)
                .map(gameLevelMapper::toDto);
        } else if (filter.toUpperCase().equals("YEAR")) {
            Instant instant = Instant.now().minus(365, ChronoUnit.DAYS);
            Timestamp start = Timestamp.from(instant);
            Timestamp end = new Timestamp(System.currentTimeMillis());
            optionalList = gameLevelRepository
                .findByGameIdAndCreatedAtBetweenOrderByScoreDesc(id, start, end)
                .map(gameLevelMapper::toDto);
        } else if (filter.toUpperCase().equals("ALL")) {
            optionalList = gameLevelRepository.findByGameIdOrderByScoreDesc(id).map(gameLevelMapper::toDto);
        }
        List<GameLevelDTO> gameLevels = optionalList.get();
        log.debug("Request to get GameLevels: {}", gameLevels);

        LeaderBoard leaderBoard = new LeaderBoard();
        List<LeaderBoardDTO> leaderBoardDTOS = new ArrayList<>();
        int rank = 1;
        for (GameLevelDTO gameLevel : gameLevels) {
            Long playerId = gameLevel.getPlayer().getId();
            Optional<Player> playerOptional = playerRepository.findById(playerId);
            if (!playerOptional.isPresent()) {
                log.debug("Player not found for ID: {}", playerId);
                return null;
            }
            Player player = playerOptional.get();
            Long userId = player.getUser().getId();
            String firstName = player.getUser().getFirstName();
            String lastName = player.getUser().getLastName();
            leaderBoardDTOS.add(new LeaderBoardDTO(userId, firstName, lastName,
                rank, gameLevel.getScore()));
            rank++;
        }
        leaderBoard.setLeaderBoard(leaderBoardDTOS);

        return leaderBoard;
    }
}

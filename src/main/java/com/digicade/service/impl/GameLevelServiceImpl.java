package com.digicade.service.impl;

import com.digicade.domain.GameLevel;
import com.digicade.domain.Player;
import com.digicade.exceptions.UserNotFoundCustomException;
import com.digicade.repository.GameLevelRepository;
import com.digicade.repository.PlayerRepository;
import com.digicade.service.GameLevelService;
import com.digicade.service.PlayerService;
import com.digicade.service.dto.*;
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

    @Autowired
    private PlayerService playerService;

    public GameLevelServiceImpl(GameLevelRepository gameLevelRepository, GameLevelMapper gameLevelMapper) {
        this.gameLevelRepository = gameLevelRepository;
        this.gameLevelMapper = gameLevelMapper;
    }

    @Override
    public GameLevelDTO save(GameLevelDTO gameLevelDTO) {
        log.debug("Request to save GameLevel : {}", gameLevelDTO);

        Optional<GameLevel> optionalGameLevel = gameLevelRepository.findByGameIdAndPlayerId(
            gameLevelDTO.getGame().getId(),
            gameLevelDTO.getPlayer().getId()
        );

        if (optionalGameLevel.isPresent()) {
            GameLevel gameLevel = optionalGameLevel.get();
            gameLevelDTO.setXp(gameLevelDTO.getXp() + gameLevel.getXp());
            gameLevelDTO.setId(gameLevel.getId());
        }

        setPlayerRank(gameLevelDTO);

        GameLevel gameLevel = gameLevelMapper.toEntity(gameLevelDTO);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        gameLevel.setCreatedAt(timestamp);
        gameLevel = gameLevelRepository.save(gameLevel);

        // update Global rank and xp in Player table
        Optional<XPRankDTO> gameLevelOptional = gameLevelRepository.findXpAndRankByPlayerId(gameLevelDTO.getPlayer().getId());
        if (gameLevelOptional.isPresent()) {
            XPRankDTO xpRankDTO = gameLevelOptional.get();
            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setId(gameLevelDTO.getPlayer().getId());
            playerDTO.setLevel(xpRankDTO.getRank().intValue());
            playerDTO.setXp(xpRankDTO.getXp().intValue());

            playerService.partialUpdate(playerDTO);
        }

        return gameLevelMapper.toDto(gameLevel);
    }

    private void setPlayerRank(GameLevelDTO gameLevelDTO) {
        if (gameLevelDTO.getXp() >= 81) {
            gameLevelDTO.setLevel(1);
        } else if (gameLevelDTO.getXp() >= 61 && gameLevelDTO.getXp() <= 80) {
            gameLevelDTO.setLevel(2);
        } else if (gameLevelDTO.getXp() >= 41 && gameLevelDTO.getXp() <= 60) {
            gameLevelDTO.setLevel(3);
        } else if (gameLevelDTO.getXp() >= 21 && gameLevelDTO.getXp() <= 40) {
            gameLevelDTO.setLevel(4);
        } else {
            gameLevelDTO.setLevel(5);
        }
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
        if (filter.toUpperCase().equals("TODAY")) {
            Instant instant = Instant.now().minus(1, ChronoUnit.DAYS);
            Timestamp start = Timestamp.from(instant);
            Timestamp end = new Timestamp(System.currentTimeMillis());

            Optional<List<LeaderBoardDTO>> leaderBoardDTOOptional = gameLevelRepository.findLeaderBoardByGameIdByGameIdWithDateRange(
                id,
                start,
                end
            );
            if (!leaderBoardDTOOptional.isPresent()) {
                return null;
            }

            List<LeaderBoardDTO> leaderBoardDTO = leaderBoardDTOOptional.get();

            log.debug("Date Start: {}", start);
            log.debug("Date End: {}", end);

            return new LeaderBoard(leaderBoardDTO);
        } else if (filter.toUpperCase().equals("WEEK")) {
            Instant instant = Instant.now().minus(7, ChronoUnit.DAYS);
            Timestamp start = Timestamp.from(instant);
            Timestamp end = new Timestamp(System.currentTimeMillis());

            Optional<List<LeaderBoardDTO>> leaderBoardDTOOptional = gameLevelRepository.findLeaderBoardByGameIdByGameIdWithDateRange(
                id,
                start,
                end
            );
            if (!leaderBoardDTOOptional.isPresent()) {
                return null;
            }

            List<LeaderBoardDTO> leaderBoardDTO = leaderBoardDTOOptional.get();

            log.debug("Date Start: {}", start);
            log.debug("Date End: {}", end);

            return new LeaderBoard(leaderBoardDTO);
        } else if (filter.toUpperCase().equals("MONTH")) {
            Instant instant = Instant.now().minus(30, ChronoUnit.DAYS);
            Timestamp start = Timestamp.from(instant);
            Timestamp end = new Timestamp(System.currentTimeMillis());

            Optional<List<LeaderBoardDTO>> leaderBoardDTOOptional = gameLevelRepository.findLeaderBoardByGameIdByGameIdWithDateRange(
                id,
                start,
                end
            );
            if (!leaderBoardDTOOptional.isPresent()) {
                return null;
            }

            List<LeaderBoardDTO> leaderBoardDTO = leaderBoardDTOOptional.get();

            log.debug("Date Start: {}", start);
            log.debug("Date End: {}", end);

            return new LeaderBoard(leaderBoardDTO);
        } else if (filter.toUpperCase().equals("YEAR")) {
            Instant instant = Instant.now().minus(365, ChronoUnit.DAYS);
            Timestamp start = Timestamp.from(instant);
            Timestamp end = new Timestamp(System.currentTimeMillis());

            Optional<List<LeaderBoardDTO>> leaderBoardDTOOptional = gameLevelRepository.findLeaderBoardByGameIdByGameIdWithDateRange(
                id,
                start,
                end
            );
            if (!leaderBoardDTOOptional.isPresent()) {
                return null;
            }

            List<LeaderBoardDTO> leaderBoardDTO = leaderBoardDTOOptional.get();

            log.debug("Date Start: {}", start);
            log.debug("Date End: {}", end);

            return new LeaderBoard(leaderBoardDTO);
        } else if (filter.toUpperCase().equals("ALL")) {
            Optional<List<LeaderBoardDTO>> leaderBoardDTOOptional = gameLevelRepository.findLeaderBoardByGameId(id);
            if (!leaderBoardDTOOptional.isPresent()) {
                return null;
            }

            List<LeaderBoardDTO> leaderBoardDTO = leaderBoardDTOOptional.get();

            return new LeaderBoard(leaderBoardDTO);
        }
        return null;
    }
}

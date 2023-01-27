package com.digicade.service.impl;

import com.digicade.domain.PlayerGameBadge;
import com.digicade.repository.PlayerGameBadgeRepository;
import com.digicade.service.PlayerGameBadgeService;
import com.digicade.service.dto.PlayerGameBadgeDTO;
import com.digicade.service.mapper.PlayerGameBadgeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlayerGameBadge}.
 */
@Service
@Transactional
public class PlayerGameBadgeServiceImpl implements PlayerGameBadgeService {

    private final Logger log = LoggerFactory.getLogger(PlayerGameBadgeServiceImpl.class);

    private final PlayerGameBadgeRepository playerGameBadgeRepository;

    private final PlayerGameBadgeMapper playerGameBadgeMapper;

    public PlayerGameBadgeServiceImpl(PlayerGameBadgeRepository playerGameBadgeRepository, PlayerGameBadgeMapper playerGameBadgeMapper) {
        this.playerGameBadgeRepository = playerGameBadgeRepository;
        this.playerGameBadgeMapper = playerGameBadgeMapper;
    }

    @Override
    public PlayerGameBadgeDTO save(PlayerGameBadgeDTO playerGameBadgeDTO) {
        log.debug("Request to save PlayerGameBadge : {}", playerGameBadgeDTO);
        PlayerGameBadge playerGameBadge = playerGameBadgeMapper.toEntity(playerGameBadgeDTO);
        playerGameBadge = playerGameBadgeRepository.save(playerGameBadge);
        return playerGameBadgeMapper.toDto(playerGameBadge);
    }

    @Override
    public PlayerGameBadgeDTO update(PlayerGameBadgeDTO playerGameBadgeDTO) {
        log.debug("Request to update PlayerGameBadge : {}", playerGameBadgeDTO);
        PlayerGameBadge playerGameBadge = playerGameBadgeMapper.toEntity(playerGameBadgeDTO);
        playerGameBadge = playerGameBadgeRepository.save(playerGameBadge);
        return playerGameBadgeMapper.toDto(playerGameBadge);
    }

    @Override
    public Optional<PlayerGameBadgeDTO> partialUpdate(PlayerGameBadgeDTO playerGameBadgeDTO) {
        log.debug("Request to partially update PlayerGameBadge : {}", playerGameBadgeDTO);

        return playerGameBadgeRepository
            .findById(playerGameBadgeDTO.getId())
            .map(existingPlayerGameBadge -> {
                playerGameBadgeMapper.partialUpdate(existingPlayerGameBadge, playerGameBadgeDTO);

                return existingPlayerGameBadge;
            })
            .map(playerGameBadgeRepository::save)
            .map(playerGameBadgeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlayerGameBadgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PlayerGameBadges");
        return playerGameBadgeRepository.findAll(pageable).map(playerGameBadgeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlayerGameBadgeDTO> findOne(Long id) {
        log.debug("Request to get PlayerGameBadge : {}", id);
        return playerGameBadgeRepository.findById(id).map(playerGameBadgeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlayerGameBadge : {}", id);
        playerGameBadgeRepository.deleteById(id);
    }
}

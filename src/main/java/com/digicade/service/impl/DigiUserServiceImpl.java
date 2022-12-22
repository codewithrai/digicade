package com.digicade.service.impl;

import com.digicade.domain.DigiUser;
import com.digicade.domain.Player;
import com.digicade.repository.DigiUserRepository;
import com.digicade.repository.PlayerRepository;
import com.digicade.service.DigiUserService;
import com.digicade.service.dto.DigiUserDTO;
import com.digicade.service.mapper.DigiUserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DigiUser}.
 */
@Service
@Transactional
public class DigiUserServiceImpl implements DigiUserService {

    private final Logger log = LoggerFactory.getLogger(DigiUserServiceImpl.class);

    private final DigiUserRepository digiUserRepository;

    private final DigiUserMapper digiUserMapper;

    private final PlayerRepository playerRepository;

    public DigiUserServiceImpl(DigiUserRepository digiUserRepository, DigiUserMapper digiUserMapper, PlayerRepository playerRepository) {
        this.digiUserRepository = digiUserRepository;
        this.digiUserMapper = digiUserMapper;
        this.playerRepository = playerRepository;
    }

    @Override
    public DigiUserDTO save(DigiUserDTO digiUserDTO) {
        log.debug("Request to save DigiUser : {}", digiUserDTO);
        DigiUser digiUser = digiUserMapper.toEntity(digiUserDTO);
//        Long playerId = digiUserDTO.getPlayer().getId();
//        playerRepository.findById(playerId).ifPresent(digiUser::player);
        Player player = new Player();
        Player savedPlayer = playerRepository.save(player);
        Long playerId = savedPlayer.getId();
        playerRepository.findById(playerId).ifPresent(digiUser::player);
        digiUser = digiUserRepository.save(digiUser);
        return digiUserMapper.toDto(digiUser);
    }

    @Override
    public DigiUserDTO update(DigiUserDTO digiUserDTO) {
        log.debug("Request to update DigiUser : {}", digiUserDTO);
        DigiUser digiUser = digiUserMapper.toEntity(digiUserDTO);
        Long playerId = digiUserDTO.getPlayer().getId();
        playerRepository.findById(playerId).ifPresent(digiUser::player);
        digiUser = digiUserRepository.save(digiUser);
        return digiUserMapper.toDto(digiUser);
    }

    @Override
    public Optional<DigiUserDTO> partialUpdate(DigiUserDTO digiUserDTO) {
        log.debug("Request to partially update DigiUser : {}", digiUserDTO);

        return digiUserRepository
            .findById(digiUserDTO.getId())
            .map(existingDigiUser -> {
                digiUserMapper.partialUpdate(existingDigiUser, digiUserDTO);

                return existingDigiUser;
            })
            .map(digiUserRepository::save)
            .map(digiUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DigiUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DigiUsers");
        return digiUserRepository.findAll(pageable).map(digiUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DigiUserDTO> findOne(Long id) {
        log.debug("Request to get DigiUser : {}", id);
        return digiUserRepository.findById(id).map(digiUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DigiUser : {}", id);
        digiUserRepository.deleteById(id);
    }
}

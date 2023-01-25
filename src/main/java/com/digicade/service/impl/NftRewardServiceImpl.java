package com.digicade.service.impl;

import com.digicade.domain.NftReward;
import com.digicade.exceptions.UserNotFoundCustomException;
import com.digicade.repository.NftRewardRepository;
import com.digicade.repository.PlayerRepository;
import com.digicade.service.NftRewardService;
import com.digicade.service.PlayerNftRewardService;
import com.digicade.service.PlayerService;
import com.digicade.service.dto.NftRewardDTO;
import com.digicade.service.dto.PlayerDTO;
import com.digicade.service.dto.PlayerNftRewardDTO;
import com.digicade.service.mapper.NftRewardMapper;
import com.digicade.service.mapper.PlayerMapper;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NftReward}.
 */
@Service
@Transactional
public class NftRewardServiceImpl implements NftRewardService {

    private final Logger log = LoggerFactory.getLogger(NftRewardServiceImpl.class);

    private final NftRewardRepository nftRewardRepository;

    private final NftRewardMapper nftRewardMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private PlayerNftRewardService playerNftRewardService;

    @Autowired
    private PlayerService playerService;

    public NftRewardServiceImpl(NftRewardRepository nftRewardRepository, NftRewardMapper nftRewardMapper) {
        this.nftRewardRepository = nftRewardRepository;
        this.nftRewardMapper = nftRewardMapper;
    }

    @Override
    public NftRewardDTO save(NftRewardDTO nftRewardDTO) {
        log.debug("Request to save NftReward : {}", nftRewardDTO);
        NftReward nftReward = nftRewardMapper.toEntity(nftRewardDTO);
        nftReward = nftRewardRepository.save(nftReward);
        return nftRewardMapper.toDto(nftReward);
    }

    @Override
    public NftRewardDTO update(NftRewardDTO nftRewardDTO) {
        log.debug("Request to update NftReward : {}", nftRewardDTO);
        NftReward nftReward = nftRewardMapper.toEntity(nftRewardDTO);
        nftReward = nftRewardRepository.save(nftReward);
        return nftRewardMapper.toDto(nftReward);
    }

    @Override
    public Optional<NftRewardDTO> partialUpdate(NftRewardDTO nftRewardDTO) {
        log.debug("Request to partially update NftReward : {}", nftRewardDTO);

        return nftRewardRepository
            .findById(nftRewardDTO.getId())
            .map(existingNftReward -> {
                nftRewardMapper.partialUpdate(existingNftReward, nftRewardDTO);

                return existingNftReward;
            })
            .map(nftRewardRepository::save)
            .map(nftRewardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NftRewardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NftRewards");
        return nftRewardRepository.findAll(pageable).map(nftRewardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NftRewardDTO> findOne(Long id) {
        log.debug("Request to get NftReward : {}", id);
        return nftRewardRepository.findById(id).map(nftRewardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NftReward : {}", id);
        nftRewardRepository.deleteById(id);
    }

    @Override
    public String purchaseNftReward(Long playerId, Long rewardId) throws Exception {
        Optional<NftRewardDTO> optionalNftRewardDTO = nftRewardRepository.findById(rewardId).map(nftRewardMapper::toDto);
        Optional<PlayerDTO> optionalPlayerDTO = playerRepository.findById(playerId).map(playerMapper::toDto);

        if (!optionalNftRewardDTO.isPresent()) {
            throw new UserNotFoundCustomException("NFT reward not found with id: " + rewardId);
        }
        if (!optionalPlayerDTO.isPresent()) {
            throw new UserNotFoundCustomException("Player not found with id: " + playerId);
        }

        PlayerDTO playerDTO = optionalPlayerDTO.get();
        NftRewardDTO nftRewardDTO = optionalNftRewardDTO.get();

        Integer playerTix = playerDTO.getTix();
        Integer playerComp = playerDTO.getComp();

        Integer nftRewardTix = nftRewardDTO.getTix();
        Integer nftRewardComp = nftRewardDTO.getComp();

        if (playerTix >= nftRewardTix && playerComp >= nftRewardComp) {
            // SAVE: Player NFT Reward
            PlayerNftRewardDTO playerNftRewardDTO = new PlayerNftRewardDTO();
            playerNftRewardDTO.setNftReward(nftRewardDTO);
            playerNftRewardDTO.setPlayer(playerDTO);
            playerNftRewardDTO.setDate(LocalDate.now());
            playerNftRewardService.save(playerNftRewardDTO);

            // UPDATE: subtract tix and comp from player
            playerDTO.setTix(playerTix - nftRewardTix);
            playerDTO.setComp(playerComp - nftRewardComp);
            playerService.partialUpdate(playerDTO);

            return "NFT purchased";
        } else {
            return nftRewardTix + " Tix and " + nftRewardComp + " comp are required to purchase this NFT Reward";
        }
    }
}

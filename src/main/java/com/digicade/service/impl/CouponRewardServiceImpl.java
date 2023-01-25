package com.digicade.service.impl;

import com.digicade.domain.CouponReward;
import com.digicade.domain.enumeration.CouponStatus;
import com.digicade.exceptions.UserNotFoundCustomException;
import com.digicade.repository.CouponRewardRepository;
import com.digicade.repository.PlayerRepository;
import com.digicade.service.CouponRewardService;
import com.digicade.service.PlayerCouponRewardService;
import com.digicade.service.PlayerService;
import com.digicade.service.dto.CouponRewardDTO;
import com.digicade.service.dto.PlayerCouponRewardDTO;
import com.digicade.service.dto.PlayerDTO;
import com.digicade.service.mapper.CouponRewardMapper;
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
 * Service Implementation for managing {@link CouponReward}.
 */
@Service
@Transactional
public class CouponRewardServiceImpl implements CouponRewardService {

    private final Logger log = LoggerFactory.getLogger(CouponRewardServiceImpl.class);

    private final CouponRewardRepository couponRewardRepository;

    private final CouponRewardMapper couponRewardMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private PlayerCouponRewardService playerCouponRewardService;

    public CouponRewardServiceImpl(CouponRewardRepository couponRewardRepository, CouponRewardMapper couponRewardMapper) {
        this.couponRewardRepository = couponRewardRepository;
        this.couponRewardMapper = couponRewardMapper;
    }

    @Override
    public CouponRewardDTO save(CouponRewardDTO couponRewardDTO) {
        log.debug("Request to save CouponReward : {}", couponRewardDTO);
        CouponReward couponReward = couponRewardMapper.toEntity(couponRewardDTO);
        couponReward = couponRewardRepository.save(couponReward);
        return couponRewardMapper.toDto(couponReward);
    }

    @Override
    public CouponRewardDTO update(CouponRewardDTO couponRewardDTO) {
        log.debug("Request to update CouponReward : {}", couponRewardDTO);
        CouponReward couponReward = couponRewardMapper.toEntity(couponRewardDTO);
        couponReward = couponRewardRepository.save(couponReward);
        return couponRewardMapper.toDto(couponReward);
    }

    @Override
    public Optional<CouponRewardDTO> partialUpdate(CouponRewardDTO couponRewardDTO) {
        log.debug("Request to partially update CouponReward : {}", couponRewardDTO);

        return couponRewardRepository
            .findById(couponRewardDTO.getId())
            .map(existingCouponReward -> {
                couponRewardMapper.partialUpdate(existingCouponReward, couponRewardDTO);

                return existingCouponReward;
            })
            .map(couponRewardRepository::save)
            .map(couponRewardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponRewardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CouponRewards");
        return couponRewardRepository.findAll(pageable).map(couponRewardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CouponRewardDTO> findOne(Long id) {
        log.debug("Request to get CouponReward : {}", id);
        return couponRewardRepository.findById(id).map(couponRewardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CouponReward : {}", id);
        couponRewardRepository.deleteById(id);
    }

    @Override
    public String purchaseCouponReward(Long playerId, Long rewardId) throws Exception {
        Optional<CouponRewardDTO> optionalCouponRewardDTO = couponRewardRepository.findById(rewardId).map(couponRewardMapper::toDto);
        Optional<PlayerDTO> optionalPlayerDTO = playerRepository.findById(playerId).map(playerMapper::toDto);

        if (!optionalCouponRewardDTO.isPresent()) {
            throw new UserNotFoundCustomException("Coupon reward not found with id: " + rewardId);
        }
        if (!optionalPlayerDTO.isPresent()) {
            throw new UserNotFoundCustomException("Player not found with id: " + playerId);
        }

        PlayerDTO playerDTO = optionalPlayerDTO.get();
        CouponRewardDTO couponRewardDTO = optionalCouponRewardDTO.get();

        Integer playerTix = playerDTO.getTix();
        Integer playerComp = playerDTO.getComp();

        Integer couponRewardTix = couponRewardDTO.getTix();
        Integer couponRewardComp = couponRewardDTO.getComp();

        if (playerTix >= couponRewardTix && playerComp >= couponRewardComp) {
            int compareValue = couponRewardDTO.getExpiry().compareTo(LocalDate.now());
            if (compareValue >= 0) {
                // SAVE: Player Coupon Reward
                PlayerCouponRewardDTO playerCouponRewardDTO = new PlayerCouponRewardDTO();
                playerCouponRewardDTO.setCouponReward(couponRewardDTO);
                playerCouponRewardDTO.setPlayer(playerDTO);
                playerCouponRewardDTO.setStatus(CouponStatus.REDEEMED);
                playerCouponRewardDTO.setDate(LocalDate.now());
                playerCouponRewardService.save(playerCouponRewardDTO);

                // UPDATE: subtract tix and comp from player
                playerDTO.setTix(playerTix - couponRewardTix);
                playerDTO.setComp(playerComp - couponRewardComp);
                playerService.partialUpdate(playerDTO);
            } else {
                return "Coupon Reward expires";
            }

            return "Coupon purchased";
        } else {
            return couponRewardTix + " Tix and " + couponRewardComp + " comp are required to purchase this Coupon Reward";
        }
    }
}
